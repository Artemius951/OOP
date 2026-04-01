package ru.nsu.kutsenko.task231;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
        gameController = new GameController(
            config,
            this::onRender,
            this::onGameOver
        );

        gamePanel = new GamePanel(gameController.getEngine(), config.getFieldWidth(),
            config.getFieldHeight());

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
        String state = gameController.getEngine().getGameState().toString();
        infoLabel.setText("State: " + state + " | Length: " + length + " | Food: " + food);
    }

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}