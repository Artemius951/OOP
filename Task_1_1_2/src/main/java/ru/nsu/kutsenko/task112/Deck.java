// Deck.java с использованием enum
package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс представляющий колоду карт.
 * Содержит логику создания, перетасовки и выдачи карт.
 */
public class Deck {
    private final List<Card> cards;
    private int currentIndex;

    /**
     * Конструктор колоды.
     *
     * @param numberOfDecks количество колод для создания.
     */
    public Deck(int numberOfDecks) {
        this.cards = new ArrayList<>();
        this.currentIndex = 0;

        for (int i = 0; i < numberOfDecks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }

        shuffle();
    }

    /**
     * Перетасовывает колоду.
     */
    public void shuffle() {
        Collections.shuffle(cards);
        // currentIndex = 0; // Убрано отсюда для ясности
    }

    /**
     * Выдает следующую карту из колоды.
     *
     * @return следующая карта.
     */
    public Card drawCard() {
        if (currentIndex >= cards.size()) {
            shuffle();          // Только перетасовываем
            currentIndex = 0;   // Явно сбрасываем индекс здесь
        }
        return cards.get(currentIndex++);
    }

    /**
     * Возвращает количество оставшихся карт в колоде.
     *
     * @return количество карт.
     */
    public int remainingCards() {
        return cards.size() - currentIndex;
    }

    /**
     * Возвращает текущий индекс в колоде.
     *
     * @return текущий индекс.
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Возвращает копию всех карт в колоде.
     *
     * @return список всех карт.
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}