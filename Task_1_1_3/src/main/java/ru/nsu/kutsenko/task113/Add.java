package ru.nsu.kutsenko.task113;

import java.util.Map;

/**
 * Класс для представления операции сложения.
 * Наследуется от BinaryOperation и реализует сложение двух выражений.
 */
public class Add extends BinaryOperation {
    /**
     * Конструктор операции сложения.
     *
     * @param left  левое выражение
     * @param right правое выражение
     */
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Возвращает строковое представление оператора сложения.
     *
     * @return строка "+"
     */
    @Override
    protected String getOperator() {
        return "+";
    }

    /**
     * Вычисляет производную операции сложения.
     * Производная суммы равна сумме производных.
     *
     * @param variable переменная, по которой вычисляется производная
     * @return выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Вычисляет значение операции сложения.
     *
     * @param variables карта переменных и их значений
     * @return сумма значений левого и правого выражений
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }

    /**
     * Применяет специфичные для сложения правила упрощения.
     *
     * @param left  упрощенное левое выражение
     * @param right упрощенное правое выражение
     * @return упрощенное выражение
     */
    @Override
    protected Expression simplifySpecific(Expression left, Expression right) {
        // 0 + x = x
        if (left instanceof Number && ((Number) left).getValue() == 0) {
            return right;
        }
        // x + 0 = x
        if (right instanceof Number && ((Number) right).getValue() == 0) {
            return left;
        }
        // Если оба операнда - числа, вычисляем результат
        if (left instanceof Number && right instanceof Number) {
            return new Number(((Number) left).getValue() + ((Number) right).getValue());
        }
        // В остальных случаях возвращаем новое сложение с упрощенными операндами
        return new Add(left, right);
    }
}