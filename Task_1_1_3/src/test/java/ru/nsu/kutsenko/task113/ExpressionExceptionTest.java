package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ExpressionExceptionTest {

    @Test
    void testExpressionExceptionWithMessage() {
        ExpressionException exception = new ExpressionException("Test message");
        assertEquals("Test message", exception.getMessage());
    }

    @Test
    void testExpressionExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        ExpressionException exception = new ExpressionException("Test message", cause);
        assertEquals("Test message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
