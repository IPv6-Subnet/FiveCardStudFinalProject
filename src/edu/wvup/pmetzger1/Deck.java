package edu.wvup.pmetzger1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class to represent a deck of cards
 *
 * @author (Preston Metzger)
 * @version (2021.06.27)
 */
public class Deck
{
    // Declare a private ArrayList of cards
    private ArrayList<Card> deck;

    /**
     * Constructor for objects of class Deck
     */
    public Deck()
    {
        // Loop through the Suit values
        // Inside that loop, loop through the numbers 2 - 15
        // Build a card using the outer loop's suit
        // and using this loop's index for the value.  Everything from 10 - King has a value of 10, and Ace has a value of 11
        // Put this newly created card in the cards ArrayList
        // Call the static method Collections.shuffle and pass it the cards ArrayList.  This will shuffle the cards for you
        deck = new ArrayList<>();
        createDeck();
        Collections.shuffle(this.deck);
    }

    private void createDeck()
    {
        for (Suit suit : Suit.values())
        {
            for (int i = 2; i < 15; i++)
            {
                if (i == 11)
                {
                    Card newCard = new Card(suit, 11, false, nameCompare(11));
                    deck.add(newCard);
                }

                else if (i >= 10)
                {
                    int n = 10;
                    Card newCard = new Card(suit, n, false, nameCompare(i));
                    deck.add(newCard);
                }

                else {
                    Card newCard = new Card(suit, i, false, nameCompare(i));
                    deck.add(newCard);
                }
            }
        }
    }

    private String nameCompare(int value)
    {
        String name = "";

        if (value == 2) {
            name = "2";
            return name;
        }

        else if (value == 3) {
            name = "3";
            return name;
        }

        else if (value == 4) {
            name = "4";
            return name;
        }

        else if (value == 5) {
            name = "5";
            return name;
        }

        else if (value == 6) {
            name = "6";
            return name;
        }

        else if (value == 7) {
            name = "7";
            return name;
        }

        else if (value == 8) {
            name = "8";
            return name;
        }

        else if (value == 9) {
            name = "9";
            return name;
        }

        else if (value == 10) {
            name = "10";
            return name;
        }

        else if (value == 11) {
            name = "Ace";
            return name;
        }

        else if (value == 12) {
            name = "Jack";
            return name;
        }

        else if (value == 13) {
            name = "Queen";
            return name;
        }

        else {
            name = "King";
            return name;
        }
    }




    /**
     * Deal a card from the deck
     * @return A Card from the deck
     */
    public Card deal()
    {
        // Call the remove method on the cards ArrayList, always using index number 0, and return this card
        return deck.remove(0);
    }

    /**
     * Report on the size of the remaining cards in the deck
     * @return The number of cards left in the deck
     */
    public int cardsLeftInDeck()
    {
        // Return the size of the cards collection
        return (this.deck.size());
    }

    /*
     * Returns 52 pick-up
     */
    @Override
    public String toString()
    {
        String tempString = "";
        // Build a string where you loop over every card and append the card's toString() to this.
        // Return this string to see all 52 cards
        for(Card card : deck)
        {
            tempString = tempString + card.toString();
        }

        return tempString;
    }
}
