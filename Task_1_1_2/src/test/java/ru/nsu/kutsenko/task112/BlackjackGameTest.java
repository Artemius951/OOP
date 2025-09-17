package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса BlackjackGame.
 * Содержит unit-тесты для основной игровой логики.
 */
public class BlackjackGameTest {

    private BlackjackGame game;

    @BeforeEach
    public void setUp() {
        game = new BlackjackGame(1);
    }

    @Test
    public void testGameInitialization() {
        assertNotNull(game);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testCheckBlackjackBoth() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

        boolean result = game.checkBlackjack();
        assertTrue(result);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testCheckBlackjackPlayer() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

        boolean result = game.checkBlackjack();
        assertTrue(result);
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testCheckBlackjackDealer() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

        boolean result = game.checkBlackjack();
        assertTrue(result);
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testDealerTurnUnder17() {
        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SIX));

        game.dealerTurn();

        assertTrue(game.dealer.getHand().size() >= 3);
    }

    @Test
    public void testDealerTurnOver17() {
        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();

        assertEquals(initialSize, game.dealer.getHand().size());
    }

    @Test
    public void testDetermineWinnerPlayerWins() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.determineWinner();
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerDealerWins() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        game.determineWinner();
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerTie() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.determineWinner();
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerPlayerBust() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.CLUBS, Card.Rank.TWO));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.determineWinner();
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerDealerBust() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TWO));

        game.determineWinner();
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testPlayerBlackjackWithAceAndTen() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));

        assertTrue(game.player.hasBlackjack());
    }

    @Test
    public void testPlayerBlackjackWithAceAndPicture() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        assertTrue(game.player.hasBlackjack());
    }

    @Test
    public void testNotBlackjackWithThreeCards() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        game.player.addCard(new Card(Card.Suit.CLUBS, Card.Rank.FIVE));

        assertFalse(game.player.hasBlackjack());
    }

    @Test
    public void testDealerRevealAllCards() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.revealAllCards();

        String handString = game.dealer.getHandString(true);
        assertTrue(handString.contains("Туз") && handString.contains("Король"));
    }

    @Test
    public void testDealerMultipleAces() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.EIGHT));

        game.dealerTurn();

        assertEquals(3, game.dealer.getHand().size());
        assertEquals(20, game.dealer.getHandValue());
    }
}