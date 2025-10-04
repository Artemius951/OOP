package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.junit.jupiter.api.Test;



class VariableTest {

    @Test
    void testVariableEval() {
        Variable var = new Variable("x");
        assertEquals(10, var.eval(Map.of("x", 10)));
    }

    @Test
    void testVariableUndefinedThrowsException() {
        Variable var = new Variable("undefinedVar");
        assertThrows(RuntimeException.class, () -> var.eval(Map.of("x", 10)));
    }

    @Test
    void testVariableDerivativeSameVariable() {
        Variable var = new Variable("x");
        Expression derivative = var.derivative("x");
        assertEquals("1", derivative.toString());
    }

    @Test
    void testVariableDerivativeDifferentVariable() {
        Variable var = new Variable("x");
        Expression derivative = var.derivative("y");
        assertEquals("0", derivative.toString());
    }

    @Test
    void testVariableToString() {
        Variable var = new Variable("myVar");
        assertEquals("myVar", var.toString());
    }

    @Test
    void testVariableGetName() {
        Variable var = new Variable("myVar");
        assertEquals("myVar", var.getName());
    }

    @Test
    void testMultiLetterVariable() {
        Variable var = new Variable("alpha");
        assertEquals("alpha", var.toString());
        assertEquals("alpha", var.getName());
    }
}