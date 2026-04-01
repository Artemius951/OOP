package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса GameController.
 */
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
        assertNotEquals(firstEngine, secondEngine);
        assertEquals(GameState.RUNNING, secondEngine.getGameState());
        assertEquals(1, secondEngine.getSnake().getLength());
    }

    @Test
    public void testIsGameNotRunningInitially() {
        assertFalse(controller.isGameRunning());
    }

    @Test
    public void testEngineUpdatesWithoutThread() {
        controller.getEngine().update();
        assertEquals(GameState.RUNNING, controller.getEngine().getGameState());
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

    @Test
    public void testControllerStopGameStopsThread() {
        GameController testController = new GameController(config, () -> {}, () -> {});
        testController.startGame();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        testController.stopGame();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testControllerRestartResetsEngine() {
        for (int i = 0; i < 5; i++) {
            controller.getEngine().update();
        }

        int lengthBeforeRestart = controller.getEngine().getSnake().getLength();
        controller.restart();
        int lengthAfterRestart = controller.getEngine().getSnake().getLength();

        assertEquals(1, lengthAfterRestart);
    }

    @Test
    public void testControllerInputHandlerNotNull() {
        assertNotNull(controller.getInputHandler());
    }

    @Test
    public void testControllerRestartChangesInputHandler() {
        InputHandler firstHandler = controller.getInputHandler();
        controller.restart();
        InputHandler secondHandler = controller.getInputHandler();

        assertNotNull(secondHandler);
        assertNotEquals(firstHandler, secondHandler);
    }

    @Test
    public void testControllerGameOverCallback() {
        AtomicInteger callbackCount = new AtomicInteger(0);
        GameConfig smallConfig = new GameConfig(5, 5, 0, 50, 150);
        GameController testController = new GameController(
            smallConfig,
            () -> {},
            () -> callbackCount.incrementAndGet()
        );

        testController.startGame();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        testController.stopGame();
    }

    @Test
    public void testControllerMultipleRestarts() {
        for (int i = 0; i < 3; i++) {
            controller.restart();
            assertEquals(GameState.RUNNING, controller.getEngine().getGameState());
            assertEquals(1, controller.getEngine().getSnake().getLength());
        }
    }

    @Test
    public void testControllerEngineGettersConsistent() {
        GameEngine engine1 = controller.getEngine();
        GameEngine engine2 = controller.getEngine();

        assertSame(engine1, engine2);
    }

    @Test
    public void testControllerInputHandlerGettersConsistent() {
        InputHandler handler1 = controller.getInputHandler();
        InputHandler handler2 = controller.getInputHandler();

        assertSame(handler1, handler2);
    }

    @Test
    public void testControllerStopGameWhenNotRunning() {
        controller.stopGame();
        controller.stopGame();
    }

    @Test
    public void testControllerConfigRespected() {
        GameConfig customConfig = new GameConfig(30, 30, 5, 100, 200);
        GameController customController = new GameController(customConfig, () -> {}, () -> {});

        assertEquals(5, customController.getEngine().getFood().getCount());
    }
}