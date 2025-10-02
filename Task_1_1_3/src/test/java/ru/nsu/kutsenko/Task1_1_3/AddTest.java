package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;



class AddTest {

    @Test
    void testAddEval() {
        Add add = new Add(new Number(3), new Number(2));
        assertEquals(5, add.eval(Map.of()));
    }

    @Test
    void testAddWithVariables() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        assertEquals(15, add.eval(Map.of("x", 10, "y", 5)));
    }

    @Test
    void testAddDerivative() {
        Add add = new Add(new Variable("x"), new Variable("y"));
        Expression derivative = add.derivative("x");
        assertEquals("(1+0)", derivative.toString());
    }

    @Test
    void testAddDerivativeBothVariables() {
        Add add = new Add(new Variable("x"), new Variable("x"));
        Expression derivative = add.derivative("x");
        assertEquals("(1+1)", derivative.toString());
    }

    @Test
    void testAddDerivativeConstants() {
        Add add = new Add(new Number(5), new Number(3));
        Expression derivative = add.derivative("x");
        assertEquals("(0+0)", derivative.toString());
    }

    @Test
    void testAddToString() {
        Add add = new Add(new Number(3), new Number(5));
        assertEquals("(3+5)", add.toString());
    }
}