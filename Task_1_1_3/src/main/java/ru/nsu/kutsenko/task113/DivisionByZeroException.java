package ru.nsu.kutsenko.task113;

/**
 * Исключение, выбрасываемое при попытке деления на ноль.
 */
public class DivisionByZeroException extends ExpressionEvaluationException {
    public DivisionByZeroException() {
        super("Division by zero");
    }
}