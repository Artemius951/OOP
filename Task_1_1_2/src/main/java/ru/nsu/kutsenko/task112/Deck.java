package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс представляющий колоду карт
 * Содержит логику создания, перетасовки и выдачи карт
 */
public class Deck {
    private final List<Card> cards;
    private int currentIndex;

    /**
     * Конструктор колоды
     * @param numberOfDecks количество колод для создания
     */
    public Deck(int numberOfDecks) {
        this.cards = new ArrayList<>();
        this.currentIndex = 0;

        String[] suits = {"Пики", "Червы", "Бубны", "Трефы"};
        String[] ranks = {"Двойка", "Тройка", "Четверка", "Пятерка", "Шестерка",
                "Семерка", "Восьмерка", "Девятка", "Десятка", "Валет", "Дама", "Король", "Туз"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (int i = 0; i < numberOfDecks; i++) {
            for (String suit : suits) {
                for (int j = 0; j < ranks.length; j++) {
                    cards.add(new Card(suit, ranks[j], values[j]));
                }
            }
        }

        shuffle();
    }

    /**
     * Перетасовывает колоду
     */
    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    /**
     * Выдает следующую карту из колоды
     * @return следующая карта
     */
    public Card drawCard() {
        if (currentIndex >= cards.size()) {
            shuffle();
        }
        return cards.get(currentIndex++);
    }

    /**
     * Возвращает количество оставшихся карт в колоде
     * @return количество карт
     */
    public int remainingCards() {
        return cards.size() - currentIndex;
    }

    /**
     * Возвращает текущий индекс в колоде
     * @return текущий индекс
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Возвращает копию всех карт в колоде
     * @return список всех карт
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}