package ru.nsu.kutsenko.task113;

import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный класс для представления математических выражений.
 * Определяет общий интерфейс для всех типов выражений.
 */
public abstract class Expression {
    /**
     * Вычисляет производную выражения по заданной переменной.
     *
     * @param variable переменная, по которой вычисляется производная
     * @return выражение, представляющее производную
     */
    public abstract Expression derivative(String variable);

    /**
     * Вычисляет значение выражения с использованием заданных переменных.
     *
     * @param variables карта переменных и их значений
     * @return результат вычисления выражения
     */
    public abstract int eval(Map<String, Integer> variables);

    /**
     * Вычисляет значение выражения с использованием строки с переменными.
     * Формат строки: "x=5; y=10; z=15"
     *
     * @param variablesStr строка с присваиваниями переменных
     * @return результат вычисления выражения
     */
    public int eval(String variablesStr) {
        Map<String, Integer> variables = parseVariables(variablesStr);
        return eval(variables);
    }

    /**
     * Возвращает строковое представление выражения.
     *
     * @return строковое представление выражения
     */
    public abstract String toString();

    /**
     * Статический метод для парсинга строки в выражение.
     *
     * @param expression строка с математическим выражением
     * @return объект Expression, представляющий распарсенное выражение
     */
    public static Expression parse(String expression) {
        ExpressionParser parser = new ExpressionParser();
        return parser.parse(expression);
    }

    /**
     * Выводит строковое представление выражения в стандартный вывод.
     */
    public void print() {
        System.out.println(this.toString());
    }

    /**
     * Парсит строку с переменными в карту значений.
     *
     * @param variablesStr строка в формате "var1=value1; var2=value2"
     * @return карта переменных и их значений
     */
    private Map<String, Integer> parseVariables(String variablesStr) {
        Map<String, Integer> variables = new HashMap<>();
        if (variablesStr == null || variablesStr.trim().isEmpty()) {
            return variables;
        }

        String[] assignments = variablesStr.split(";");
        for (String assignment : assignments) {
            assignment = assignment.trim();
            if (assignment.isEmpty()) {
                continue;
            }

            String[] parts = assignment.split("=");
            if (parts.length == 2) {
                String varName = parts[0].trim();
                try {
                    int value = Integer.parseInt(parts[1].trim());
                    variables.put(varName, value);
                } catch (NumberFormatException e) {
                    System.err.println("Warning invalid number format for variable:" + assignment);
                }
            }
        }
        return variables;
    }
}