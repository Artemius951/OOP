package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса Hand.
 * Содержит unit-тесты для базовой функциональности руки карт.
 */
public class HandTest {

    private static class TestHand extends Hand {

    }

    @Test
    public void testAddCard() {
        TestHand hand = new TestHand();
        Card card = new Card("Пики", "Туз", 11);

        hand.addCard(card);

        assertEquals(1, hand.getHand().size());
        assertEquals(card, hand.getHand().get(0));
    }

    @Test
    public void testClearHand() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Король", 10));

        hand.clearHand();

        assertEquals(0, hand.getHand().size());
    }

    @Test
    public void testGetHandValueBasic() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Десятка", 10));
        hand.addCard(new Card("Червы", "Семерка", 7));

        assertEquals(17, hand.getHandValue());
    }

    @Test
    public void testGetHandValueWithAces() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Девятка", 9));

        assertEquals(20, hand.getHandValue()); // 11 + 9 = 20
    }

    @Test
    public void testGetHandValueWithMultipleAces() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Туз", 11));
        hand.addCard(new Card("Бубны", "Девятка", 9));

        assertEquals(21, hand.getHandValue()); // 11 + 1 + 9 = 21
    }

    @Test
    public void testHasBlackjack() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Король", 10));

        assertTrue(hand.hasBlackjack());
    }

    @Test
    public void testHasBlackjackFalse() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Девятка", 9));

        assertFalse(hand.hasBlackjack());
    }

    @Test
    public void testGetHandString() {
        TestHand hand = new TestHand();
        hand.addCard(new Card("Пики", "Туз", 11));
        hand.addCard(new Card("Червы", "Король", 10));

        String expected = "[Туз Пики (11), Король Червы (10)]";
        assertEquals(expected, hand.getHandString());
    }
}