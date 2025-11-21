package ru.nsu.kutsenko.task141;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс электронной зачетной книжки студента ФИТ.
 */
public class Book {
    private final List<Grade> grades;
    private EducationForm.Form educationForm;

    /**
     * Конструктор зачетной книжки.
     * @param educationForm форма обучения (бюджетная или платная).
     */
    public Book(EducationForm.Form educationForm) {
        if (educationForm == null) {
            throw new IllegalArgumentException("Форма обучения не может быть null");
        }
        this.educationForm = educationForm;
        this.grades = new ArrayList<>();
    }

    /**
     * Добавляет оценку в зачетную книжку.
     * @param grade оценка для добавления.
     */
    public void addGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Оценка не может быть null");
        }
        grades.add(grade);
    }

    /**
     * Получает список всех оценок.
     * @return список оценок.
     */
    public List<Grade> getGrades() {
        return new ArrayList<>(grades);
    }

    /**
     * Получает текущую форму обучения.
     * @return форма обучения.
     */
    public EducationForm.Form getEducationForm() {
        return educationForm;
    }

    /**
     * Устанавливает форму обучения.
     * @param educationForm форма обучения.
     */
    public void setEducationForm(EducationForm.Form educationForm) {
        if (educationForm == null) {
            throw new IllegalArgumentException("Форма обучения не может быть null");
        }
        this.educationForm = educationForm;
    }

    /**
     * Вычисляет текущий средний балл за все время обучения.
     * Учитываются только оценки с числовым значением.
     * @return средний балл или 0.0, если нет оценок с числовым значением.
     */
    public double getAverageGrade() {
        List<Grade> gradedGrades = grades.stream()
            .filter(grade -> grade.getType().isGradedType())
            .collect(Collectors.toList());

        if (gradedGrades.isEmpty()) {
            return 0.0;
        }

        double sum = gradedGrades.stream()
            .mapToDouble(Grade::getNumericGrade)
            .sum();

        return sum / gradedGrades.size();
    }

    /**
     * Проверяет возможность перевода с платной на бюджетную форму обучения.
     * Требование: отсутствие оценок "удовлетворительно" за последние две экзаменационные сессии.
     * (за экзамены, в дифференцированных зачетах допустимы оценки "удовлетворительно").
     * @return true, если возможен перевод на бюджетную форму обучения.
     */
    public boolean canTransferToBudget() {
        if (educationForm.isBudget()) {
            return false;
        }

        Map<Integer, List<Grade>> gradesBySemester = grades.stream()
            .filter(grade -> grade.getType().isExam())
            .collect(Collectors.groupingBy(
                grade -> grade.getYear() * 10 + grade.getSemesterNumber(),
                Collectors.toList()
            ));

        List<Integer> sessionKeys = gradesBySemester.keySet().stream()
            .sorted(Comparator.reverseOrder())
            .limit(2)
            .collect(Collectors.toList());

        if (sessionKeys.size() < 2) {
            return false;
        }

        for (Integer sessionKey : sessionKeys) {
            List<Grade> sessionGrades = gradesBySemester.get(sessionKey);
            for (Grade grade : sessionGrades) {
                if (grade.getType().isExam() && grade.getGrade().isSatisfactory()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Проверяет возможность получения «красного» диплома с отличием.
     * Требования.
     * - 75% оценок в приложении к диплому (последняя оценка) – "отлично".
     * - отсутствие итоговых оценок "удовлетворительно".
     * - квалификационная работа на "отлично".
     * @return true, если возможен красный диплом.
     */
    public boolean canGetRedDiploma() {

        Map<String, Grade> lastGradesBySubject = grades.stream()
            .collect(Collectors.toMap(
                Grade::getSubjectName,
                grade -> grade,
                (existing, replacement) -> {
                    int existingKey = existing.getYear() * 10 + existing.getSemesterNumber();
                    int replacementKey = replacement.getYear() * 10
                        + replacement.getSemesterNumber();
                    return replacementKey > existingKey ? replacement : existing;
                }
            ));

        List<Grade> diplomaGrades = new ArrayList<>(lastGradesBySubject.values());

        if (diplomaGrades.isEmpty()) {
            return false;
        }

        long excellentCount = diplomaGrades.stream()
            .filter(grade -> grade.getGrade().isExcellent())
            .count();

        double excellentPercentage = (double) excellentCount / diplomaGrades.size() * 100;
        if (excellentPercentage < 75.0) {
            return false;
        }

        boolean hasSatisfactoryInFinal = diplomaGrades.stream()
            .filter(grade -> grade.getType().isExamOrDifferentiatedCredit())
            .anyMatch(grade -> grade.getGrade().isSatisfactory());

        if (hasSatisfactoryInFinal) {
            return false;
        }

        boolean vkrIsExcellent = diplomaGrades.stream()
            .filter(grade -> grade.getType().isVKRDefense())
            .allMatch(grade -> grade.getGrade().isExcellent());

        boolean hasVKR = diplomaGrades.stream()
            .anyMatch(grade -> grade.getType().isVKRDefense());

        if (hasVKR && !vkrIsExcellent) {
            return false;
        }

        return true;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре.
     * Обычно требуется средний балл >= 4.5 и отсутствие оценок "удовлетворительно".
     * @param currentSemester текущий семестр.
     * @param currentYear текущий учебный год.
     * @return true, если возможна повышенная стипендия.
     */
    public boolean canGetIncreasedScholarship(Semester.Number currentSemester, int currentYear) {
        if (currentSemester == null) {
            throw new IllegalArgumentException("Текущий семестр не может быть null");
        }

        List<Grade> currentSemesterGrades = grades.stream()
            .filter(grade -> grade.getSemester() == currentSemester
                && grade.getYear() == currentYear)
            .filter(grade -> grade.getType().isGradedType())
            .collect(Collectors.toList());

        if (currentSemesterGrades.isEmpty()) {
            return false;
        }

        boolean hasSatisfactory = currentSemesterGrades.stream()
            .anyMatch(grade -> grade.getGrade().isSatisfactory());

        if (hasSatisfactory) {
            return false;
        }

        double average = currentSemesterGrades.stream()
            .mapToDouble(Grade::getNumericGrade)
            .average()
            .orElse(0.0);

        return average >= 4.5;
    }
}