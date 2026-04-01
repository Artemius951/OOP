package ru.nsu.kutsenko.task231;

import javafx.application.Platform;

/**
 * Управляет игровым циклом, потоками и взаимодействием между движком и представлением.
 */
public class GameController {
    private GameEngine engine;
    private GameConfig config;
    private InputHandler inputHandler;
    private Thread gameThread;
    private final Runnable onRender;
    private final Runnable onGameOver;

    /**
     * Создает контроллер игры.
     *
     * @param config     конфигурация игры
     * @param onRender   обратный вызов для рендеринга кадра
     * @param onGameOver обратный вызов при окончании игры
     */
    public GameController(GameConfig config, Runnable onRender, Runnable onGameOver) {
        this.config = config;
        this.onRender = onRender;
        this.onGameOver = onGameOver;
        this.inputHandler = new InputHandler();
        this.engine = new GameEngine(config, inputHandler);
    }

    /**
     * Возвращает игровой движок.
     *
     * @return объект GameEngine
     */
    public GameEngine getEngine() {
        return engine;
    }

    /**
     * Возвращает обработчик ввода.
     *
     * @return объект InputHandler
     */
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    /**
     * Проверяет, запущен ли игровой цикл.
     *
     * @return true, если игра запущена, иначе false
     */
    public boolean isGameRunning() {
        return gameThread != null && gameThread.isAlive();
    }

    /**
     * Запускает игровой цикл в отдельном потоке.
     */
    public void startGame() {
        gameThread = new Thread(this::gameLoop);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    /**
     * Основной игровой цикл, обновляющий состояние игры и управляющий задержками.
     */
    private void gameLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            engine.update();
            invokeRender();

            try {
                Thread.sleep(config.getTickMs());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            if (engine.getGameState().isFinished()) {
                invokeGameOver();
                break;
            }
        }
    }

    /**
     * Вызывает обратный вызов рендеринга безопасно через Platform.runLater.
     */
    private void invokeRender() {
        if (onRender != null) {
            try {
                Platform.runLater(onRender);
            } catch (IllegalStateException e) {
                // Toolkit not initialized, ignore in tests
            }
        }
    }

    /**
     * Вызывает обратный вызов окончания игры безопасно через Platform.runLater.
     */
    private void invokeGameOver() {
        if (onGameOver != null) {
            try {
                Platform.runLater(onGameOver);
            } catch (IllegalStateException e) {
                // Toolkit not initialized, ignore in tests
            }
        }
    }

    /**
     * Останавливает игровой цикл и ожидает завершения потока.
     */
    public void stopGame() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            try {
                gameThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Перезапускает игру, останавливая текущий игровой цикл,
     * сбрасывая движок и обработчик ввода, и запуская новый игровой цикл.
     */
    public void restart() {
        stopGame();
        this.inputHandler = new InputHandler();
        this.engine = new GameEngine(config, inputHandler);
        startGame();
    }
}