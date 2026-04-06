package ru.nsu.kutsenko.task231;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

            gameController = new GameController(
                config,
                this::onRender,
                this::onGameOver
            );

            gamePanel = new GamePanel(gameController.getEngine(), config.getFieldWidth(),
                config.getFieldHeight());

            root.getChildren().add(gamePanel);
            VBox.setVgrow(gamePanel, Priority.ALWAYS);

            Scene scene = new Scene(root);
            scene.setOnKeyPressed(gameController.getInputHandler()::keyPressed);

            primaryStage.setScene(scene);
            gamePanel.requestFocus();
            gameController.startGame();

        } catch (IOException e) {
            String errorMessage = "Не удало��ь загрузить FXML файл: " + e.getMessage();
            System.err.println(errorMessage);
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
        showGameOverDialog();
    }

    /**
     * Показывает диалог окончания игры с кнопками Restart и Exit.
     */
    private void showGameOverDialog() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setTitle("Game Over");
        dialogStage.setResizable(false);

        GameState state = gameController.getEngine().getGameState();
        boolean isWon = state == GameState.WON;
        String title = isWon ? "YOU WON!" : "GAME OVER!";
        String bgColor = isWon ? "#4CAF50" : "#f44336";

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lengthLabelDialog = new Label("Final Length: "
            + gameController.getEngine().getSnake().getLength());
        lengthLabelDialog.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        restartButton.setOnAction(event -> {
            dialogStage.close();
            restartGame();
        });

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        exitButton.setOnAction(event -> {
            gameController.stopGame();
            primaryStage.close();
        });

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(restartButton, exitButton);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30; -fx-background-color: " + bgColor + ";");
        root.getChildren().addAll(titleLabel, lengthLabelDialog, buttonBox);

        Scene scene = new Scene(root, 300, 250);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
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
        stateLabel.setText(state);
        lengthLabel.setText(String.valueOf(length));
        foodLabel.setText(String.valueOf(food));
        goalLabel.setText(String.valueOf(gameController.getEngine().getGoal()));
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