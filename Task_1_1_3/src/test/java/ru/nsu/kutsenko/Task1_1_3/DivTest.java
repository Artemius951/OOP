package ru.nsu.kutsenko.task113;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivTest {

    @Test
    void testDivEval() {
        Div div = new Div(new Number(10), new Number(2));
        assertEquals(5, div.eval(Map.of()));
    }

    @Test
    void testDivWithVariables() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        assertEquals(2, div.eval(Map.of("x", 10, "y", 5)));
    }

    @Test
    void testDivByZeroThrowsException() {
        Div div = new Div(new Number(10), new Number(0));
        assertThrows(ArithmeticException.class, () -> div.eval(Map.of()));
    }

    @Test
    void testDivDerivative() {
        Div div = new Div(new Variable("x"), new Variable("y"));
        Expression derivative = div.derivative("x");
        assertEquals("(((1*y)-(x*0))/(y*y))", derivative.toString());
    }

    @Test
    void testDivDerivativeConstants() {
        Div div = new Div(new Number(10), new Number(2));
        Expression derivative = div.derivative("x");
        assertEquals("(((0*2)-(10*0))/(2*2))", derivative.toString());
    }

    @Test
    void testDivToString() {
        Div div = new Div(new Number(10), new Number(2));
        assertEquals("(10/2)", div.toString());
    }
}