package ru.nsu.kutsenko.task231;

public enum GameState {
    RUNNING,
    WON,
    LOST,
    PAUSED;

    public boolean isActive() {
        return this == RUNNING || this == PAUSED;
    }

    public boolean isFinished() {
        return this == WON || this == LOST;
    }
}