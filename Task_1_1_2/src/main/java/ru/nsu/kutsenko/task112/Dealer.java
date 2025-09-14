package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий дилера в игре Blackjack.
 * Наследует функциональность Hand и добавляет логику скрытия карт.
 */
public class Dealer {
    private final List<Card> hand;
    private boolean allCardsRevealed = false;

    /**
     * Конструктор дилера.
     */
    public Dealer() {
        this.hand = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку дилера.
     *
     * @param card карта для добавления
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Вычисляет сумму очков в руке дилера.
     *
     * @return сумма очков с учетом правила туза
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
     * Проверяет наличие блэкджека у дилера.
     *
     * @return true если у дилера блэкджек
     */
    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21;
    }

    /**
     * Раскрывает все карты дилера.
     */
    public void revealAllCards() {
        allCardsRevealed = true;
    }

    /**
     * Очищает руку дилера.
     */
    public void clearHand() {
        hand.clear();
        allCardsRevealed = false;
    }

    /**
     * Возвращает строковое представление руки дилера.
     *
     * @param revealed показывать ли все карты
     * @return строка с описанием карт в руке
     */
    public String getHandString(boolean revealed) {
        if (revealed || allCardsRevealed) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < hand.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(hand.get(i).textCard());
            }
            sb.append("]");
            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder("[");
            if (!hand.isEmpty()) {
                sb.append(hand.get(0).textCard());
                if (hand.size() > 1) {
                    sb.append(", <закрытая карта>");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    /**
     * Проверяет раскрыты ли все карты дилера.
     *
     * @return true если все карты раскрыты
     */
    public boolean isAllCardsRevealed() {
        return allCardsRevealed;
    }

    /**
     * Возвращает копию руки дилера.
     *
     * @return список карт в руке
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
}