package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;

class DivisionByZeroExceptionTest {

    @Test
    void testDivisionByZeroException() {
        DivisionByZeroException exception = new DivisionByZeroException();
        assertEquals("Evaluation error: Division by zero", exception.getMessage());
    }

    @Test
    void testDivisionByZeroExceptionThrownInDivEval() {
        Div div = new Div(new Number(10), new Number(0));
        DivisionByZeroException exception = assertThrows(
            DivisionByZeroException.class,
            () -> div.eval(Map.of())
        );
        assertEquals("Evaluation error: Division by zero", exception.getMessage());
    }

    @Test
    void testDivisionByZeroExceptionThrownWithVariables() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        DivisionByZeroException exception = assertThrows(
            DivisionByZeroException.class,
            () -> div.eval(Map.of("x", 10, "y", 0))
        );
        assertEquals("Evaluation error: Division by zero", exception.getMessage());
    }
}