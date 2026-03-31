package ru.nsu.kutsenko.task231;

public class CollisionChecker {

    public static boolean isOutOfBounds(Cell head, GameConfig config) {
        return !config.isInBounds(head);
    }

    public static boolean isHitSelf(Cell head, Snake snake) {
        return snake.contains(head);
    }

    public static boolean isHitFood(Cell head, Food food) {
        return food.contains(head);
    }

    public static boolean isGameOver(Cell head, Snake snake, GameConfig config) {
        return isOutOfBounds(head, config) || isHitSelf(head, snake);
    }
}