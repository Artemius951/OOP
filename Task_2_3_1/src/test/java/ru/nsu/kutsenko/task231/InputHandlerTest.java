package ru.nsu.kutsenko.task231;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса InputHandler.
 */
public class InputHandlerTest {
    private InputHandler inputHandler;

    @BeforeEach
    public void setUp() {
        inputHandler = new InputHandler();
    }

    @Test
    public void testInitialDirection() {
        assertEquals(Direction.DOWN, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedUpArrow() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            KeyCode.UP,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.UP, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedDownArrow() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            KeyCode.DOWN,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.DOWN, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedLeftArrow() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            KeyCode.LEFT,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.LEFT, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedRightArrow() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            KeyCode.RIGHT,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.RIGHT, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedW() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "w",
            "w",
            KeyCode.W,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.UP, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedS() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "s",
            "s",
            KeyCode.S,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.DOWN, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedA() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "a",
            "a",
            KeyCode.A,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.LEFT, inputHandler.getNextDirection());
    }

    @Test
    public void testKeyPressedD() {
        KeyEvent event = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "d",
            "d",
            KeyCode.D,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(event);
        assertEquals(Direction.RIGHT, inputHandler.getNextDirection());
    }

    @Test
    public void testMultipleKeyPresses() {
        KeyEvent eventUp = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            KeyCode.UP,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(eventUp);
        assertEquals(Direction.UP, inputHandler.getNextDirection());

        KeyEvent eventLeft = new KeyEvent(
            KeyEvent.KEY_PRESSED,
            "",
            "",
            KeyCode.LEFT,
            false,
            false,
            false,
            false
        );

        inputHandler.keyPressed(eventLeft);
        assertEquals(Direction.LEFT, inputHandler.getNextDirection());
    }
}