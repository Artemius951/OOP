package ru.nsu.kutsenko.task113;

import java.util.Map;

/**
 * Класс для представления операции умножения.
 * Наследуется от BinaryOperation и реализует умножение двух выражений.
 */
public class Mul extends BinaryOperation {
    /**
     * Конструктор операции умножения.
     *
     * @param left  левое выражение
     * @param right правое выражение
     */
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Возвращает строковое представление оператора умножения.
     *
     * @return строка "*"
     */
    @Override
    protected String getOperator() {
        return "*";
    }

    /**
     * Вычисляет производную операции умножения по правилу произведения.
     * Производная произведения: (fg)' = f'g + fg'
     *
     * @param variable переменная, по которой вычисляется производная
     * @return выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
            new Mul(left.derivative(variable), right),
            new Mul(left, right.derivative(variable))
        );
    }

    /**
     * Вычисляет значение операции умножения.
     *
     * @param variables карта переменных и их значений
     * @return произведение значений левого и правого выражений
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    /**
     * Применяет специфичные для умножения правила упрощения.
     *
     * @param left  упрощенное левое выражение
     * @param right упрощенное правое выражение
     * @return упрощенное выражение
     */
    @Override
    protected Expression simplifySpecific(Expression left, Expression right) {
        // 0 * x = 0
        if (left instanceof Number && ((Number) left).getValue() == 0) {
            return new Number(0);
        }
        // x * 0 = 0
        if (right instanceof Number && ((Number) right).getValue() == 0) {
            return new Number(0);
        }
        // 1 * x = x
        if (left instanceof Number && ((Number) left).getValue() == 1) {
            return right;
        }
        // x * 1 = x
        if (right instanceof Number && ((Number) right).getValue() == 1) {
            return left;
        }
        // Если оба операнда - числа, вычисляем результат
        if (left instanceof Number && right instanceof Number) {
            return new Number(((Number) left).getValue() * ((Number) right).getValue());
        }
        // В остальных случаях возвращаем новое умножение с упрощенными операндами
        return new Mul(left, right);
    }
}