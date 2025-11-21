package ru.nsu.kutsenko.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки Book.
 */
public class BookTest {

    private Book book;
    private Grade mathExcellent;
    private Grade physicsGood;
    private Grade chemistrySatisfactory;

    @BeforeEach
    void setUp() {
        book = new Book(EducationForm.Form.BUDGET);

        mathExcellent = new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023);
        physicsGood = new Grade("Физика", GradeValue.Value.GOOD,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023);
        chemistrySatisfactory = new Grade("Химия", GradeValue.Value.SATISFACTORY,
            GradeType.Type.EXAM, Semester.Number.SECOND, 2023);
    }

    @Test
    void testCreateBook() {
        Book budgetBook = new Book(EducationForm.Form.BUDGET);
        assertEquals(EducationForm.Form.BUDGET, budgetBook.getEducationForm());
        assertTrue(budgetBook.getGrades().isEmpty());
    }

    @Test
    void testCreateBookWithNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null));
    }

    @Test
    void testAddOneGrade() {
        book.addGrade(mathExcellent);
        List<Grade> grades = book.getGrades();

        assertEquals(1, grades.size());
        assertEquals(mathExcellent, grades.get(0));
    }

    @Test
    void testAddThreeGrades() {
        book.addGrade(mathExcellent);
        book.addGrade(physicsGood);
        book.addGrade(chemistrySatisfactory);

        assertEquals(3, book.getGrades().size());
    }

    @Test
    void testAddNullGrade() {
        assertThrows(IllegalArgumentException.class, () -> book.addGrade(null));
    }

    @Test
    void testGetGradesCopy() {
        book.addGrade(mathExcellent);
        List<Grade> firstCopy = book.getGrades();
        List<Grade> secondCopy = book.getGrades();

        assertEquals(firstCopy.size(), secondCopy.size());
        assertNotSame(firstCopy, secondCopy);
    }

    @Test
    void testChangeEducationForm() {
        book.setEducationForm(EducationForm.Form.PAID);
        assertEquals(EducationForm.Form.PAID, book.getEducationForm());
    }

    @Test
    void testChangeToNullForm() {
        assertThrows(IllegalArgumentException.class, () -> book.setEducationForm(null));
    }

    @Test
    void testAverageNoGrades() {
        assertEquals(0.0, book.getAverageGrade(), 0.001);
    }

    @Test
    void testAverageOnlyNumberGrades() {
        book.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физика", GradeValue.Value.GOOD,
            GradeType.Type.DIFFERENTIATED_CREDIT, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Химия", GradeValue.Value.SATISFACTORY,
            GradeType.Type.COLLOQUIUM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физра", GradeValue.Value.EXCELLENT,
            GradeType.Type.CREDIT, Semester.Number.FIRST, 2023));

        assertEquals(4.0, book.getAverageGrade(), 0.001);
    }

    @Test
    void testTransferFromPaidToBudget() {
        Book paidBook = new Book(EducationForm.Form.PAID);

        paidBook.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        paidBook.addGrade(new Grade("Физика", GradeValue.Value.GOOD,
            GradeType.Type.EXAM, Semester.Number.SECOND, 2023));

        assertTrue(paidBook.canTransferToBudget());
    }

    @Test
    void testCannotTransferFromBudget() {
        assertFalse(book.canTransferToBudget());
    }

    @Test
    void testCannotTransferWithBadGrades() {
        Book paidBook = new Book(EducationForm.Form.PAID);

        paidBook.addGrade(new Grade("Математика", GradeValue.Value.SATISFACTORY,
            GradeType.Type.EXAM, Semester.Number.SECOND, 2023));

        assertFalse(paidBook.canTransferToBudget());
    }

    @Test
    void testCanGetRedDiploma() {
        book.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физика", GradeValue.Value.EXCELLENT,
            GradeType.Type.DIFFERENTIATED_CREDIT, Semester.Number.SECOND, 2023));
        book.addGrade(new Grade("ВКР", GradeValue.Value.EXCELLENT,
            GradeType.Type.VKR_DEFENSE, Semester.Number.EIGHTH, 2024));

        assertTrue(book.canGetRedDiploma());
    }

    @Test
    void testCannotGetRedDiplomaWithBadGrade() {
        book.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физика", GradeValue.Value.SATISFACTORY,
            GradeType.Type.EXAM, Semester.Number.SECOND, 2023));

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void testCanGetScholarship() {
        book.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физика", GradeValue.Value.EXCELLENT,
            GradeType.Type.DIFFERENTIATED_CREDIT, Semester.Number.FIRST, 2023));

        assertTrue(book.canGetIncreasedScholarship(Semester.Number.FIRST, 2023));
    }

    @Test
    void testCannotGetScholarshipWithBadGrade() {
        book.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физика", GradeValue.Value.SATISFACTORY,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));

        assertFalse(book.canGetIncreasedScholarship(Semester.Number.FIRST, 2023));
    }

    @Test
    void testScholarshipNullSemester() {
        assertThrows(IllegalArgumentException.class,
            () -> book.canGetIncreasedScholarship(null, 2023));
    }

    @Test
    void testAverageWithMixedGrades() {
        book.addGrade(new Grade("Математика", GradeValue.Value.EXCELLENT,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Физика", GradeValue.Value.GOOD,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));
        book.addGrade(new Grade("Химия", GradeValue.Value.GOOD,
            GradeType.Type.EXAM, Semester.Number.FIRST, 2023));

        assertEquals(4.333, book.getAverageGrade(), 0.001);
    }
}