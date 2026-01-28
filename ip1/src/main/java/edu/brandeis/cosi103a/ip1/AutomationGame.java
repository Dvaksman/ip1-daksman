package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AutomationGame {
	private static final int AUTOMATION_METHOD_COST = 2;
	private static final int AUTOMATION_MODULE_COST = 5;
	private static final int AUTOMATION_FRAMEWORK_COST = 8;
	private static final int CRYPTO_BITCOIN_COST = 0;
	private static final int CRYPTO_ETHEREUM_COST = 3;
	private static final int CRYPTO_DOGECOIN_COST = 6;

	private List<Card> sharedDeck;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private String phase;
	private int frameworksPurchased;

	public AutomationGame() {
		this.sharedDeck = buildDeck();
		this.player1 = new Player("Computer Player 1");
		this.player2 = new Player("Computer Player 2");
		this.frameworksPurchased = 0;
		initializePlayerDecks();
		initializeStartingHands();
		playGame();
	}

	public List<Card> getSharedDeck() {
		return Collections.unmodifiableList(sharedDeck);
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	private static List<Card> buildDeck() {
		List<Card> cards = new ArrayList<>(160);

		addAutomationCards(cards, AUTOMATION_METHOD_COST, 14);
		addAutomationCards(cards, AUTOMATION_MODULE_COST, 8);
		addAutomationCards(cards, AUTOMATION_FRAMEWORK_COST, 8);

		addCryptocurrencyCards(cards, CRYPTO_BITCOIN_COST, 60);
		addCryptocurrencyCards(cards, CRYPTO_ETHEREUM_COST, 40);
		addCryptocurrencyCards(cards, CRYPTO_DOGECOIN_COST, 30);

		return cards;
	}

	private static void addAutomationCards(List<Card> cards, int cost, int count) {
		for (int i = 0; i < count; i++) {
			cards.add(new AutomationCard(cost));
		}
	}

	private static void addCryptocurrencyCards(List<Card> cards, int cost, int count) {
		for (int i = 0; i < count; i++) {
			cards.add(new CryptocurrencyCard(cost));
		}
	}

	private void initializePlayerDecks() {
		// Each player gets 7 bitcoins and 3 methods from the shared deck
		int bitcoinsGiven = 0;
		int methodsGiven = 0;

		for (int i = sharedDeck.size() - 1; i >= 0 && (bitcoinsGiven < 14 || methodsGiven < 6); i--) {
			Card card = sharedDeck.get(i);
			if (card instanceof CryptocurrencyCard && card.getCost() == CRYPTO_BITCOIN_COST && bitcoinsGiven < 14) {
				sharedDeck.remove(i);
				if (bitcoinsGiven < 7) {
					player1.addCardToDeck(card);
				} else {
					player2.addCardToDeck(card);
				}
				bitcoinsGiven++;
			} else if (card instanceof AutomationCard && card.getCost() == AUTOMATION_METHOD_COST && methodsGiven < 6) {
				sharedDeck.remove(i);
				if (methodsGiven < 3) {
					player1.addCardToDeck(card);
				} else {
					player2.addCardToDeck(card);
				}
				methodsGiven++;
			}
		}
	}

	private void initializeStartingHands() {
		player1.shuffleDeck();
		player1.drawFromDeck(5);
		player2.shuffleDeck();
		player2.drawFromDeck(5);
	}

	private void playGame() {
		int turn = 1;

        Random random = new Random();
		currentPlayer = random.nextBoolean() ? player1 : player2;

		while (true) {
			
			// Turn logic
			phase = "buy";
			
			// Calculate purchase power for current player
			int purchase_power = 0;
			for (Card card : currentPlayer.getHand()) {
				if (card instanceof CryptocurrencyCard) {
					purchase_power += card.getValue();
				}
			}

			// Speed up logic: if turn >= 50 and purchase_power >= 8, automatically buy framework
			if (turn >= 50 && purchase_power >= 8) {
				Card frameworkCard = null;
				for (Card card : sharedDeck) {
					if (card instanceof AutomationCard && card.getCost() == AUTOMATION_FRAMEWORK_COST) {
						frameworkCard = card;
						break;
					}
				}
				if (frameworkCard != null) {
					sharedDeck.remove(frameworkCard);
					currentPlayer.addToDiscardPile(frameworkCard);
					frameworksPurchased++;
				}
			} else {
				// the player can purchase one card if they have enough purchase power to buy it
				// the player will buy a random affordable card
				List<Card> affordableCards = new ArrayList<>();
				for (Card card : sharedDeck) {
					if (card.getCost() <= purchase_power) {
						affordableCards.add(card);
					}
				}

				if (!affordableCards.isEmpty()) {
					Card cardToBuy = affordableCards.get(random.nextInt(affordableCards.size()));
					sharedDeck.remove(cardToBuy);
					currentPlayer.addToDiscardPile(cardToBuy);
					if (cardToBuy instanceof AutomationCard && cardToBuy.getCost() == AUTOMATION_FRAMEWORK_COST) {
						frameworksPurchased++;
					}
				}
			}

            // if a card was purchased, set the phase to "cleanup"
            if (phase.equals("buy")) {
                phase = "cleanup";
            }

            // discard the current player's hand
            for (Card card : currentPlayer.getHand()) {
                currentPlayer.addToDiscardPile(card);
            }

            // clear the current player's hand
            currentPlayer.clearHand();

            // draw the remaining cards from the players deck until it is empty
            currentPlayer.drawFromDeck(currentPlayer.getDeckSize());

            // shuffle the discard pile back into the deck
            for (Card card : currentPlayer.getDiscardPile()) {
                currentPlayer.addCardToDeck(card);
            }

            // clear the discard pile and shuffle the deck
            currentPlayer.clearDiscardPile();
            currentPlayer.shuffleDeck();

            // Switch players, if the current player is player1, switch to player2, and vice versa
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
			turn++;

            // end condition: once 8 frameworks have been purchased, end the game, else if turns exceed 100, end the game
            if (frameworksPurchased >= 8) {
                break;  
            }
		}

		// Game ended, print scores
		printScores();
	}

	private void printScores() {
		// Calculate player1 score
		int player1Score = 0;
		for (Card card : player1.getHand()) {
			if (card instanceof AutomationCard) {
				player1Score += card.getValue();
			}
		}
		for (Card card : player1.getDeck()) {
			if (card instanceof AutomationCard) {
				player1Score += card.getValue();
			}
		}
		for (Card card : player1.getDiscardPile()) {
			if (card instanceof AutomationCard) {
				player1Score += card.getValue();
			}
		}

		// Calculate player2 score
		int player2Score = 0;
		for (Card card : player2.getHand()) {
			if (card instanceof AutomationCard) {
				player2Score += card.getValue();
			}
		}
		for (Card card : player2.getDeck()) {
			if (card instanceof AutomationCard) {
				player2Score += card.getValue();
			}
		}
		for (Card card : player2.getDiscardPile()) {
			if (card instanceof AutomationCard) {
				player2Score += card.getValue();
			}
		}

		// Print scores
		System.out.println("=== GAME OVER ===");
		System.out.println(player1.getName() + " Score: " + player1Score);
		System.out.println(player2.getName() + " Score: " + player2Score);

		// Determine winner
		if (player1Score > player2Score) {
			System.out.println(player1.getName() + " WINS!");
		} else if (player2Score > player1Score) {
			System.out.println(player2.getName() + " WINS!");
		} else {
			System.out.println("It's a TIE!");
		}
	}
}


