package com.suraj.realestate.listing.exception;

/**
 * Thrown when an operation that requires an ACTIVE listing (e.g. adding or
 * deleting a photo) is attempted on a DEACTIVE one.
 */
public class ListingNotActiveException extends RuntimeException {
    public ListingNotActiveException(String message) {
        super(message);
    }
}
