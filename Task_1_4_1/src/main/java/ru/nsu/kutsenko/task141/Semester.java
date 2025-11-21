package ru.nsu.kutsenko.task141;

/**
 * Класс для представления номеров семестров.
 * Содержит enum Number для семестров от 1 до 8.
 */
public class Semester {

    /**
     * Enum для номеров семестров.
     */
    public enum Number {
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4),
        FIFTH(5),
        SIXTH(6),
        SEVENTH(7),
        EIGHTH(8);

        private final int value;

        Number(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        /**
         * Получает номер семестра по числовому значению.
         */
        public static Number fromValue(int value) {
            for (Number semester : values()) {
                if (semester.value == value) {
                    return semester;
                }
            }
            throw new IllegalArgumentException("Номер семестра должен быть от 1 до 8, получено: "
                + value);
        }
    }
}