package ru.nsu.kutsenko.task231;

import java.util.Objects;

/**
 * Представляет одну ячейку на игровом поле.
 */
public final class Cell {
    /**
     * X координата ячейки.
     */
    public final int xCoord;

    /**
     * Y координата ячейки.
     */
    public final int yCoord;

    /**
     * Создает ячейку с заданными координатами.
     *
     * @param x координата X
     * @param y координата Y
     */
    public Cell(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
    }

    /**
     * Возвращает новую ячейку смещенную на заданные дельты.
     *
     * @param dx смещение по X
     * @param dy смещение по Y
     * @return новая ячейка с скорректированными координатами
     */
    public Cell add(int dx, int dy) {
        return new Cell(xCoord + dx, yCoord + dy);
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
        return xCoord == cell.xCoord && yCoord == cell.yCoord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord);
    }

    @Override
    public String toString() {
        return xCoord + "," + yCoord;
    }
}