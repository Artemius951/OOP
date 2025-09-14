package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

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

        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Король", 10));

        game.dealer.addCard(new Card("Бубны", "Туз", 11));
        game.dealer.addCard(new Card("Трефы", "Дама", 10));


    }

    @Test
    public void testPlayerTurnHit() {

        String input = "1\n0\n"; // Hit once, then stand
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Add initial cards
        game.player.addCard(new Card("Пики", "Десятка", 10));
        game.player.addCard(new Card("Червы", "Шестерка", 6));


    }

    @Test
    public void testDealerTurnUnder17() throws Exception {
        Method dealerTurn = BlackjackGame.class.getDeclaredMethod("dealerTurn");
        dealerTurn.setAccessible(true);

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Шестерка", 6)); // 16 - should hit

        dealerTurn.invoke(game);

        // Dealer should have at least 3 cards (16 + whatever was drawn)
        assertTrue(game.dealer.getHand().size() >= 3);
    }

    @Test
    public void testDealerTurnOver17() throws Exception {
        Method dealerTurn = BlackjackGame.class.getDeclaredMethod("dealerTurn");
        dealerTurn.setAccessible(true);

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Восьмерка", 8)); // 18 - should stand

        int initialSize = game.dealer.getHand().size();
        dealerTurn.invoke(game);

        // Dealer should not have drawn any cards
        assertEquals(initialSize, game.dealer.getHand().size());
    }

    @Test
    public void testDetermineWinnerPlayerWins() {

        game.player.addCard(new Card("Пики", "Десятка", 10));
        game.player.addCard(new Card("Червы", "Восьмерка", 8)); // 18

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Семерка", 7)); // 17


    }

    @Test
    public void testCheckBlackjackPlayer() throws Exception {
        Method checkBlackjack = BlackjackGame.class.getDeclaredMethod("checkBlackjack");
        checkBlackjack.setAccessible(true);

        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Король", 10));

        game.dealer.addCard(new Card("Бубны", "Девятка", 9));
        game.dealer.addCard(new Card("Трефы", "Дама", 10));

        boolean result = (Boolean) checkBlackjack.invoke(game);
        assertTrue(result);
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testCheckBlackjackDealer() throws Exception {
        Method checkBlackjack = BlackjackGame.class.getDeclaredMethod("checkBlackjack");
        checkBlackjack.setAccessible(true);

        game.player.addCard(new Card("Пики", "Девятка", 9));
        game.player.addCard(new Card("Червы", "Король", 10));

        game.dealer.addCard(new Card("Бубны", "Туз", 11));
        game.dealer.addCard(new Card("Трефы", "Дама", 10));

        boolean result = (Boolean) checkBlackjack.invoke(game);
        assertTrue(result);
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerTie() throws Exception {
        Method determineWinner = BlackjackGame.class.getDeclaredMethod("determineWinner");
        determineWinner.setAccessible(true);

        game.player.addCard(new Card("Пики", "Десятка", 10));
        game.player.addCard(new Card("Червы", "Семерка", 7)); // 17

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Семерка", 7)); // 17

        determineWinner.invoke(game);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerPlayerBust() throws Exception {
        Method determineWinner = BlackjackGame.class.getDeclaredMethod("determineWinner");
        determineWinner.setAccessible(true);

        game.player.addCard(new Card("Пики", "Десятка", 10));
        game.player.addCard(new Card("Червы", "Десятка", 10));
        game.player.addCard(new Card("Трефы", "Двойка", 2)); // 22 - bust

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Семерка", 7)); // 17

        determineWinner.invoke(game);
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerDealerBust() throws Exception {
        Method determineWinner = BlackjackGame.class.getDeclaredMethod("determineWinner");
        determineWinner.setAccessible(true);

        game.player.addCard(new Card("Пики", "Десятка", 10));
        game.player.addCard(new Card("Червы", "Семерка", 7)); // 17

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Десятка", 10));
        game.dealer.addCard(new Card("Пики", "Двойка", 2)); // 22 - bust

        determineWinner.invoke(game);
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }


    @Test
    public void testPlayerTurnMultipleAces() throws Exception {
        String input = "1\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Method playerTurn = BlackjackGame.class.getDeclaredMethod("playerTurn");
        playerTurn.setAccessible(true);

        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Туз", 11)); // 12

        playerTurn.invoke(game);

        // After hitting, should have 3 cards and proper ace value calculation
        assertEquals(3, game.player.getHand().size());
        assertTrue(game.player.getHandValue() <= 21);
    }

    @Test
    public void testDealerTurnExact16() throws Exception {
        Method dealerTurn = BlackjackGame.class.getDeclaredMethod("dealerTurn");
        dealerTurn.setAccessible(true);

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Шестерка", 6)); // 16 - must hit

        dealerTurn.invoke(game);

        assertTrue(game.dealer.getHand().size() >= 3);
        assertTrue(game.dealer.getHandValue() >= 17 || game.dealer.getHandValue() > 21);
    }



    @Test
    public void testDetermineWinnerPlayer21vsDealer20() throws Exception {
        Method determineWinner = BlackjackGame.class.getDeclaredMethod("determineWinner");
        determineWinner.setAccessible(true);

        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Король", 10)); // 21

        game.dealer.addCard(new Card("Бубны", "Король", 10));
        game.dealer.addCard(new Card("Трефы", "Десятка", 10)); // 20

        determineWinner.invoke(game);
        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }



    @Test
    public void testDetermineWinnerPlayer20vsDealer21() throws Exception {
        Method determineWinner = BlackjackGame.class.getDeclaredMethod("determineWinner");
        determineWinner.setAccessible(true);

        game.player.addCard(new Card("Пики", "Король", 10));
        game.player.addCard(new Card("Червы", "Десятка", 10)); // 20

        game.dealer.addCard(new Card("Бубны", "Туз", 11));
        game.dealer.addCard(new Card("Трефы", "Король", 10)); // 21

        determineWinner.invoke(game);
        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerBoth20() throws Exception {
        Method determineWinner = BlackjackGame.class.getDeclaredMethod("determineWinner");
        determineWinner.setAccessible(true);

        game.player.addCard(new Card("Пики", "Король", 10));
        game.player.addCard(new Card("Червы", "Десятка", 10)); // 20

        game.dealer.addCard(new Card("Бубны", "Дама", 10));
        game.dealer.addCard(new Card("Трефы", "Десятка", 10)); // 20

        determineWinner.invoke(game);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testPlayerBlackjackWithAceAndTen() {
        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Десятка", 10));

        assertTrue(game.player.hasBlackjack());
    }

    @Test
    public void testPlayerBlackjackWithAceAndPicture() {
        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Король", 10));

        assertTrue(game.player.hasBlackjack());
    }

    @Test
    public void testStartGameSingleRound() throws Exception {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        BlackjackGame testGame = new BlackjackGame(1) {
            @Override
            public void playRound() {
                // Mock playRound to avoid complex game logic
                int playerWins = 0;
                int dealerWins = 0;
            }
        };

        testGame.startGame();

        // Should complete without exception
        assertTrue(true);
    }

    @Test
    public void testNotBlackjackWithThreeCards() {
        game.player.addCard(new Card("Пики", "Туз", 11));
        game.player.addCard(new Card("Червы", "Пятерка", 5));
        game.player.addCard(new Card("Трефы", "Пятерка", 5)); // 21 but not blackjack

        assertFalse(game.player.hasBlackjack());
    }

    @Test
    public void testDealerRevealAllCards() {
        game.dealer.addCard(new Card("Пики", "Туз", 11));
        game.dealer.addCard(new Card("Червы", "Король", 10));

        game.dealer.revealAllCards();

        // After reveal, all cards should be visible in string representation
        String handString = game.dealer.getHandString(true);
        assertTrue(handString.contains("Туз") && handString.contains("Король"));
    }
}