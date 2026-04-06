package ru.nsu.kutsenko.task231;

import java.util.Random;

/**
 * Генерирует еду на игровом поле в свободных клетках.
 */
public class FoodGenerator {
    private Random random;

    /**
     * Создает генератор еды с инициализированным генератором случайных чисел.
     */
    public FoodGenerator() {
        this.random = new Random();
    }

    /**
     * Генерирует одну свободную клетку для размещения еды.
     *
     * Использует метод случайного поиска с множественными попытками.
     * При наличии свободных клеток вероятность успешного нахождения практически 100%.
     * Если после всех попыток свободная клетка не найдена, возвращает null.
     * Это может происходить только в редких случаях, когда поле почти полностью заполнено.
     *
     * @param config конфигурация игрового поля
     * @param snake  объект змейки
     * @param food   объект с текущей едой
     * @return свободная клетка для еды, или null если свободная клетка не найдена
     *         после множественных попыток случайного поиска
     */
    public Cell generateFoodCell(GameConfig config, Snake snake, Food food) {
        int maxAttempts = config.getFieldWidth() * config.getFieldHeight() * 10;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int x = random.nextInt(config.getFieldWidth());
            int y = random.nextInt(config.getFieldHeight());
            Cell cell = new Cell(x, y);

            if (!snake.contains(cell) && !food.contains(cell)) {
                return cell;
            }

            attempts++;
        }

        return null;
    }

    /**
     * Генерирует указанное количество объектов еды.
     *
     * @param count  количество объектов еды для генерации
     * @param config конфигурация игрового поля
     * @param snake  объект змейки
     * @param food   объект для добавления сгенерированной еды
     */
    public void generateFood(int count, GameConfig config, Snake snake, Food food) {
        for (int i = 0; i < count; i++) {
            Cell foodCell = generateFoodCell(config, snake, food);
            if (foodCell != null) {
                food.add(foodCell);
            }
        }
    }

    /**
     * Инициализирует еду на игровом поле при старте игры.
     *
     * @param config конфигурация игрового поля
     * @param snake  объект змейки
     * @param food   объект для добавления еды
     */
    public void initializeFood(GameConfig config, Snake snake, Food food) {
        generateFood(config.getFoodCount(), config, snake, food);
    }
}