package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void testMulEval() {
        Mul mul = new Mul(new Number(3), new Number(2));
        assertEquals(6, mul.eval(Map.of()));
    }

    @Test
    void testMulWithVariables() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        assertEquals(50, mul.eval(Map.of("x", 10, "y", 5)));
    }

    @Test
    void testMulDerivative() {
        Mul mul = new Mul(new Variable("x"), new Variable("y"));
        Expression derivative = mul.derivative("x");
        assertEquals("((1*y)+(x*0))", derivative.toString());
    }

    @Test
    void testMulDerivativeConstants() {
        Mul mul = new Mul(new Number(5), new Number(3));
        Expression derivative = mul.derivative("x");
        assertEquals("((0*3)+(5*0))", derivative.toString());
    }

    @Test
    void testMulToString() {
        Mul mul = new Mul(new Number(3), new Number(5));
        assertEquals("(3*5)", mul.toString());
    }

    @Test
    void testMulZero() {
        Mul mul = new Mul(new Number(0), new Number(5));
        assertEquals(0, mul.eval(Map.of()));
    }
}