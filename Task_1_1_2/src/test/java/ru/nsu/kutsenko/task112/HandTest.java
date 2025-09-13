package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    private Hand hand;

    @BeforeEach
    void setUp() {
        hand = new Hand() {}; // Anonymous subclass for testing
    }

    @Test
    void testAddCard() {
        Card card = new Card("Пики", "Туз", 11);
        hand.addCard(card);

        assertEquals(1, hand.getHand().size());
        assertEquals(card, hand.getHand().get(0));
    }

    @Test
    void testClearHand() {
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Десятка", 10));

        hand.clearHand();
        assertEquals(0, hand.getHand().size());
    }

    @Test
    void testGetHandValueBasic() {
        hand.addCard(new Card("Пики", "Двойка", 2));
        hand.addCard(new Card("Червы", "Тройка", 3));

        assertEquals(5, hand.getHandValue());
    }

    @Test
    void testGetHandValueWithAces() {
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Десятка", 10));

        assertEquals(21, hand.getHandValue());
    }

    @Test
    void testGetHandValueMultipleAces() {
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Туз", 11));
        hand.addCard(new Card("Бубны", "Девятка", 9));

        // Should be 11 + 1 + 9 = 21 (second ace becomes 1)
        assertEquals(21, hand.getHandValue());
    }

    @Test
    void testHasBlackjackTrue() {
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Десятка", 10));

        assertTrue(hand.hasBlackjack());
    }

    @Test
    void testHasBlackjackFalse() {
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Девятка", 9));

        assertFalse(hand.hasBlackjack());
    }

    @Test
    void testGetHandString() {
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Десятка", 10));

        String expected = "[Туз Пики (11), Десятка Червы (10)]";
        assertEquals(expected, hand.getHandString());
    }
}