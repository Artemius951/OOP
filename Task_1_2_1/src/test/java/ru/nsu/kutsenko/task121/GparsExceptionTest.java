package ru.nsu.kutsenko.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Тесты для класса GParsException.
 */
public class GparsExceptionTest {

    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Test error message";
        GparsException exception = new GparsException(errorMessage);
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndNullCause() {
        String errorMessage = "Test error message with null cause";
        GparsException exception = new GparsException(errorMessage, null);
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String errorMessage = "Test error message with cause";
        Throwable cause = new IllegalArgumentException("Root cause");
        GparsException exception = new GparsException(errorMessage, cause);
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
        GparsException exception = new GparsException(errorMessage);
        assertTrue(exception instanceof RuntimeException,
            "GParsException should be a subclass of RuntimeException");
    }

    @Test
    public void testEmptyMessage() {
        String emptyMessage = "";
        GparsException exception = new GparsException(emptyMessage);
        assertNotNull(exception);
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    public void testNullMessage() {
        String nullMessage = null;
        GparsException exception = new GparsException(nullMessage);
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testChainedExceptions() {
        String innerMessage = "Inner exception";
        String outerMessage = "Outer exception";
        NullPointerException innerException = new NullPointerException(innerMessage);
        GparsException outerException = new GparsException(outerMessage, innerException);
        assertEquals(outerMessage, outerException.getMessage());
        assertEquals(innerException, outerException.getCause());
        assertEquals(innerMessage, outerException.getCause().getMessage());
    }

    @Test
    public void testStackTracePreservation() {
        String errorMessage = "Stack trace test";
        GparsException exception = new GparsException(errorMessage);
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0,
            "Exception should have stack trace");
    }

    @Test
    public void testToString() {
        String errorMessage = "Test toString";
        GparsException exception = new GparsException(errorMessage);
        String toStringResult = exception.toString();
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("GParsException"));
        assertTrue(toStringResult.contains(errorMessage));
    }
}