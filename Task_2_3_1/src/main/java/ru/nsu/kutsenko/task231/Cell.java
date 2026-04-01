package ru.nsu.kutsenko.task231;

import java.util.Objects;

/**
 * Представляет одну ячейку на игровом поле.
 */
public final class Cell {
    /**
     * X координата ячейки.
     */
    public final int cellx;

    /**
     * Y координата ячейки.
     */
    public final int celly;

    /**
     * Создает ячейку с заданными координатами.
     *
     * @param cellx координата X
     * @param celly координата Y
     */
    public Cell(int cellx, int celly) {
        this.cellx = cellx;
        this.celly = celly;
    }

    /**
     * Возвращает новую ячейку смещенную на заданные дельты.
     *
     * @param dx смещение по X
     * @param dy смещение по Y
     * @return новая ячейка с скорректированными координатами
     */
    public Cell add(int dx, int dy) {
        return new Cell(cellx + dx, celly + dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cell)) {
            return false;
        }
        Cell cell = (Cell) o;
        return cellx == cell.cellx && celly == cell.celly;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellx, celly);
    }

    @Override
    public String toString() {
        return cellx + "," + celly;
    }
}