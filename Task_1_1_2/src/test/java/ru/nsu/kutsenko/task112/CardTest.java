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
        Card card = new Card("Пики", "Туз", 11);
        assertEquals("Пики", card.getSuit());
        assertEquals("Туз", card.getRank());
        assertEquals(11, card.getValue());
    }

    @Test
    void testTextCard() {
        Card card = new Card("Червы", "Король", 10);
        assertEquals("Король Червы (10)", card.textCard());
    }

    @Test
    void testToString() {
        Card card = new Card("Бубны", "Дама", 10);
        assertEquals("Дама Бубны (10)", card.toString());
    }
}