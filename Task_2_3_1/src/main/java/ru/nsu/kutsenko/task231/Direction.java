package ru.nsu.kutsenko.task231;

/**
 * Перечисление направлений движения змейки.
 * Каждое направление содержит вектор смещения по осям X и Y.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public final int dx;
    public final int dy;

    /**
     * Создает направление движения с заданным вектором смещения.
     *
     * @param dx смещение по оси X
     * @param dy смещение по оси Y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}