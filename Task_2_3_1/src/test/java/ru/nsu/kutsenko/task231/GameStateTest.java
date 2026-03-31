package ru.nsu.kutsenko.task231;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateTest {

    @Test
    public void testGameStateRunning() {
        GameState state = GameState.RUNNING;
        assertTrue(state.isActive());
        assertFalse(state.isFinished());
    }

    @Test
    public void testGameStatePaused() {
        GameState state = GameState.PAUSED;
        assertTrue(state.isActive());
        assertFalse(state.isFinished());
    }

    @Test
    public void testGameStateWon() {
        GameState state = GameState.WON;
        assertFalse(state.isActive());
        assertTrue(state.isFinished());
    }

    @Test
    public void testGameStateLost() {
        GameState state = GameState.LOST;
        assertFalse(state.isActive());
        assertTrue(state.isFinished());
    }

    @Test
    public void testAllGameStatesExist() {
        GameState[] states = GameState.values();
        assertEquals(4, states.length);
    }
}