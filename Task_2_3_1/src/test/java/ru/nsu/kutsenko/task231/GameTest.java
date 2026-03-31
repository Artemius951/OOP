package ru.nsu.kutsenko.task231;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {

    @Test
    public void testGameConfigLoadingFallback() {
        GameConfig config = ConfigLoader.createDefault();

        assertNotNull(config);
        assertTrue(config.getFieldWidth() >= 0);
        assertTrue(config.getFieldHeight() >= 0);
    }

    @Test
    public void testGameInfoUpdateLogic() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        int length = engine.getSnake().getLength();
        int food = engine.getFood().getCount();
        String state = engine.getGameState().toString();

        String info = "State:  " + state + " | Length:  " + length + " | Food:  " + food;

        assertTrue(info.contains("RUNNING"));
        assertEquals(1, length);
        assertEquals(3, food);
    }

    @Test
    public void testGameInfoUpdateAfterMove() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        engine.update();

        int length = engine.getSnake().getLength();
        String state = engine.getGameState().toString();

        String info = "State:  " + state + " | Length:  " + length;

        assertTrue(info.contains("RUNNING"));
        assertEquals(1, length);
    }

    @Test
    public void testGameConfigCreation() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);

        assertEquals(20, config.getFieldWidth());
        assertEquals(20, config.getFieldHeight());
        assertEquals(3, config.getFoodCount());
    }

    @Test
    public void testGameEngineInitialState() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        assertEquals(GameState.RUNNING, engine.getGameState());
        assertTrue(engine.isRunning());
    }
}