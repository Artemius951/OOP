package ru.nsu.kutsenko.task231;

import javafx.scene.input.KeyEvent;

/**
 * Обрабатывает ввод с клавиатуры для управления змейкой.
 */
public class InputHandler {
    private volatile Direction nextDirection = Direction.DOWN;

    /**
     * Возвращает следующее направление движения.
     *
     * @return текущее запрошенное направление
     */
    public synchronized Direction getNextDirection() {
        return nextDirection;
    }

    /**
     * Обрабатывает нажатие клавиши и устанавливает соответствующее направление.
     * Поддерживает клавиши стрелок (UP, DOWN, LEFT, RIGHT) и клавиши WASD.
     *
     * @param e событие нажатия клавиши
     */
    public synchronized void keyPressed(KeyEvent e) {
        String code = e.getCode().toString();

        switch (code) {
            case "UP":
                nextDirection = Direction.UP;
                break;
            case "DOWN":
                nextDirection = Direction.DOWN;
                break;
            case "LEFT":
                nextDirection = Direction.LEFT;
                break;
            case "RIGHT":
                nextDirection = Direction.RIGHT;
                break;
            case "W":
                nextDirection = Direction.UP;
                break;
            case "S":
                nextDirection = Direction.DOWN;
                break;
            case "A":
                nextDirection = Direction.LEFT;
                break;
            case "D":
                nextDirection = Direction.RIGHT;
                break;
        }
    }
}