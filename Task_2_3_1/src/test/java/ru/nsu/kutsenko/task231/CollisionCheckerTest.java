package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CollisionCheckerTest {

    @Test
    public void testIsOutOfBoundsValid() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Cell cell = new Cell(10, 10);

        assertFalse(CollisionChecker.isOutOfBounds(cell, config));
    }

    @Test
    public void testIsOutOfBoundsNegative() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);

        assertTrue(CollisionChecker.isOutOfBounds(new Cell(-1, 10), config));
        assertTrue(CollisionChecker.isOutOfBounds(new Cell(10, -1), config));
    }

    @Test
    public void testIsOutOfBoundsTooLarge() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);

        assertTrue(CollisionChecker.isOutOfBounds(new Cell(20, 10), config));
        assertTrue(CollisionChecker.isOutOfBounds(new Cell(10, 20), config));
    }

    @Test
    public void testIsHitSelf() {
        Snake snake = new Snake(new Cell(10, 10));
        snake.grow(new Cell(10, 9));

        assertTrue(CollisionChecker.isHitSelf(new Cell(10, 10), snake));
        assertTrue(CollisionChecker.isHitSelf(new Cell(10, 9), snake));
        assertFalse(CollisionChecker.isHitSelf(new Cell(10, 8), snake));
    }

    @Test
    public void testIsHitFood() {
        Food food = new Food();
        Cell foodCell = new Cell(5, 5);
        food.add(foodCell);

        assertTrue(CollisionChecker.isHitFood(foodCell, food));
        assertFalse(CollisionChecker.isHitFood(new Cell(6, 6), food));
    }

    @Test
    public void testIsGameOver() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        Snake snake = new Snake(new Cell(10, 10));

        assertFalse(CollisionChecker.isGameOver(new Cell(10, 9), snake, config));

        snake.grow(new Cell(10, 9));
        assertTrue(CollisionChecker.isGameOver(new Cell(10, 9), snake, config));

        assertTrue(CollisionChecker.isGameOver(new Cell(-1, 10), snake, config));
    }
}