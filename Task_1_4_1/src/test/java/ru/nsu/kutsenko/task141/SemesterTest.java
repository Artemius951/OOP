package ru.nsu.kutsenko.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки Semester
 */
public class SemesterTest {

    @Test
    void testSemesterValues() {
        assertEquals(1, Semester.Number.FIRST.getValue());
        assertEquals(2, Semester.Number.SECOND.getValue());
        assertEquals(3, Semester.Number.THIRD.getValue());
        assertEquals(4, Semester.Number.FOURTH.getValue());
        assertEquals(5, Semester.Number.FIFTH.getValue());
        assertEquals(6, Semester.Number.SIXTH.getValue());
        assertEquals(7, Semester.Number.SEVENTH.getValue());
        assertEquals(8, Semester.Number.EIGHTH.getValue());
    }

    @Test
    void testFromValueValid() {
        assertEquals(Semester.Number.FIRST, Semester.Number.fromValue(1));
        assertEquals(Semester.Number.SECOND, Semester.Number.fromValue(2));
        assertEquals(Semester.Number.THIRD, Semester.Number.fromValue(3));
        assertEquals(Semester.Number.FOURTH, Semester.Number.fromValue(4));
        assertEquals(Semester.Number.FIFTH, Semester.Number.fromValue(5));
        assertEquals(Semester.Number.SIXTH, Semester.Number.fromValue(6));
        assertEquals(Semester.Number.SEVENTH, Semester.Number.fromValue(7));
        assertEquals(Semester.Number.EIGHTH, Semester.Number.fromValue(8));
    }

    @Test
    void testFromValueInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Semester.Number.fromValue(0));
        assertThrows(IllegalArgumentException.class, () -> Semester.Number.fromValue(9));
        assertThrows(IllegalArgumentException.class, () -> Semester.Number.fromValue(-1));
        assertThrows(IllegalArgumentException.class, () -> Semester.Number.fromValue(100));
    }

    @Test
    void testValuesOrder() {
        Semester.Number[] values = Semester.Number.values();
        assertEquals(8, values.length);
        assertEquals(Semester.Number.FIRST, values[0]);
        assertEquals(Semester.Number.SECOND, values[1]);
        assertEquals(Semester.Number.THIRD, values[2]);
        assertEquals(Semester.Number.FOURTH, values[3]);
        assertEquals(Semester.Number.FIFTH, values[4]);
        assertEquals(Semester.Number.SIXTH, values[5]);
        assertEquals(Semester.Number.SEVENTH, values[6]);
        assertEquals(Semester.Number.EIGHTH, values[7]);
    }

    @Test
    void testAllValuesHaveUniqueNumbers() {
        long uniqueValues = java.util.Arrays.stream(Semester.Number.values())
            .mapToInt(Semester.Number::getValue)
            .distinct()
            .count();
        assertEquals(Semester.Number.values().length, uniqueValues);
    }

    @Test
    void testBoundaryValues() {
        assertEquals(1, Semester.Number.FIRST.getValue());
        assertEquals(Semester.Number.FIRST, Semester.Number.fromValue(1));
        assertEquals(8, Semester.Number.EIGHTH.getValue());
        assertEquals(Semester.Number.EIGHTH, Semester.Number.fromValue(8));
    }

    @Test
    void testMiddleSemesters() {
        assertEquals(4, Semester.Number.FOURTH.getValue());
        assertEquals(5, Semester.Number.FIFTH.getValue());

        assertEquals(Semester.Number.FOURTH, Semester.Number.fromValue(4));
        assertEquals(Semester.Number.FIFTH, Semester.Number.fromValue(5));
    }

    @Test
    void testExceptionMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> Semester.Number.fromValue(0));

        String expectedMessage = "Номер семестра должен быть от 1 до 8, получено: 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testAllSemestersCovered() {
        for (int i = 1; i <= 8; i++) {
            Semester.Number semester = Semester.Number.fromValue(i);
            assertEquals(i, semester.getValue());
        }
    }

    @Test
    void testEnumConsistency() {
        for (Semester.Number semester : Semester.Number.values()) {
            int value = semester.getValue();
            Semester.Number fromValue = Semester.Number.fromValue(value);
            assertEquals(semester, fromValue);
        }
    }
}