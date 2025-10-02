package ru.nsu.kutsenko.task113;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MulTest {

    @Test
    void testMulEval() {
        Mul mul = new Mul(new Number(4), new Number(3));
        assertEquals(12, mul.eval(Map.of()));
    }

    @Test
    void testMulWithVariables() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(50, mul.eval(Map.of("x", 10, "y", 5)));
    }

    @Test
    void testMulByZero() {
        Mul mul = new Mul(new Number(5), new Number(0));
        assertEquals(0, mul.eval(Map.of()));
    }

    @Test
    void testMulDerivative() {
        // d(x*y)/dx = (1*y + x*0) = y
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = mul.derivative("x");
        assertEquals("((1*y)+(x*0))", derivative.toString());
    }

    @Test
    void testMulDerivativeSameVariable() {
        // d(x*x)/dx = (1*x + x*1) = (x + x)
        Mul mul = new Mul(new Variable("x"), new Variable("x"));
        Expression derivative = mul.derivative("x");
        assertEquals("((1*x)+(x*1))", derivative.toString());
    }

    @Test
    void testMulDerivativeConstants() {
        Mul mul = new Mul(new Number(5), new Number(3));
        Expression derivative = mul.derivative("x");
        assertEquals("((0*3)+(5*0))", derivative.toString());
    }

    @Test
    void testMulToString() {
        Mul mul = new Mul(new Number(4), new Number(3));
        assertEquals("(4*3)", mul.toString());
    }
}