# Real Estate Marketplace — SOLID Practice Project

Side project (not the primary portfolio project) built specifically to
practice SOLID principles in a Spring Boot + JPA codebase. Correctness
matters, but the point of this project is clean separation of concerns —
check every design decision against SRP/OCP/LSP/ISP/DIP before writing it.

See @docs/domain-model.md for entities, relationships, and the booking
state machine — this applies to almost every feature, read it first.

See @docs/package-structure.md for the full feature-based package layout
(entity, enums, repository, service, service/impl, controller, dto,
mapper, validator, and extension-point folders like service/gateway).
Read this before generating ANY code, not just entities — it defines
where every layer goes as the project grows.

See @docs/progress.md for current status — check this before starting
work each session, and update it when a feature is finished.

Feature-specific business rules live in docs/features/ — read the
relevant file before implementing that feature:
- docs/features/listing.md — create/edit/deactivate listings, photo add/delete
- docs/features/booking.md — request/approve/reject/cancel/complete, state machine
- docs/features/payment.md — Stripe Checkout + webhook flow

## Tech Stack
- Spring Boot, Spring Data JPA, MySQL
- Lombok — but never `@Data`. Use explicit `@Getter`, `@Setter`,
  `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder` as needed.
  `@Data` generates equals/hashCode/toString you don't always want on
  JPA entities, and hides what's actually being generated.
- Stripe (Checkout + webhooks) for payments

## Conventions
- Package structure is feature-based, not layer-based across the whole
  app — but each feature package has its own layer subfolders internally.
  Full layout is in docs/package-structure.md — follow it exactly.
  Each enum lives inside the feature package it belongs to, not a
  shared `common/enums` dumping ground — this is what makes the
  packaging actually feature-based rather than just relabeled layers.
- Entities are pure data + JPA mappings only. No business logic, no
  validation, no authorization checks inside entity classes.
- Business logic and validation live in the service layer. Role/ownership
  authorization is a separate concern from persistence logic — don't
  inline it into the same method that also does the actual work.
- Extension points (payment methods, notification channels, booking
  conflict rules) are interfaces with concrete implementations, designed
  before the service that consumes them — not retrofitted after.
- Document public classes and non-obvious methods with proper Javadoc.
  Don't add comments that just restate what the code already says.
- Enums are stored as STRING in the database (`@Enumerated(EnumType.STRING)`),
  never ORDINAL.

## What NOT to do
- Don't add fields, entities, or features beyond what's specified in
  docs/features/ and docs/domain-model.md without checking first — this
  project is deliberately scope-limited.
- Don't put booking-availability or conflict-resolution logic on the
  Listing entity itself — that belongs in the booking service layer.
- If a task needs a package or folder not listed in
  docs/package-structure.md, STOP and ask before creating it. Don't
  invent a reasonable-looking structure on the fly — package layout is
  a deliberate decision, not something that should drift mid-task.