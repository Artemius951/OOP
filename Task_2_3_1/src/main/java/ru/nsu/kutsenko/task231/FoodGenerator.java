package ru.nsu.kutsenko.task231;

import java.util.Random;

public class FoodGenerator {
    private Random random;

    public FoodGenerator() {
        this.random = new Random();
    }

    public Cell generateFoodCell(GameConfig config, Snake snake, Food food) {
        int maxAttempts = config.getFieldWidth() * config.getFieldHeight();
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

    public void generateFood(int count, GameConfig config, Snake snake, Food food) {
        for (int i = 0; i < count; i++) {
            Cell foodCell = generateFoodCell(config, snake, food);
            if (foodCell != null) {
                food.add(foodCell);
            }
        }
    }

    public void initializeFood(GameConfig config, Snake snake, Food food) {
        generateFood(config.getFoodCount(), config, snake, food);
    }
}