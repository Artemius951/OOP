package ru.nsu.kutsenko.task141;

import java.util.Arrays;

/**
 * Класс для представления формы обучения.
 * Содержит enum Form для различных форм обучения.
 */
public class EducationForm {

    /**
     * Enum для форм обучения.
     */
    public enum Form {
        BUDGET("бюджетная"),
        PAID("платная");

        private final String description;

        Form(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public boolean isBudget() {
            return this == BUDGET;
        }

        public boolean isPaid() {
            return this == PAID;
        }
    }

    /**
     * Получает форму обучения по строковому описанию.
     */
    public static Form fromDescription(String description) {
        return Arrays.stream(Form.values())
            .filter(form -> form.getDescription().equals(description))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Неизвестная форма обучения: " +
                description));
    }
}