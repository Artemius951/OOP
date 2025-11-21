package ru.nsu.kutsenko.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Тестовый класс для проверки Grade.
 */
public class GradeTest {

    private Grade validGrade;
    private String testSubjectName;
    private GradeValue.Value testGradeValue;
    private GradeType.Type testGradeType;
    private Semester.Number testSemester;
    private int testYear;

    @BeforeEach
    void setUp() {
        testSubjectName = "Математика";
        testGradeValue = GradeValue.Value.EXCELLENT;
        testGradeType = GradeType.Type.EXAM;
        testSemester = Semester.Number.FIRST;
        testYear = 2023;

        validGrade = new Grade(testSubjectName, testGradeValue, testGradeType, testSemester, testYear);
    }

    @Test
    void testConstructorValidParameters() {
        assertEquals(testSubjectName, validGrade.getSubjectName());
        assertEquals(testGradeValue, validGrade.getGrade());
        assertEquals(testGradeType, validGrade.getType());
        assertEquals(testSemester, validGrade.getSemester());
        assertEquals(testYear, validGrade.getYear());
    }

    @Test
    void testConstructorTrimsSubjectName() {
        Grade grade = new Grade("  Программирование  ", GradeValue.Value.SATISFACTORY,
            GradeType.Type.CREDIT, Semester.Number.THIRD, 2023);

        assertEquals("Программирование", grade.getSubjectName());
    }

    @Test
    void testConstructorNullSubjectName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Grade(null, testGradeValue, testGradeType, testSemester, testYear));
    }

    @Test
    void testConstructorEmptySubjectName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Grade("", testGradeValue, testGradeType, testSemester, testYear));
    }

    @Test
    void testConstructorBlankSubjectName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Grade("   ", testGradeValue, testGradeType, testSemester, testYear));
    }

    @Test
    void testConstructorNullGrade() {
        assertThrows(IllegalArgumentException.class, () ->
            new Grade(testSubjectName, null, testGradeType, testSemester, testYear));
    }

    @Test
    void testConstructorNullType() {
        assertThrows(IllegalArgumentException.class, () ->
            new Grade(testSubjectName, testGradeValue, null, testSemester, testYear));
    }

    @Test
    void testConstructorNullSemester() {
        assertThrows(IllegalArgumentException.class, () ->
            new Grade(testSubjectName, testGradeValue, testGradeType, null, testYear));
    }

    @Test
    void testGetters() {
        assertEquals(testSubjectName, validGrade.getSubjectName());
        assertEquals(testGradeValue, validGrade.getGrade());
        assertEquals(testGradeType, validGrade.getType());
        assertEquals(testSemester, validGrade.getSemester());
        assertEquals(testYear, validGrade.getYear());
    }

    @Test
    void testGetNumericGrade() {
        assertEquals(5, validGrade.getNumericGrade());

        Grade goodGrade = new Grade("Физика", GradeValue.Value.GOOD, GradeType.Type.EXAM,
            Semester.Number.FIRST, 2023);
        assertEquals(4, goodGrade.getNumericGrade());

        Grade satisfactoryGrade = new Grade("Химия", GradeValue.Value.SATISFACTORY,
            GradeType.Type.CREDIT, Semester.Number.FIRST, 2023);
        assertEquals(3, satisfactoryGrade.getNumericGrade());

        Grade unsatisfactoryGrade = new Grade("История", GradeValue.Value.UNSATISFACTORY,
            GradeType.Type.TEST, Semester.Number.FIRST, 2023);
        assertEquals(2, unsatisfactoryGrade.getNumericGrade());
    }

    @Test
    void testGetSemesterNumber() {
        assertEquals(1, validGrade.getSemesterNumber());

        Grade secondSemesterGrade = new Grade("Физика", GradeValue.Value.GOOD,
            GradeType.Type.EXAM, Semester.Number.SECOND, 2023);
        assertEquals(2, secondSemesterGrade.getSemesterNumber());

        Grade eighthSemesterGrade = new Grade("ВКР", GradeValue.Value.EXCELLENT,
            GradeType.Type.VKR_DEFENSE, Semester.Number.EIGHTH, 2024);
        assertEquals(8, eighthSemesterGrade.getSemesterNumber());
    }

    @Test
    void testToString() {
        String result = validGrade.toString();

        assertTrue(result.contains(testSubjectName));
        assertTrue(result.contains("отлично"));
        assertTrue(result.contains("экзамен"));
        assertTrue(result.contains("1 семестр"));
        assertTrue(result.contains("2023 год"));

        String expectedFormat = String.format("%s: %s (%s) - %d семестр, %d год",
            testSubjectName,
            testGradeValue.getDescription(),
            testGradeType.getDescription(),
            testSemester.getValue(),
            testYear);

        assertEquals(expectedFormat, result);
    }

    @Test
    void testToStringWithDifferentValues() {
        Grade grade = new Grade("Программирование", GradeValue.Value.SATISFACTORY,
            GradeType.Type.CREDIT, Semester.Number.FOURTH, 2024);

        String result = grade.toString();

        assertTrue(result.contains("Программирование"));
        assertTrue(result.contains("удовлетворительно"));
        assertTrue(result.contains("зачет"));
        assertTrue(result.contains("4 семестр"));
        assertTrue(result.contains("2024 год"));
    }

    @Test
    void testAllGradeValues() {
        for (GradeValue.Value gradeValue : GradeValue.Value.values()) {
            for (GradeType.Type gradeType : GradeType.Type.values()) {
                for (Semester.Number semester : Semester.Number.values()) {
                    Grade grade = new Grade("Тестовый предмет", gradeValue,
                        gradeType, semester, 2023);

                    assertEquals("Тестовый предмет", grade.getSubjectName());
                    assertEquals(gradeValue, grade.getGrade());
                    assertEquals(gradeType, grade.getType());
                    assertEquals(semester, grade.getSemester());
                    assertEquals(2023, grade.getYear());
                    assertEquals(gradeValue.getNumericValue(), grade.getNumericGrade());
                    assertEquals(semester.getValue(), grade.getSemesterNumber());
                }
            }
        }
    }

    @Test
    void testDifferentYears() {
        int[] years = {2020, 2021, 2022, 2023, 2024, 2025};

        for (int year : years) {
            Grade grade = new Grade("Предмет", GradeValue.Value.GOOD,
                GradeType.Type.EXAM, Semester.Number.FIRST, year);
            assertEquals(year, grade.getYear());
        }
    }

    @Test
    void testEdgeCaseSubjectNames() {
        String[] subjectNames = {
            "А",
            "Очень длинное название предмета которое может быть очень очень очень длинным",
            "Subject with English",
            "123",
            "Предмет с цифрами 123",
            "Предмет со спецсимволами !@#$%"
        };

        for (String subjectName : subjectNames) {
            Grade grade = new Grade(subjectName, testGradeValue, testGradeType, testSemester,
                testYear);
            assertEquals(subjectName.trim(), grade.getSubjectName());
        }
    }
}