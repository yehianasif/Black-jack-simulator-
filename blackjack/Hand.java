package blackjack;

import java.util.ArrayList;
import java.util.List;

class Hand {
    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getTotalValue() {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            total += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        // Adjust for Aces if total is over 21
        while (total > 21 && aceCount > 0) {
            total -= 10;  // Convert Ace from 11 to 1
            aceCount--;
        }

        return total;
    }
    public Card getCard(int index) {
        return cards.get(index);
    }

    @Override
    public String toString() {
        return cards.toString() + " (Total: " + getTotalValue() + ")";
    }
}
