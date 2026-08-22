package com.suraj.realestate.common.exception;

/**
 * Thrown when an authenticated user attempts an action they don't have
 * the role or ownership to perform (e.g. a buyer approving a booking,
 * a seller editing another seller's listing).
 */
public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
