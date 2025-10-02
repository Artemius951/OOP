package ru.nsu.kutsenko.task113;

import java.util.HashMap;
import java.util.Map;

abstract class Expression {
    public abstract Expression derivative(String variable);
    public abstract int eval(Map<String, Integer> variables);
    public abstract String toString();

    public int eval(String variablesStr) {
        Map<String, Integer> variables = parseVariables(variablesStr);
        return eval(variables);
    }

    private Map<String, Integer> parseVariables(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();
        if (variablesStr == null || variablesStr.trim().isEmpty()) {
            return variables;
        }

        String[] assignments = variablesStr.split(";");
        for (String assignment : assignments) {
            assignment = assignment.trim();
            if (assignment.isEmpty()) continue;

            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                try {
                    int value = Integer.parseInt(parts[1].trim());
                    variables.put(varName, value);
                } catch (NumberFormatException e) {
                }
            }
        }
        return variables;
    }

    public static Expression parse(String expression) {
        ExpressionParser parser = new ExpressionParser();
        return parser.parse(expression);
    }

    public void print() {
        System.out.println(this.toString());
    }
}