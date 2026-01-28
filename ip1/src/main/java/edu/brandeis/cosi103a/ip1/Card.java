package edu.brandeis.cosi103a.ip1;

public class Card {
    private final int cost;
    private final int value;

    public Card(int cost, int value) {
        this.cost = cost;
        this.value = value;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }
}
