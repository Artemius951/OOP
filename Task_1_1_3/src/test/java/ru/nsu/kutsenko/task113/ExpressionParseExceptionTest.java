package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ExpressionParseExceptionTest {

    @Test
    void testExpressionParseExceptionWithMessage() {
        ExpressionParseException exception = new ExpressionParseException("Parse error message");
        assertEquals("Parse error: Parse error message", exception.getMessage());
    }

    @Test
    void testExpressionParseExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        ExpressionParseException exception = new ExpressionParseException("Parse error message", cause);
        assertEquals("Parse error: Parse error message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}