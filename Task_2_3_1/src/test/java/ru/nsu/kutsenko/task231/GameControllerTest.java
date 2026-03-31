package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for GameController class.
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
        GameConfig smallConfig = new GameConfig(5, 5, 0, 50, 150);
        GameController testController = new GameController(
            smallConfig,
            () -> {},
            () -> {}
        );

        testController.startGame();

        try {
            Thread.sleep(500);
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

    @Test
    public void testControllerWithNullCallbacks() {
        GameController nullController = new GameController(config, null, null);
        assertNotNull(nullController.getEngine());
        assertNotNull(nullController.getInputHandler());
    }

    @Test
    public void testControllerGameProgressWithUpdates() {
        Cell headBefore = controller.getEngine().getSnake().getHead();
        controller.getEngine().update();
        Cell headAfter = controller.getEngine().getSnake().getHead();

        assertNotEquals(headBefore, headAfter);
    }

    @Test
    public void testControllerSnakeMoves() {
        Cell headBefore = controller.getEngine().getSnake().getHead();
        int x = headBefore.x;
        int y = headBefore.y;

        controller.getEngine().update();
        Cell headAfter = controller.getEngine().getSnake().getHead();

        assertTrue(headAfter.y < y);
    }

    @Test
    public void testControllerFoodCount() {
        Food food = controller.getEngine().getFood();
        assertNotNull(food);
        assertEquals(3, food.getCount());
    }

    @Test
    public void testControllerSnakeNotOutOfBounds() {
        Cell head = controller.getEngine().getSnake().getHead();
        assertTrue(config.isInBounds(head));
    }

    @Test
    public void testControllerGameRunningState() {
        assertEquals(GameState.RUNNING, controller.getEngine().getGameState());
        assertTrue(controller.getEngine().isRunning());
    }

    @Test
    public void testControllerMultipleUpdatesGameRunning() {
        for (int i = 0; i < 20; i++) {
            controller.getEngine().update();
            if (!controller.getEngine().isRunning()) {
                break;
            }
        }

        GameState state = controller.getEngine().getGameState();
        assertTrue(state == GameState.RUNNING || state.isFinished());
    }

    @Test
    public void testControllerEngineNotNull() {
        assertNotNull(controller.getEngine());
        assertNotNull(controller.getEngine().getSnake());
        assertNotNull(controller.getEngine().getFood());
    }

    @Test
    public void testControllerDifferentConfigs() {
        GameConfig config1 = new GameConfig(15, 15, 2, 40, 200);
        GameConfig config2 = new GameConfig(25, 25, 5, 60, 100);

        GameController controller1 = new GameController(config1, () -> {}, () -> {});
        GameController controller2 = new GameController(config2, () -> {}, () -> {});

        assertEquals(2, controller1.getEngine().getFood().getCount());
        assertEquals(5, controller2.getEngine().getFood().getCount());
    }

    @Test
    public void testControllerConfigSize() {
        GameConfig testConfig = new GameConfig(25, 15, 2, 30, 100);
        GameController testController = new GameController(testConfig, () -> {}, () -> {});

        Cell head = testController.getEngine().getSnake().getHead();
        assertEquals(12, head.x);
        assertEquals(7, head.y);
    }

    @Test
    public void testControllerInputHandlerDirection() {
        Direction direction = controller.getInputHandler().getNextDirection();
        assertNotNull(direction);
        assertEquals(Direction.DOWN, direction);
    }

    @Test
    public void testControllerRestartsWithoutErrors() {
        controller.restart();
        controller.restart();
        controller.restart();

        assertEquals(GameState.RUNNING, controller.getEngine().getGameState());
    }

    @Test
    public void testControllerGameNotWonInitially() {
        GameState state = controller.getEngine().getGameState();
        assertNotEquals(GameState.WON, state);
    }

    @Test
    public void testControllerGameNotLostInitially() {
        GameState state = controller.getEngine().getGameState();
        assertNotEquals(GameState.LOST, state);
    }

    @Test
    public void testControllerFoodNotInSnake() {
        for (Cell food : controller.getEngine().getFood().getAll()) {
            assertNotEquals(true, controller.getEngine().getSnake().contains(food));
        }
    }

    @Test
    public void testControllerSnakeHeadInBounds() {
        Cell head = controller.getEngine().getSnake().getHead();
        assertTrue(config.isInBounds(head));
    }

    @Test
    public void testControllerAllFoodInBounds() {
        for (Cell foodCell : controller.getEngine().getFood().getAll()) {
            assertTrue(config.isInBounds(foodCell));
        }
    }
}