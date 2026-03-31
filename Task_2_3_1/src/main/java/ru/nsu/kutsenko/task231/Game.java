package ru.nsu.kutsenko.task231;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class Game extends Application {
    private GameEngine engine;
    private GamePanel gamePanel;
    private Label infoLabel;
    private Button restartButton;
    private Thread gameThread;
    private GameConfig config;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            config = ConfigLoader.loadConfig("config.json");
        } catch (Exception e) {
            config = ConfigLoader.createDefault();
        }

        createGame();

        primaryStage.setTitle("Snake Game");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            if (gameThread != null && gameThread.isAlive()) {
                gameThread.interrupt();
            }
        });
    }

    private void createGame() {
        InputHandler inputHandler = new InputHandler();
        engine = new GameEngine(config, inputHandler);
        gamePanel = new GamePanel(engine, config.getFieldWidth(), config.getFieldHeight());

        infoLabel = new Label("State: RUNNING | Length: 1 | Food: " + config.getFoodCount());
        infoLabel.setStyle("-fx-font-size: 14; -fx-padding: 10;");

        restartButton = new Button("Restart Game");
        restartButton.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        restartButton.setOnAction(event -> restartGame());
        restartButton.setVisible(false);
        restartButton.setFocusTraversable(false);

        HBox topPanel = new HBox(10);
        topPanel.setStyle("-fx-padding: 10;");
        topPanel.setAlignment(Pos.CENTER);
        topPanel.getChildren().addAll(infoLabel, restartButton);

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0;");
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(topPanel, gamePanel);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(inputHandler::keyPressed);

        primaryStage.setScene(scene);

        gamePanel.requestFocus();

        startGameLoop();
    }

    private void restartGame() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            try {
                gameThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        createGame();
    }

    private void startGameLoop() {
        gameThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                engine.update();

                Platform.runLater(() -> {
                    gamePanel.render();
                    updateInfo();
                });

                try {
                    Thread.sleep(config.getTickMs());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                if (engine.getGameState().isFinished()) {
                    Platform.runLater(() -> {
                        gamePanel.render();
                        updateInfo();
                        restartButton.setVisible(true);
                        restartButton.requestFocus();
                    });
                    break;
                }
            }
        });

        gameThread.setDaemon(true);
        gameThread.start();
    }

    private void updateInfo() {
        int length = engine.getSnake().getLength();
        int food = engine.getFood().getCount();
        String state = engine.getGameState().toString();
        infoLabel.setText("State: " + state + " | Length: " + length + " | Food: " + food);
    }

    public static void main(String[] args) {
        launch(args);
    }
}