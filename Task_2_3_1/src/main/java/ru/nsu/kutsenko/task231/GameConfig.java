package ru.nsu.kutsenko.task231;

public class GameConfig {
    private int fieldWidth;
    private int fieldHeight;
    private int foodCount;
    private int winLength;
    private int tickMs;


    public GameConfig() {
    }

    public GameConfig(int fieldWidth, int fieldHeight, int foodCount, int winLength, int tickMs) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.foodCount = foodCount;
        this.winLength = winLength;
        this.tickMs = tickMs;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public int getWinLength() {
        return winLength;
    }

    public int getTickMs() {
        return tickMs;
    }


    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < fieldWidth && y >= 0 && y < fieldHeight;
    }

    public boolean isInBounds(Cell cell) {
        return isInBounds(cell.x, cell.y);
    }

    @Override
    public String toString() {
        return "GameConfig{" +
            "fieldWidth=" + fieldWidth +
            ", fieldHeight=" + fieldHeight +
            ", foodCount=" + foodCount +
            ", winLength=" + winLength +
            ", tickMs=" + tickMs +
            '}';
    }
}