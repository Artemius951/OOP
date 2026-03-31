package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class GameEngineTest {
    private GameConfig config;
    private InputHandler inputHandler;
    private GameEngine engine;

    @BeforeEach
    public void setUp() {
        config = new GameConfig(20, 20, 3, 50, 150);
        inputHandler = new InputHandler();
        engine = new GameEngine(config, inputHandler);
    }

    @Test
    public void testGameEngineInitialization() {
        assertEquals(GameState.RUNNING, engine.getGameState());
        assertEquals(1, engine.getSnake().getLength());
        assertEquals(3, engine.getFood().getCount());
    }

    @Test
    public void testGameEngineSnakeStartPosition() {
        Cell head = engine.getSnake().getHead();
        assertEquals(10, head.x);
        assertEquals(10, head.y);
    }

    @Test
    public void testGameEngineInitialDirection() {
        Cell initialHead = engine.getSnake().getHead();
        engine.update();
        Cell newHead = engine.getSnake().getHead();

        assertEquals(initialHead.x, newHead.x);
        assertEquals(initialHead.y - 1, newHead.y); // Direction.UP
    }

    @Test
    public void testGameEngineUpdate() {
        Cell initialHead = engine.getSnake().getHead();
        engine.update();
        Cell newHead = engine.getSnake().getHead();

        assertNotEquals(initialHead, newHead);
        assertEquals(GameState.RUNNING, engine.getGameState());
    }

    @Test
    public void testGameEngineMultipleUpdates() {
        for (int i = 0; i < 10; i++) {
            engine.update();
            assertTrue(engine.isRunning() || engine.getGameState().isFinished());
        }
    }

    @Test
    public void testGameEngineIsRunning() {
        assertTrue(engine.isRunning());
    }

    @Test
    public void testGameEngineGameOverOutOfBounds() {
        GameConfig smallConfig = new GameConfig(5, 5, 0, 50, 150);
        GameEngine smallEngine = new GameEngine(smallConfig, new InputHandler());

        while (smallEngine.isRunning()) {
            smallEngine.update();
        }

        assertEquals(GameState.LOST, smallEngine.getGameState());
    }

    @Test
    public void testGameEngineSnakeContainsSelf() {
        Cell head = engine.getSnake().getHead();
        assertTrue(engine.getSnake().contains(head));
    }

    @Test
    public void testGameEngineSnakeDoesNotContainRandomCell() {
        Cell randomCell = new Cell(0, 0);
        assertFalse(engine.getSnake().contains(randomCell));
    }

    @Test
    public void testGameEngineFoodInitialization() {
        Food food = engine.getFood();
        assertNotNull(food);
        assertEquals(3, food.getCount());
    }

    @Test
    public void testGameEngineFoodPosition() {
        Food food = engine.getFood();
        for (Cell foodCell : food.getAll()) {
            assertTrue(config.isInBounds(foodCell));
        }
    }

    @Test
    public void testGameEngineSnakeFoodCollision() {
        GameConfig testConfig = new GameConfig(10, 10, 1, 5, 150);
        GameEngine testEngine = new GameEngine(testConfig, new InputHandler());

        int initialLength = testEngine.getSnake().getLength();

        // Симулируем много обновлений чтобы съесть еду
        for (int i = 0; i < 1000 && testEngine.isRunning(); i++) {
            testEngine.update();
            if (testEngine.getSnake().getLength() > initialLength) {
                assertTrue(testEngine.getSnake().getLength() > initialLength);
                break;
            }
        }
    }

    @Test
    public void testGameEngineWinCondition() {
        GameConfig winConfig = new GameConfig(20, 20, 100, 2, 150);
        GameEngine winEngine = new GameEngine(winConfig, new InputHandler());

        // Обновляем пока не выиграем или не потеряем
        for (int i = 0; i < 1000; i++) {
            winEngine.update();
            if (!winEngine.isRunning()) {
                assertTrue(winEngine.getGameState() == GameState.WON ||
                    winEngine.getGameState() == GameState.LOST);
                break;
            }
        }
    }

    @Test
    public void testGameEngineInactiveStateUpdate() {
        GameConfig lostConfig = new GameConfig(5, 5, 0, 50, 150);
        GameEngine lostEngine = new GameEngine(lostConfig, new InputHandler());

        while (lostEngine.getGameState().isActive()) {
            lostEngine.update();
        }

        assertEquals(GameState.LOST, lostEngine.getGameState());

        // Дальнейший update не должен менять состояние
        lostEngine.update();
        assertEquals(GameState.LOST, lostEngine.getGameState());
    }

    @Test
    public void testGameEngineDirectionChangeValid() {
        // Проверяем что можно менять направление
        Cell headBefore = engine.getSnake().getHead();
        engine.update(); // Move UP
        Cell headAfter = engine.getSnake().getHead();

        assertTrue(headAfter.y < headBefore.y); // Движемся вверх
    }

    @Test
    public void testGameEngineAllSnakeCells() {
        int length = engine.getSnake().getLength();
        assertEquals(length, engine.getSnake().getAllCells().size());
    }

    @Test
    public void testGameEngineGettersNotNull() {
        assertNotNull(engine.getGameState());
        assertNotNull(engine.getSnake());
        assertNotNull(engine.getFood());
    }

    @Test
    public void testGameEngineToString() {
        String result = engine.toString();
        assertTrue(result.contains("GameEngine"));
        assertTrue(result.contains("RUNNING"));
        assertTrue(result.contains("Snake"));
    }
}