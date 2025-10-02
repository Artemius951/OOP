package ru.nsu.kutsenko.task113;

import java.util.Map;

class Div extends BinaryOperation {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperator() {
        return "/";
    }

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

    @Override
    public int eval(Map<String, Integer> variables) {
        int denominator = right.eval(variables);
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return left.eval(variables) / denominator;
    }
}