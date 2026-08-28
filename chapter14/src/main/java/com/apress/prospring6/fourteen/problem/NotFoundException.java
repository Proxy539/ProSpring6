package com.apress.prospring6.fourteen.problem;

/**
 * Raised when a requested entity does not exist. Handled by {@link GlobalExceptionHandler}
 * and rendered as an HTTP 404.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
