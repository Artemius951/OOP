package ru.nsu.kutsenko.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки EducationForm.
 */
public class EducationFormTest {

    @Test
    void testFormDescriptions() {
        assertEquals("бюджетная", EducationForm.Form.BUDGET.getDescription());
        assertEquals("платная", EducationForm.Form.PAID.getDescription());
    }

    @Test
    void testIsBudget() {
        assertTrue(EducationForm.Form.BUDGET.isBudget());
        assertFalse(EducationForm.Form.PAID.isBudget());
    }

    @Test
    void testIsPaid() {
        assertTrue(EducationForm.Form.PAID.isPaid());
        assertFalse(EducationForm.Form.BUDGET.isPaid());
    }

    @Test
    void testFromDescriptionValid() {
        assertEquals(EducationForm.Form.BUDGET, EducationForm.fromDescription("бюджетная"));
        assertEquals(EducationForm.Form.PAID, EducationForm.fromDescription("платная"));
    }

    @Test
    void testFromDescriptionInvalid() {
        assertThrows(IllegalArgumentException.class, () -> EducationForm.fromDescription(""));
        assertThrows(IllegalArgumentException.class, () -> EducationForm.fromDescription("бюджет"));
        assertThrows(IllegalArgumentException.class, () -> EducationForm.fromDescription("платно"));
        assertThrows(IllegalArgumentException.class, () -> EducationForm.fromDescription("unknown"));
        assertThrows(IllegalArgumentException.class, () -> EducationForm.fromDescription(null));
    }

    @Test
    void testValuesOrder() {
        EducationForm.Form[] values = EducationForm.Form.values();
        assertEquals(2, values.length);
        assertEquals(EducationForm.Form.BUDGET, values[0]);
        assertEquals(EducationForm.Form.PAID, values[1]);
    }

    @Test
    void testAllValuesHaveUniqueDescriptions() {
        long uniqueDescriptions = java.util.Arrays.stream(EducationForm.Form.values())
            .map(EducationForm.Form::getDescription)
            .distinct()
            .count();
        assertEquals(EducationForm.Form.values().length, uniqueDescriptions);
    }

    @Test
    void testBudgetFormProperties() {
        EducationForm.Form budget = EducationForm.Form.BUDGET;
        assertEquals("бюджетная", budget.getDescription());
        assertTrue(budget.isBudget());
        assertFalse(budget.isPaid());
    }

    @Test
    void testPaidFormProperties() {
        EducationForm.Form paid = EducationForm.Form.PAID;
        assertEquals("платная", paid.getDescription());
        assertTrue(paid.isPaid());
        assertFalse(paid.isBudget());
    }

    @Test
    void testExceptionMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> EducationForm.fromDescription("неизвестная форма"));

        String expectedMessage = "Неизвестная форма обучения: неизвестная форма";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testMutualExclusion() {
        for (EducationForm.Form form : EducationForm.Form.values()) {
            if (form.isBudget()) {
                assertFalse(form.isPaid());
            }
            if (form.isPaid()) {
                assertFalse(form.isBudget());
            }
        }
    }

    @Test
    void testCaseSensitivity() {
        assertThrows(IllegalArgumentException.class, () ->
            EducationForm.fromDescription("Бюджетная"));
        assertThrows(IllegalArgumentException.class, () ->
            EducationForm.fromDescription("ПЛАТНАЯ"));
    }
}