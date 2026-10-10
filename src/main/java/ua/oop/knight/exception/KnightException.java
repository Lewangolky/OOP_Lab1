/*
 * KnightException.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.exception;

/**
 * Base class for the expected failures of the application, which are
 * reported to the user instead of terminating it with a stack trace.
 */
public class KnightException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with the given message.
     *
     * @param message description of the problem
     */
    public KnightException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the given message and cause.
     *
     * @param message description of the problem
     * @param cause   original exception
     */
    public KnightException(String message, Throwable cause) {
        super(message, cause);
    }
}
