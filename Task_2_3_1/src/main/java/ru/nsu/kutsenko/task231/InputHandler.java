package ru.nsu.kutsenko.task231;

import javafx.scene.input.KeyEvent;

/**
 * Обрабатывает ввод пользователя и управляет направлением движения змейки.
 */
public class InputHandler {
    private volatile Direction nextDirection = Direction.DOWN;

    /**
     * Возвращает следующее направление, выбранное пользователем.
     *
     * @return направление движения
     */
    public synchronized Direction getNextDirection() {
        return nextDirection;
    }

    /**
     * Обрабатывает нажатие клавиши и обновляет направление движения.
     * Поддерживает как стрелки, так и WASD управление.
     *
     * @param e событие нажатия клавиши
     */
    public synchronized void keyPressed(KeyEvent e) {
        String code = e.getCode().toString();

        switch (code) {
            case "UP" -> nextDirection = Direction.UP;
            case "DOWN" -> nextDirection = Direction.DOWN;
            case "LEFT" -> nextDirection = Direction.LEFT;
            case "RIGHT" -> nextDirection = Direction.RIGHT;
            case "W" -> nextDirection = Direction.UP;
            case "S" -> nextDirection = Direction.DOWN;
            case "A" -> nextDirection = Direction.LEFT;
            case "D" -> nextDirection = Direction.RIGHT;
            default -> {
            }
        }
    }
}