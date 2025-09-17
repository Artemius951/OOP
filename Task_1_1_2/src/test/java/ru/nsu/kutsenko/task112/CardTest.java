// CardTest.java
package ru.nsu.kutsenko.task112;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для класса Card.
 * Содержит unit-тесты для функциональности карт.
 */
class CardTest {

    @Test
    void testCardCreation() {
        Card card = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        assertEquals(Card.Suit.SPADES, card.getSuit());
        assertEquals(Card.Rank.ACE, card.getRank());
        assertEquals(11, card.getValue());
    }

    @Test
    void testTextCard() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.KING);
        assertEquals("Король Червы (10)", card.textCard());
    }

    @Test
    void testToString() {
        Card card = new Card(Card.Suit.DIAMONDS, Card.Rank.QUEEN);
        assertEquals("Дама Бубны (10)", card.toString());
    }

    @Test
    void testSuitEnumValues() {
        assertEquals(4, Card.Suit.values().length);
        assertEquals("Пики", Card.Suit.SPADES.getName());
        assertEquals("Червы", Card.Suit.HEARTS.getName());
        assertEquals("Бубны", Card.Suit.DIAMONDS.getName());
        assertEquals("Трефы", Card.Suit.CLUBS.getName());
    }

    @Test
    void testRankEnumValues() {
        assertEquals(13, Card.Rank.values().length);
        assertEquals("Двойка", Card.Rank.TWO.getName());
        assertEquals("Туз", Card.Rank.ACE.getName());
        assertEquals(2, Card.Rank.TWO.getValue());
        assertEquals(11, Card.Rank.ACE.getValue());
    }
}