package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for Player class.
 * Contains unit tests for Player functionality.
 */
public class PlayerTest {

    @Test
    public void testAddCard() {
        Player player = new Player();
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);

        player.addCard(card);

        assertEquals(1, player.getHand().size());
        assertEquals(card, player.getHand().get(0));
    }

    @Test
    public void testClearHand() {
        Player player = new Player();
        player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        player.clearHand();

        assertEquals(0, player.getHand().size());
    }

    @Test
    public void testGetHandValue() {
        Player player = new Player();
        player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        assertEquals(17, player.getHandValue());
    }

    @Test
    public void testHasBlackjack() {
        Player player = new Player();
        player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        assertTrue(player.hasBlackjack());
    }

    @Test
    public void testGetHandString() {
        Player player = new Player();
        player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        String expected = "[Туз Пики (11), Король Червы (10)]";
        assertEquals(expected, player.getHandString());
    }

    @Test
    public void testGetHandReturnsCopy() {
        Player player = new Player();
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        player.addCard(card);

        player.getHand().clear();

        assertEquals(1, player.getHand().size());
    }
}