package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;



class ExpressionTest {

    @Test
    void testNumberEval() {
        Expression num = new Number(5);
        assertEquals(5, num.eval(new HashMap<>()));
    }

    @Test
    void testVariableEval() {
        Expression var = new Variable("x");
        Map<String, Integer> vars = Map.of("x", 10);
        assertEquals(10, var.eval(vars));
    }

    @Test
    void testAddEval() {
        Expression add = new Add(new Number(3), new Number(2));
        assertEquals(5, add.eval(new HashMap<>()));
    }

    @Test
    void testComplexExpressionEval() {
        Expression expr = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        Map<String, Integer> vars = Map.of("x", 10);
        assertEquals(23, expr.eval(vars));
    }

    @Test
    void testEvalWithString() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        assertEquals(15, expr.eval("x=10; y=5"));
    }

    @Test
    void testEvalWithEmptyString() {
        Expression expr = new Number(42);
        assertEquals(42, expr.eval(""));
    }

    @Test
    void testParseVariables() {
        Expression expr = new Add(new Variable("a"), new Variable("b"));
        assertEquals(15, expr.eval("a=5; b=10"));
    }

    @Test
    void testParseVariablesWithSpaces() {
        Expression expr = new Add(new Variable("a"), new Variable("b"));
        assertEquals(15, expr.eval("a = 5; b = 10"));
    }

    @Test
    void testParseVariablesWithInvalidFormat() {
        Expression expr = new Variable("x");
        assertEquals(10, expr.eval("x=10; invalid; y=20"));
    }

    @Test
    void testParseMethod() {
        Expression expr = Expression.parse("(x+5)");
        assertNotNull(expr);
        assertEquals(15, expr.eval("x=10"));
    }

    @Test
    void testPrintMethod() {
        Expression expr = new Add(new Number(3), new Variable("x"));
        expr.print();
    }
}