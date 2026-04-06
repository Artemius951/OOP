package ru.nsu.kutsenko.task231;

import javafx.scene.input.KeyEvent;

/**
 * Обрабатывает ввод пользователя и управляет направлением движения змейки.
 */
public class InputHandler {
    private volatile Direction nextDirection = Direction.DOWN;
    private volatile boolean pauseRequested = false;

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
     * Обрабатывает нажатие клавиши и обновляет направление движения.
     * Поддерживает как стрелки, так и WASD управление.
     * Пробел - для паузы.
     *
     * @param e событие нажатия клавиши
     */
    public synchronized void keyPressed(KeyEvent e) {
        String code = e.getCode().toString();

        switch (code) {
            case "UP","W" -> nextDirection = Direction.UP;
            case "DOWN","S" -> nextDirection = Direction.DOWN;
            case "LEFT","A" -> nextDirection = Direction.LEFT;
            case "RIGHT","D" -> nextDirection = Direction.RIGHT;
            case "SPACE" -> pauseRequested = true;
            default -> {
            }
        }
    }
}