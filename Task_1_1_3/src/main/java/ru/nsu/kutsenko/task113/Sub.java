package ru.nsu.kutsenko.task113;

import java.util.Map;

/**
 * Класс для представления операции вычитания.
 * Наследуется от BinaryOperation и реализует вычитание двух выражений.
 */
public class Sub extends BinaryOperation {
    /**
     * Конструктор операции вычитания.
     *
     * @param left  левое выражение (уменьшаемое)
     * @param right правое выражение (вычитаемое)
     */
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Возвращает строковое представление оператора вычитания.
     *
     * @return строка "-"
     */
    @Override
    protected String getOperator() {
        return "-";
    }

    /**
     * Вычисляет производную операции вычитания.
     * Производная разности равна разности производных.
     *
     * @param variable переменная, по которой вычисляется производная
     * @return выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    /**
     * Вычисляет значение операции вычитания.
     *
     * @param variables карта переменных и их значений
     * @return разность значений левого и правого выражений
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return left.eval(variables) - right.eval(variables);
    }

    /**
     * Применяет специфичные для вычитания правила упрощения.
     *
     * @param left  упрощенное левое выражение
     * @param right упрощенное правое выражение
     * @return упрощенное выражение
     */
    @Override
    protected Expression simplifySpecific(Expression left, Expression right) {
        // x - 0 = x
        if (right instanceof Number && ((Number) right).getValue() == 0) {
            return left;
        }
        // x - x = 0
        if (left.toString().equals(right.toString())) {
            return new Number(0);
        }
        // Если оба операнда - числа, вычисляем результат
        if (left instanceof Number && right instanceof Number) {
            return new Number(((Number) left).getValue() - ((Number) right).getValue());
        }
        // В остальных случаях возвращаем новое вычитание с упрощенными операндами
        return new Sub(left, right);
    }
}