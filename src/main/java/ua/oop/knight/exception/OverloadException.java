/*
 * OverloadException.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.exception;

/**
 * Thrown when a knight is given more ammunition than he can carry.
 */
public class OverloadException extends KnightException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with the given message.
     *
     * @param message description of the problem
     */
    public OverloadException(String message) {
        super(message);
    }
}
