package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;
    private List<Card> deck;
    private List<Card> discardPile;
    private int AP;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.AP = 0;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public List<Card> getDeck() {
        return Collections.unmodifiableList(deck);
    }

    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(discardPile);
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public void addCardToDeck(Card card) {
        deck.add(card);
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void drawFromDeck(int count) {
        for (int i = 0; i < count && !deck.isEmpty(); i++) {
            hand.add(deck.remove(0));
        }
    }

    public int getHandSize() {
        return hand.size();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public int getDiscardPileSize() {
        return discardPile.size();
    }

    public void addToDiscardPile(Card card) {
        discardPile.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public void clearDiscardPile() {
        discardPile.clear();
    }
}
