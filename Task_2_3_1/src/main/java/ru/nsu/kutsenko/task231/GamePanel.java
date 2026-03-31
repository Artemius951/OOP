package ru.nsu.kutsenko.task231;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GamePanel extends Canvas {
    private GameEngine engine;
    private int fieldWidth;
    private int fieldHeight;
    private static final int CELL_SIZE = 20;

    public GamePanel(GameEngine engine, int fieldWidth, int fieldHeight) {
        this.engine = engine;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        setWidth(fieldWidth * CELL_SIZE);
        setHeight(fieldHeight * CELL_SIZE);
    }

    public void render() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        drawGrid(gc);
        drawFood(gc);
        drawSnake(gc);
        drawGameState(gc);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        for (int x = 0; x <= fieldWidth; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, getHeight());
        }

        for (int y = 0; y <= fieldHeight; y++) {
            gc.strokeLine(0, y * CELL_SIZE, getWidth(), y * CELL_SIZE);
        }
    }

    private void drawFood(GraphicsContext gc) {
        gc.setFill(Color.RED);

        for (Cell foodCell : engine.getFood().getAll()) {
            int x = foodCell.x * CELL_SIZE;
            int y = foodCell.y * CELL_SIZE;
            gc.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
        }
    }

    private void drawSnake(GraphicsContext gc) {
        var cells = engine.getSnake().getAllCells();

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            int x = cell.x * CELL_SIZE;
            int y = cell.y * CELL_SIZE;

            if (i == 0) {
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.LIGHTGREEN);
            }

            gc.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
        }
    }

    private void drawGameState(GraphicsContext gc) {
        GameState state = engine.getGameState();

        if (state == GameState.WON) {
            drawGameOverMessage(gc, "YOU WON!", Color.GREEN);
        } else if (state == GameState.LOST) {
            drawGameOverMessage(gc, "GAME OVER!", Color.RED);
        }
    }

    private void drawGameOverMessage(GraphicsContext gc, String message, Color color) {

        gc.setFill(Color.color(0, 0, 0, 0.7));
        gc.fillRect(0, 0, getWidth(), getHeight());


        gc.setFill(color);
        gc.setFont(Font.font("Arial", 50));
        gc.setTextAlign(TextAlignment.CENTER);

        double centerX = getWidth() / 2;
        double centerY = getHeight() / 2 - 30;

        gc.fillText(message, centerX, centerY);


        gc.setFont(Font.font("Arial", 20));
        gc.setFill(Color.WHITE);
        String info = "Length: " + engine.getSnake().getLength();
        gc.fillText(info, centerX, centerY + 50);
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }
}