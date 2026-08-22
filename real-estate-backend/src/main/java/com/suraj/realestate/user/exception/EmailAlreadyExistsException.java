package com.suraj.realestate.user.exception;

/** Thrown when registration is attempted with an email already in use. */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}