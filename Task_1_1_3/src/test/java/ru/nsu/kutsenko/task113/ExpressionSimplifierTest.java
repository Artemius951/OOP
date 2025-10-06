package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для упрощения выражений.
 */
class ExpressionSimplifierTest {

    @Test
    void testRuleA_ConstantEvaluation() {
        Expression expr = new Add(new Number(5), new Number(3));
        Expression simplified = expr.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(8, ((Number) simplified).getValue());
    }

    @Test
    void testRuleB_MultiplicationByZero() {
        Expression expr1 = new Mul(new Number(0), new Variable("x"));
        Expression simplified1 = expr1.simplify();
        assertTrue(simplified1 instanceof Number);
        assertEquals(0, ((Number) simplified1).getValue());

        Expression expr2 = new Mul(new Variable("x"), new Number(0));
        Expression simplified2 = expr2.simplify();
        assertTrue(simplified2 instanceof Number);
        assertEquals(0, ((Number) simplified2).getValue());
    }

    @Test
    void testRuleC_MultiplicationByOne() {
        Expression expr1 = new Mul(new Number(1), new Variable("x"));
        Expression simplified1 = expr1.simplify();
        assertTrue(simplified1 instanceof Variable);
        assertEquals("x", ((Variable) simplified1).getName());

        Expression expr2 = new Mul(new Variable("x"), new Number(1));
        Expression simplified2 = expr2.simplify();
        assertTrue(simplified2 instanceof Variable);
        assertEquals("x", ((Variable) simplified2).getName());
    }

    @Test
    void testRuleD_SubtractionOfSameExpressions() {
        Expression expr = new Sub(new Variable("x"), new Variable("x"));
        Expression simplified = expr.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testAdditionWithZero() {
        Expression expr1 = new Add(new Number(0), new Variable("x"));
        Expression simplified1 = expr1.simplify();
        assertTrue(simplified1 instanceof Variable);
        assertEquals("x", ((Variable) simplified1).getName());

        Expression expr2 = new Add(new Variable("x"), new Number(0));
        Expression simplified2 = expr2.simplify();
        assertTrue(simplified2 instanceof Variable);
        assertEquals("x", ((Variable) simplified2).getName());
    }

    @Test
    void testSubtractionWithZero() {
        Expression expr = new Sub(new Variable("x"), new Number(0));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", ((Variable) simplified).getName());
    }

    @Test
    void testDivisionByOne() {
        Expression expr = new Div(new Variable("x"), new Number(1));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", ((Variable) simplified).getName());
    }

    @Test
    void testDivisionOfSameExpressions() {
        Expression expr = new Div(new Variable("x"), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(1, ((Number) simplified).getValue());
    }

    @Test
    void testZeroDividedByNonZero() {
        Expression expr = new Div(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testComplexSimplification() {
        Expression expr = new Add(
            new Mul(new Number(0), new Variable("x")),
            new Sub(new Variable("y"), new Variable("y"))
        );
        Expression simplified = expr.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testNestedSimplification() {
        Expression innerAdd = new Add(new Variable("x"), new Number(0));
        Expression innerMul = new Mul(new Number(1), innerAdd);
        Expression innerSub = new Sub(new Variable("y"), new Variable("y"));
        Expression expr = new Mul(innerMul, innerSub);

        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testExpressionWithVariablesNotSimplified() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        Expression simplified = expr.simplify();

        assertTrue(simplified instanceof Add);
        Add add = (Add) simplified;
        assertTrue(add.left instanceof Variable);
        assertTrue(add.right instanceof Variable);
    }

    @Test
    void testEvaluationAfterSimplification() {
        Expression original = new Add(
            new Mul(new Number(2), new Number(3)),
            new Sub(new Number(5), new Number(1))
        );
        Expression simplified = original.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(10, ((Number) simplified).getValue());

        assertEquals(10, simplified.eval(java.util.Collections.emptyMap()));
    }

    @Test
    void testSimplifyMethodInExpression() {
        Expression expr = new Mul(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();

        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }
}