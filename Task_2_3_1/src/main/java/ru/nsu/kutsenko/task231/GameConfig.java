package ru.nsu.kutsenko.task231;

/**
 * Конфигурация игры, содержащая параметры игрового поля и правила игры.
 */
public class GameConfig {
    private int fieldWidth;
    private int fieldHeight;
    private int foodCount;
    private int winLength;
    private int tickMs;

    /**
     * Конструктор по умолчанию для десериализации JSON.
     */
    public GameConfig() {
    }

    /**
     * Создает конфигурацию игры с заданными параметрами.
     *
     * @param fieldWidth  ширина игрового поля
     * @param fieldHeight высота игрового поля
     * @param foodCount   количество еды на поле
     * @param winLength   длина змейки для победы
     * @param tickMs      задержка между кадрами в миллисекундах
     */
    public GameConfig(int fieldWidth, int fieldHeight, int foodCount, int winLength, int tickMs) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.foodCount = foodCount;
        this.winLength = winLength;
        this.tickMs = tickMs;
    }

    /**
     * Возвращает ширину игрового поля.
     *
     * @return ширина поля в клетках
     */
    public int getFieldWidth() {
        return fieldWidth;
    }

    /**
     * Возвращает высоту игрового поля.
     *
     * @return высота поля в клетках
     */
    public int getFieldHeight() {
        return fieldHeight;
    }

    /**
     * Возвращает количество еды на игровом поле.
     *
     * @return количество объектов еды
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     * Возвращает длину змейки, необходимую для победы.
     *
     * @return длина змейки для победы
     */
    public int getWinLength() {
        return winLength;
    }

    /**
     * Возвращает задержку между кадрами игры.
     *
     * @return задержка в миллисекундах
     */
    public int getTickMs() {
        return tickMs;
    }

    /**
     * Проверяет, находятся ли координаты в пределах игрового поля.
     *
     * @param x координата по оси X
     * @param y координата по оси Y
     * @return true, если координаты внутри поля, иначе false
     */
    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < fieldWidth && y >= 0 && y < fieldHeight;
    }

    /**
     * Проверяет, находится ли клетка в пределах игрового поля.
     *
     * @param cell проверяемая клетка
     * @return true, если клетка внутри поля, иначе false
     */
    public boolean isInBounds(Cell cell) {
        return isInBounds(cell.cellx, cell.celly);
    }

    /**
     * Возвращает строковое представление конфигурации.
     *
     * @return строка с параметрами конфигурации
     */
    @Override
    public String toString() {
        return "GameConfig{"
            + "fieldWidth=" + fieldWidth
            + ", fieldHeight=" + fieldHeight
            + ", foodCount=" + foodCount
            + ", winLength=" + winLength
            + ", tickMs=" + tickMs
            + '}';
    }
}