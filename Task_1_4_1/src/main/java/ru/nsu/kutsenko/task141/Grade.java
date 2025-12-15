package ru.nsu.kutsenko.task141;

/**
 * Класс для представления оценки по предмету.
 */
public class Grade {
    private final String subjectName;
    private final GradeValue.Value grade;
    private final GradeType.Type type;
    private final Semester.Number semester;
    private final int year;

    /**
     * Конструктор для создания оценки по предмету.
     *
     * @param subjectName название предмета
     * @param grade оценка
     * @param type тип контроля
     * @param semester семестр
     * @param year учебный год
     * @throws IllegalArgumentException если любой из параметров null или subjectName пустой
     */
    public Grade(String subjectName, GradeValue.Value grade,
                 GradeType.Type type, Semester.Number semester, int year) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Название предмета не может быть пустым");
        }
        if (grade == null) {
            throw new IllegalArgumentException("Оценка не может быть null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Тип контроля не может быть null");
        }
        if (semester == null) {
            throw new IllegalArgumentException("Семестр не может быть null");
        }

        this.subjectName = subjectName.trim();
        this.grade = grade;
        this.type = type;
        this.semester = semester;
        this.year = year;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public GradeValue.Value getGrade() {
        return grade;
    }

    public GradeType.Type getType() {
        return type;
    }

    public Semester.Number getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    /**
     * Получает числовое значение оценки.
     */
    public int getNumericGrade() {
        return grade.getNumericValue();
    }

    /**
     * Получает числовое значение номера семестра.
     */
    public int getSemesterNumber() {
        return semester.getValue();
    }

    @Override
    public String toString() {
        return String.format("%s: %s (%s) - %d семестр, %d год",
            subjectName, grade.getDescription(), type.getDescription(),
            semester.getValue(), year);
    }
}