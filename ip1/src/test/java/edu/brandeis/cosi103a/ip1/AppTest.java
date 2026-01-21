package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

/**
 * Unit tests for the Dice Game App.
 * These tests are lightweight and discoverable by the VS Code Java Test Runner.
 */
public class AppTest {

    @Test
    public void testRollDieWithinRange() {
        Random rng = new Random(1);
        for (int i = 0; i < 50; i++) {
            int roll = App.rollDie(rng);
            assertTrue("Roll should be between 1 and 6", roll >= 1 && roll <= 6);
        }
    }

    @Test
    public void testPlayTurnKeepsInitialRoll() {
        Random rng = new Random(42);
        boolean[] decisions = { true }; // keep first roll
        int result = App.playTurnAutomatic(decisions, rng);
        assertTrue(result >= 1 && result <= 6);
    }

    @Test
    public void testPlayTurnRerollThenKeep() {
        Random rng = new Random(42);
        boolean[] decisions = { false, true }; // reroll once, then keep
        int result = App.playTurnAutomatic(decisions, rng);
        assertTrue(result >= 1 && result <= 6);
    }

    @Test
    public void testPlayGameAutomaticProducesScores() {
        Random rng = new Random(100);

        boolean[][] player1 = new boolean[10][2];
        boolean[][] player2 = new boolean[10][2];
        // player1 keeps first roll; player2 rerolls once then keeps
        for (int i = 0; i < 10; i++) {
            player1[i][0] = true;
            player2[i][0] = false;
            player2[i][1] = true;
        }

        int[] scores = App.playGameAutomatic(player1, player2, rng);
        assertTrue(scores[0] >= 10 && scores[0] <= 60);
        assertTrue(scores[1] >= 10 && scores[1] <= 60);
    }

    @Test
    public void testDetermineWinnerOutcomes() {
        assertEquals(1, App.determineWinner(20, 10)); // player 1 wins
        assertEquals(2, App.determineWinner(5, 9));   // player 2 wins
        assertEquals(0, App.determineWinner(7, 7));   // tie
    }
}
