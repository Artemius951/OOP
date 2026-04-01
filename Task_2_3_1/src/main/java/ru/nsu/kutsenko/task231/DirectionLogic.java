package ru.nsu.kutsenko.task231;

public class DirectionLogic {

    /**
     * Проверяет, являются ли два направления противоположными.
     *
     * @param current текущее направление
     * @param other   проверяемое направление
     * @return true, если направления противоположны, иначе false
     */
    public static boolean isOpposite(Direction current, Direction other) {
        return current.dx == -other.dx && current.dy == -other.dy;
    }

    /**
     * Возвращает вектор смещения в виде клетки для заданного направления.
     *
     * @param direction направление движения
     * @return клетка с координатами (dx, dy), соответствующими направлению
     */
    public static Cell getOffset(Direction direction) {
        return new Cell(direction.dx, direction.dy);
    }

    /**
     * Проверяет, можно ли изменить направление движения.
     * Запрещено изменять направление на противоположное.
     *
     * @param currentDir текущее направление движения
     * @param newDir     новое направление
     * @return true, если направление можно изменить, иначе false
     */
    public static boolean canChangeDirection(Direction currentDir, Direction newDir) {
        return !isOpposite(currentDir, newDir);
    }
}