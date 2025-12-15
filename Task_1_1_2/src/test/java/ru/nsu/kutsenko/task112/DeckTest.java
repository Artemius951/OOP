package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса Deck.
 * Содержит unit-тесты для функциональности колоды карт.
 */
public class DeckTest {

    @Test
    public void testDeckCreationSingleDeck() {
        Deck deck = new Deck(1);

        assertEquals(52, deck.getCards().size());
        assertEquals(0, deck.getCurrentIndex());
    }

    @Test
    public void testDeckCreationMultipleDecks() {
        Deck deck = new Deck(4);

        assertEquals(208, deck.getCards().size());
    }

    @Test
    public void testShuffle() {
        Deck deck = new Deck(1);
        Card firstCardBeforeShuffle = deck.getCards().get(0);

        deck.shuffle();
        Card firstCardAfterShuffle = deck.getCards().get(0);

        assertNotEquals(firstCardBeforeShuffle, firstCardAfterShuffle);
        assertEquals(0, deck.getCurrentIndex());
    }

    @Test
    public void testDrawCard() {
        Deck deck = new Deck(1);
        int initialSize = deck.getCards().size();

        Card drawnCard = deck.drawCard();

        assertNotNull(drawnCard);
        assertEquals(1, deck.getCurrentIndex());
        assertEquals(initialSize - 1, deck.remainingCards());
    }

    @Test
    public void testDrawCardWithReshuffle() {
        Deck deck = new Deck(1);

        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }

        assertEquals(52, deck.getCurrentIndex());
        assertEquals(0, deck.remainingCards());

        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(1, deck.getCurrentIndex());
    }
}