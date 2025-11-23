package ru.nsu.kutsenko.task113;

/**
 * Базовый класс для всех исключений, связанных с работой выражений.
 * Наследуется от RuntimeException, что делает все его потомки непроверяемыми.
 */
public class ExpressionException extends RuntimeException {
    public ExpressionException(String message) {
        super(message);
    }

    public ExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}