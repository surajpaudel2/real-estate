# Domain Model

## Entities (settled)

### User
- id
- name
- email
- roles: `Set<Role>` — a user can hold multiple roles (BUYER, SELLER).
  Chosen over a single `Role` field so a user isn't locked into one role,
  and without adding role-specific fields to User itself — if a role ever
  needs extra fields later, that becomes a separate linked entity
  (e.g. SellerProfile), not a change to User.

### Listing
- id
- propertyType: enum (see below)
- price
- city: String (plain field, no dedup/shared Location entity — kept
  intentionally simple)
- bedrooms
- bathrooms
- description
- photos: One-to-Many → Photo
- availableParkings
- status: enum [ACTIVE, DEACTIVE]
- seller: Many-to-One → User
- availabilityWindows: One-to-Many → AvailabilityWindow (see below)

### Photo
- id
- uploadDate
- url (S3)
- listing: Many-to-One → Listing (required for the One-to-Many above to map)

### AvailabilityWindow
Supports both recurring rules ("every Monday 9am–5pm") and specific
one-off date-time ranges ("Dec 24, 10am–2pm"). Implemented as JPA
single-table inheritance rather than nullable fields on one entity —
both subtypes are fully substitutable for the base type (LSP), and
adding a third kind later (e.g. a blackout window) means one new
subclass, no changes to callers.

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "availability_type")
public abstract class AvailabilityWindow {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id")
    private Listing listing;

    private LocalTime startTime;
    private LocalTime endTime;

    public abstract boolean covers(LocalDate date, LocalTime requestedStart, LocalTime requestedEnd);
}

@Entity
@DiscriminatorValue("RECURRING")
public class RecurringAvailabilityWindow extends AvailabilityWindow {
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    // covers(): date.getDayOfWeek() == this.dayOfWeek && time within [startTime, endTime]
}

@Entity
@DiscriminatorValue("SPECIFIC")
public class SpecificAvailabilityWindow extends AvailabilityWindow {
    private LocalDate date;
    // covers(): date == this.date && time within [startTime, endTime]
}
```

At the DB level this is one physical table with a discriminator column
and both subtypes' columns side by side (nullable at the DB level only —
never nullable in the Java model). The booking validator never needs to
know which subtype it's dealing with:
`listing.getAvailabilityWindows().stream().anyMatch(w -> w.covers(date, start, end))`

### Booking
- id
- listing: Many-to-One → Listing
- buyer: Many-to-One → User
- requestedDate
- requestedTime
- status: enum [REQUESTED, APPROVED, REJECTED, COMPLETED, CANCELLED]

### Payment
- id
- depositAmount — computed as `listing.price * depositPercentage` at
  Checkout Session creation time, then persisted as a snapshot (never
  recalculated later even if listing price changes afterward)
- buyer: Many-to-One → User
- booking: Many-to-One → Booking (many, not one-to-one — a failed payment
  can be retried, producing multiple Payment rows for one booking)
- status: enum [PENDING, SUCCESS, FAILED]
- stripeSessionId — required so an incoming webhook event can be
  correlated back to the right Payment row

## Enums

```java
public enum PropertyType {
    APARTMENT, HOUSE, TOWNHOUSE, CONDO, STUDIO, LAND, COMMERCIAL
}

public enum ListingStatus {
    ACTIVE, DEACTIVE
}

public enum BookingStatus {
    REQUESTED, APPROVED, REJECTED, COMPLETED, CANCELLED
}

public enum PaymentStatus {
    PENDING, SUCCESS, FAILED
}

public enum Role {
    BUYER, SELLER
}
```

## Booking State Machine

| From | To | By |
|---|---|---|
| REQUESTED | APPROVED | Seller |
| REQUESTED | REJECTED | Seller |
| REQUESTED | CANCELLED | Buyer |
| APPROVED | COMPLETED | Seller |
| APPROVED | CANCELLED | Buyer |
| APPROVED | REJECTED | Seller |
| COMPLETED / CANCELLED / REJECTED | — | terminal, no further transitions |

Implement this as a `BookingTransitionValidator` (or similar): a lookup of
`(currentState, actorRole) -> allowedNextStates`, not an if/else chain
inside `BookingService`.

## Relationships & JPA mapping notes

- **Listing ↔ Photo**: One-to-Many, cascade + orphanRemoval on the Listing
  side (photos have no meaning without their listing).
- **Listing ↔ Booking**: Many-to-One from Booking only (unidirectional).
  No cascade-delete — deleting/deactivating a listing must never wipe
  booking history.
- **Booking ↔ Payment**: Many-to-One from Payment (not One-to-One — see
  Payment notes above).
- **User ↔ Listing/Booking**: Many-to-One from the owning side only
  (`Listing.seller`, `Booking.buyer`). No back-collections on User unless
  a real navigation need shows up — prefer repository queries.