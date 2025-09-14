package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    private BlackjackGame game;
    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck(1);
        game = new BlackjackGame(1) {
            @Override
            public Card drawCard() {
                return deck.drawCard();
            }
        };
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game);
        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }





    @Test
    void testDetermineWinnerPlayerWins() throws Exception {
        // Устанавливаем значения рук через рефлексию
        setHandValues(20, 18);

        Method method = BlackjackGame.class.getDeclaredMethod("determineWinner");
        method.setAccessible(true);
        method.invoke(game);

        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    void testDetermineWinnerDealerWins() throws Exception {
        setHandValues(18, 20);

        Method method = BlackjackGame.class.getDeclaredMethod("determineWinner");
        method.setAccessible(true);
        method.invoke(game);

        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    void testDetermineWinnerTie() throws Exception {
        setHandValues(20, 20);

        Method method = BlackjackGame.class.getDeclaredMethod("determineWinner");
        method.setAccessible(true);
        method.invoke(game);

        assertEquals(0, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }

    @Test
    void testDetermineWinnerPlayerBust() throws Exception {
        setHandValues(22, 18);

        Method method = BlackjackGame.class.getDeclaredMethod("determineWinner");
        method.setAccessible(true);
        method.invoke(game);

        assertEquals(0, game.getPlayerWins());
        assertEquals(1, game.getDealerWins());
    }

    @Test
    void testDetermineWinnerDealerBust() throws Exception {
        setHandValues(18, 22);

        Method method = BlackjackGame.class.getDeclaredMethod("determineWinner");
        method.setAccessible(true);
        method.invoke(game);

        assertEquals(1, game.getPlayerWins());
        assertEquals(0, game.getDealerWins());
    }


    @Test
    void testPlayerBlackjackDetection() {
        Player player = new Player();
        player.addCard(new Card("Пики", "Туз", 11));
        player.addCard(new Card("Червы", "Король", 10));

        assertTrue(player.hasBlackjack());
    }

    @Test
    void testPlayerNotBlackjack() {
        Player player = new Player();
        player.addCard(new Card("Пики", "Туз", 11));
        player.addCard(new Card("Червы", "Девятка", 9));

        assertFalse(player.hasBlackjack());
    }

    @Test
    void testDealerBlackjackDetection() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Бубны", "Туз", 11));
        dealer.addCard(new Card("Трефы", "Дама", 10));

        assertTrue(dealer.hasBlackjack());
    }




    // Вспомогательный метод для установки значений рук
    private void setHandValues(int playerValue, int dealerValue) throws Exception {
        // Создаем тестового игрока и дилера
        TestPlayer testPlayer = new TestPlayer();
        TestDealer testDealer = new TestDealer();

        testPlayer.setHandValue(playerValue);
        testDealer.setHandValue(dealerValue);

        // Устанавливаем через рефлексию
        Field playerField = BlackjackGame.class.getDeclaredField("player");
        playerField.setAccessible(true);
        playerField.set(game, testPlayer);

        Field dealerField = BlackjackGame.class.getDeclaredField("dealer");
        dealerField.setAccessible(true);
        dealerField.set(game, testDealer);
    }

    // Вспомогательные классы для тестирования
    private static class TestDeck extends Deck {
        private final List<Card> testCards;
        private int drawCount;

        public TestDeck() {
            super(1);
            testCards = new ArrayList<>();
            drawCount = 0;
        }

        public void addCard(Card card) {
            testCards.add(card);
        }

        @Override
        public Card drawCard() {
            if (drawCount < testCards.size()) {
                return testCards.get(drawCount++);
            }
            return new Card("Пики", "Двойка", 2); // fallback
        }

        public int getDrawCount() {
            return drawCount;
        }

        @Override
        public int remainingCards() {
            return testCards.size() - drawCount;
        }
    }

    private static class TestPlayer extends Player {
        private int handValue;

        public void setHandValue(int value) {
            this.handValue = value;
        }

        @Override
        public int getHandValue() {
            return handValue;
        }

        @Override
        public String getHandString() {
            return "[Test Cards]";
        }
    }

    private static class TestDealer extends Dealer {
        private int handValue;

        public void setHandValue(int value) {
            this.handValue = value;
        }

        @Override
        public int getHandValue() {
            return handValue;
        }

        @Override
        public String getHandString(boolean revealed) {
            return "[Test Cards]";
        }
    }
}