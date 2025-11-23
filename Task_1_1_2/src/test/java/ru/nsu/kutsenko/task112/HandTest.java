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
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);

        hand.addCard(card);

        assertEquals(1, hand.getHand().size());
        assertEquals(card, hand.getHand().get(0));
    }

    @Test
    public void testClearHand() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        hand.clearHand();

        assertEquals(0, hand.getHand().size());
    }

    @Test
    public void testGetHandValueBasic() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        assertEquals(17, hand.getHandValue());
    }

    @Test
    public void testGetHandValueWithAces() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.NINE));

        assertEquals(20, hand.getHandValue());
    }

    @Test
    public void testGetHandValueWithMultipleAces() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE));

        assertEquals(21, hand.getHandValue());
    }

    @Test
    public void testHasBlackjack() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        assertTrue(hand.hasBlackjack());
    }

    @Test
    public void testHasBlackjackFalse() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.NINE));

        assertFalse(hand.hasBlackjack());
    }

    @Test
    public void testGetHandString() {
        TestHand hand = new TestHand();
        hand.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        hand.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        String expected = "[Туз Пики (11), Король Червы (10)]";
        assertEquals(expected, hand.getHandString());
    }
}