package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void testSubEval() {
        Sub sub = new Sub(new Number(10), new Number(3));
        assertEquals(7, sub.eval(Map.of()));
    }

    @Test
    void testSubWithVariables() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        assertEquals(5, sub.eval(Map.of("x", 10, "y", 5)));
    }

    @Test
    void testSubNegativeResult() {
        Sub sub = new Sub(new Number(3), new Number(10));
        assertEquals(-7, sub.eval(Map.of()));
    }

    @Test
    void testSubDerivative() {
        Sub sub = new Sub(new Variable("x"), new Variable("y"));
        Expression derivative = sub.derivative("x");
        assertEquals("(1-0)", derivative.toString());
    }

    @Test
    void testSubDerivativeConstants() {
        Sub sub = new Sub(new Number(5), new Number(3));
        Expression derivative = sub.derivative("x");
        assertEquals("(0-0)", derivative.toString());
    }

    @Test
    void testSubToString() {
        Sub sub = new Sub(new Number(10), new Number(3));
        assertEquals("(10-3)", sub.toString());
    }
}