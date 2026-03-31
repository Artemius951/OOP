package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameControllerTest {
    private GameController controller;
    private GameConfig config;

    @BeforeEach
    public void setUp() {
        config = new GameConfig(20, 20, 3, 50, 150);
        controller = new GameController(config, () -> {}, () -> {});
    }

    @Test
    public void testControllerInitialization() {
        assertNotNull(controller.getEngine());
        assertEquals(GameState.RUNNING, controller.getEngine().getGameState());
    }

    @Test
    public void testControllerHasInputHandler() {
        assertNotNull(controller.getInputHandler());
    }

    @Test
    public void testGameEngineInitialSnakeLength() {
        assertEquals(1, controller.getEngine().getSnake().getLength());
    }

    @Test
    public void testGameEngineInitialFoodCount() {
        assertEquals(3, controller.getEngine().getFood().getCount());
    }

    @Test
    public void testEngineUpdate() {
        controller.getEngine().update();
        assertTrue(controller.getEngine().isRunning());
    }

    @Test
    public void testEngineMultipleUpdates() {
        for (int i = 0; i < 5; i++) {
            controller.getEngine().update();
        }
        assertTrue(controller.getEngine().isRunning());
    }

    @Test
    public void testControllerRestart() {
        GameEngine firstEngine = controller.getEngine();

        controller.restart();

        GameEngine secondEngine = controller.getEngine();
        assertNotNull(secondEngine);
        assertEquals(GameState.RUNNING, secondEngine.getGameState());
        assertEquals(1, secondEngine.getSnake().getLength());
    }

    @Test
    public void testGameStateAfterUpdate() {
        controller.getEngine().update();
        GameState state = controller.getEngine().getGameState();

        assertTrue(state == GameState.RUNNING || state.isFinished());
    }

    @Test
    public void testSnakeLengthAfterUpdate() {
        int initialLength = controller.getEngine().getSnake().getLength();
        controller.getEngine().update();
        int lengthAfterUpdate = controller.getEngine().getSnake().getLength();

        assertTrue(lengthAfterUpdate >= initialLength);
    }
}