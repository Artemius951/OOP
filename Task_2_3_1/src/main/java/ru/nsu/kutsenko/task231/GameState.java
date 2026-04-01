package ru.nsu.kutsenko.task231;

/**
 * Перечисление возможных состояний игры.
 * Паузу скоро добавлю
 */
public enum GameState {
    RUNNING,
    WON,
    LOST,
    PAUSED;

    /**
     * Проверяет, является ли состояние игры активным (выполняется или на паузе).
     *
     * @return true, если состояние RUNNING или PAUSED, иначе false
     */
    public boolean isActive() {
        return this == RUNNING || this == PAUSED;
    }

    /**
     * Проверяет, завершена ли игра (победа или поражение).
     *
     * @return true, если состояние WON или LOST, иначе false
     */
    public boolean isFinished() {
        return this == WON || this == LOST;
    }
}