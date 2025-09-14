package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private final List<Card> hand;
    private boolean allCardsRevealed = false;

    public Dealer() {
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

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

    public boolean hasBlackjack() {
        return hand.size() == 2 && getHandValue() == 21;
    }

    public void revealAllCards() {
        allCardsRevealed = true;
    }

    public void clearHand() {
        hand.clear();
        allCardsRevealed = false;
    }

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

    public boolean isAllCardsRevealed() {
        return allCardsRevealed;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
}