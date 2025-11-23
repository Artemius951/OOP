package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса BlackjackGame.
 * Содержит unit-тесты для основной игровой логики.
 */

public class BlackjackGameTest {

    private BlackjackGame game;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        game = new BlackjackGame(1);
        game.scanner = new Scanner(new ByteArrayInputStream("".getBytes()));
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Cleans up the test environment after each test.
     */
    @AfterEach
    public void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
        if (game != null && game.scanner != null) {
            game.scanner.close();
        }
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
    public void testCheckBlackjackNone() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        boolean result = game.checkBlackjack();
        assertFalse(result);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testDealerTurnUnder17() {
        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SIX));

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();

        assertTrue(game.dealer.getHand().size() > initialSize);
        assertTrue(game.dealer.isAllCardsRevealed());
    }

    @Test
    public void testDealerTurnOver17() {
        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();

        assertEquals(initialSize, game.dealer.getHand().size());
        assertTrue(game.dealer.isAllCardsRevealed());
    }

    @Test
    public void testDealerTurnBust() {
        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));

        game.dealerTurn();

        assertTrue(game.dealer.getHandValue() > 21);
        assertTrue(game.dealer.isAllCardsRevealed());
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
    public void testDisplayGameStateDealerHidden() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.displayGameState(false);

        String output = outputStream.toString();
        assertTrue(output.contains("Ваши карты:"));
        assertTrue(output.contains("Карты дилера:"));
        assertTrue(output.contains("<закрытая карта>"));
    }

    @Test
    public void testDisplayGameStateDealerRevealed() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));
        game.dealer.revealAllCards();

        game.displayGameState(true);

        String output = outputStream.toString();
        assertTrue(output.contains("Ваши карты:"));
        assertTrue(output.contains("Карты дилера:"));
        assertTrue(output.contains("Десятка") || output.contains("Семерка"));
    }

    @Test
    public void testPlayRoundWithPlayerBlackjack() {
        List<Card> fixedCards = List.of(
            new Card(Card.Suit.SPADES, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.NINE),
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.QUEEN)
        );

        Deck mockDeck = new Deck(1) {
            private int index = 0;

            @Override
            public Card drawCard() {
                return fixedCards.get(index++ % fixedCards.size());
            }
        };

        game.deck = mockDeck;
        game.playRound();

        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testPlayRoundWithDealerBlackjack() {
        List<Card> fixedCards = List.of(
            new Card(Card.Suit.SPADES, Card.Rank.NINE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.KING)
        );

        Deck mockDeck = new Deck(1) {
            private int index = 0;

            @Override
            public Card drawCard() {
                return fixedCards.get(index++ % fixedCards.size());
            }
        };

        game.deck = mockDeck;
        game.playRound();

        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testPlayRoundWithBothBlackjack() {
        List<Card> fixedCards = List.of(
            new Card(Card.Suit.SPADES, Card.Rank.ACE),
            new Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
            new Card(Card.Suit.HEARTS, Card.Rank.KING),
            new Card(Card.Suit.CLUBS, Card.Rank.KING)
        );

        Deck mockDeck = new Deck(1) {
            private int index = 0;

            @Override
            public Card drawCard() {
                return fixedCards.get(index++ % fixedCards.size());
            }
        };

        game.deck = mockDeck;
        game.playRound();

        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testHandValueCalculationOptimalAceUsage() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE));

        assertEquals(21, game.player.getHandValue());
    }





    @Test
    public void testDealerTurnExact17() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();

        assertEquals(initialSize, game.dealer.getHand().size());
        assertEquals(17, game.dealer.getHandValue());
    }

    @Test
    public void testDealerTurnSoft17() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SIX));

        Deck mockDeck = new Deck(1) {
            @Override
            public Card drawCard() {
                return new Card(Card.Suit.DIAMONDS, Card.Rank.TWO);
            }
        };
        game.deck = mockDeck;

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();
        assertTrue(game.dealer.getHand().size() > initialSize);
        assertTrue(game.dealer.getHandValue() >= 17);
    }

    @Test
    public void testDetermineWinnerPlayerWinsHigherScore() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.determineWinner();

        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    public void testDetermineWinnerDealerWinsHigherScore() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.EIGHT));

        game.determineWinner();

        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }


    @Test
    public void testDisplayGameState() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.KING));

        game.dealer.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.SEVEN));

        game.displayGameState(false);

        String output = outputStream.toString();
        assertTrue(output.contains("Ваши карты:"));
        assertTrue(output.contains("Карты дилера:"));
        assertTrue(output.contains("<закрытая карта>"));
    }

    @Test
    public void testMainMethodWithValidInput() {
        String input = "2\n0\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        try {
            System.setIn(inputStream);
            System.setOut(new PrintStream(outContent));

            Thread thread = new Thread(() -> {
                try {
                    System.setIn(new ByteArrayInputStream(input.getBytes()));
                    BlackjackGame.main(new String[]{});
                } catch (Exception e) {
                    System.err.println("Ожидаемое исключение в тесте: "
                        + e.getClass().getSimpleName());
                }
            });
            thread.start();

            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (thread.isAlive()) {
                thread.interrupt();
            }

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        assertTrue(output.contains("Добро пожаловать в Блэкджек!")
            || output.contains("Введите количество колод")
            || output.contains("Игра завершена"));
    }

    @Test
    public void testDealerTurnHard17() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();

        assertEquals(initialSize, game.dealer.getHand().size());
        assertEquals(17, game.dealer.getHandValue());
    }

    @Test
    public void testDealerTurnExact16() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SIX));

        Deck mockDeck = new Deck(1) {
            @Override
            public Card drawCard() {
                return new Card(Card.Suit.DIAMONDS, Card.Rank.TWO);
            }
        };
        game.deck = mockDeck;

        int initialSize = game.dealer.getHand().size();
        game.dealerTurn();

        assertTrue(game.dealer.getHand().size() > initialSize);
    }

    @Test
    public void testDetermineWinnerBothBust() {
        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO));

        game.dealer.addCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.THREE));

        game.determineWinner();

        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }


    @Test
    public void testPlayerTurnBust() {
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.scanner = new Scanner(System.in);

        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));

        Deck mockDeck = new Deck(1) {
            @Override
            public Card drawCard() {
                return new Card(Card.Suit.DIAMONDS, Card.Rank.KING);
            }
        };
        game.deck = mockDeck;

        game.playerTurn();

        assertTrue(game.player.getHandValue() > 21);
    }

    @Test
    public void testPlayerTurnStand() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.scanner = new Scanner(System.in);

        game.player.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.player.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        int initialSize = game.player.getHand().size();
        game.playerTurn();

        assertEquals(initialSize, game.player.getHand().size());
        assertEquals(17, game.player.getHandValue());
    }

    @Test
    public void testHasSoft17True() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SIX));

        assertTrue(game.hasSoft17(game.dealer));
    }

    @Test
    public void testHasSoft17False() {
        game.dealer.addCard(new Card(Card.Suit.SPADES, Card.Rank.TEN));
        game.dealer.addCard(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));

        assertFalse(game.hasSoft17(game.dealer));
    }


    @Test
    public void testPlayRoundPlayerBust() {
        List<Card> fixedCards = List.of(
            new Card(Card.Suit.SPADES, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.NINE),
            new Card(Card.Suit.HEARTS, Card.Rank.TEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.KING)
        );

        Deck mockDeck = new Deck(1) {
            private int index = 0;

            @Override
            public Card drawCard() {
                return fixedCards.get(index++);
            }
        };

        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.scanner = new Scanner(System.in);

        game.deck = mockDeck;
        game.playRound();

        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    public void testPlayRoundDealerBust() {
        List<Card> fixedCards = List.of(
            new Card(Card.Suit.SPADES, Card.Rank.TEN),
            new Card(Card.Suit.DIAMONDS, Card.Rank.TEN),
            new Card(Card.Suit.HEARTS, Card.Rank.SEVEN),
            new Card(Card.Suit.CLUBS, Card.Rank.SIX),
            new Card(Card.Suit.CLUBS, Card.Rank.KING)
        );

        Deck mockDeck = new Deck(1) {
            private int index = 0;

            @Override
            public Card drawCard() {
                return fixedCards.get(index++);
            }
        };

        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        game.scanner = new Scanner(System.in);

        game.deck = mockDeck;
        game.playRound();

        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }



}