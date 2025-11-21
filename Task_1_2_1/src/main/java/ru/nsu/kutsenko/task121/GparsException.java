package ru.nsu.kutsenko.task121;

/**
 * Исключение для ошибок формата файла графа.
 */
public class GparsException extends RuntimeException {
    public GparsException(String mes) {
        super(mes);
    }

    public GparsException(String mes, Throwable cause) {
        super(mes, cause);
    }
}