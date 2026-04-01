package ru.nsu.kutsenko.task231;

import javafx.application.Platform;


public class GameController {
    private GameEngine engine;
    private GameConfig config;
    private InputHandler inputHandler;
    private Thread gameThread;
    private final Runnable onRender;
    private final Runnable onGameOver;

    public GameController(GameConfig config, Runnable onRender, Runnable onGameOver) {
        this.config = config;
        this.onRender = onRender;
        this.onGameOver = onGameOver;
        this.inputHandler = new InputHandler();
        this.engine = new GameEngine(config, inputHandler);
    }


    public GameEngine getEngine() {
        return engine;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public boolean isGameRunning() {
        return gameThread != null && gameThread.isAlive();
    }

    public void startGame() {
        gameThread = new Thread(this::gameLoop);
        gameThread.setDaemon(true);
        gameThread.start();
    }


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
     * Invokes the render callback safely using Platform.runLater.
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
     * Invokes the game over callback safely using Platform.runLater.
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
     * Stops the game loop and waits for the thread to terminate.
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
     * Restarts the game by stopping the current game loop,
     * resetting the engine and input handler, and starting a new game loop.
     */
    public void restart() {
        stopGame();
        this.inputHandler = new InputHandler();
        this.engine = new GameEngine(config, inputHandler);
        startGame();
    }
}