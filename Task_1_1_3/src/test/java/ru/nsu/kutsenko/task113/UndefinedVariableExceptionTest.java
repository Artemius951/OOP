package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UndefinedVariableExceptionTest {

    @Test
    void testUndefinedVariableExceptionWithVariableName() {
        UndefinedVariableException exception = new UndefinedVariableException("x");
        assertEquals("Evaluation error: Undefined variable: 'x'", exception.getMessage());
    }

    @Test
    void testUndefinedVariableExceptionWithVariableNameAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        UndefinedVariableException exception = new UndefinedVariableException("y", cause);
        assertEquals("Evaluation error: Undefined variable: 'y'", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testUndefinedVariableExceptionThrownInVariableEval() {
        Variable var = new Variable("undefinedVar");
        try {
            var.eval(java.util.Map.of("x", 10));
        } catch (UndefinedVariableException e) {
            assertEquals("Evaluation error: Undefined variable: 'undefinedVar'", e.getMessage());
        }
    }
}