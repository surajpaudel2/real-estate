# Package Structure

Feature-based packages, not layer-based. Each feature package contains
its own entity/repository/service/controller/dto/mapper/validator
subfolders — a feature's whole vertical slice lives together.

```
com.realestate
├── RealEstateApplication.java
│
├── common/
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java   (@ControllerAdvice)
│   │   ├── ResourceNotFoundException.java
│   │   └── UnauthorizedActionException.java
│   ├── config/
│   │   └── (app-wide config: AsyncConfig, security config, etc. —
│   │        NOT feature-specific config, that stays in the feature)
│   └── storage/
│       ├── service/
│       │   ├── StorageService.java        (interface — extension point)
│       │   └── impl/
│       │       └── S3StorageService.java
│
├── user/
│   ├── entity/
│   │   └── User.java
│   ├── enums/
│   │   └── Role.java
│   ├── repository/
│   │   └── UserRepository.java
│   ├── service/
│   │   ├── UserService.java
│   │   └── impl/
│   │       └── UserServiceImpl.java
│   ├── controller/
│   │   └── UserController.java
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   └── mapper/
│       └── UserMapper.java
│
├── listing/
│   ├── entity/
│   │   ├── Listing.java
│   │   ├── Photo.java
│   │   ├── AvailabilityWindow.java
│   │   ├── RecurringAvailabilityWindow.java
│   │   └── SpecificAvailabilityWindow.java
│   ├── enums/
│   │   ├── PropertyType.java
│   │   └── ListingStatus.java
│   ├── repository/
│   │   ├── ListingRepository.java
│   │   ├── PhotoRepository.java
│   │   └── AvailabilityWindowRepository.java
│   ├── service/
│   │   ├── ListingService.java
│   │   ├── PhotoService.java
│   │   └── impl/
│   │       ├── ListingServiceImpl.java
│   │       └── PhotoServiceImpl.java
│   ├── controller/
│   │   └── ListingController.java
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   ├── mapper/
│   │   └── ListingMapper.java
│   └── validator/
│       └── ListingOwnershipValidator.java
│
├── booking/
│   ├── entity/
│   │   └── Booking.java
│   ├── enums/
│   │   └── BookingStatus.java
│   ├── repository/
│   │   └── BookingRepository.java
│   ├── service/
│   │   ├── BookingService.java
│   │   └── impl/
│   │       └── BookingServiceImpl.java
│   ├── controller/
│   │   └── BookingController.java
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   ├── mapper/
│   │   └── BookingMapper.java
│   └── validator/
│       └── BookingTransitionValidator.java   (state machine + role rules)
│
├── payment/
│   ├── entity/
│   │   └── Payment.java
│   ├── enums/
│   │   └── PaymentStatus.java
│   ├── repository/
│   │   └── PaymentRepository.java
│   ├── service/
│   │   ├── PaymentService.java
│   │   ├── gateway/
│   │   │   ├── PaymentGateway.java        (interface — extension point)
│   │   │   └── StripePaymentGateway.java
│   │   └── impl/
│   │       └── PaymentServiceImpl.java
│   ├── controller/
│   │   ├── PaymentController.java
│   │   └── StripeWebhookController.java
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   └── mapper/
│       └── PaymentMapper.java
│
└── notification/
    ├── service/
    │   ├── NotificationSender.java        (interface — extension point)
    │   └── impl/
    │       ├── EmailNotificationSender.java
    │       └── InAppNotificationSender.java
    ├── event/
    │   └── (domain events, e.g. BookingApprovedEvent, PaymentSucceededEvent)
    └── listener/
        └── (@EventListener classes reacting to booking/payment events,
             calling NotificationSender — async via @Async)
```

## What goes where

- **entity/** — pure JPA entities. No logic, no validation.
- **enums/** — enums owned by that feature (BookingStatus lives in
  booking, not in a shared enums package).
- **repository/** — Spring Data JPA interfaces only.
- **service/** — interface + `impl/` subfolder for the implementation.
  Controllers and other services depend on the interface, never the impl
  directly (DIP).
- **service/gateway/** or a feature's own extension-point folder — for
  interfaces meant to have multiple swappable implementations
  (`PaymentGateway`, `NotificationSender`, `StorageService`). These are
  the actual OCP/DIP practice points — design the interface before the
  implementation.
- **controller/** — thin, delegates to services. No business logic here.
- **dto/request/** and **dto/response/** — never expose entities directly
  over the API.
- **mapper/** — entity ↔ DTO conversion, kept out of both the entity and
  the service.
- **validator/** — authorization and business-rule checks that don't
  belong inside the main service method (e.g. `BookingTransitionValidator`,
  `ListingOwnershipValidator`).

## Rule: don't invent packages silently

If a task needs a package or folder not listed above, stop and ask
before creating it. Don't guess a reasonable-looking structure and
create it unprompted — this file is the source of truth for layout,
and it should be updated deliberately, not drift from ad-hoc decisions
made mid-task.