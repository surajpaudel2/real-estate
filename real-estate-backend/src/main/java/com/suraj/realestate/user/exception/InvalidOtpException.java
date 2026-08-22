package com.suraj.realestate.user.exception;

/**
 * Thrown when a submitted OTP doesn't match or has expired. One
 * exception for both cases — no security reason to distinguish which,
 * unlike the login credentials/active split.
 */
public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String message) {
        super(message);
    }
}