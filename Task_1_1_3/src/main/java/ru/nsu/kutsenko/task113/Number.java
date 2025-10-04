package ru.nsu.kutsenko.task113;

import java.util.Map;

/**
 * Класс для представления числовых констант в выражениях.
 */
public class Number extends Expression {
    private int value;

    /**
     * Конструктор числовой константы.
     *
     * @param value числовое значение
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Вычисляет производную числовой константы.
     * Производная константы всегда равна нулю.
     *
     * @param variable переменная, по которой вычисляется производная
     * @return ноль (Number(0))
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * Возвращает значение числовой константы.
     *
     * @param variables карта переменных (не используется)
     * @return числовое значение константы
     */
    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    /**
     * Возвращает строковое представление числа.
     *
     * @return строковое представление числа
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Возвращает значение числа.
     *
     * @return числовое значение
     */
    public int getValue() {
        return value;
    }
}