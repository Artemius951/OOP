package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExpressionParserTest {

    @Test
    void testParseSimpleAddition() {
        Expression expr = ExpressionParser.parse("(3+5)");
        assertNotNull(expr);
        assertEquals(8, expr.eval(""));
    }

    @Test
    void testParseComplexExpression() {
        Expression expr = ExpressionParser.parse("((x+y)*(a-b))");
        assertNotNull(expr);
        assertEquals(30, expr.eval("x=5; y=5; a=10; b=7"));
    }

    @Test
    void testParseWithSpaces() {
        Expression expr = ExpressionParser.parse("(  x  +  y  )");
        assertNotNull(expr);
        assertEquals(15, expr.eval("x=10; y=5"));
    }

    @Test
    void testParseNumber() {
        Expression expr = ExpressionParser.parse("42");
        assertNotNull(expr);
        assertEquals(42, expr.eval(""));
    }

    @Test
    void testParseVariable() {
        Expression expr = ExpressionParser.parse("myVar");
        assertNotNull(expr);
        assertEquals(100, expr.eval("myVar=100"));
    }

    @Test
    void testParseInvalidExpressionThrowsException() {
        assertThrows(ExpressionParseException.class, () -> ExpressionParser.parse("(3+5"));
    }

    @Test
    void testParseUnknownOperatorThrowsException() {
        assertThrows(UnknownOperatorException.class, () -> ExpressionParser.parse("(3$5)"));
    }

    @Test
    void testParseEmptyExpressionThrowsException() {
        assertThrows(ExpressionParseException.class, () -> ExpressionParser.parse(""));
    }

    @Test
    void testParseMultiDigitNumbers() {
        Expression expr = ExpressionParser.parse("(100+200)");
        assertNotNull(expr);
        assertEquals(300, expr.eval(""));
    }

    @Test
    void testParseMultiLetterVariables() {
        Expression expr = ExpressionParser.parse("(alpha+beta)");
        assertNotNull(expr);
        assertEquals(25, expr.eval("alpha=10; beta=15"));
    }

    @Test
    void testParseAllOperations() {
        Expression add = ExpressionParser.parse("(3+5)");
        Expression sub = ExpressionParser.parse("(10-3)");
        Expression mul = ExpressionParser.parse("(4*5)");
        Expression div = ExpressionParser.parse("(20/4)");

        assertEquals(8, add.eval(""));
        assertEquals(7, sub.eval(""));
        assertEquals(20, mul.eval(""));
        assertEquals(5, div.eval(""));
    }

    @Test
    void testParseDeeplyNestedExpression() {
        Expression expr = ExpressionParser.parse("(((a+b)*(c-d))/(e+f))");
        assertNotNull(expr);
        assertEquals(16, expr.eval("a=5; b=3; c=10; d=2; e=2; f=2"));
    }
}