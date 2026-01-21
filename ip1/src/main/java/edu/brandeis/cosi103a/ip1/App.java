package edu.brandeis.cosi103a.ip1;

import java.util.Random;
import java.util.Scanner;

/**
 * Dice Game - A two-player dice game where players take turns rolling dice
 */
public class App 
{
    private static final int MAX_TURNS = 10;
    private static final int MAX_REROLLS = 2;
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main( String[] args )
    {
        System.out.println("Welcome to the Dice Game!");
        System.out.println("Each player gets " + MAX_TURNS + " turns.");
        System.out.println("On each turn, roll a die (1-6) and choose to keep it or re-roll up to 2 times.");
        System.out.println();

        int player1Score = 0;
        int player2Score = 0;

        // Play 10 turns for each player
        for (int turn = 1; turn <= MAX_TURNS; turn++) {
            System.out.println("=== TURN " + turn + " ===");
            
            // Player 1's turn
            System.out.println("\nPlayer 1's turn:");
            player1Score += playTurn();
            System.out.println("Player 1's total score: " + player1Score);
            
            // Player 2's turn
            System.out.println("\nPlayer 2's turn:");
            player2Score += playTurn();
            System.out.println("Player 2's total score: " + player2Score);
            
            System.out.println();
        }

        // Display final results
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Player 1 final score: " + player1Score);
        System.out.println("Player 2 final score: " + player2Score);
        
        if (player1Score > player2Score) {
            System.out.println("Player 1 wins!");
        } else if (player2Score > player1Score) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }

        scanner.close();
    }

    /**
     * Plays a single turn for one player
     * @return the final score for this turn
     */
    static int playTurn() {
        return playTurn(scanner, random);
    }

    /**
     * Plays a single turn for one player (testable version)
     * @param scanner the Scanner to use for input
     * @param rng the Random number generator to use
     * @return the final score for this turn
     */
    static int playTurn(Scanner scanner, Random rng) {
        int roll = rollDie(rng);
        int rerollsUsed = 0;

        System.out.println("Initial roll: " + roll);

        while (rerollsUsed < MAX_REROLLS) {
            System.out.print("Keep this roll? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                break;
            } else if (response.equals("n") || response.equals("no")) {
                rerollsUsed++;
                roll = rollDie(rng);
                System.out.println("Re-roll " + rerollsUsed + ": " + roll);
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }

        System.out.println("Final roll for this turn: " + roll);
        return roll;
    }

    /**
     * Plays a single turn with automated decisions (testable version)
     * @param keepDecisions array of booleans indicating whether to keep (true) or reroll (false)
     * @param rng the Random number generator to use
     * @return the final score for this turn
     */
    static int playTurnAutomatic(boolean[] keepDecisions, Random rng) {
        int roll = rollDie(rng);
        int rerollsUsed = 0;

        for (int i = 0; i < keepDecisions.length && i < MAX_REROLLS && rerollsUsed < MAX_REROLLS; i++) {
            if (keepDecisions[i]) {
                // Keep the current roll
                break;
            } else {
                // Reroll
                rerollsUsed++;
                roll = rollDie(rng);
            }
        }

        return roll;
    }

    /**
     * Plays a complete game and returns the final scores
     * @param player1Decisions 2D array of decisions for player 1 (10 turns, up to 2 decisions each)
     * @param player2Decisions 2D array of decisions for player 2 (10 turns, up to 2 decisions each)
     * @param rng the Random number generator to use
     * @return array with [player1Score, player2Score]
     */
    static int[] playGameAutomatic(boolean[][] player1Decisions, boolean[][] player2Decisions, Random rng) {
        int player1Score = 0;
        int player2Score = 0;

        for (int turn = 0; turn < MAX_TURNS; turn++) {
            player1Score += playTurnAutomatic(player1Decisions[turn], rng);
            player2Score += playTurnAutomatic(player2Decisions[turn], rng);
        }

        return new int[]{player1Score, player2Score};
    }

    /**
     * Determines the winner given two scores
     * @param player1Score score for player 1
     * @param player2Score score for player 2
     * @return 1 if player 1 wins, 2 if player 2 wins, 0 if tie
     */
    static int determineWinner(int player1Score, int player2Score) {
        if (player1Score > player2Score) {
            return 1;
        } else if (player2Score > player1Score) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Rolls a six-sided die
     * @return a random number between 1 and 6
     */
    static int rollDie() {
        return rollDie(random);
    }

    /**
     * Rolls a six-sided die (testable version)
     * @param rng the Random number generator to use
     * @return a random number between 1 and 6
     */
    static int rollDie(Random rng) {
        return rng.nextInt(6) + 1;
    }
}
