// Hand.java с обновленной проверкой туза
package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный класс представляющий руку карт.
 * Содержит общую функциональность для игрока и дилера.
 */
public abstract class Hand {
    protected static final int BLACKJACK_VALUE = 21;
    protected List<Card> hand;

    /**
     * Конструктор руки.
     */
    public Hand() {
        this.hand = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку.
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
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Вычисляет сумму очков в руке.
     */
    public int getHandValue() {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank() == Card.Rank.ACE) {
                aces++;
            }
        }

        while (value > BLACKJACK_VALUE && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    /**
     * Проверяет наличие блэкджека.
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == BLACKJACK_VALUE;
    }

    /**
     * Возвращает строковое представление руки.
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