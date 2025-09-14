package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс представляющий руку карт.
 * Содержит общую функциональность для игрока и дилера.
 */
public abstract class Hand {
    protected final List<Card> hand;

    /**
     * Конструктор руки.
     */
    public Hand() {
        this.hand = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку.
     *
     * @param card карта для добавления.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Очищает руку.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Возвращает копию руки.
     *
     * @return список карт в руке.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Вычисляет сумму очков в руке.
     *
     * @return сумма очков с учетом правила туза.
     */
    public int getHandValue() {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Туз")) {
                aces++;
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    /**
     * Проверяет наличие блэкджека.
     *
     * @return true если в руке блэкджек.
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21;
    }

    /**
     * Возвращает строковое представление руки.
     *
     * @return строка с описанием карт в руке.
     */
    public String getHandString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < hand.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(hand.get(i).textCard());
        }
        sb.append("]");
        return sb.toString();
    }
}