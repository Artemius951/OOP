package ru.nsu.kutsenko.task113;

/**
 * Исключение, выбрасываемое при попытке использования неопределенной переменной.
 */
public class UndefinedVariableException extends ExpressionEvaluationException {
    public UndefinedVariableException(String variableName) {
        super("Undefined variable: '" + variableName + "'");
    }

    public UndefinedVariableException(String variableName, Throwable cause) {
        super("Undefined variable: '" + variableName + "'", cause);
    }
}