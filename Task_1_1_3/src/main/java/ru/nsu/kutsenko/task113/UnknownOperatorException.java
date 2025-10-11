package ru.nsu.kutsenko.task113;

/**
 * Исключение, выбрасываемое при встрече неизвестного оператора.
 */
public class UnknownOperatorException extends ExpressionParseException {
    public UnknownOperatorException(char operator) {
        super("Unknown operator: '" + operator + "'");
    }
}