package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class ExpressionParserTest {

    @Test
    void testParseSimpleAddition() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(3+5)");
        assertNotNull(expr);
        assertEquals(8, expr.eval(""));
    }

    @Test
    void testParseComplexExpression() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("((x+y)*(a-b))");
        assertNotNull(expr);
        assertEquals(30, expr.eval("x=5; y=5; a=10; b=7"));
    }

    @Test
    void testParseWithSpaces() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(  x  +  y  )");
        assertNotNull(expr);
        assertEquals(15, expr.eval("x=10; y=5"));
    }

    @Test
    void testParseNumber() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("42");
        assertNotNull(expr);
        assertEquals(42, expr.eval(""));
    }

    @Test
    void testParseVariable() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("myVar");
        assertNotNull(expr);
        assertEquals(100, expr.eval("myVar=100"));
    }

    @Test
    void testParseInvalidExpressionThrowsException() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> parser.parse("(3+5"));
    }

    @Test
    void testParseUnknownOperatorThrowsException() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> parser.parse("(3$5)"));
    }

    @Test
    void testParseEmptyExpressionThrowsException() {
        ExpressionParser parser = new ExpressionParser();
        assertThrows(RuntimeException.class, () -> parser.parse(""));
    }

    @Test
    void testParseMultiDigitNumbers() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(100+200)");
        assertNotNull(expr);
        assertEquals(300, expr.eval(""));
    }

    @Test
    void testParseMultiLetterVariables() {
        ExpressionParser parser = new ExpressionParser();
        Expression expr = parser.parse("(alpha+beta)");
        assertNotNull(expr);
        assertEquals(25, expr.eval("alpha=10; beta=15"));
    }
}