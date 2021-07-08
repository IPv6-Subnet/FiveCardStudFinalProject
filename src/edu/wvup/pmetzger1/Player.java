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
        nameLabel.setForeground(FiveCardStud.foregroundColor);
        stashLabel.setForeground(FiveCardStud.foregroundColor);
        scoreLabel.setForeground(FiveCardStud.foregroundColor);

        cardPanel = new JPanel();
        cardPanel.setBackground(new Color(53, 101, 77));

        // Fix code duplication
        nameLabel.setFont(FiveCardStud.fontSize);
        stashLabel.setFont(FiveCardStud.fontSize);
        scoreLabel.setFont(FiveCardStud.fontSize);
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


        nameLabel.setForeground(FiveCardStud.foregroundColor);
        stashLabel.setForeground(FiveCardStud.foregroundColor);
        scoreLabel.setForeground(FiveCardStud.foregroundColor);

        cardPanel = new JPanel();
        cardPanel.setBackground(new Color(53, 101, 77));
        nameLabel.setFont(FiveCardStud.fontSize);
        stashLabel.setFont(FiveCardStud.fontSize);
        scoreLabel.setFont(FiveCardStud.fontSize);
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
        // Call method
        return HandScore.valueHand(hand.toArray(new Card[0])); // Create anonymous object

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


    private static class HandScore
    {
        public static final int STRAIGHT_FLUSH = 8000000;
        // + valueHighCard()
        public static final int FOUR_OF_A_KIND = 7000000;
        // + Quads Card Rank
        public static final int FULL_HOUSE     = 6000000;
        // + SET card rank
        public static final int FLUSH          = 5000000;
        // + valueHighCard()
        public static final int STRAIGHT       = 4000000;
        // + valueHighCard()
        public static final int SET            = 3000000;
        // + Set card value
        public static final int TWO_PAIRS      = 2000000;
        // + High2*14^4+ Low2*14^2 + card
        public static final int ONE_PAIR       = 1000000;
        // + high*14^2 + high2*14^1 + low



        /***********************************************************
         Methods used to determine a certain Poker hand
         ***********************************************************/

   /* --------------------------------------------------------
      valueHand(): return value of a hand
      -------------------------------------------------------- */
        public static int valueHand( Card[] h )
        {
            if ( isFlush(h) && isStraight(h) )
                return valueStraightFlush(h);
            else if ( is4s(h) )
                return valueFourOfAKind(h);
            else if ( isFullHouse(h) )
                return valueFullHouse(h);
            else if ( isFlush(h) )
                return valueFlush(h);
            else if ( isStraight(h) )
                return valueStraight(h);
            else if ( is3s(h) )
                return valueSet(h);
            else if ( is22s(h) )
                return valueTwoPairs(h);
            else if ( is2s(h) )
                return valueOnePair(h);
            else
                return valueHighCard(h);
        }


        /* -----------------------------------------------------
           valueFlush(): return value of a Flush hand

                 value = FLUSH + valueHighCard()
           ----------------------------------------------------- */
        public static int valueStraightFlush( Card[] h )
        {
            return STRAIGHT_FLUSH + valueHighCard(h);
        }

        /* -----------------------------------------------------
           valueFlush(): return value of a Flush hand

                 value = FLUSH + valueHighCard()
           ----------------------------------------------------- */
        public static int valueFlush( Card[] h )
        {
            return FLUSH + valueHighCard(h);
        }

        /* -----------------------------------------------------
           valueStraight(): return value of a Straight hand

                 value = STRAIGHT + valueHighCard()
           ----------------------------------------------------- */
        public static int valueStraight( Card[] h )
        {
            return STRAIGHT + valueHighCard(h);
        }

        /* ---------------------------------------------------------
           valueFourOfAKind(): return value of a 4 of a kind hand

                 value = FOUR_OF_A_KIND + 4sCardRank

           Trick: card h[2] is always a card that is part of
                  the 4-of-a-kind hand
              There is ONLY ONE hand with a quads of a given rank.
           --------------------------------------------------------- */
        public static int valueFourOfAKind( Card[] h )
        {
            sortByRank(h);

            return FOUR_OF_A_KIND + h[2].getValue();
        }

        /* -----------------------------------------------------------
           valueFullHouse(): return value of a Full House hand

                 value = FULL_HOUSE + SetCardRank

           Trick: card h[2] is always a card that is part of
                  the 3-of-a-kind in the full house hand
              There is ONLY ONE hand with a FH of a given set.
           ----------------------------------------------------------- */
        public static int valueFullHouse( Card[] h )
        {
            sortByRank(h);

            return FULL_HOUSE + h[2].getValue();
        }

        /* ---------------------------------------------------------------
           valueSet(): return value of a Set hand

                 value = SET + SetCardRank

           Trick: card h[2] is always a card that is part of the set hand
              There is ONLY ONE hand with a set of a given rank.
           --------------------------------------------------------------- */
        public static int valueSet( Card[] h )
        {
            sortByRank(h);

            return SET + h[2].getValue();
        }

        /* -----------------------------------------------------
           valueTwoPairs(): return value of a Two-Pairs hand

                 value = TWO_PAIRS
                        + 14*14*HighPairCard
                        + 14*LowPairCard
                        + UnmatchedCard
           ----------------------------------------------------- */
        public static int valueTwoPairs( Card[] h )
        {
            int val = 0;

            sortByRank(h);

            if ( h[0].getValue() == h[1].getValue() &&
                    h[2].getValue() == h[3].getValue() )
                val = 14*14*h[2].getValue() + 14*h[0].getValue() + h[4].getValue();
            else if ( h[0].getValue() == h[1].getValue() &&
                    h[3].getValue() == h[4].getValue() )
                val = 14*14*h[3].getValue() + 14*h[0].getValue() + h[2].getValue();
            else
                val = 14*14*h[3].getValue() + 14*h[1].getValue() + h[0].getValue();

            return TWO_PAIRS + val;
        }

        /* -----------------------------------------------------
           valueOnePair(): return value of a One-Pair hand

                 value = ONE_PAIR
                        + 14^3*PairCard
                        + 14^2*HighestCard
                        + 14*MiddleCard
                        + LowestCard
           ----------------------------------------------------- */
        public static int valueOnePair( Card[] h )
        {
            int val = 0;

            sortByRank(h);

            if ( h[0].getValue() == h[1].getValue() )
                val = 14*14*14*h[0].getValue() +
                        + h[2].getValue() + 14*h[3].getValue() + 14*14*h[4].getValue();
            else if ( h[1].getValue() == h[2].getValue() )
                val = 14*14*14*h[1].getValue() +
                        + h[0].getValue() + 14*h[3].getValue() + 14*14*h[4].getValue();
            else if ( h[2].getValue() == h[3].getValue() )
                val = 14*14*14*h[2].getValue() +
                        + h[0].getValue() + 14*h[1].getValue() + 14*14*h[4].getValue();
            else
                val = 14*14*14*h[3].getValue() +
                        + h[0].getValue() + 14*h[1].getValue() + 14*14*h[2].getValue();

            return ONE_PAIR + val;
        }

        /* -----------------------------------------------------
           valueHighCard(): return value of a high card hand

                 value =  14^4*highestCard
                        + 14^3*2ndHighestCard
                        + 14^2*3rdHighestCard
                        + 14^1*4thHighestCard
                        + LowestCard
           ----------------------------------------------------- */
        public static int valueHighCard( Card[] h )
        {
            int val = 0;
            int multi = 1;

            sortByRank(h);
            for(int i = 0 ; i < 4 ; i++) {
                if (i < h.length) {
                    Card c = h[i];
                    if(c.isVisible()) {
                        val += c.getValue() * multi;
                    }
                    multi = (int) Math.pow(14, i);
                }
            }
            return val;
        }

        /***********************************************************
         Methods used to determine a certain Poker hand
         ***********************************************************/


   /* ---------------------------------------------
      is4s(): true if h has 4 of a kind
              false otherwise
      --------------------------------------------- */
        public static boolean is4s( Card[] h )
        {
            boolean a1, a2;

            if ( h.length != 5 )
                return(false);

            sortByRank(h);

            a1 = h[0].getValue() == h[1].getValue() &&
                    h[1].getValue() == h[2].getValue() &&
                    h[2].getValue() == h[3].getValue() ;

            a2 = h[1].getValue() == h[2].getValue() &&
                    h[2].getValue() == h[3].getValue() &&
                    h[3].getValue() == h[4].getValue() ;

            return( a1 || a2 );
        }


        /* ----------------------------------------------------
           isFullHouse(): true if h has Full House
                          false otherwise
           ---------------------------------------------------- */
        public static boolean isFullHouse( Card[] h )
        {
            boolean a1, a2;

            if ( h.length != 5 )
                return(false);

            sortByRank(h);

            a1 = h[0].getValue() == h[1].getValue() &&  //  x x x y y
                    h[1].getValue() == h[2].getValue() &&
                    h[3].getValue() == h[4].getValue();

            a2 = h[0].getValue() == h[1].getValue() &&  //  x x y y y
                    h[2].getValue() == h[3].getValue() &&
                    h[3].getValue() == h[4].getValue();

            return( a1 || a2 );
        }



        /* ----------------------------------------------------
           is3s(): true if h has 3 of a kind
                   false otherwise

           **** Note: use is3s() ONLY if you know the hand
                      does not have 4 of a kind
           ---------------------------------------------------- */
        public static boolean is3s( Card[] h )
        {
            boolean a1, a2, a3;

            if ( h.length != 5 )
                return(false);

            if ( is4s(h) || isFullHouse(h) )
                return(false);        // The hand is not 3 of a kind (but better)

      /* ----------------------------------------------------------
         Now we know the hand is not 4 of a kind or a full house !
         ---------------------------------------------------------- */
            sortByRank(h);

            a1 = h[0].getValue() == h[1].getValue() &&
                    h[1].getValue() == h[2].getValue() ;

            a2 = h[1].getValue() == h[2].getValue() &&
                    h[2].getValue() == h[3].getValue() ;

            a3 = h[2].getValue() == h[3].getValue() &&
                    h[3].getValue() == h[4].getValue() ;

            return( a1 || a2 || a3 );
        }

        /* -----------------------------------------------------
           is22s(): true if h has 2 pairs
                    false otherwise

           **** Note: use is22s() ONLY if you know the hand
                      does not have 3 of a kind or better
           ----------------------------------------------------- */
        public static boolean is22s( Card[] h )
        {
            boolean a1, a2, a3;

            if ( h.length != 5 )
                return(false);

            if ( is4s(h) || isFullHouse(h) || is3s(h) )
                return(false);        // The hand is not 2 pairs (but better)

            sortByRank(h);

            a1 = h[0].getValue() == h[1].getValue() &&
                    h[2].getValue() == h[3].getValue() ;

            a2 = h[0].getValue() == h[1].getValue() &&
                    h[3].getValue() == h[4].getValue() ;

            a3 = h[1].getValue() == h[2].getValue() &&
                    h[3].getValue() == h[4].getValue() ;

            return( a1 || a2 || a3 );
        }


        /* -----------------------------------------------------
           is2s(): true if h has one pair
                   false otherwise

           **** Note: use is22s() ONLY if you know the hand
                      does not have 2 pairs or better
           ----------------------------------------------------- */
        public static boolean is2s( Card[] h )
        {
            boolean a1, a2, a3, a4;

            if ( h.length != 5 )
                return(false);

            if ( is4s(h) || isFullHouse(h) || is3s(h) || is22s(h) )
                return(false);        // The hand is not one pair (but better)

            sortByRank(h);

            a1 = h[0].getValue() == h[1].getValue() ;
            a2 = h[1].getValue() == h[2].getValue() ;
            a3 = h[2].getValue() == h[3].getValue() ;
            a4 = h[3].getValue() == h[4].getValue() ;

            return( a1 || a2 || a3 || a4 );
        }


        /* ---------------------------------------------
           isFlush(): true if h has a flush
                      false otherwise
           --------------------------------------------- */
        public static boolean isFlush( Card[] h )
        {
            if ( h.length != 5 )
                return(false);

            sortBySuit(h);

            return( h[0].getSuit() == h[4].getSuit() );   // All cards has same suit
        }


        /* ---------------------------------------------
           isStraight(): true if h is a Straight
                         false otherwise
           --------------------------------------------- */
        public static boolean isStraight( Card[] h )
        {
            int i, testRank;

            if ( h.length != 5 )
                return(false);

            sortByRank(h);

      /* ===========================
         Check if hand has an Ace
         =========================== */
            if ( h[4].getValue() == 14 )
            {
         /* =================================
            Check straight using an Ace
            ================================= */
                boolean a = h[0].getValue() == 2 && h[1].getValue() == 3 &&
                        h[2].getValue() == 4 && h[3].getValue() == 5 ;
                boolean b = h[0].getValue() == 10 && h[1].getValue() == 11 &&
                        h[2].getValue() == 12 && h[3].getValue() == 13 ;

                return ( a || b );
            }
            else
            {
         /* ===========================================
            General case: check for increasing values
            =========================================== */
                testRank = h[0].getValue() + 1;

                for ( i = 1; i < 5; i++ )
                {
                    if ( h[i].getValue() != testRank )
                        return(false);        // Straight failed...

                    testRank++;
                }

                return(true);        // Straight found !
            }
        }

   /* ===========================================================
      Helper methods
      =========================================================== */

        /* ---------------------------------------------
           Sort hand by rank:

               smallest ranked card first ....

           (Finding a straight is eaiser that way)
           --------------------------------------------- */
        public static void sortByRank( Card[] h )
        {
            int i, j, min_j;

      /* ---------------------------------------------------
         The selection sort algorithm
         --------------------------------------------------- */
            for ( i = 0 ; i < h.length ; i ++ )
            {
         /* ---------------------------------------------------
            Find array element with min. value among
            h[i], h[i+1], ..., h[n-1]
            --------------------------------------------------- */
                min_j = i;   // Assume elem i (h[i]) is the minimum

                for ( j = i+1 ; j < h.length ; j++ )
                {
                    if ( h[j].getValue() < h[min_j].getValue() )
                    {
                        min_j = j;    // We found a smaller minimum, update min_j
                    }
                }

         /* ---------------------------------------------------
            Swap a[i] and a[min_j]
            --------------------------------------------------- */
                Card help = h[i];
                h[i] = h[min_j];
                h[min_j] = help;
            }
        }

        /* ---------------------------------------------
           Sort hand by suit:

               smallest suit card first ....

           (Finding a flush is eaiser that way)
           --------------------------------------------- */
        public static void sortBySuit( Card[] h )
        {
            int i, j, min_j;

      /* ---------------------------------------------------
         The selection sort algorithm
         --------------------------------------------------- */
            for ( i = 0 ; i < h.length ; i ++ )
            {
         /* ---------------------------------------------------
            Find array element with min. value among
            h[i], h[i+1], ..., h[n-1]
            --------------------------------------------------- */
                min_j = i;   // Assume elem i (h[i]) is the minimum

                for ( j = i+1 ; j < h.length ; j++ )
                {
                    if ( h[j].getSuit().ordinal() < h[min_j].getSuit().ordinal() )
                    {
                        min_j = j;    // We found a smaller minimum, update min_j
                    }
                }

         /* ---------------------------------------------------
            Swap a[i] and a[min_j]
            --------------------------------------------------- */
                Card help = h[i];
                h[i] = h[min_j];
                h[min_j] = help;
            }
        }

    }
}

