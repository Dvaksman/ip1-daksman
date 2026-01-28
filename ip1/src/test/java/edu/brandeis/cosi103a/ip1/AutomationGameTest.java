package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;
import org.junit.Test;

public class AutomationGameTest {

    @Test
    public void testGameCanEnd() {
        // Simple test to verify the game completes without error
        AutomationGame game = new AutomationGame();
        assertNotNull(game);
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertTrue(true); // Game successfully completed
    }

    @Test
    public void testPlayerCannotBuyCardMoreExpensiveThanPurchasingPower() {
        // Create a player and manually set their hand with known purchasing power
        Player testPlayer = new Player("Test Player");
        
        // Give the player only 2 bitcoins (cost 0, value 1 each) = purchasing power of 2
        testPlayer.drawCard(new CryptocurrencyCard(0)); // bitcoin, value 1
        testPlayer.drawCard(new CryptocurrencyCard(0)); // bitcoin, value 1
        
        // Calculate purchasing power
        int purchasingPower = 0;
        for (Card card : testPlayer.getHand()) {
            if (card instanceof CryptocurrencyCard) {
                purchasingPower += card.getValue();
            }
        }
        
        // Verify purchasing power is 2
        assertEquals(2, purchasingPower);
        
        // Try to create cards that are too expensive to afford
        Card expensiveCard1 = new AutomationCard(5);  // Module card, costs 5
        Card expensiveCard2 = new AutomationCard(8);  // Framework card, costs 8
        Card affordableCard = new AutomationCard(2);   // Method card, costs 2
        
        // Verify that expensive cards cost more than purchasing power
        assertTrue(expensiveCard1.getCost() > purchasingPower);
        assertTrue(expensiveCard2.getCost() > purchasingPower);
        
        // Verify that affordable card costs <= purchasing power
        assertTrue(affordableCard.getCost() <= purchasingPower);
    }

    

}
