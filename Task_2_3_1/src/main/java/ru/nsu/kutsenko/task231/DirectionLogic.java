package ru.nsu.kutsenko.task231;

public class DirectionLogic {

    public static boolean isOpposite(Direction current, Direction other) {
        return current.dx == -other.dx && current.dy == -other.dy;
    }

    public static Cell getOffset(Direction direction) {
        return new Cell(direction.dx, direction.dy);
    }

    public static boolean canChangeDirection(Direction currentDir, Direction newDir) {
        return !isOpposite(currentDir, newDir);
    }
}