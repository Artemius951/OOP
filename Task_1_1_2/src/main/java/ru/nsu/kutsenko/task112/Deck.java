package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;
    private int currentIndex;

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

    public void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    public Card drawCard() {
        if (currentIndex >= cards.size()) {
            shuffle();
        }
        return cards.get(currentIndex++);
    }

    public int remainingCards() {
        return cards.size() - currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}