package ru.nsu.kutsenko.task231;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Игровая панель для отрисовки игрового поля, змейки, еды и состояния игры.
 */
public class GamePanel extends Canvas {
    private GameEngine engine;
    private int fieldWidth;
    private int fieldHeight;
    private static final int CELL_SIZE = 20;

    /**
     * Создает игровую панель с заданным движком и размерами поля.
     *
     * @param engine      игровой движок
     * @param fieldWidth  ширина поля в клетках
     * @param fieldHeight высота поля в клетках
     */
    public GamePanel(GameEngine engine, int fieldWidth, int fieldHeight) {
        this.engine = engine;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        setWidth(fieldWidth * CELL_SIZE);
        setHeight(fieldHeight * CELL_SIZE);
    }

    /**
     * Отрисовывает все элементы игры: сетку, еду, змейку и сообщение о паузе.
     */
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        drawGrid(gc);
        drawFood(gc);
        drawSnake(gc);
        drawPause(gc);
    }

    /**
     * Отрисовывает сетку игрового поля.
     *
     * @param gc графический контекст
     */
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

    /**
     * Отрисовывает еду на игровом поле.
     *
     * @param gc графический контекст
     */
    private void drawFood(GraphicsContext gc) {
        gc.setFill(Color.RED);

        for (Cell foodCell : engine.getFood().getAll()) {
            int xpos = foodCell.cellx * CELL_SIZE;
            int ypos = foodCell.celly * CELL_SIZE;
            gc.fillRect(xpos + 2, ypos + 2, CELL_SIZE - 4, CELL_SIZE - 4);
        }
    }

    /**
     * Отрисовывает змейку на игровом поле.
     *
     * @param gc графический контекст
     */
    private void drawSnake(GraphicsContext gc) {
        var cells = engine.getSnake().getAllCells();

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            int xpos = cell.cellx * CELL_SIZE;
            int ypos = cell.celly * CELL_SIZE;

            if (i == 0) {
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.LIGHTGREEN);
            }

            gc.fillRect(xpos + 1, ypos + 1, CELL_SIZE - 2, CELL_SIZE - 2);
        }
    }

    /**
     * Отрисовывает сообщение о паузе, если игра на паузе.
     *
     * @param gc графический контекст
     */
    private void drawPause(GraphicsContext gc) {
        if (engine.getGameState() == GameState.PAUSED) {
            gc.setFill(Color.color(0, 0, 0, 0.7));
            gc.fillRect(0, 0, getWidth(), getHeight());

            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font("Arial", 50));
            gc.setTextAlign(TextAlignment.CENTER);

            double centerx = getWidth() / 2;
            double centery = getHeight() / 2 - 30;

            gc.fillText("PAUSED", centerx, centery);

            gc.setFont(Font.font("Arial", 20));
            gc.setFill(Color.WHITE);
            String info = "Length: " + engine.getSnake().getLength();
            gc.fillText(info, centerx, centery + 50);
        }
    }

    /**
     * Возвращает размер клетки в пикселях.
     *
     * @return размер клетки
     */
    public static int getCellSize() {
        return CELL_SIZE;
    }
}