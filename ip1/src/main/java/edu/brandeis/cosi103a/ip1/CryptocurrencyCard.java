package edu.brandeis.cosi103a.ip1;

public class CryptocurrencyCard extends Card {
	private final String type;

	// constructor derives type and value from cost
	public CryptocurrencyCard(int cost) {
		super(cost, determineValue(cost));
		this.type = determineType(cost);
	}

	public String getType() {
		return type;
	}

	private static String determineType(int cost) {
		return switch (cost) {
			case 0 -> "bitcoin";
			case 3 -> "ethereum";
			case 6 -> "dogecoin";
			default -> throw new IllegalArgumentException("Unsupported cryptocurrency card cost: " + cost);
		};
	}

	private static int determineValue(int cost) {
		return switch (cost) {
			case 0 -> 1;
			case 3 -> 2;
			case 6 -> 3;
			default -> throw new IllegalArgumentException("Unsupported cryptocurrency card cost: " + cost);
		};
	}
}
