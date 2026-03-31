package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void testDirectionUp() {
        assertEquals(0, Direction.UP.dx);
        assertEquals(-1, Direction.UP.dy);
    }

    @Test
    public void testDirectionDown() {
        assertEquals(0, Direction.DOWN.dx);
        assertEquals(1, Direction.DOWN.dy);
    }

    @Test
    public void testDirectionLeft() {
        assertEquals(-1, Direction.LEFT.dx);
        assertEquals(0, Direction.LEFT.dy);
    }

    @Test
    public void testDirectionRight() {
        assertEquals(1, Direction.RIGHT.dx);
        assertEquals(0, Direction.RIGHT.dy);
    }

    @Test
    public void testAllDirectionsExist() {
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
    }
}