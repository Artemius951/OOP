package ru.nsu.kutsenko.task112;

public class Dealer extends Hand {
    private boolean allCardsRevealed = false;

    public void revealAllCards() {
        allCardsRevealed = true;
    }

    @Override
    public void clearHand() {
        super.clearHand();
        allCardsRevealed = false;
    }

    public String getHandString(boolean revealed) {
        if (revealed || allCardsRevealed) {
            return getHandString();
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

    // Добавим вспомогательный метод для тестирования
    public boolean isAllCardsRevealed() {
        return allCardsRevealed;
    }
}