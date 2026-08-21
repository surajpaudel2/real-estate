# Payment Feature

**As a Buyer/Renter, I can pay a deposit to secure an APPROVED booking.**

Integration: Stripe Checkout (hosted page) + webhooks for confirmation.
Never trust a frontend "payment succeeded" call directly — the webhook,
signature-verified, is the only source of truth for payment status.

## Deposit amount
`depositAmount = listing.price * depositPercentage`, where
`depositPercentage` is a config value (e.g. `deposit.percentage=0.05` in
application.yml). Computed once when the Checkout Session is created,
then persisted on Payment as a snapshot — never recalculated later even
if the listing price changes afterward.

## Flow
1. **Buyer initiates payment** — input: userId, bookingId. Validate:
   user is the buyer, booking belongs to that buyer, booking status is
   APPROVED.
2. **Duplicate check** — no existing PENDING or SUCCESS Payment already
   exists for this booking.
3. **Create Stripe Checkout Session** — amount = computed deposit,
   success_url, cancel_url, metadata containing bookingId (so the
   webhook can correlate back later).
4. **Persist Payment** as PENDING, storing depositAmount, buyer, booking,
   and the Stripe session id.
5. **Redirect buyer** to the Checkout Session URL.
6. **Stripe sends webhook event** — verify the Stripe signature header
   against the webhook secret before doing anything else.
7. **Idempotency** — Stripe can redeliver the same event. Either track
   processed event ids, or make the status update itself idempotent
   (updating an already-SUCCESS Payment to SUCCESS again is a no-op).
8. **Update Payment status + notify**:
    - `checkout.session.completed` → SUCCESS
    - `checkout.session.expired` or `payment_intent.payment_failed` → FAILED
    - Notify both buyer and seller (async).