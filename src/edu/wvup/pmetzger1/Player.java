package edu.wvup.pmetzger1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * The Player class represents a Blackjack player in the game
 *
 * @author Charles Almond
 * @author Preston Metzger
 * @version 2021.06.05.01
 */
public class Player
{
    // Private instance variables - Declare an ArrayList of Cards called hand, a name(String), and a stash(int)
    private String name;
    private Integer stash; // Changed
    private ArrayList<Card> hand;

    private JPanel cardPanel;
    private JLabel nameLabel;
    private JLabel stashLabel;
    private JLabel scoreLabel;

    private int score;


    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables - A new ArrayList, set the name to "Player",
        name = "Player";
        stash =  0; // Include no money first
        score = 0;
        hand = new ArrayList<>();
        //stash = 500;


        nameLabel = new JLabel(name);
        stashLabel = new JLabel(stash.toString()); // pass integer or toString to get string
        scoreLabel = new JLabel("0");// keep track of wins
        alterScoreLabel();
        setStash(500); // Set money to 500

        // Think of how I came up with this
        nameLabel.setForeground(Blackjack.foregroundColor);
        stashLabel.setForeground(Blackjack.foregroundColor);
        scoreLabel.setForeground(Blackjack.foregroundColor);

        cardPanel = new JPanel();
        cardPanel.setBackground(new Color(53, 101, 77));

        // Fix code duplication
        nameLabel.setFont(Blackjack.fontSize);
        stashLabel.setFont(Blackjack.fontSize);
        scoreLabel.setFont(Blackjack.fontSize);
    }

    /**
     * Overloaded constructor for custom name and amount of money to bet with
     */
    public Player(String name, Integer stash)
    {
        // Initialize instance variables - A new ArrayList for the hand, but now set the name and initial
        // stash
        this.name = name;
        this.stash = stash;
        score = 0;
        hand = new ArrayList<>();


        nameLabel = new JLabel(name); //@@@ Test @@@
        stashLabel = new JLabel(stash.toString()); // pass integer or toString to get string
        scoreLabel = new JLabel("0");// keep track of wins @@ Test @@
        alterScoreLabel();
        setStash(stash); //Check this


        nameLabel.setForeground(Blackjack.foregroundColor);
        stashLabel.setForeground(Blackjack.foregroundColor);
        scoreLabel.setForeground(Blackjack.foregroundColor);

        cardPanel = new JPanel();
        cardPanel.setBackground(new Color(53, 101, 77));
        nameLabel.setFont(Blackjack.fontSize);
        stashLabel.setFont(Blackjack.fontSize);
        scoreLabel.setFont(Blackjack.fontSize);
    }

    /**
     * Get the name label
     * @return The label for the name
     */
    public JLabel getNameLabel()
    {
        return nameLabel;
    }


    /**
     * Get the hand
     * @return The players hand.
     */
    // @@ TEST @@@
    public ArrayList getHand()
    {
        return hand;
    }

    /**
     * Get the stash label
     * @return The label for the stash
     */
    public JLabel getStashLabel()
    {
        return stashLabel;
    }

    /**
     * Get the score label
     * @return The label for the score
     */
    public JLabel getScoreLabel()
    {
        return scoreLabel;
    }

    /**
     * Get the card panel
     * @return The panel for the card
     */
    public JPanel getCardPanel()
    {
        return cardPanel;
    }

    /**
     * Get a card from the dealer
     * @param card A card from the deck of cards
     * @param visibility Set the card face-up (true) or face-down (false)
     */
    public void receiveCard(Card card, boolean visibility)
    {
        // If the visibility is true, call the card's show method and then add it to the hand
        // Otherwise, call the card's hide method and then add it to the hand

        if(visibility) // Checks true/.false be default
        {
            card.show();
            //scoreLabel.setText(Integer.parseInt(scoreLabel.getText()) + card.getValue() + "");
            score += card.getValue();
            alterScoreLabel();
        }
        else
        {
            card.hide();
        }
        hand.add(card); // add card from parameter
        cardPanel.add(card.getCardLabel());
    }

    /**
     * Set the amount of money the player has available to bet, must be >= 0
     */
    public void setStash(int stash)
    {
        // Validate that the stash is greater than or equal to zero before assigning it.
        if(stash >= 0)
        {
            this.stashLabel.setText("$" + stash);
            this.stash = stash;
        }
    }

    /**
     * Get the amount of money the player has available to bet
     * @return The amount of money the player has to bet
     */
    public int getStash()
    {
        return this.stash;
    }

    /**
     * Clear the player's hand, for use after one round of Blackjack
     */
    public void clearHand()
    {
        // Remove all of the cards from the hand
        hand.clear();
        cardPanel.removeAll();
        score = 0;
        alterScoreLabel();
    }

    /**
     * Score the cards in the player's hand for Blackjack
     * If the score is more than 21, the method reduces the score by 10 for each Ace present
     * until the score is less than or equal to 21 or all cards are examined
     * @return The score of all cards in the player's hand
     */
    public int scoreHand()
    {
        int score = 0;

        // Check for a Blackjack.  If the hand has only 2 cards in it, ignore visibility and score
        // the hand by adding each card's point value to score.
        // If the score is 21, call showAllCards and return the score
        if(hand.size() == 2)
        {
            for(Card card : hand) // go through hand, score
            {
                score += card.getValue();
            }
            if(score == 21)
            {
                showAllCards();
                return score; // end
            }
        }

        // If the hand is not Blackjack, score the hand normally
        //  reset score to 0, and loop over every card in the hand, adding its score only if the card is visible
        //  Check for bust, if the score is greater than 21
        //      If the score is greater than 21, loop over all the cards in the hand again
        //          If the card is an Ace and it is visible, subtract 10 from the score (Ace can be worth 11 or 1)
        //          If the score is now less than or equal to 21, return the score, otherwise keep looping
        //  When done, return the score

        score = 0;
        for(Card card : hand)
        {
            if(card.isVisible()) // if visible, add
            {
                score += card.getValue();
            }
        }
        // When loop is over, check for Bust card > 21
        if(score > 21)
        {
            for(Card card : hand)
            {
                if(card.getName().equals("Ace"))
                {
                    //score += card.getValue();
                    score -= 10;
                    if(score <=21)
                    {
                        return score;
                    }
                }
            }
        }
        return score;
    }

    /**
     * Flips all cards face-up
     */
    public void showAllCards()
    {
        // Loop over every card in the hand and call the card's show method to set its visibility
        for(Card card : hand)
        {
            card.show();
        }
    }

    /*
     * Return a string representing the player and the amount of money they have available
     */
    @Override
    public String toString()
    {
        String player = name + " has $" + stash + "\n";
        player += "Current points: " + scoreHand() + "\n";
        for(Card c : hand)
        {
            player += c.toString() + "\n";
        }
        return player;
    }

    /*
     * Alters the score label
     */
    private void alterScoreLabel()
    {
        scoreLabel.setText("Hand Value: " + score);
    }
}
