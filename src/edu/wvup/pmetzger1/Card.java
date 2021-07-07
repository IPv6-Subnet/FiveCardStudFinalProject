package edu.wvup.pmetzger1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Card represents a standard playing card from a 52 card deck
 *
 * @author Charles Almond
 * @author Preston Metzger
 * @version 2021.06.23
 */
public class Card implements Comparable<Card> // Interface
{
    // private instance variables - Declare a suit (Suit), value (int), visible (boolean), and name (String)
    private Suit suit;
    private int value;
    private boolean visible;
    private String name;

    private BufferedImage face;
    private BufferedImage back;
    private JLabel cardLabel;



    /**
     * Constructor for objects of class Card
     * @param suit The suit of the card (Clubs, Spades, Diamonds, Hearts)
     * @param value The point value of the card, between 1 and 11, inclusive
     * @param visible The setting to show or hide the card when dealt, change with show() or hide()
     * @param name The name of the card
     */
    public Card(Suit suit, int value, boolean visible, String name)
    {
        // Assign properties, and ensure the value is between 1 and 11, inclusive.
        this.suit = suit;
        this.value = value;
        this.visible = visible;
        this.name = name;
        this.cardLabel = new JLabel();
        this.cardLabel.setBackground(Color.white);


        try
        {
            this.face = ImageIO.read(new File("png/" + name + "_of_" + suit.toString().toLowerCase() +".png"));
            this.back = ImageIO.read(new File("png/back.png"));
        }
        catch(IOException e)
        {
            System.out.print(e.getStackTrace());
        }
        if(visible)
        {
            this.show(); // face and back
        }
        else
        {
            this.hide();
        }
    }

    /**
     * Compare cards.
     * @param c The card to be compared.
     */
    public int compareTo(Card c)
    {
        return 0;
    }


    /**
     * Get the card label
     * @return The card's label
     */
    public JLabel getCardLabel()
    {
        return cardLabel;
    }

    /**
     * Get the visibility for an image
     */
    public BufferedImage getImage()
    {
        if (isVisible()) // == is implied
        {
            return face;
        }
        return back;
    }

    /**
     * Get the card's suit
     * @return The card's suit
     */
    public Suit getSuit()
    {
        return this.suit;
    }

    /**
     * Get the card's point value
     * @return A number 2 - 11 of the card's point value
     */
    public int getValue()
    {
        return this.value;
    }

    /**
     * Get the card's name
     * @return The name of the card ("2 - 10, Jack, Queen, King, or Ace")
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Report if the card is facec-up or face-down
     * @return true if the card is face-up, false if face-down
     */
    public boolean isVisible()
    {
        return this.visible;
    }

    /**
     * Set the card as visible, so the front of the card is shown
     */
    public void show()
    {
        this.visible = true;
        this.getCardLabel().setIcon(new ImageIcon(getImage()));
    }

    /**
     * Set the card as hidden, so the back of the card is shown
     */
    public void hide()
    {
        this.visible = false;
        getCardLabel().setIcon(new ImageIcon(getImage()));
    }

    /*
     * Display the front or back of the card, based on the visible field's value
     */
    @Override
    public String toString()
    {
        // If the card is visible, display the card's name of suit (ex: Queen of Spades)
        if(this.visible)
        {
            return (this.name + " of " + this.suit);
        }
        // If the card is not visible, return the string literal "Hidden Card"
        else {
            return ("FaceDown Card");
        }
    }
}
