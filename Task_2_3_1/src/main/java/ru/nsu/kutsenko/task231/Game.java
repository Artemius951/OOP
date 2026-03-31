package ru.nsu.kutsenko.task231;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;

public class Game extends Application {
    private GameController gameController;
    private GamePanel gamePanel;
    private Label infoLabel;
    private Button restartButton;
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
            gameController.stopGame();
        });
    }

    private void createGame() {
        gameController = new GameController(
            config,
            this::onRender,
            this::onGameOver
        );

        gamePanel = new GamePanel(gameController.getEngine(), config.getFieldWidth(), config.getFieldHeight());

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
        scene.setOnKeyPressed(gameController.getInputHandler()::keyPressed);

        primaryStage.setScene(scene);

        gamePanel.requestFocus();

        gameController.startGame();
    }

    private void onRender() {
        gamePanel.render();
        updateInfo();
    }

    private void onGameOver() {
        gamePanel.render();
        updateInfo();
        restartButton.setVisible(true);
        restartButton.requestFocus();
    }

    private void restartGame() {
        gameController.stopGame();
        createGame();
    }

    private void updateInfo() {
        int length = gameController.getEngine().getSnake().getLength();
        int food = gameController.getEngine().getFood().getCount();
        String state = gameController.getEngine().getGameState().toString();
        infoLabel.setText("State: " + state + " | Length: " + length + " | Food: " + food);
    }

    public static void main(String[] args) {
        launch(args);
    }
}