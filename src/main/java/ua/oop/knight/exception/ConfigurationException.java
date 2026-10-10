/*
 * ConfigurationException.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.exception;

/**
 * Thrown when an initialization file is missing, unreadable or contains
 * invalid data.
 */
public class ConfigurationException extends KnightException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with the given message.
     *
     * @param message description of the problem
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the given message and cause.
     *
     * @param message description of the problem
     * @param cause   original exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
