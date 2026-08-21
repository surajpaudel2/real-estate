package com.suraj.realestate.payment.enums;

/**
 * State of a {@link com.suraj.realestate.payment.entity.Payment}, driven by
 * Stripe webhook events.
 */
public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED
}
