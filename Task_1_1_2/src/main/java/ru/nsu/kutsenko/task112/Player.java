package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий игрока в игре Blackjack.
 * Содержит функциональность для управления рукой карт игрока.
 */
public class Player {
    private final List<Card> hand;

    /**
     * Конструктор игрока.
     * Инициализирует пустую руку карт.
     */
    public Player() {
        this.hand = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку игрока.
     *
     * @param card карта для добавления.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Вычисляет сумму очков в руке игрока.
     * Учитывает правило туза (11 или 1 очко).
     *
     * @return сумма очков в руке.
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
     * Проверяет наличие блэкджека у игрока.
     * Блэкджек - две карты с суммой 21 очко.
     *
     * @return true если у игрока блэкджек.
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21;
    }

    /**
     * Возвращает строковое представление руки игрока.
     *
     * @return строка с описанием всех карт в руке.
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

    /**
     * Очищает руку игрока.
     * Удаляет все карты из руки.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Возвращает копию руки игрока.
     *
     * @return список карт в руке игрока.
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
}