package ru.nsu.kutsenko.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки GradeType.
 */
public class GradeTypeTest {

    @Test
    void testTypeDescriptions() {
        assertEquals("задание", GradeType.Type.ASSIGNMENT.getDescription());
        assertEquals("контрольная", GradeType.Type.TEST.getDescription());
        assertEquals("коллоквиум", GradeType.Type.COLLOQUIUM.getDescription());
        assertEquals("экзамен", GradeType.Type.EXAM.getDescription());
        assertEquals("дифференцированный зачет",
            GradeType.Type.DIFFERENTIATED_CREDIT.getDescription());
        assertEquals("зачет", GradeType.Type.CREDIT.getDescription());
        assertEquals("защита отчёта по практике",
            GradeType.Type.PRACTICE_REPORT_DEFENSE.getDescription());
        assertEquals("защита ВКР", GradeType.Type.VKR_DEFENSE.getDescription());
    }

    @Test
    void testIsExam() {
        assertTrue(GradeType.Type.EXAM.isExam());
        assertFalse(GradeType.Type.DIFFERENTIATED_CREDIT.isExam());
        assertFalse(GradeType.Type.ASSIGNMENT.isExam());
    }

    @Test
    void testIsDifferentiatedCredit() {
        assertTrue(GradeType.Type.DIFFERENTIATED_CREDIT.isDifferentiatedCredit());
        assertFalse(GradeType.Type.EXAM.isDifferentiatedCredit());
        assertFalse(GradeType.Type.CREDIT.isDifferentiatedCredit());
    }

    @Test
    void testIsCredit() {
        assertTrue(GradeType.Type.CREDIT.isCredit());
        assertFalse(GradeType.Type.DIFFERENTIATED_CREDIT.isCredit());
        assertFalse(GradeType.Type.EXAM.isCredit());
    }

    @Test
    void testIsVKRDefense() {
        assertTrue(GradeType.Type.VKR_DEFENSE.isVKRDefense());
        assertFalse(GradeType.Type.PRACTICE_REPORT_DEFENSE.isVKRDefense());
        assertFalse(GradeType.Type.EXAM.isVKRDefense());
    }

    @Test
    void testIsGradedType() {
        assertTrue(GradeType.Type.EXAM.isGradedType());
        assertTrue(GradeType.Type.DIFFERENTIATED_CREDIT.isGradedType());
        assertTrue(GradeType.Type.COLLOQUIUM.isGradedType());
        assertFalse(GradeType.Type.CREDIT.isGradedType());
        assertFalse(GradeType.Type.ASSIGNMENT.isGradedType());
        assertFalse(GradeType.Type.TEST.isGradedType());
    }

    @Test
    void testIsExamOrDifferentiatedCredit() {
        assertTrue(GradeType.Type.EXAM.isExamOrDifferentiatedCredit());
        assertTrue(GradeType.Type.DIFFERENTIATED_CREDIT.isExamOrDifferentiatedCredit());
        assertFalse(GradeType.Type.COLLOQUIUM.isExamOrDifferentiatedCredit());
        assertFalse(GradeType.Type.CREDIT.isExamOrDifferentiatedCredit());
        assertFalse(GradeType.Type.ASSIGNMENT.isExamOrDifferentiatedCredit());
    }

    @Test
    void testFromDescriptionValid() {
        assertEquals(GradeType.Type.EXAM, GradeType.fromDescription("экзамен"));
        assertEquals(GradeType.Type.DIFFERENTIATED_CREDIT, GradeType.fromDescription("дифференцированный зачет"));
        assertEquals(GradeType.Type.VKR_DEFENSE, GradeType.fromDescription("защита ВКР"));
    }

    @Test
    void testFromDescriptionInvalid() {
        assertThrows(IllegalArgumentException.class,
            () -> GradeType.fromDescription("неизвестный тип"));
        assertThrows(IllegalArgumentException.class, () -> GradeType.fromDescription(""));
        assertThrows(IllegalArgumentException.class, () -> GradeType.fromDescription(null));
    }

    @Test
    void testAllTypesHaveUniqueDescriptions() {

        long uniqueDescriptions = java.util.Arrays.stream(GradeType.Type.values())
            .map(GradeType.Type::getDescription)
            .distinct()
            .count();
        assertEquals(GradeType.Type.values().length, uniqueDescriptions);
    }

    @Test
    void testPracticeReportDefenseProperties() {
        GradeType.Type practiceDefense = GradeType.Type.PRACTICE_REPORT_DEFENSE;
        assertEquals("защита отчёта по практике", practiceDefense.getDescription());
        assertFalse(practiceDefense.isExam());
        assertFalse(practiceDefense.isGradedType());
        assertFalse(practiceDefense.isVKRDefense());
    }

    @Test
    void testColloquiumProperties() {
        GradeType.Type colloquium = GradeType.Type.COLLOQUIUM;
        assertEquals("коллоквиум", colloquium.getDescription());
        assertFalse(colloquium.isExam());
        assertTrue(colloquium.isGradedType());
        assertFalse(colloquium.isExamOrDifferentiatedCredit());
    }
}