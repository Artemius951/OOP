package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamePanelTest {
    @Test
    public void testGamePanelCellSize() {
        assertEquals(20, GamePanel.getCellSize());
    }
}