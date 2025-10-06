package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void testParseWithoutParenthesesMethodExists() {
        Expression expr = Expression.parseWithoutParentheses("x + y");
        assertNotNull(expr);
        assertTrue(expr instanceof Expression);
    }

    @Test
    void testParseWithoutParenthesesSimpleExpression() {
        Expression expr = Expression.parseWithoutParentheses("5 + 3");
        assertEquals(8, expr.eval(new HashMap<>()));
    }

    @Test
    void testParseWithoutParenthesesWithVariablesEvaluation() {
        Expression expr = Expression.parseWithoutParentheses("x + y * 2");
        Map<String, Integer> variables = Map.of("x", 5, "y", 10);
        assertEquals(25, expr.eval(variables));
    }

    @Test
    void testParseWithoutParenthesesUsingEvalWithString() {
        Expression expr = Expression.parseWithoutParentheses("a * b + c");
        assertEquals(17, expr.eval("a=3;b=4;c=5"));
    }

    @Test
    void testSimplifyMethodExists() {
        Expression expr = new Number(5);
        Expression simplified = expr.simplify();
        assertNotNull(simplified);
    }

    @Test
    void testSimplifyNumber() {
        Expression num = new Number(42);
        Expression simplified = num.simplify();
        assertEquals(num, simplified);
        assertEquals(42, simplified.eval(new HashMap<>()));
    }

    @Test
    void testSimplifyVariable() {
        Expression var = new Variable("x");
        Expression simplified = var.simplify();
        assertEquals(var, simplified);
        assertEquals(10, simplified.eval(Map.of("x", 10)));
    }

    @Test
    void testSimplifyAdditionWithZero() {
        Expression expr = new Add(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", simplified.toString());
        assertEquals(5, simplified.eval(Map.of("x", 5)));
    }

    @Test
    void testSimplifyMultiplicationWithZero() {
        Expression expr = new Mul(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
        assertEquals(0, simplified.eval(new HashMap<>()));
    }

    @Test
    void testSimplifyMultiplicationWithOne() {
        Expression expr = new Mul(new Number(1), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", simplified.toString());
        assertEquals(7, simplified.eval(Map.of("x", 7)));
    }

    @Test
    void testSimplifySubtractionSameExpressions() {
        Expression expr = new Sub(new Variable("x"), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
        assertEquals(0, simplified.eval(new HashMap<>()));
    }

    @Test
    void testSimplifyDoesNotModifyOriginal() {
        Expression original = new Mul(new Number(0), new Variable("x"));
        Expression simplified = original.simplify();

        assertNotSame(original, simplified);
        assertEquals("(0*x)", original.toString());
        assertEquals("0", simplified.toString());
    }

    @Test
    void testSimplifyConstantExpression() {
        Expression expr = new Add(new Number(5), new Mul(new Number(3),
            new Number(2)));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(11, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyNestedExpression() {
        Expression expr = new Add(
            new Mul(new Number(1), new Variable("x")),
            new Sub(new Variable("y"), new Variable("y"))
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", simplified.toString());
        assertEquals(10, simplified.eval(Map.of("x", 10)));
    }

    @Test
    void testSimplifyComplexExpression() {
        Expression expr = new Add(
            new Mul(new Number(0), new Variable("x")),
            new Sub(new Variable("y"), new Variable("y"))
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyDivisionByOne() {
        Expression expr = new Div(new Variable("x"), new Number(1));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", simplified.toString());
        assertEquals(5, simplified.eval(Map.of("x", 5)));
    }

    @Test
    void testSimplifyDivisionSameExpressions() {
        Expression expr = new Div(new Variable("x"), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(1, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyZeroDividedByVariable() {
        Expression expr = new Div(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyAfterParseWithoutParentheses() {
        Expression parsed = Expression.parseWithoutParentheses("x * 0 + y - y");
        Expression simplified = parsed.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
        assertEquals(0, simplified.eval(new HashMap<>()));
    }

    @Test
    void testSimplifyAfterParse() {
        Expression parsed = Expression.parse("((0*x)+(y-y))");
        Expression simplified = parsed.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyPreservesEvaluation() {
        Expression original = new Add(new Mul(new Number(2), new Number(3)), new Number(4));
        Expression simplified = original.simplify();

        Map<String, Integer> vars = new HashMap<>();
        assertEquals(original.eval(vars), simplified.eval(vars));
        assertEquals(10, simplified.eval(vars));
    }

    @Test
    void testSimplifyMultipleTimes() {
        Expression expr = new Mul(new Number(1), new Variable("x"));
        Expression simplified1 = expr.simplify();
        Expression simplified2 = simplified1.simplify();

        assertEquals(simplified1.toString(), simplified2.toString());
        assertTrue(simplified2 instanceof Variable);
    }
}