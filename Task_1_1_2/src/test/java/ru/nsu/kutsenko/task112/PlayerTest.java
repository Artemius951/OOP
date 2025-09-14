package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class PlayerTest {
    private Player player;
    private Card aceSpades;
    private Card kingHearts;
    private Card fiveDiamonds;

    @BeforeEach
    void setUp() {
        player = new Player();
        aceSpades = new Card("Пики", "Туз", 11);
        kingHearts = new Card("Червы", "Король", 10);
        fiveDiamonds = new Card("Бубны", "Пятерка", 5);
    }

    @Test
    void testPlayerCreation() {
        assertNotNull(player);
        assertTrue(player.getHand().isEmpty());
        assertEquals(0, player.getHandValue());
    }

    @Test
    void testAddCard() {
        player.addCard(aceSpades);
        List<Card> hand = player.getHand();

        assertEquals(1, hand.size());
        assertEquals(aceSpades, hand.get(0));
    }

    @Test
    void testGetHandValueSingleCard() {
        player.addCard(fiveDiamonds);
        assertEquals(5, player.getHandValue());
    }

    @Test
    void testGetHandValueMultipleCards() {
        player.addCard(fiveDiamonds);
        player.addCard(kingHearts);
        assertEquals(15, player.getHandValue());
    }

    @Test
    void testGetHandValueWithAceAs11() {
        player.addCard(aceSpades);
        player.addCard(kingHearts);
        assertEquals(21, player.getHandValue());
    }

    @Test
    void testGetHandValueWithAceAs1() {
        player.addCard(aceSpades);
        player.addCard(kingHearts);
        player.addCard(fiveDiamonds);
        assertEquals(16, player.getHandValue()); // Туз становится 1 вместо 11
    }

    @Test
    void testHasBlackjackTrue() {
        player.addCard(aceSpades);
        player.addCard(kingHearts);
        assertTrue(player.hasBlackjack());
    }

    @Test
    void testHasBlackjackFalseByValue() {
        player.addCard(aceSpades);
        player.addCard(fiveDiamonds);
        player.addCard(fiveDiamonds);
        assertFalse(player.hasBlackjack());
    }

    @Test
    void testHasBlackjackFalseByCardCount() {
        player.addCard(aceSpades);
        player.addCard(kingHearts);
        player.addCard(fiveDiamonds);
        assertFalse(player.hasBlackjack());
    }

    @Test
    void testGetHandStringEmpty() {
        assertEquals("", player.getHandString());
    }

    @Test
    void testGetHandStringSingleCard() {
        player.addCard(aceSpades);
        String expected = "Туз Пики (11)";
        assertEquals(expected, player.getHandString());
    }

    @Test
    void testGetHandStringMultipleCards() {
        player.addCard(aceSpades);
        player.addCard(kingHearts);
        String expected = "Туз Пики (11), Король Червы (10)";
        assertEquals(expected, player.getHandString());
    }

    @Test
    void testClearHand() {
        player.addCard(aceSpades);
        player.addCard(kingHearts);

        player.clearHand();

        assertTrue(player.getHand().isEmpty());
        assertEquals(0, player.getHandValue());
    }

    @Test
    void testGetHandReturnsCopy() {
        player.addCard(aceSpades);
        List<Card> hand = player.getHand();

        // Модификация возвращенного списка не должна влиять на оригинал
        hand.clear();

        assertEquals(1, player.getHand().size());
        assertEquals(aceSpades, player.getHand().get(0));
    }
}