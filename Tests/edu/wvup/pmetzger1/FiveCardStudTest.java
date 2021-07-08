package edu.wvup.pmetzger1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * The test class BlackjackGUITest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class FiveCardStudTest
{
    FiveCardStud game;

    /**
     * Default constructor for test class BlackjackGUITest
     */
    public FiveCardStudTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        game = new FiveCardStud();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        game.cleanUp();
        game = null;
    }

    @Test
    public void declareWinnerDetectsPushTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 5, true, "5"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 5, true, "5"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.declareWinner();

        // Assert
        assertNull(winner);
    }

    @Test
    public void frameUsesBorderLayoutTest()
    {
        // Arrange

        // Act

        // Assert
        assertTrue(game.getFrame().getLayout() instanceof BorderLayout);
    }


    @Test
    public void windowHasMenuBarTest()
    {
        // Arrange

        // Act

        // Assert
        assertTrue(game.getFrame().getJMenuBar().getMenuCount() > 0);
    }


    @Test
    public void windowUsesNestedLayoutsTest()
    {
        // Arrange
        int flowLayoutCount = 0;
        int boxLayoutCount = 0;
        int gridLayoutCount = 0;

        ArrayList<JComponent> components = new ArrayList<>();
        for (int i = 0; i < game.getFrame().getContentPane().getComponentCount(); i++)
        {
            components.add((JComponent) game.getFrame().getContentPane().getComponent(i));
        }

        // Act
        for (JComponent panel : components)
        {
            if (panel.getLayout() instanceof FlowLayout)
            {
                flowLayoutCount++;
            }
            else if (panel.getLayout() instanceof BoxLayout)
            {
                boxLayoutCount++;
            }
            else if (panel.getLayout() instanceof GridLayout)
            {
                gridLayoutCount++;
            }
        }

        // Assert
        assertTrue(gridLayoutCount > 0);
        assertTrue(boxLayoutCount > 0);
        assertTrue(flowLayoutCount > 0);
    }

    @Test
    public void buttonsHaveActionListeners()
    {
        // Arrange
        JButton betButton = game.getBetButton();
        JButton hitButton = game.getHitButton();
        JButton stayButton = game.getStayButton();

        // Act
        ActionListener[] betListener = betButton.getActionListeners();
        ActionListener[] hitListener = hitButton.getActionListeners();
        ActionListener[] stayListener = stayButton.getActionListeners();

        // Assert
        assertTrue(betListener.length > 0);
        assertTrue(hitListener.length > 0);
        assertTrue(stayListener.length > 0);
    }

    @Test
    public void playerDeclaredWinnerWithHighScoreTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 5, true, "5"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 10, true, "10"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.declareWinner();

        // Assert - Really ugly way to compare players
        assertTrue(winner.getNameLabel().getText().equals(game.getPlayer().getNameLabel().getText()));
    }

    @Test
    public void dealerDeclaredWinnerWithHighScoreTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 5, true, "5"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.declareWinner();

        // Assert - Really ugly way to compare players
        assertTrue(winner.getNameLabel().getText().equals(game.getDealer().getNameLabel().getText()));
    }

    @Test
    public void playerDeclaredWinnerWithDealerBustTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 5, true, "5"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 10, true, "10"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.declareWinner();

        // Assert - Really ugly way to compare players
        assertTrue(winner.getNameLabel().getText().equals(game.getPlayer().getNameLabel().getText()));
    }

    @Test
    public void dealerDeclaredWinnerWithPlayerBustTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 5, true, "5"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.declareWinner();

        // Assert - Really ugly way to compare players
        assertTrue(winner.getNameLabel().getText().equals(game.getDealer().getNameLabel().getText()));
    }

    @Test
    public void playerBlackjackTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 11, true, "Ace"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.checkWinner();

        // Assert - Really ugly way to compare players
        assertTrue(winner.getNameLabel().getText().equals(game.getPlayer().getNameLabel().getText()));
    }

    @Test
    public void dealerBlackjackTest()
    {
        // Arrange
        Player winner = null;
        game.getDealer().receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 11, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 10, true, "Ace"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.checkWinner();

        // Assert
        assertTrue(winner.getNameLabel().getText().equals(game.getDealer().getNameLabel().getText()));
    }

    @Test
    public void notABlackjackTest()
    {
        // Arrange
        Player winner = game.getPlayer();
        game.getDealer().receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        game.getDealer().receiveCard(new Card(Suit.Hearts, 10, true, "Queen"), true);

        game.getPlayer().receiveCard(new Card(Suit.Diamonds, 10, true, "Ace"), true);
        game.getPlayer().receiveCard(new Card(Suit.Spades, 10, true, "Queen"), true);

        // Act
        winner = game.checkWinner();

        // Assert
        assertNull(winner);
    }

    @Test
    public void awardPotIfPlayerWinsTest()
    {
        // Arrange
        int playerStash = game.getPlayer().getStash();

        // Act
        try
        {
            game.takeBet("100");
        }
        catch(IllegalBetException ibe)
        {
            assertTrue(false);
        }

        game.awardPotToWinner(game.getPlayer());
        // Assert
        assertTrue(playerStash < game.getPlayer().getStash());
    }

    @Test
    public void awardPotIfDealerWinsTest()
    {
        // Arrange
        int dealerStash = game.getDealer().getStash();

        // Act
        try
        {
            game.takeBet("100");
        }
        catch(IllegalBetException ibe)
        {
            assertTrue(false);
        }

        game.awardPotToWinner(game.getDealer());
        // Assert
        assertTrue(dealerStash < game.getDealer().getStash());
    }

    @Test
    public void returnPotIfTieTest()
    {
        // Arrange
        int dealerStash = game.getDealer().getStash();
        int playerStash = game.getPlayer().getStash();

        // Act
        try
        {
            game.takeBet("100");
        }
        catch(IllegalBetException ibe)
        {
            assertTrue(false);
        }

        game.awardPotToWinner(null);

        // Assert
        assertTrue(dealerStash == game.getDealer().getStash());
        assertTrue(playerStash == game.getPlayer().getStash());
    }

    @Test
    public void returnPotIfTieAndDealerAllInTest()
    {
        // Arrange
        game.getDealer().setStash(100);
        game.getPlayer().setStash(200);

        // Act
        try
        {
            game.takeBet("200");
        }
        catch(IllegalBetException ibe)
        {
            assertTrue(false);
        }

        game.awardPotToWinner(null);

        // Assert
        assertTrue(100 == game.getDealer().getStash());
        assertTrue(200 == game.getPlayer().getStash());
    }

    @Test
    public void potLabelTest()
    {
        // Arrange
        String initialPotLabel = game.getPotLabel().getText();
        String newPotLabel = "";

        // Act
        try
        {
            game.takeBet("100");
        }
        catch(IllegalBetException ibe)
        {
            assertTrue(false);
        }

        newPotLabel = game.getPotLabel().getText();

        // Assert
        assertFalse(initialPotLabel.equals(newPotLabel));
    }


    // Bet tests
    @Test
    public void validBetTest()
    {
        // Arrange
        boolean result = true;

        // Act
        try
        {
            game.takeBet("100");
        }
        catch(IllegalBetException ibe)
        {
            result = false; // This should be a valid bet, so if we get here something went wrong
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void cannotBetMoreThanYouHaveTest()
    {
        // Arrange
        boolean result = false;

        // Act
        try
        {
            game.takeBet("10000000");
        }
        catch(IllegalBetException ibe)
        {
            result = true;
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void cannotBetLessThanZeroTest()
    {
        // Arrange
        boolean result = false;

        // Act
        try
        {
            game.takeBet("-50");
        }
        catch(IllegalBetException ibe)
        {
            result = true;
        }

        // Assert
        assertTrue(result);
    }

    @Test
    public void dealerGoesAllInTest()
    {
        // Arrange
        game.getDealer().setStash(100);
        game.getPlayer().setStash(200);

        // Act
        try
        {
            game.takeBet("200");
        }
        catch(IllegalBetException ibe)
        {
            assertTrue(false);
        }

        // Assert
        assertEquals(game.getPot(), 300);
    }
}

