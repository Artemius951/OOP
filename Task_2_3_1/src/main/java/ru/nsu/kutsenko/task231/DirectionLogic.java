package ru.nsu.kutsenko.task231;

/**
 * Вспомогательный класс для логики, связанной с направлениями.
 */
public class DirectionLogic {

    /**
     * Проверяет, являются ли два направления противоположными.
     *
     * @param current текущее направление
     * @param other другое направление
     * @return true если направления противоположны, иначе false
     */
    public static boolean isOpposite(Direction current, Direction other) {
        return current.dx == -other.dx && current.dy == -other.dy;
    }

    /**
     * Получает смещение ячейки для заданного направления.
     *
     * @param direction направление
     * @return ячейка с дельта-значениями направления
     */
    public static Cell getOffset(Direction direction) {
        return new Cell(direction.dx, direction.dy);
    }

    /**
     * Проверяет, является ли изменение направления допустимым.
     *
     * @param currentDir текущее направление
     * @param newDir новое направление
     * @return true если изменение разрешено, false если направления противоположны
     */
    public static boolean canChangeDirection(Direction currentDir, Direction newDir) {
        return !isOpposite(currentDir, newDir);
    }
}