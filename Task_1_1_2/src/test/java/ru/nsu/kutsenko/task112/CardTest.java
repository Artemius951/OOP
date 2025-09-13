package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card("Пики", "Туз", 11);

        assertEquals("Пики", card.getSuit());
        assertEquals("Туз", card.getRank());
        assertEquals(11, card.getValue());
    }

    @Test
    void testTextCard() {
        Card card = new Card("Червы", "Десятка", 10);

        assertEquals("Десятка Червы (10)", card.textCard());
    }

    @Test
    void testCardEquality() {
        Card card1 = new Card("Бубны", "Король", 10);
        Card card2 = new Card("Бубны", "Король", 10);

        assertEquals(card1.getSuit(), card2.getSuit());
        assertEquals(card1.getRank(), card2.getRank());
        assertEquals(card1.getValue(), card2.getValue());
    }
}