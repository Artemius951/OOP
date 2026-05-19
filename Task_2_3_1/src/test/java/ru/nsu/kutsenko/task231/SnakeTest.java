package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Snake.
 */
public class SnakeTest {

    @Test
    public void testSnakeCreation() {
        Cell startCell = new Cell(10, 10);
        Snake snake = new Snake(startCell);

        assertEquals(1, snake.getLength());
        assertEquals(startCell, snake.getHead());
        assertEquals(startCell, snake.getTail());
    }

    @Test
    public void testSnakeMove() {
        Snake snake = new Snake(new Cell(10, 10));
        Cell newHead = new Cell(10, 9);

        snake.move(newHead);

        assertEquals(1, snake.getLength());
        assertEquals(newHead, snake.getHead());
    }

    @Test
    public void testSnakeGrow() {
        Snake snake = new Snake(new Cell(10, 10));
        Cell newHead = new Cell(10, 9);

        snake.grow(newHead);

        assertEquals(2, snake.getLength());
        assertEquals(newHead, snake.getHead());
    }

    @Test
    public void testSnakeContains() {
        Snake snake = new Snake(new Cell(10, 10));

        assertTrue(snake.contains(new Cell(10, 10)));
        assertFalse(snake.contains(new Cell(10, 9)));
    }

    @Test
    public void testSnakeGrowMultiple() {
        Snake snake = new Snake(new Cell(10, 10));
        snake.grow(new Cell(10, 9));
        snake.grow(new Cell(10, 8));

        assertEquals(3, snake.getLength());
        assertEquals(new Cell(10, 8), snake.getHead());
        assertEquals(new Cell(10, 10), snake.getTail());
    }

    @Test
    public void testSnakeMoveAfterGrow() {
        Snake snake = new Snake(new Cell(10, 10));
        snake.grow(new Cell(10, 9));
        snake.move(new Cell(10, 8));

        assertEquals(2, snake.getLength());
        assertEquals(new Cell(10, 8), snake.getHead());
        assertTrue(snake.contains(new Cell(10, 9)));
    }

    @Test
    public void testSnakeGetAllCells() {
        Snake snake = new Snake(new Cell(10, 10));
        snake.grow(new Cell(10, 9));
        snake.grow(new Cell(10, 8));

        var cells = snake.getAllCells();
        assertEquals(3, cells.size());
        assertTrue(cells.contains(new Cell(10, 10)));
        assertTrue(cells.contains(new Cell(10, 9)));
        assertTrue(cells.contains(new Cell(10, 8)));
    }

    @Test
    public void testSnakeToString() {
        Snake snake = new Snake(new Cell(10, 10));
        String result = snake.toString();

        assertTrue(result.contains("Snake"));
        assertTrue(result.contains("10,10"));
    }
}