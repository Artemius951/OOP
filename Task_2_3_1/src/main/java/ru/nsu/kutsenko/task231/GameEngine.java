package ru.nsu.kutsenko.task231;

public class GameEngine {
    private GameConfig config;
    private Snake snake;
    private Food food;
    private FoodGenerator foodGenerator;
    private GameState gameState;
    private Direction currentDirection;
    private InputHandler inputHandler;

    public GameEngine(GameConfig config, InputHandler inputHandler) {
        this.config = config;
        this.inputHandler = inputHandler;
        this.foodGenerator = new FoodGenerator();
        this.gameState = GameState.RUNNING;
        this.currentDirection = Direction.UP;

        Cell startCell = new Cell(config.getFieldWidth() / 2, config.getFieldHeight() / 2);
        this.snake = new Snake(startCell);

        this.food = new Food();
        foodGenerator.initializeFood(config, snake, food);
    }


    public GameState getGameState() {
        return gameState;
    }


    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public void update() {
        if (!gameState.isActive()) {
            return;
        }

        Direction newDirection = inputHandler.getNextDirection();
        if (DirectionLogic.canChangeDirection(currentDirection, newDirection)) {
            currentDirection = newDirection;
        }

        Cell newHead = snake.getHead().add(currentDirection.dx, currentDirection.dy);

        if (CollisionChecker.isGameOver(newHead, snake, config)) {
            gameState = GameState.LOST;
            return;
        }

        if (CollisionChecker.isHitFood(newHead, food)) {
            snake.grow(newHead);
            food.remove(newHead);

            Cell newFood = foodGenerator.generateFoodCell(config, snake, food);
            if (newFood != null) {
                food.add(newFood);
            }

            if (snake.getLength() >= config.getWinLength()) {
                gameState = GameState.WON;
                return;
            }
        } else {
            snake.move(newHead);
        }
    }


    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    @Override
    public String toString() {
        return "GameEngine{" +
            "state=" + gameState +
            ", snake=" + snake +
            ", food=" + food +
            ", direction=" + currentDirection +
            '}';
    }
}