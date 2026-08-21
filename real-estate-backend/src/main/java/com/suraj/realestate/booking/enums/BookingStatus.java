package com.suraj.realestate.booking.enums;

/**
 * State of a {@link com.suraj.realestate.booking.entity.Booking} in the
 * booking state machine. See {@code docs/domain-model.md} for the allowed
 * transitions between these states.
 */
public enum BookingStatus {
    REQUESTED,
    APPROVED,
    REJECTED,
    COMPLETED,
    CANCELLED
}
