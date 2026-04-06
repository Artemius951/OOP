package ru.nsu.kutsenko.task231;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Главный класс приложения Snake Game.
 * Управляет графическим интерфейсом и жизненным циклом приложения.
 */
public class Game extends Application {
    private GameController gameController;
    private GamePanel gamePanel;
    private Label stateLabel;
    private Label lengthLabel;
    private Label foodLabel;
    private Label goalLabel;
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

            stateLabel = (Label) root.lookup("#stateLabel");
            lengthLabel = (Label) root.lookup("#lengthLabel");
            foodLabel = (Label) root.lookup("#foodLabel");
            goalLabel = (Label) root.lookup("#goalLabel");
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
            VBox.setVgrow(gamePanel, Priority.ALWAYS);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(gameController.getInputHandler()::keyPressed);

            primaryStage.setScene(scene);
            gamePanel.requestFocus();
            gameController.startGame();

        } catch (IOException e) {
            String errorMessage = "Не удалось загрузить FXML файл: " + e.getMessage();
            System.err.println(errorMessage);
            e.printStackTrace();
            showErrorDialog("Ошибка загрузки интерфейса", errorMessage);
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

        stateLabel.setText(state);
        lengthLabel.setText(String.valueOf(length));
        foodLabel.setText(String.valueOf(food));
        goalLabel.setText(String.valueOf(goal));
    }

    /**
     * Показывает диалог ошибки пользователю.
     *
     * @param title   заголовок диалога
     * @param message текст сообщения об ошибке
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
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