package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса FoodGenerator.
 */
public class FoodGeneratorTest {

    @Test
    public void testGenerateFoodCell() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Snake snake = new Snake(new Cell(10, 10));
        Food food = new Food();
        FoodGenerator generator = new FoodGenerator();

        Cell newFood = generator.generateFoodCell(config, snake, food);

        assertNotNull(newFood);
        assertFalse(snake.contains(newFood));
        assertFalse(food.contains(newFood));
    }

    @Test
    public void testGenerateFoodCellNotOnSnake() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Snake snake = new Snake(new Cell(10, 10));
        snake.grow(new Cell(10, 9));
        Food food = new Food();
        FoodGenerator generator = new FoodGenerator();

        Cell newFood = generator.generateFoodCell(config, snake, food);

        assertNotNull(newFood);
        assertFalse(snake.contains(newFood));
    }

    @Test
    public void testGenerateFoodCellNotOnExistingFood() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Snake snake = new Snake(new Cell(10, 10));
        Food food = new Food();
        food.add(new Cell(5, 5));
        FoodGenerator generator = new FoodGenerator();

        Cell newFood = generator.generateFoodCell(config, snake, food);

        assertNotNull(newFood);
        assertFalse(food.contains(newFood));
    }

    @Test
    public void testGenerateFood() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Snake snake = new Snake(new Cell(10, 10));
        Food food = new Food();
        FoodGenerator generator = new FoodGenerator();

        generator.generateFood(3, config, snake, food);

        assertEquals(3, food.getCount());
    }

    @Test
    public void testInitializeFood() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Snake snake = new Snake(new Cell(10, 10));
        Food food = new Food();
        FoodGenerator generator = new FoodGenerator();

        generator.initializeFood(config, snake, food);

        assertEquals(3, food.getCount());
    }

    @Test
    public void testGenerateFoodCellFullField() {
        GameConfig config = new GameConfig(2, 2, 0, 50, 150);
        Snake snake = new Snake(new Cell(0, 0));
        snake.grow(new Cell(0, 1));
        snake.grow(new Cell(1, 0));
        snake.grow(new Cell(1, 1));
        Food food = new Food();
        FoodGenerator generator = new FoodGenerator();

        Cell newFood = generator.generateFoodCell(config, snake, food);

        assertNull(newFood);
    }
}