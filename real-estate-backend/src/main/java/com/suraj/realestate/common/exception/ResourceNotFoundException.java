package com.suraj.realestate.common.exception;

/**
 * Thrown when a requested entity (listing, booking, etc.) doesn't exist,
 * whether never created or not visible to the caller.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
