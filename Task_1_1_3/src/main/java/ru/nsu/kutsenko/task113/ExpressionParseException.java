package ru.nsu.kutsenko.task113;

/**
 * Исключение, выбрасываемое при ошибках синтаксического разбора выражения.
 */
public class ExpressionParseException extends ExpressionException {
    public ExpressionParseException(String message) {
        super("Parse error: " + message);
    }

    public ExpressionParseException(String message, Throwable cause) {
        super("Parse error: " + message, cause);
    }
}