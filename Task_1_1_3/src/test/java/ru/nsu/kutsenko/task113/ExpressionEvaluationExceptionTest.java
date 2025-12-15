package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExpressionEvaluationExceptionTest {

    @Test
    void testExpressionEvaluationExceptionWithMessage() {
        ExpressionEvaluationException exception =
            new ExpressionEvaluationException("Evaluation error message");
        assertEquals("Evaluation error: Evaluation error message",
            exception.getMessage());
    }

    @Test
    void testExpressionEvaluationExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        ExpressionEvaluationException exception =
            new ExpressionEvaluationException("Evaluation error message", cause);
        assertEquals("Evaluation error: Evaluation error message",
            exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}