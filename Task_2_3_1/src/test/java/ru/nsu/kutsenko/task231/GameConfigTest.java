package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса GameConfig.
 */
public class GameConfigTest {

    @Test
    public void testGameConfigCreation() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);

        assertEquals(20, config.getFieldWidth());
        assertEquals(20, config.getFieldHeight());
        assertEquals(3, config.getFoodCount());
        assertEquals(50, config.getWinLength());
        assertEquals(150, config.getTickMs());
    }

    @Test
    public void testGameConfigDefault() {
        GameConfig config = new GameConfig();

        assertEquals(0, config.getFieldWidth());
        assertEquals(0, config.getFieldHeight());
    }

    @Test
    public void testIsInBoundsCoordinates() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);

        assertTrue(config.isInBounds(0, 0));
        assertTrue(config.isInBounds(10, 10));
        assertTrue(config.isInBounds(19, 19));

        assertFalse(config.isInBounds(-1, 0));
        assertFalse(config.isInBounds(20, 0));
        assertFalse(config.isInBounds(0, -1));
        assertFalse(config.isInBounds(0, 20));
    }

    @Test
    public void testIsInBoundsCell() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);

        assertTrue(config.isInBounds(new Cell(0, 0)));
        assertTrue(config.isInBounds(new Cell(19, 19)));
        assertFalse(config.isInBounds(new Cell(20, 20)));
    }

    @Test
    public void testGameConfigToString() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        String result = config.toString();

        assertTrue(result.contains("fieldWidth=20"));
        assertTrue(result.contains("fieldHeight=20"));
        assertTrue(result.contains("foodCount=3"));
    }
}