package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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

    // ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ

    @Test
    void testSimplifyNumberDoesNotChange() {
        Expression num = new Number(42);
        Expression simplified = num.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(42, ((Number) simplified).getValue());
        assertEquals(num.toString(), simplified.toString());
    }

    @Test
    void testSimplifyVariableDoesNotChange() {
        Expression var = new Variable("x");
        Expression simplified = var.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", ((Variable) simplified).getName());
        assertEquals(var.toString(), simplified.toString());
    }

    @Test
    void testSimplifyMultipleZeroMultiplications() {
        Expression expr = new Mul(new Mul(new Number(0), new Variable("x")), new Variable("y"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyMultipleOneMultiplications() {
        Expression expr = new Mul(new Mul(new Number(1), new Variable("x")), new Number(1));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", ((Variable) simplified).getName());
    }

    @Test
    void testSimplifyAdditionWithConstants() {
        Expression expr = new Add(new Add(new Number(2), new Number(3)), new Number(4));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(9, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyMultiplicationWithConstants() {
        Expression expr = new Mul(new Mul(new Number(2), new Number(3)), new Number(4));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(24, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyDivisionWithConstants() {
        Expression expr = new Div(new Number(20), new Number(5));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(4, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifySubtractionWithConstants() {
        Expression expr = new Sub(new Number(10), new Number(3));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(7, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyMixedOperationsWithConstants() {
        Expression expr = new Add(
            new Mul(new Number(2), new Number(3)),
            new Div(new Number(10), new Number(2))
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(11, ((Number) simplified).getValue()); // 2*3 + 10/2 = 6 + 5 = 11
    }

    @Test
    void testSimplifyComplexNestedExpression() {
        Expression expr = new Add(
            new Mul(new Number(1), new Add(new Variable("x"), new Number(0))),
            new Sub(new Mul(new Number(0), new Variable("y")), new Number(0))
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Variable);
        assertEquals("x", ((Variable) simplified).getName());
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
    void testSimplifyMultipleSameVariablesSubtraction() {
        Expression expr = new Sub(new Variable("x"), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyMultipleSameVariablesDivision() {
        Expression expr = new Div(new Variable("x"), new Variable("x"));
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(1, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyComplexExpressionWithAllRules() {
        Expression expr = new Add(
            new Mul(new Number(0), new Variable("a")), // 0 * a = 0
            new Sub(
                new Mul(new Number(1), new Variable("b")), // 1 * b = b
                new Variable("b") // b - b = 0
            )
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyDeeplyNestedExpression() {
        Expression expr = new Mul(
            new Add(new Number(0), new Variable("x")), // 0 + x = x
            new Sub(new Variable("y"), new Variable("y")) // y - y = 0
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }

    @Test
    void testSimplifyWithDifferentVariables() {
        Expression expr = new Add(new Variable("x"), new Variable("y"));
        Expression simplified = expr.simplify();
        // Должно остаться сложением, так как переменные разные
        assertTrue(simplified instanceof Add);
        assertEquals("(x+y)", simplified.toString());
    }

    @Test
    void testSimplifyDivisionZeroByZero() {
        Expression expr = new Div(new Number(0), new Number(0));
        Expression simplified = expr.simplify();
        // Должно остаться делением, так как деление 0/0 не определено
        assertTrue(simplified instanceof Div);
        assertEquals("(0/0)", simplified.toString());
    }

    @Test
    void testSimplifyMultipleSimplificationCalls() {
        Expression expr = new Mul(new Number(1), new Variable("x"));
        Expression simplified1 = expr.simplify();
        Expression simplified2 = simplified1.simplify();

        assertTrue(simplified1 instanceof Variable);
        assertTrue(simplified2 instanceof Variable);
        assertEquals(simplified1.toString(), simplified2.toString());
    }

    @Test
    void testSimplifyComplexExpressionEvaluationConsistency() {
        Expression original = new Add(
            new Mul(new Number(2), new Number(3)),
            new Sub(new Variable("x"), new Variable("x"))
        );
        Expression simplified = original.simplify();

        // Оригинальное выражение должно вычисляться в 6 + 0 = 6
        assertEquals(6, original.eval(java.util.Map.of("x", 10)));
        // Упрощенное выражение должно вычисляться в 6
        assertEquals(6, simplified.eval(java.util.Map.of("x", 10)));
    }

    @Test
    void testSimplifyWithBinaryOperationStructure() {
        Expression expr = new Add(
            new Mul(new Number(1), new Variable("x")),
            new Number(0)
        );
        Expression simplified = expr.simplify();

        // Должно упроститься до x
        assertTrue(simplified instanceof Variable);
        assertEquals("x", simplified.toString());
    }

    @Test
    void testSimplifyPreservesExpressionType() {
        Expression add = new Add(new Variable("x"), new Number(0)).simplify();
        Expression sub = new Sub(new Variable("x"), new Number(0)).simplify();
        Expression mul = new Mul(new Variable("x"), new Number(1)).simplify();
        Expression div = new Div(new Variable("x"), new Number(1)).simplify();

        assertTrue(add instanceof Variable);
        assertTrue(sub instanceof Variable);
        assertTrue(mul instanceof Variable);
        assertTrue(div instanceof Variable);
    }

    @Test
    void testSimplifyComplexChain() {
        Expression expr = new Div(
            new Mul(
                new Add(new Number(0), new Variable("x")),
                new Sub(new Variable("x"), new Variable("x"))
            ),
            new Number(1)
        );
        Expression simplified = expr.simplify();
        assertTrue(simplified instanceof Number);
        assertEquals(0, ((Number) simplified).getValue());
    }
}