// DealerTest.java
package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса Dealer.
 * Содержит unit-тесты для функциональности дилера.
 */
class DealerTest {

    @Test
    void testAddCard() {
        Dealer dealer = new Dealer();
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        dealer.addCard(card);
        assertEquals(1, dealer.getHand().size());
    }

    @Test
    void testGetHandValue() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        assertEquals(21, dealer.getHandValue());
    }

    @Test
    void testHasBlackjack() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        assertTrue(dealer.hasBlackjack());
    }

    @Test
    void testRevealAllCards() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        assertFalse(dealer.isAllCardsRevealed());
        dealer.revealAllCards();
        assertTrue(dealer.isAllCardsRevealed());
    }

    @Test
    void testClearHand() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.clearHand();
        assertEquals(0, dealer.getHand().size());
        assertFalse(dealer.isAllCardsRevealed());
    }
}