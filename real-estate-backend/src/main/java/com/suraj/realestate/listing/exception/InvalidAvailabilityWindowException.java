package com.suraj.realestate.listing.exception;

/**
 * Thrown when a listing's availability windows fail a cross-field business
 * rule that bean validation on the request DTO can't express (e.g. a
 * window's start time is not before its end time).
 */
public class InvalidAvailabilityWindowException extends RuntimeException {
    public InvalidAvailabilityWindowException(String message) {
        super(message);
    }
}
