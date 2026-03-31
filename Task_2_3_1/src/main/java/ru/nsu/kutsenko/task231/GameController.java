package ru.nsu.kutsenko.task231;

import javafx.application.Platform;

public class GameController {
    private GameEngine engine;
    private GameConfig config;
    private InputHandler inputHandler;
    private Thread gameThread;
    private Runnable onRender;
    private Runnable onGameOver;

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

    public void startGame() {
        gameThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                engine.update();

                if (onRender != null) {
                    Platform.runLater(onRender);
                }

                try {
                    Thread.sleep(config.getTickMs());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                if (engine.getGameState().isFinished()) {
                    if (onGameOver != null) {
                        Platform.runLater(onGameOver);
                    }
                    break;
                }
            }
        });

        gameThread.setDaemon(true);
        gameThread.start();
    }

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

    public void restart() {
        stopGame();
        this.inputHandler = new InputHandler();
        this.engine = new GameEngine(config, inputHandler);
        startGame();
    }
}