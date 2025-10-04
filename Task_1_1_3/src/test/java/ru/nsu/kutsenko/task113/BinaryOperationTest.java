package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class BinaryOperationTest {

    @Test
    void testBinaryOperationToString() {
        BinaryOperation add = new Add(new Number(3), new Number(5));
        assertEquals("(3+5)", add.toString());
    }

    @Test
    void testNestedBinaryOperationsToString() {
        BinaryOperation expr = new Add(new Number(3), new Mul(new Number(5), new Number(2)));
        assertEquals("(3+(5*2))", expr.toString());
    }

    @Test
    void testComplexBinaryOperationToString() {
        BinaryOperation expr = new Mul(
            new Add(new Variable("x"), new Variable("y")),
            new Sub(new Variable("a"), new Variable("b"))
        );
        assertEquals("((x+y)*(a-b))", expr.toString());
    }
}