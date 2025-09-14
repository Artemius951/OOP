package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DealerTest {

    @Test
    public void testAddCard() {
        Dealer dealer = new Dealer();
        Card card = new Card("Пики", "Туз", 11);

        dealer.addCard(card);

        assertEquals(1, dealer.getHand().size());
        assertEquals(card, dealer.getHand().get(0));
    }

    @Test
    public void testClearHand() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));

        dealer.clearHand();

        assertEquals(0, dealer.getHand().size());
        assertFalse(dealer.isAllCardsRevealed());
    }

    @Test
    public void testGetHandValue() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Десятка", 10));
        dealer.addCard(new Card("Червы", "Семерка", 7));

        assertEquals(17, dealer.getHandValue());
    }

    @Test
    public void testHasBlackjack() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));

        assertTrue(dealer.hasBlackjack());
    }

    @Test
    public void testRevealAllCards() {
        Dealer dealer = new Dealer();

        assertFalse(dealer.isAllCardsRevealed());
        dealer.revealAllCards();
        assertTrue(dealer.isAllCardsRevealed());
    }

    @Test
    public void testGetHandStringNotRevealed() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));

        String expected = "[Туз Пики (11), <закрытая карта>]";
        assertEquals(expected, dealer.getHandString(false));
    }

    @Test
    public void testGetHandStringRevealed() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));

        String expected = "[Туз Пики (11), Король Червы (10)]";
        assertEquals(expected, dealer.getHandString(true));
    }

    @Test
    public void testGetHandStringAfterRevealAll() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Король", 10));

        dealer.revealAllCards();
        String expected = "[Туз Пики (11), Король Червы (10)]";
        assertEquals(expected, dealer.getHandString(false));
    }

    @Test
    public void testGetHandReturnsCopy() {
        Dealer dealer = new Dealer();
        Card card = new Card("Пики", "Туз", 11);
        dealer.addCard(card);

        dealer.getHand().clear();

        assertEquals(1, dealer.getHand().size());
    }
}