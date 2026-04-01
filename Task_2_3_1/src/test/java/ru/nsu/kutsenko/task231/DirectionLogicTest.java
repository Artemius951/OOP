package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса DirectionLogic.
 */
public class DirectionLogicTest {

    @Test
    public void testIsOppositeUp() {
        assertTrue(DirectionLogic.isOpposite(Direction.UP, Direction.DOWN));
        assertFalse(DirectionLogic.isOpposite(Direction.UP, Direction.UP));
    }

    @Test
    public void testIsOppositeDown() {
        assertTrue(DirectionLogic.isOpposite(Direction.DOWN, Direction.UP));
        assertFalse(DirectionLogic.isOpposite(Direction.DOWN, Direction.DOWN));
    }

    @Test
    public void testIsOppositeLeft() {
        assertTrue(DirectionLogic.isOpposite(Direction.LEFT, Direction.RIGHT));
        assertFalse(DirectionLogic.isOpposite(Direction.LEFT, Direction.LEFT));
    }

    @Test
    public void testIsOppositeRight() {
        assertTrue(DirectionLogic.isOpposite(Direction.RIGHT, Direction.LEFT));
        assertFalse(DirectionLogic.isOpposite(Direction.RIGHT, Direction.RIGHT));
    }

    @Test
    public void testCanChangeDirectionValid() {
        assertTrue(DirectionLogic.canChangeDirection(Direction.UP, Direction.LEFT));
        assertTrue(DirectionLogic.canChangeDirection(Direction.UP, Direction.RIGHT));
        assertTrue(DirectionLogic.canChangeDirection(Direction.DOWN, Direction.LEFT));
    }

    @Test
    public void testCanChangeDirectionInvalid() {
        assertFalse(DirectionLogic.canChangeDirection(Direction.UP, Direction.DOWN));
        assertFalse(DirectionLogic.canChangeDirection(Direction.DOWN, Direction.UP));
        assertFalse(DirectionLogic.canChangeDirection(Direction.LEFT, Direction.RIGHT));
    }

    @Test
    public void testGetOffset() {
        Cell offset = DirectionLogic.getOffset(Direction.UP);
        assertEquals(0, offset.cellx);
        assertEquals(-1, offset.celly);
    }
}