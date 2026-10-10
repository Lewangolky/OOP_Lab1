/*
 * ConfigurationExceptionTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ConfigurationExceptionTest {

    @Test
    void messageIsKept() {
        ConfigurationException e = new ConfigurationException("no file");

        assertEquals("no file", e.getMessage());
    }

    @Test
    void messageAndCauseAreKept() {
        IOException cause = new IOException("disk");

        ConfigurationException e = new ConfigurationException("no file",
                cause);

        assertEquals("no file", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    void configurationExceptionIsKnightException() {
        assertInstanceOf(KnightException.class,
                new ConfigurationException("x"));
    }
}
