package ru.nsu.kutsenko.task113;

import java.util.Map;

class Variable extends Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        Integer value = variables.get(name);
        if (value == null) {
            throw new RuntimeException("Variable '" + name + "' is not defined");
        }
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}