# Progress

Update this file whenever a feature moves state — Claude Code reads it
at the start of every session to know where things stand.

| Area | Status |
|---|---|
| Domain model & entities | Done — entity classes generated |
| Listing CRUD (create/edit/activate/deactivate) | Done |
| Listing view (single + paginated browse) | Done |
| Photo add/delete | Wired end to end, but blocked on real S3 — see below |
| Booking request/approve/reject/cancel/complete | Not started |
| Payment (Stripe Checkout + webhook) | Not started |

## Currently working on
Create-listing is implemented end to end: `POST /api/listings`, gated to
SELLER role via `@PreAuthorize`, seller id from the JWT principal,
`CreateListingRequest` (availability windows as a polymorphic
RECURRING/SPECIFIC request DTO mirroring the entity's single-table
inheritance), `AvailabilityWindowValidator` for cross-field checks,
`ListingRepository` added (first repository in the `listing` package —
`PhotoRepository`/`AvailabilityWindowRepository` not added yet, not needed
until their own features land). Photo upload was explicitly deferred to
the separate "Add photos" endpoint — this request has no photos field.
`GlobalExceptionHandler` gained handlers for `AccessDeniedException` (403,
the `@PreAuthorize` failure path) and `InvalidAvailabilityWindowException`
(400).

Edit-listing is now implemented end to end: `PUT /api/listings/{listingId}`,
gated to SELLER role via `@PreAuthorize`. `UpdateListingRequest` mirrors
`CreateListingRequest` field-for-field (still no photos field — photos stay
on their own endpoints) and reuses the existing polymorphic
`AvailabilityWindowRequest` hierarchy as-is. Ownership authorization is
its own component, `ListingOwnershipValidator` (validator/), kept separate
from `ListingServiceImpl` per the "authorization is a separate concern
from persistence logic" rule — it throws `UnauthorizedActionException`
(already handled by `GlobalExceptionHandler`, 403) if the listing's seller
doesn't match the caller. `ListingMapToMapper` gained `updateFromRequest`,
which mutates the managed `Listing` in place rather than building a new
entity: scalar fields are set directly, and the availability-window
collection is cleared and refilled (not replaced by reference) so
Hibernate's `orphanRemoval` on `Listing.availabilityWindows` actually
deletes the dropped rows instead of leaving them orphaned outside the
tracked collection. Response mapping follows the same one-mapper-per-
response-type split as create (`UpdateListingResponseMapToMapper`).

Activate/deactivate listing: `PATCH /api/listings/{listingId}/activate`
and `.../deactivate`, SELLER + `ListingOwnershipValidator`-gated, sharing
one `ListingStatusResponse`/`ListingStatusResponseMapToMapper` pair.
Idempotent — no error re-activating an already-active listing (no state
machine here, unlike bookings).

View listing: `GET /api/listings/{listingId}` (single, public, any
status — a direct link/owner preview still works) and
`GET /api/listings?page=&size=` (paginated, public, `ACTIVE` only via
`ListingRepository.findByStatus`). `ListingResponse` composes
`PhotoResponse` and a new polymorphic `AvailabilityWindowResponse`
hierarchy (`RecurringAvailabilityWindowResponse`/
`SpecificAvailabilityWindowResponse`) that mirrors the request-side
shape; `AvailabilityWindowResponseMapToMapper` mirrors
`AvailabilityWindowMapToMapper` in the entity→DTO direction. Pagination
uses Spring Data's `Page<T>` directly inside `ApiResponse<T>` — no custom
pagination wrapper. `SecurityConfig` was tightened: `/api/listings` and
`/api/listings/*` are `permitAll()` for **GET only** now (previously the
whole exact `/api/listings` path was unconditionally `permitAll()`, which
under-protected `POST` at the filter-chain level even though
`@PreAuthorize` still caught it).

Photo add/delete: `POST /api/listings/{listingId}/photos` (multipart,
multiple files) and `DELETE /api/listings/{listingId}/photos/{photoId}`,
both SELLER + ownership + a new `ListingActiveValidator`-gated (listing
must be ACTIVE; violation → new `ListingNotActiveException`, 409). Photo
storage is behind a new extension-point interface,
`common/storage/service/StorageService`
(`uploadAll(List<MultipartFile>)`, `delete(String url)`), so
`PhotoServiceImpl` never depends on a concrete provider. The only
implementation, `S3StorageService`, is a stub — both methods currently
throw `UnsupportedOperationException`; real AWS SDK calls are still
outstanding, so the add-photos endpoint will 500 until that lands. No
`PhotoRepository` was added despite `package-structure.md` reserving the
filename — add/delete mutate the already-cascading
`Listing.photos` collection (`cascade=ALL, orphanRemoval=true`) instead,
same pattern as `availabilityWindows`; a standalone repository had no
query to justify it yet.

Next up: real S3 wiring for `S3StorageService`, then booking
request/approve/reject/cancel/complete.

## Log
- Requirements, feature-by-feature business rules, and booking state
  machine finalized.
- Stripe integration style decided: Checkout (hosted page) + webhooks
  for confirmation, deposit = percentage of listing price.
- Location: kept as a plain `city` String field on Listing — no shared/
  dedup entity, intentionally simple.
- Availability: single-table inheritance (AvailabilityWindow base +
  RecurringAvailabilityWindow + SpecificAvailabilityWindow) to support
  both recurring and one-off windows without nullable-field mess.
- Generated all entities (User, Listing, Photo, AvailabilityWindow +
  subtypes, Booking, Payment) and their enums (Role, PropertyType,
  ListingStatus, BookingStatus, PaymentStatus), placed under the
  corrected feature-based package structure
  (`com.suraj.realestate.<feature>.entity` / `.enums`) per
  docs/package-structure.md. Compiles cleanly.