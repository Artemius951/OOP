package ru.nsu.kutsenko.task231;

import java.util.Objects;

/**
 * Представляет одну ячейку на игровом поле.
 */
public final class Cell {
    /**
     * X координата ячейки.
     */
    public final int x;

    /**
     * Y координата ячейки.
     */
    public final int y;

    /**
     * Создает ячейку с заданными координатами.
     *
     * @param x координата X
     * @param y координата Y
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Возвращает новую ячейку смещенну�� на заданные дельты.
     *
     * @param dx смещение по X
     * @param dy смещение по Y
     * @return новая ячейка с скорректированными координатами
     */
    public Cell add(int dx, int dy) {
        return new Cell(x + dx, y + dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}