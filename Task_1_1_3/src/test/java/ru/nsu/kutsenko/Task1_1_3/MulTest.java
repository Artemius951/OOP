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
}