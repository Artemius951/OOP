package ru.nsu.kutsenko.task121;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса GParsException.
 */
public class GParsExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Test error message";
        GParsException exception = new GParsException(errorMessage);
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndNullCause() {
        String errorMessage = "Test error message with null cause";
        GParsException exception = new GParsException(errorMessage, null);
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String errorMessage = "Test error message with cause";
        Throwable cause = new IllegalArgumentException("Root cause");
        GParsException exception = new GParsException(errorMessage, cause);
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(cause, exception.getCause());
        assertEquals("Root cause", exception.getCause().getMessage());
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
    }

    @Test
    public void testExceptionInheritance() {
        String errorMessage = "Test inheritance";
        GParsException exception = new GParsException(errorMessage);
        assertTrue(exception instanceof RuntimeException,
            "GParsException should be a subclass of RuntimeException");
    }

    @Test
    public void testEmptyMessage() {
        String emptyMessage = "";
        GParsException exception = new GParsException(emptyMessage);
        assertNotNull(exception);
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    public void testNullMessage() {
        String nullMessage = null;
        GParsException exception = new GParsException(nullMessage);
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testChainedExceptions() {
        String innerMessage = "Inner exception";
        String outerMessage = "Outer exception";
        NullPointerException innerException = new NullPointerException(innerMessage);
        GParsException outerException = new GParsException(outerMessage, innerException);
        assertEquals(outerMessage, outerException.getMessage());
        assertEquals(innerException, outerException.getCause());
        assertEquals(innerMessage, outerException.getCause().getMessage());
    }

    @Test
    public void testStackTracePreservation() {
        String errorMessage = "Stack trace test";
        GParsException exception = new GParsException(errorMessage);
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0,
            "Exception should have stack trace");
    }

    @Test
    public void testToString() {
        String errorMessage = "Test toString";
        GParsException exception = new GParsException(errorMessage);
        String toStringResult = exception.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("GParsException"));
        assertTrue(toStringResult.contains(errorMessage));
    }
}