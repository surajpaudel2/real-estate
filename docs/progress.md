# Progress

Update this file whenever a feature moves state — Claude Code reads it
at the start of every session to know where things stand.

| Area | Status |
|---|---|
| Domain model & entities | Done — entity classes generated |
| Listing CRUD (create/edit/deactivate) | Not started |
| Photo add/delete | Not started |
| Booking request/approve/reject/cancel/complete | Not started |
| Payment (Stripe Checkout + webhook) | Not started |

## Currently working on
Entities are complete (User, Listing, Photo, AvailabilityWindow +
RecurringAvailabilityWindow/SpecificAvailabilityWindow, Booking, Payment,
plus their enums), placed in the feature-based package structure. Next
up: repositories.

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