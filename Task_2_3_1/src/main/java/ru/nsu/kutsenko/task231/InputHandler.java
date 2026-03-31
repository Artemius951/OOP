package ru.nsu.kutsenko.task231;

import javafx.scene.input.KeyEvent;

public class InputHandler {
    private volatile Direction nextDirection = Direction.DOWN;

    public synchronized Direction getNextDirection() {
        return nextDirection;
    }

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