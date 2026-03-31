package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameEngineTest {

    @Test
    public void testGameEngineInitialization() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        assertEquals(GameState.RUNNING, engine.getGameState());
        assertEquals(1, engine.getSnake().getLength());
        assertEquals(3, engine.getFood().getCount());
    }

    @Test
    public void testGameEngineSnakeStartPosition() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        Cell head = engine.getSnake().getHead();
        assertEquals(10, head.x);
        assertEquals(10, head.y);
    }

    @Test
    public void testGameEngineUpdate() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        Cell initialHead = engine.getSnake().getHead();
        engine.update();
        Cell newHead = engine.getSnake().getHead();

        assertNotEquals(initialHead, newHead);
        assertEquals(GameState.RUNNING, engine.getGameState());
    }

    @Test
    public void testGameEngineSnakeMovesDown() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        Cell initialHead = engine.getSnake().getHead();
        engine.update();
        Cell newHead = engine.getSnake().getHead();

        assertEquals(initialHead.x, newHead.x);
        assertEquals(initialHead.y + 1, newHead.y);
    }

    @Test
    public void testGameEngineIsRunning() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        assertTrue(engine.isRunning());
    }

    @Test
    public void testGameEngineGameOverOutOfBounds() {
        GameConfig config = new GameConfig(5, 5, 0, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        while (engine.isRunning()) {
            engine.update();
        }

        assertEquals(GameState.LOST, engine.getGameState());
    }

    @Test
    public void testGameEngineToString() {
        GameConfig config = new GameConfig(20, 20, 3, 50, 150);
        InputHandler input = new InputHandler();
        GameEngine engine = new GameEngine(config, input);

        String result = engine.toString();
        assertTrue(result.contains("GameEngine"));
        assertTrue(result.contains("RUNNING"));
    }
}