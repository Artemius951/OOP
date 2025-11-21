package ru.nsu.kutsenko.task121;

/**
 * Исключение для ошибок формата файла графа.
 */
public class GParsException extends RuntimeException {
    public GParsException(String mes) {
        super(mes);
    }

    public GParsException(String mes, Throwable cause) {
        super(mes, cause);
    }
}