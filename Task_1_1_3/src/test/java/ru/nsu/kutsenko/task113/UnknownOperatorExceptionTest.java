package ru.nsu.kutsenko.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UnknownOperatorExceptionTest {

    @Test
    void testUnknownOperatorException() {
        UnknownOperatorException exception = new UnknownOperatorException('$');
        assertEquals("Parse error: Unknown operator: '$'", exception.getMessage());
    }

    @Test
    void testUnknownOperatorExceptionThrownInParser() {
        UnknownOperatorException exception = assertThrows(
            UnknownOperatorException.class,
            () -> ExpressionParser.parse("(3$5)")
        );
        assertEquals("Parse error: Unknown operator: '$'", exception.getMessage());
    }
}