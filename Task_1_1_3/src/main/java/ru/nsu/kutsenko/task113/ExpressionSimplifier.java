package ru.nsu.kutsenko.task113;

/**
 * Класс для упрощения математических выражений.
 * Теперь используется как фасад для вызова метода simplify() выражений.
 */
public class ExpressionSimplifier {

    private ExpressionSimplifier() {}

    /**
     * Упрощает выражение по заданным правилам.
     * Не изменяет исходное выражение, возвращает новое упрощенное.
     *
     * @param expression выражение для упрощения
     * @return упрощенное выражение
     */
    public static Expression simplify(Expression expression) {
        return expression.simplify();
    }
}