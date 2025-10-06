package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        final Expression div = ExpressionParser.parse("(20/4)");

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

    @Test
    void testParseWithoutParenthesesSimpleAddition() {
        Expression expr = ExpressionParser.parseWithoutParentheses("3+5");
        assertNotNull(expr);
        assertEquals("(3+5)", expr.toString());
        assertEquals(8, expr.eval(""));
    }

    @Test
    void testParseWithoutParenthesesMultiplicationPrecedence() {
        Expression expr = ExpressionParser.parseWithoutParentheses("3+5*2");
        assertNotNull(expr);
        assertEquals("(3+(5*2))", expr.toString());
        assertEquals(13, expr.eval(""));
    }

    @Test
    void testParseWithoutParenthesesDivisionPrecedence() {
        Expression expr = ExpressionParser.parseWithoutParentheses("10/2+3");
        assertNotNull(expr);
        assertEquals("((10/2)+3)", expr.toString());
        assertEquals(8, expr.eval(""));
    }

    @Test
    void testParseWithoutParenthesesComplexPrecedence() {
        Expression expr = ExpressionParser.parseWithoutParentheses("a+b*c-d/e");
        assertNotNull(expr);
        assertEquals("((a+(b*c))-(d/e))", expr.toString());
        assertEquals(5, expr.eval("a=1;b=2;c=3;d=4;e=2"));
    }

    @Test
    void testParseWithoutParenthesesWithVariables() {
        Expression expr = ExpressionParser.parseWithoutParentheses("x+y*z");
        assertNotNull(expr);
        assertEquals("(x+(y*z))", expr.toString());
        assertEquals(25, expr.eval("x=5;y=10;z=2"));
    }

    @Test
    void testParseWithoutParenthesesMultiLetterVariables() {
        Expression expr = ExpressionParser.parseWithoutParentheses("alpha+beta*gamma");
        assertNotNull(expr);
        assertEquals("(alpha+(beta*gamma))", expr.toString());
        assertEquals(35, expr.eval("alpha=5;beta=10;gamma=3"));
    }

    @Test
    void testParseWithoutParenthesesMultiDigitNumbers() {
        Expression expr = ExpressionParser.parseWithoutParentheses("100+200*50");
        assertNotNull(expr);
        assertEquals("(100+(200*50))", expr.toString());
        assertEquals(10100, expr.eval(""));
    }

    @Test
    void testParseWithoutParenthesesAllOperations() {
        Expression add = ExpressionParser.parseWithoutParentheses("3+5");
        Expression sub = ExpressionParser.parseWithoutParentheses("10-3");
        Expression mul = ExpressionParser.parseWithoutParentheses("4*5");
        Expression div = ExpressionParser.parseWithoutParentheses("20/4");

        assertEquals(8, add.eval(""));
        assertEquals(7, sub.eval(""));
        assertEquals(20, mul.eval(""));
        assertEquals(5, div.eval(""));
    }

    @Test
    void testParseWithoutParenthesesInvalidOperator() {
        assertThrows(ExpressionParseException.class, () ->
            ExpressionParser.parseWithoutParentheses("3++5")
        );
    }

    @Test
    void testParseWithoutParenthesesEmptyExpression() {
        assertThrows(ExpressionParseException.class, () ->
            ExpressionParser.parseWithoutParentheses("")
        );
    }

    @Test
    void testParseWithoutParenthesesOnlySpaces() {
        assertThrows(ExpressionParseException.class, () ->
            ExpressionParser.parseWithoutParentheses("   ")
        );
    }

    @Test
    void testParseWithoutParenthesesInvalidCharacter() {
        assertThrows(ExpressionParseException.class, () ->
            ExpressionParser.parseWithoutParentheses("x+y@z")
        );
    }

    @Test
    void testParseWithoutParenthesesSingleNumber() {
        Expression expr = ExpressionParser.parseWithoutParentheses("42");
        assertTrue(expr instanceof Number);
        assertEquals(42, ((Number) expr).getValue());
    }

    @Test
    void testParseWithoutParenthesesSingleVariable() {
        Expression expr = ExpressionParser.parseWithoutParentheses("x");
        assertTrue(expr instanceof Variable);
        assertEquals("x", ((Variable) expr).getName());
    }

    @Test
    void testParseWithoutParenthesesComplexExpressionEvaluation() {
        Expression expr = ExpressionParser.parseWithoutParentheses("a*3+b/2-c+5");
        assertEquals(33, expr.eval("a=10;b=6;c=5"));
    }

    @Test
    void testParseWithoutParenthesesWithSpaces() {
        Expression expr = ExpressionParser.parseWithoutParentheses("x + y * 2");
        assertNotNull(expr);
        assertEquals("(x+(y*2))", expr.toString());
        assertEquals(25, expr.eval("x=5;y=10"));
    }

    @Test
    void testParseWithoutParenthesesMultipleSamePrecedence() {
        Expression expr = ExpressionParser.parseWithoutParentheses("a+b+c");
        assertNotNull(expr);
        assertEquals("((a+b)+c)", expr.toString());
        assertEquals(6, expr.eval("a=1;b=2;c=3"));
    }

    @Test
    void testParseWithoutParenthesesMixedPrecedenceGroups() {
        Expression expr = ExpressionParser.parseWithoutParentheses("a*b+c*d");
        assertNotNull(expr);
        assertEquals("((a*b)+(c*d))", expr.toString());
        assertEquals(14, expr.eval("a=1;b=2;c=3;d=4"));
    }
}