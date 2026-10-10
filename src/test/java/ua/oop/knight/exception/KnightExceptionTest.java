/*
 * KnightExceptionTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class KnightExceptionTest {

    @Test
    void messageIsKept() {
        KnightException e = new KnightException("broken");

        assertEquals("broken", e.getMessage());
        assertNull(e.getCause());
    }

    @Test
    void messageAndCauseAreKept() {
        IOException cause = new IOException("disk");

        KnightException e = new KnightException("broken", cause);

        assertEquals("broken", e.getMessage());
        assertSame(cause, e.getCause());
    }

    @Test
    void knightExceptionIsUnchecked() {
        assertInstanceOf(RuntimeException.class, new KnightException("x"));
    }
}
