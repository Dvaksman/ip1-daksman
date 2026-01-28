package edu.brandeis.cosi103a.ip1;

public class AutomationCard extends Card {
	private final String type;

    // the only parmeter is cost; type and value are derived from cost
	public AutomationCard(int cost) {
		super(cost, determineValue(cost));
		this.type = determineType(cost);
	}

	public String getType() {
		return type;
	}

	private static String determineType(int cost) {
		return switch (cost) {
			case 2 -> "method";
			case 5 -> "module";
			case 8 -> "framework";
			default -> throw new IllegalArgumentException("Unsupported automation card cost: " + cost);
		};
	}

	private static int determineValue(int cost) {
		return switch (cost) {
			case 2 -> 1;
			case 5 -> 3;
			case 8 -> 6;
			default -> throw new IllegalArgumentException("Unsupported automation card cost: " + cost);
		};
	}
}
