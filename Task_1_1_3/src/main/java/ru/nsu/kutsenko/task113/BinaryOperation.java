package ru.nsu.kutsenko.task113;

import java.util.Map;

abstract class BinaryOperation extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract String getOperator();

    @Override
    public String toString() {
        return "(" + left.toString() + getOperator() + right.toString() + ")";
    }
}