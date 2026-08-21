# Booking Feature

State machine is defined in `docs/domain-model.md` — read that first.

## Request a viewing
**As a Buyer/Renter, I can request a viewing for a listing on a given
date/time.**

- Frontend pre-checks the requested slot looks available before submitting.
- Input: userId, listingId, requestedDateTime.
- Validate: user is a buyer, the listing isn't the buyer's own, listing
  is active.
- Duplicate rule: if the same buyer already has a REQUESTED booking for
  the same listing, block the new request — they must cancel the
  existing one first. This rule applies only to REQUESTED status, not
  to other states.
- Validate the requested time falls within the seller's declared
  availability for that listing.
- Create Booking with status REQUESTED.
- Respond, notify both buyer and seller (async).

## Approve or reject a booking
**As a Seller/Landlord, I can approve or reject a booking request.**

- Input: userId, bookingId.
- Validate: user is a seller, the booking's listing belongs to that seller.
- Apply the state transition (must be REQUESTED → APPROVED or
  REQUESTED → REJECTED).
- Respond, notify the buyer (async).

## Buyer cancels a booking
**Buyer can cancel a REQUESTED or APPROVED booking.**

- Input: userId, bookingId.
- Validate: user is a buyer, booking exists, booking belongs to that buyer.
- Validate the transition is allowed by the state machine
  (REQUESTED → CANCELLED or APPROVED → CANCELLED).
- Update, respond, notify both buyer and seller (async).

## Seller completes or rejects an approved booking
**As a Seller/Landlord, I can complete an approved booking; a seller can
also reject an already-approved one (APPROVED → REJECTED).**

- Input: userId, bookingId.
- Validate: booking exists, user is a seller, the booking's listing
  belongs to that seller.
- Validate the transition is allowed by the state machine.
- Update, notify (async).

## Notes
- All state-changing UI restricts the buyer/seller to only the transitions
  they're allowed — the backend re-validates regardless, never trusts
  the frontend's assumed current state.
- Authorization (role + ownership check) is a separate step from the
  transition-validity check — keep them as distinct pieces of logic,
  not one combined conditional.