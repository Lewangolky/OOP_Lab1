/*
 * OverloadExceptionTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

class OverloadExceptionTest {

    @Test
    void messageIsKept() {
        assertEquals("too heavy",
                new OverloadException("too heavy").getMessage());
    }

    @Test
    void overloadExceptionIsKnightException() {
        assertInstanceOf(KnightException.class, new OverloadException("x"));
    }
}
