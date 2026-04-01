package ru.nsu.kutsenko.task231;

/**
 * Предоставляет статические методы для проверки коллизий в игре "Змейка".
 * Содержит проверки на выход за границы, столкновение с собой и поедание еды.
 */
public class CollisionChecker {

    /**
     * Проверяет, выходит ли заданная клетка за границы игрового поля.
     *
     * @param head   клетка для проверки (обычно голова змейки).
     * @param config конфигурация игры, содержащая границы поля.
     * @return true, если клетка находится за пределами поля, иначе false.
     */
    public static boolean isOutOfBounds(Cell head, GameConfig config) {
        return !config.isInBounds(head);
    }

    /**
     * Проверяет, столкнулась ли голова змейки с её собственным телом.
     *
     * @param head  клетка головы змейки.
     * @param snake объект змейки, содержащий все её сегменты.
     * @return true, если голова находится на месте любого сегмента тела, иначе false.
     */
    public static boolean isHitSelf(Cell head, Snake snake) {
        return snake.contains(head);
    }

    /**
     * Проверяет, съела ли змейка еду.
     *
     * @param head клетка головы змейки.
     * @param food объект еды, содержащий её текущую позицию.
     * @return true, если голова находится на клетке с едой, иначе false.
     */
    public static boolean isHitFood(Cell head, Food food) {
        return food.contains(head);
    }

    /**
     * Проверяет, закончена ли игра.
     * Игра считается оконченной, если голова змейки выходит за границы поля
     * или сталкивается с собственным телом.
     *
     * @param head   клетка головы змейки.
     * @param snake  объект змейки.
     * @param config конфигурация игры с границами поля.
     * @return true, если игра окончена, иначе false.
     */
    public static boolean isGameOver(Cell head, Snake snake, GameConfig config) {
        return isOutOfBounds(head, config) || isHitSelf(head, snake);
    }
}