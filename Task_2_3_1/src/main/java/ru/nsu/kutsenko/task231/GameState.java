package ru.nsu.kutsenko.task231;

/**
 * Перечисление возможных состояний игры.
 */
public enum GameState {
    /**
     * Игра активна и работает.
     */
    RUNNING,

    /**
     * Игрок выиграл игру.
     */
    WON,

    /**
     * Игрок проиграл игру.
     */
    LOST,

    /**
     * Игра приостановлена.
     */
    PAUSED;

    /**
     * Проверяет, находится ли игра в активном состоянии.
     *
     * @return true если RUNNING или PAUSED, иначе false
     */
    public boolean isActive() {
        return this == RUNNING || this == PAUSED;
    }

    /**
     * Проверяет, завершена ли игра.
     *
     * @return true если WON или LOST, иначе false
     */
    public boolean isFinished() {
        return this == WON || this == LOST;
    }
}