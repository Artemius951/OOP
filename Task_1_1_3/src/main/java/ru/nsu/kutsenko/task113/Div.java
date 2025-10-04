package ru.nsu.kutsenko.task113;

import java.util.Map;

/**
 * Класс для представления операции деления.
 * Наследуется от BinaryOperation и реализует деление двух выражений.
 */
public class Div extends BinaryOperation {
    /**
     * Конструктор операции деления.
     *
     * @param left  левое выражение (делимое)
     * @param right правое выражение (делитель)
     */
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Возвращает строковое представление оператора деления.
     *
     * @return строка "/"
     */
    @Override
    protected String getOperator() {
        return "/";
    }

    /**
     * Вычисляет производную операции деления по правилу частного.
     * Производная частного: (f/g)' = (f'g - fg') / g²
     *
     * @param variable переменная, по которой вычисляется производная
     * @return выражение, представляющее производную
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
            new Sub(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
            ),
            new Mul(right, right)
        );
    }

    /**
     * Вычисляет значение операции деления.
     *
     * @param variables карта переменных и их значений
     * @return результат деления левого выражения на правое
     * @throws DivisionByZeroException если происходит деление на ноль
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        int denominator = right.eval(variables);
        if (denominator == 0) {
            throw new DivisionByZeroException();
        }
        return left.eval(variables) / denominator;
    }
}