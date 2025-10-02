package ru.nsu.kutsenko.task113;

import java.util.Map;

/**
 * Класс для представления переменных в выражениях.
 */
public class Variable extends Expression {
    private String name;

    /**
     * Конструктор переменной.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Вычисляет производную переменной.
     * Производная по самой переменной равна 1, по другим переменным - 0.
     *
     * @param variable переменная, по которой вычисляется производная
     * @return 1 если переменная совпадает с name, иначе 0
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    /**
     * Возвращает значение переменной из карты переменных.
     *
     * @param variables карта переменных и их значений
     * @return значение переменной
     * @throws UndefinedVariableException если переменная не определена
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        Integer value = variables.get(name);
        if (value == null) {
            throw new UndefinedVariableException(name);
        }
        return value;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает имя переменной.
     *
     * @return имя переменной
     */
    public String getName() {
        return name;
    }
}