package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;



class NumberTest {

    @Test
    void testNumberEval() {
        Number num = new Number(42);
        assertEquals(42, num.eval(Map.of()));
    }

    @Test
    void testNumberDerivative() {
        Number num = new Number(42);
        Expression derivative = num.derivative("x");
        assertEquals("0", derivative.toString());
    }

    @Test
    void testNumberToString() {
        Number num = new Number(42);
        assertEquals("42", num.toString());
    }

    @Test
    void testNumberGetValue() {
        Number num = new Number(42);
        assertEquals(42, num.getValue());
    }

    @Test
    void testZeroNumber() {
        Number zero = new Number(0);
        assertEquals(0, zero.eval(Map.of()));
        assertEquals("0", zero.toString());
    }

    @Test
    void testNegativeNumber() {
        Number negative = new Number(-5);
        assertEquals(-5, negative.eval(Map.of()));
        assertEquals("-5", negative.toString());
    }
}