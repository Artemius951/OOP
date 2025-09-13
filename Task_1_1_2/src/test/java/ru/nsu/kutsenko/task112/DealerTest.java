package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
    }

    @Test
    void testRevealAllCards() {
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Десятка", 10));

        // Initially should show hidden card
        String hidden = dealer.getHandString(false);
        assertTrue(hidden.contains("<закрытая карта>"));

        dealer.revealAllCards();

        // After revealing, should show all cards even with false parameter
        String revealed = dealer.getHandString(false);
        assertTrue(revealed.contains("Туз Пики"));
        assertTrue(revealed.contains("Десятка Червы"));
        assertFalse(revealed.contains("<закрытая карта>"));
    }

    @Test
    void testGetHandStringHidden() {
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Десятка", 10));

        String result = dealer.getHandString(false);
        assertTrue(result.contains("Туз Пики"));
        assertTrue(result.contains("<закрытая карта>"));
        assertFalse(result.contains("Десятка Червы"));
    }

    @Test
    void testGetHandStringRevealedParameter() {
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.addCard(new Card("Червы", "Десятка", 10));

        String result = dealer.getHandString(true);
        assertTrue(result.contains("Туз Пики"));
        assertTrue(result.contains("Десятка Червы"));
        assertFalse(result.contains("<закрытая карта>"));
    }

    @Test
    void testClearHandResetsState() {
        dealer.addCard(new Card("Пики", "Туз", 11));
        dealer.revealAllCards();

        // Verify cards are revealed
        String revealed = dealer.getHandString(false);
        assertFalse(revealed.contains("<закрытая карта>"));

        dealer.clearHand();

        // After clearing, add new cards - they should be hidden again
        dealer.addCard(new Card("Червы", "Десятка", 10));
        dealer.addCard(new Card("Бубны", "Двойка", 2));

        String result = dealer.getHandString(false);
        assertTrue(result.contains("Десятка Червы"));
        assertTrue(result.contains("<закрытая карта>"));
        assertFalse(result.contains("Двойка Бубны")); // Second card should be hidden
    }

    @Test
    void testSingleCardHand() {
        dealer.addCard(new Card("Пики", "Туз", 11));

        String result = dealer.getHandString(false);
        assertEquals("[Туз Пики (11)]", result);
        assertFalse(result.contains("<закрытая карта>"));
    }

    @Test
    void testEmptyHand() {
        String result = dealer.getHandString(false);
        assertEquals("[]", result);
    }
}