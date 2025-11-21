package ru.nsu.kutsenko.task112;

/**
 * Класс представляющий дилера в игре Blackjack.
 * Наследует функциональность Hand и добавляет логику скрытия карт.
 */
public class Dealer extends Hand {
    private boolean allCardsRevealed = false;

    /**
     * Конструктор дилера.
     */
    public Dealer() {
        super();
    }

    /**
     * Раскрывает все карты дилера.
     */
    public void revealAllCards() {
        allCardsRevealed = true;
    }

    /**
     * Возвращает строковое представление руки дилера.
     */
    public String getHandString(boolean revealed) {
        if (revealed || allCardsRevealed) {
            return super.getHandString();
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
     */
    public boolean isAllCardsRevealed() {
        return allCardsRevealed;
    }

    /**
     * Очищает руку дилера.
     */
    @Override
    public void clearHand() {
        super.clearHand();
        allCardsRevealed = false;
    }
}