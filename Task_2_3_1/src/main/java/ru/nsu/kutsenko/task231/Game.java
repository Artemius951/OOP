package ru.nsu.kutsenko.task231;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Главный класс приложения Snake Game.
 * Управляет графическим интерфейсом и жизненным циклом приложения.
 */
public class Game extends Application {
    private GameController gameController;
    private GamePanel gamePanel;
    private Label infoLabel;
    private Button restartButton;
    private GameConfig config;
    private Stage primaryStage;

    /**
     * Точка входа в JavaFX приложение.
     *
     * @param primaryStage основная сцена приложения
     */
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

    /**
     * Создает и настраивает игровые компоненты, сцену и элементы управления.
     */
    private void createGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            VBox root = loader.load();

            infoLabel = (Label) root.lookup("#infoLabel");
            restartButton = (Button) root.lookup("#restartButton");

            gameController = new GameController(
                config,
                this::onRender,
                this::onGameOver
            );

            gamePanel = new GamePanel(gameController.getEngine(), config.getFieldWidth(),
                config.getFieldHeight());

            restartButton.setOnAction(event -> restartGame());

            root.getChildren().add(gamePanel);
            VBox.setVgrow(gamePanel, javafx.scene.layout.Priority.ALWAYS);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(gameController.getInputHandler()::keyPressed);

            primaryStage.setScene(scene);
            gamePanel.requestFocus();
            gameController.startGame();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает событие рендеринга кадра.
     */
    private void onRender() {
        gamePanel.render();
        updateInfo();
    }

    /**
     * Обрабатывает событие окончания игры.
     */
    private void onGameOver() {
        gamePanel.render();
        updateInfo();
        restartButton.setVisible(true);
        restartButton.requestFocus();
    }

    /**
     * Перезапускает игру.
     */
    private void restartGame() {
        gameController.stopGame();
        createGame();
    }

    /**
     * Обновляет информационную панель с текущими данными игры.
     */
    private void updateInfo() {
        int length = gameController.getEngine().getSnake().getLength();
        int food = gameController.getEngine().getFood().getCount();
        int goal = gameController.getEngine().getGoal();
        String state = gameController.getEngine().getGameState().toString();
        infoLabel.setText(
            "State: " + state + " | Length: " + length + " | Food: " + food
                + " | Goal: " + goal
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}