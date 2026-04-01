package ru.nsu.kutsenko.task231;

/**
 * Основной игровой движок, управляющий логикой игры, обновлением состояния и проверкой правил.
 */
public class GameEngine {
    private GameConfig config;
    private Snake snake;
    private Food food;
    private FoodGenerator foodGenerator;
    private GameState gameState;
    private Direction currentDirection;
    private InputHandler inputHandler;

    /**
     * Создает игровой движок с заданной конфигурацией и обработчиком ввода.
     * Инициализирует начальное положение змейки в центре поля и генерирует начальное количество еды.
     *
     * @param config       конфигурация игры
     * @param inputHandler обработчик ввода пользователя
     */
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

    /**
     * Возвращает текущее состояние игры.
     *
     * @return состояние игры (RUNNING, LOST, WON)
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Возвращает объект змейки.
     *
     * @return объект Snake
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Возвращает объект с едой.
     *
     * @return объект Food
     */
    public Food getFood() {
        return food;
    }

    /**
     * Возвращает текущую длину змейки.
     *
     * @return длина змейки
     */
    public int getSnakeLength() {
        return snake.getLength();
    }

    /**
     * Возвращает текущее количество еды на поле.
     *
     * @return количество еды
     */
    public int getFoodCount() {
        return food.getCount();
    }

    /**
     * Возвращает целевую длину для победы.
     *
     * @return целевая длина змейки
     */
    public int getGoal() {
        return config.getWinLength();
    }

    /**
     * Обновляет состояние игры на один кадр.
     * Обрабатывает движение змейки, проверку коллизий, поедание еды и условия победы/поражения.
     */
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

    /**
     * Проверяет, выполняется ли игра в данный момент.
     *
     * @return true, если игра активна, иначе false
     */
    public boolean isRunning() {
        return gameState == GameState.RUNNING;
    }

    /**
     * Возвращает строковое представление игрового движка.
     *
     * @return строка с текущим состоянием игры
     */
    @Override
    public String toString() {
        return "GameEngine{" +
            "state=" + gameState +
            ", snakeLength=" + snake.getLength() +
            ", foodCount=" + food.getCount() +
            ", goal=" + config.getWinLength() +
            ", snake=" + snake +
            ", food=" + food +
            ", direction=" + currentDirection +
            '}';
    }
}