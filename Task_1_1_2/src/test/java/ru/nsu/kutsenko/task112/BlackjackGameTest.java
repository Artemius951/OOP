package ru.nsu.kutsenko.task112;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    public void testDetermineWinnerPlayerWins() {

        game.player.addCard(new Card("Пики", "Десятка", 10));
        game.player.addCard(new Card("Червы", "Восьмерка", 8)); // 18

        game.dealer.addCard(new Card("Бубны", "Десятка", 10));
        game.dealer.addCard(new Card("Трефы", "Семерка", 7)); // 17


    }


}