package ru.nsu.kutsenko.task141;

import java.util.Arrays;

/**
 * Класс для типов контроля знаний.
 * Содержит enum Type для представления различных типов контроля.
 */
public class GradeType {

    /**
     * Enum для типов контроля знаний.
     */
    public enum Type {
        ASSIGNMENT("задание"),
        TEST("контрольная"),
        COLLOQUIUM("коллоквиум"),
        EXAM("экзамен"),
        DIFFERENTIATED_CREDIT("дифференцированный зачет"),
        CREDIT("зачет"),
        PRACTICE_REPORT_DEFENSE("защита отчёта по практике"),
        VKR_DEFENSE("защита ВКР");

        private final String description;

        Type(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public boolean isExam() {
            return this == EXAM;
        }

        public boolean isDifferentiatedCredit() {
            return this == DIFFERENTIATED_CREDIT;
        }

        public boolean isCredit() {
            return this == CREDIT;
        }

        public boolean isVkrDefense() {
            return this == VKR_DEFENSE;
        }

        public boolean isGradedType() {
            return this == EXAM || this == DIFFERENTIATED_CREDIT || this == COLLOQUIUM;
        }

        public boolean isExamOrDifferentiatedCredit() {
            return this == EXAM || this == DIFFERENTIATED_CREDIT;
        }
    }

    /**
     * Получает тип контроля по строковому описанию.
     */
    public static Type fromDescription(String description) {
        return Arrays.stream(Type.values())
            .filter(type -> type.getDescription().equals(description))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Неизвестный тип контроля: " +
                description));
    }
}