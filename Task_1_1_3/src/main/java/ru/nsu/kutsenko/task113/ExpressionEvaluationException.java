package ru.nsu.kutsenko.task113;

/**
 * Исключение, выбрасываемое при ошибках во время вычисления выражения.
 */
public class ExpressionEvaluationException extends ExpressionException {
    public ExpressionEvaluationException(String message) {
        super("Evaluation error: " + message);
    }

    public ExpressionEvaluationException(String message, Throwable cause) {
        super("Evaluation error: " + message, cause);
    }
}