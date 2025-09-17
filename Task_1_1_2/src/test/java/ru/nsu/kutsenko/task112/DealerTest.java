// DealerTest.java
package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса Dealer.
 * Содержит unit-тесты для функциональности дилера.
 */
class DealerTest {

    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
    }
    
    @Test
    void testAddCard() {
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        dealer.addCard(card);
        assertEquals(1, dealer.getHand().size());
        assertEquals(card, dealer.getHand().get(0));
    }

    @Test
    void testAddMultipleCards() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN));
        assertEquals(3, dealer.getHand().size());
    }

    @Test
    void testGetHandValueBasic() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));
        assertEquals(17, dealer.getHandValue());
    }

    @Test
    void testGetHandValueWithAcesSoft() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SIX));
        assertEquals(17, dealer.getHandValue()); // Ace = 11 + 6 = 17
    }

    @Test
    void testGetHandValueWithAcesHard() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));
        assertEquals(16, dealer.getHandValue()); // Ace = 1 + 10 + 5 = 16
    }

    @Test
    void testGetHandValueMultipleAces() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE));
        assertEquals(21, dealer.getHandValue()); // Ace=11 + Ace=1 + 9 = 21
    }

    @Test
    void testGetHandValueThreeAces() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        assertEquals(13, dealer.getHandValue()); // Ace=11 + Ace=1 + Ace=1 = 13
    }

    @Test
    void testHasBlackjackTrue() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        assertTrue(dealer.hasBlackjack());
    }

    @Test
    void testHasBlackjackFalse() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.NINE));
        assertFalse(dealer.hasBlackjack());
    }

    @Test
    void testHasBlackjackThreeCards() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));
        assertFalse(dealer.hasBlackjack()); // 21 очков, но не блэкджек (3 карты)
    }

    @Test
    void testRevealAllCards() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        assertFalse(dealer.isAllCardsRevealed());
        dealer.revealAllCards();
        assertTrue(dealer.isAllCardsRevealed());
    }

    @Test
    void testRevealAllCardsEmptyHand() {
        assertFalse(dealer.isAllCardsRevealed());
        dealer.revealAllCards();
        assertTrue(dealer.isAllCardsRevealed());
    }

    @Test
    void testClearHand() {
        dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        dealer.revealAllCards();

        dealer.clearHand();

        assertEquals(0, dealer.getHand().size());
        assertFalse(dealer.isAllCardsRevealed());
    }

    @Test
    void testClearHandEmpty() {
        dealer.clearHand();
        assertEquals(0, dealer.getHand().size());
        assertFalse(dealer.isAllCardsRevealed());
    }

    @Test
    void testGetFirstCard() {
        Card firstCard = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        dealer.addCard(firstCard);
        dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        // Проверяем, что можно получить первую карту (может быть полезно для отображения)
        assertEquals(firstCard, dealer.getHand().get(0));
    }

    @Test
    void testEmptyHandValue() {
        assertEquals(0, dealer.getHandValue());
    }

    @Test
    void testEmptyHandBlackjack() {
        assertFalse(dealer.hasBlackjack());
    }
}