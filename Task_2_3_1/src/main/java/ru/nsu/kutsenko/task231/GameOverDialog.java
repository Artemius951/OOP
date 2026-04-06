package ru.nsu.kutsenko.task231;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Диалоговое окно, отображаемое при окончании игры.
 * Показывает результат игры (победа/поражение) и предлагает опции перезагрузки или выхода.
 */
public class GameOverDialog {
    private Stage ownerStage;
    private GameEngine engine;
    private Runnable onRestart;
    private Runnable onExit;

    /**
     * Создает диалог окончания игры.
     *
     * @param ownerStage родительское окно
     * @param engine     игровой движок с информацией о состоянии игры
     * @param onRestart  обработчик нажатия кнопки "Restart"
     * @param onExit     обработчик нажатия кнопки "Exit"
     */
    public GameOverDialog(Stage ownerStage, GameEngine engine, Runnable onRestart, Runnable onExit) {
        this.ownerStage = ownerStage;
        this.engine = engine;
        this.onRestart = onRestart;
        this.onExit = onExit;
    }

    /**
     * Показывает диалог и ожидает ответа пользователя.
     */
    public void show() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(ownerStage);
        dialogStage.setTitle("Game Over");
        dialogStage.setResizable(false);

        GameState state = engine.getGameState();
        boolean isWon = state == GameState.WON;
        String title = isWon ? "YOU WON!" : "GAME OVER!";
        String bgColor = isWon ? "#4CAF50" : "#f44336";

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 28; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lengthLabelDialog = new Label("Final Length: " + engine.getSnake().getLength());
        lengthLabelDialog.setStyle("-fx-font-size: 16; -fx-text-fill: white;");

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        restartButton.setOnAction(event -> {
            dialogStage.close();
            if (onRestart != null) {
                onRestart.run();
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        exitButton.setOnAction(event -> {
            dialogStage.close();
            if (onExit != null) {
                onExit.run();
            }
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
}