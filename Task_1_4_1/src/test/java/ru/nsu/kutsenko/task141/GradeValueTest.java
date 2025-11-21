package ru.nsu.kutsenko.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки GradeValue
 */
public class GradeValueTest {

    @Test
    void testNumericValues() {
        assertEquals(5, GradeValue.Value.EXCELLENT.getNumericValue());
        assertEquals(4, GradeValue.Value.GOOD.getNumericValue());
        assertEquals(3, GradeValue.Value.SATISFACTORY.getNumericValue());
        assertEquals(2, GradeValue.Value.UNSATISFACTORY.getNumericValue());
    }

    @Test
    void testDescriptions() {
        assertEquals("отлично", GradeValue.Value.EXCELLENT.getDescription());
        assertEquals("хорошо", GradeValue.Value.GOOD.getDescription());
        assertEquals("удовлетворительно", GradeValue.Value.SATISFACTORY.getDescription());
        assertEquals("неудовлетворительно",
            GradeValue.Value.UNSATISFACTORY.getDescription());
    }

    @Test
    void testIsExcellent() {
        assertTrue(GradeValue.Value.EXCELLENT.isExcellent());
        assertFalse(GradeValue.Value.GOOD.isExcellent());
        assertFalse(GradeValue.Value.SATISFACTORY.isExcellent());
        assertFalse(GradeValue.Value.UNSATISFACTORY.isExcellent());
    }

    @Test
    void testIsGood() {
        assertTrue(GradeValue.Value.GOOD.isGood());
        assertFalse(GradeValue.Value.EXCELLENT.isGood());
        assertFalse(GradeValue.Value.SATISFACTORY.isGood());
        assertFalse(GradeValue.Value.UNSATISFACTORY.isGood());
    }

    @Test
    void testIsSatisfactory() {
        assertTrue(GradeValue.Value.SATISFACTORY.isSatisfactory());
        assertFalse(GradeValue.Value.EXCELLENT.isSatisfactory());
        assertFalse(GradeValue.Value.GOOD.isSatisfactory());
        assertFalse(GradeValue.Value.UNSATISFACTORY.isSatisfactory());
    }

    @Test
    void testIsUnsatisfactory() {
        assertTrue(GradeValue.Value.UNSATISFACTORY.isUnsatisfactory());
        assertFalse(GradeValue.Value.EXCELLENT.isUnsatisfactory());
        assertFalse(GradeValue.Value.GOOD.isUnsatisfactory());
        assertFalse(GradeValue.Value.SATISFACTORY.isUnsatisfactory());
    }

    @Test
    void testFromNumericValid() {
        assertEquals(GradeValue.Value.EXCELLENT, GradeValue.Value.fromNumeric(5));
        assertEquals(GradeValue.Value.GOOD, GradeValue.Value.fromNumeric(4));
        assertEquals(GradeValue.Value.SATISFACTORY, GradeValue.Value.fromNumeric(3));
        assertEquals(GradeValue.Value.UNSATISFACTORY, GradeValue.Value.fromNumeric(2));
    }

    @Test
    void testFromNumericInvalid() {
        assertThrows(IllegalArgumentException.class, () -> GradeValue.Value.fromNumeric(1));
        assertThrows(IllegalArgumentException.class, () -> GradeValue.Value.fromNumeric(6));
        assertThrows(IllegalArgumentException.class, () -> GradeValue.Value.fromNumeric(0));
        assertThrows(IllegalArgumentException.class, () -> GradeValue.Value.fromNumeric(-1));
        assertThrows(IllegalArgumentException.class, () -> GradeValue.Value.fromNumeric(100));
    }

    @Test
    void testValuesOrder() {
        GradeValue.Value[] values = GradeValue.Value.values();
        assertEquals(4, values.length);
        assertEquals(GradeValue.Value.EXCELLENT, values[0]);
        assertEquals(GradeValue.Value.GOOD, values[1]);
        assertEquals(GradeValue.Value.SATISFACTORY, values[2]);
        assertEquals(GradeValue.Value.UNSATISFACTORY, values[3]);
    }

    @Test
    void testAllValuesHaveUniqueNumericValues() {
        long uniqueNumericValues = java.util.Arrays.stream(GradeValue.Value.values())
            .mapToInt(GradeValue.Value::getNumericValue)
            .distinct()
            .count();
        assertEquals(GradeValue.Value.values().length, uniqueNumericValues);
    }

    @Test
    void testAllValuesHaveUniqueDescriptions() {
        long uniqueDescriptions = java.util.Arrays.stream(GradeValue.Value.values())
            .map(GradeValue.Value::getDescription)
            .distinct()
            .count();
        assertEquals(GradeValue.Value.values().length, uniqueDescriptions);
    }
}