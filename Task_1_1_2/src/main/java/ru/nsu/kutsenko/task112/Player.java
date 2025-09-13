package ru.nsu.kutsenko.task112;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand;

    public Player() {
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

    public String getHandString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(hand.get(i).textCard());
        }
        return sb.toString();
    }

    public void clearHand() {
        hand.clear();
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
}