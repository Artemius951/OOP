package ru.nsu.kutsenko.task231;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePanelTest {

    @Test
    public void testGamePanelCellSize() {
        int size = GamePanel.getCellSize();
        assertEquals(20, size);
    }

    @Test
    public void testGamePanelDimensions() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        GamePanel panel = new GamePanel(engine, config.getFieldWidth(), config.getFieldHeight());

        assertEquals(20 * 20, panel.getWidth());
        assertEquals(20 * 20, panel.getHeight());
    }

    @Test
    public void testGamePanelSmallField() {
        GameConfig config = new GameConfig(10, 15, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        GamePanel panel = new GamePanel(engine, config.getFieldWidth(), config.getFieldHeight());

        assertEquals(10 * 20, panel.getWidth());
        assertEquals(15 * 20, panel.getHeight());
    }

    @Test
    public void testGamePanelCreation() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        GamePanel panel = new GamePanel(engine, config.getFieldWidth(), config.getFieldHeight());

        assertTrue(panel.getWidth() > 0);
        assertTrue(panel.getHeight() > 0);
    }
}