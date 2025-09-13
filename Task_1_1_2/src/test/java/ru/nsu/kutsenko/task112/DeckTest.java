package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.List;

class DeckTest {

    @Test
    void testDeckCreationSingleDeck() {
        Deck deck = new Deck(1);

        assertEquals(52, getRemainingCardsCount(deck));
    }

    @Test
    void testDeckCreationMultipleDecks() {
        Deck deck = new Deck(2);

        assertEquals(104, getRemainingCardsCount(deck));
    }

    @Test
    void testDrawCard() {
        Deck deck = new Deck(1);
        Card card = deck.drawCard();

        assertNotNull(card);
        assertTrue(card.getValue() >= 2 && card.getValue() <= 11);
        assertEquals(51, getRemainingCardsCount(deck));
    }

    @Test
    void testShuffleResetsIndex() {
        Deck deck = new Deck(1);

        // Draw some cards
        for (int i = 0; i < 10; i++) {
            deck.drawCard();
        }

        deck.shuffle();
        assertEquals(52, getRemainingCardsCount(deck));
    }

    @Test
    void testDeckAutoShuffle() {
        Deck deck = new Deck(1);

        // Draw all cards
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }

        // Next draw should trigger shuffle
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, getRemainingCardsCount(deck));
    }

    private int getRemainingCardsCount(Deck deck) {
        try {
            Field cardsField = Deck.class.getDeclaredField("cards");
            cardsField.setAccessible(true);
            List<Card> cards = (List<Card>) cardsField.get(deck);

            Field indexField = Deck.class.getDeclaredField("currentIndex");
            indexField.setAccessible(true);
            int currentIndex = (int) indexField.get(deck);

            return cards.size() - currentIndex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}