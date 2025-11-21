package ru.nsu.kutsenko.task141;

/**
 * Класс для представления оценок.
 * Содержит enum Value для различных оценок.
 */
public class GradeValue {

    /**
     * Enum для оценок.
     */
    public enum Value {
        EXCELLENT(5, "отлично"),
        GOOD(4, "хорошо"),
        SATISFACTORY(3, "удовлетворительно"),
        UNSATISFACTORY(2, "неудовлетворительно");

        private final int numericValue;
        private final String description;

        Value(int numericValue, String description) {
            this.numericValue = numericValue;
            this.description = description;
        }

        public int getNumericValue() {
            return numericValue;
        }

        public String getDescription() {
            return description;
        }

        public boolean isExcellent() {
            return this == EXCELLENT;
        }

        public boolean isGood() {
            return this == GOOD;
        }

        public boolean isSatisfactory() {
            return this == SATISFACTORY;
        }

        public boolean isUnsatisfactory() {
            return this == UNSATISFACTORY;
        }

        public static Value fromNumeric(int value) {
            for (Value grade : values()) {
                if (grade.numericValue == value) {
                    return grade;
                }
            }
            throw new IllegalArgumentException("Неверное числовое значение оценки: " + value);
        }
    }
}