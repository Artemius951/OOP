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
        Card card = new Card("Пики", "Туз", 11);
        dealer.addCard(card);
        assertEquals(1, dealer.getHand().size());
    }

    @Test
    void testGetHandValue() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));
        assertEquals(21, dealer.getHandValue());
    }

    @Test
    void testHasBlackjack() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));
        assertTrue(dealer.hasBlackjack());
    }

    @Test
    void testRevealAllCards() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        assertFalse(dealer.isAllCardsRevealed());
        dealer.revealAllCards();
        assertTrue(dealer.isAllCardsRevealed());
    }

    @Test
    void testClearHand() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.clearHand();
        assertEquals(0, dealer.getHand().size());
        assertFalse(dealer.isAllCardsRevealed());
    }
}