package ru.nsu.kutsenko.task231;
import java.util.Objects;

/**
 * Представляет клетку на координатной плоскости с координатами X и Y.
 */
public final class Cell {
    public final int x;
    public final int y;

    /**
     * Создает клетку с заданными координатами.
     *
     * @param x координата по оси X.
     * @param y координата по оси Y.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает новую клетку, смещенную относительно текущей на заданные значения.
     *
     * @param dx смещение по оси X.
     * @param dy смещение по оси Y.
     * @return новая клетка с координатами (x + dx, y + dy).
     */
    public Cell add(int dx, int dy) {
        return new Cell(x + dx, y + dy);
    }

    /**
     * Проверяет равенство объектов по координатам.
     * @param o объект для сравнения.
     * @return true, если координаты совпадают, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    /**
     * Вычисляет хэш-код на основе координат клетки.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Возвращает строковое представление клетки в формате "x,y".
     * @return строка с координатами.
     */
    @Override
    public String toString() {
        return x + "," + y;
    }
}