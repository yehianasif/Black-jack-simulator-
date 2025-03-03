package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Deck {
    private final List<Card> cards;
    private final String[] ranks = {
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "Jack", "Queen", "King", "Ace"
    };
    private final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11}; // Ace as 11 (handled in game logic)

    public Deck() {
        cards = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            // Blackjack uses multiple decks, but for simplicity, we use one here
            for (int j = 0; j < 4; j++) {  // Four suits worth of each rank
                cards.add(new Card(ranks[i], values[i]));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public int remainingCards() {
        return cards.size();
    }
}