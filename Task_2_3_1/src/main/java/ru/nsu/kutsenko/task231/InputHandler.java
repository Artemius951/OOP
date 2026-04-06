package ru.nsu.kutsenko.task231;

import javafx.scene.input.KeyEvent;

/**
 * Обрабатывает ввод пользователя и управляет направлением движения змейки.
 */
public class InputHandler {
    private volatile Direction nextDirection = Direction.DOWN;
    private volatile boolean pauseRequested = false;
    private volatile GameState gameState = GameState.RUNNING;

    /**
     * Возвращает следующее направление, выбранное пользователем.
     *
     * @return направление движения
     */
    public synchronized Direction getNextDirection() {
        return nextDirection;
    }

    /**
     * Проверяет, был ли запрос паузы, и сбрасывает флаг.
     *
     * @return true, если была нажата клавиша паузы
     */
    public synchronized boolean isPauseRequested() {
        boolean requested = pauseRequested;
        pauseRequested = false;
        return requested;
    }

    /**
     * Обновляет текущее состояние игры.
     * Используется для определения, разрешены ли изменения направления.
     *
     * @param state текущее состояние игры
     */
    public synchronized void setGameState(GameState state) {
        this.gameState = state;
    }

    /**
     * Обрабатывает нажатие клавиши и обновляет направление движения.
     * Поддерживает как стрелки, так и WASD управление.
     * Пробел - для паузы.
     * Изменение направления запрещено во время паузы.
     *
     * @param e событие нажатия клавиши
     */
    public synchronized void keyPressed(KeyEvent e) {
        String code = e.getCode().toString();

        if ("SPACE".equals(code)) {
            pauseRequested = true;
            return;
        }

        if (gameState == GameState.PAUSED) {
            return;
        }

        switch (code) {
            case "UP", "W" -> nextDirection = Direction.UP;
            case "DOWN", "S" -> nextDirection = Direction.DOWN;
            case "LEFT", "A" -> nextDirection = Direction.LEFT;
            case "RIGHT", "D" -> nextDirection = Direction.RIGHT;
            default -> {
            }
        }
    }
}