package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardCreation() {
        Card card = new Card("Пики", "Туз", 11);

        assertEquals("Пики", card.getSuit());
        assertEquals("Туз", card.getRank());
        assertEquals(11, card.getValue());
    }

    @Test
    public void testTextCard() {
        Card card = new Card("Червы", "Король", 10);
        String expected = "Король Червы (10)";

        assertEquals(expected, card.textCard());
    }

    @Test
    public void testToString() {
        Card card = new Card("Бубны", "Дама", 10);
        String expected = "Дама Бубны (10)";

        assertEquals(expected, card.toString());
    }
}