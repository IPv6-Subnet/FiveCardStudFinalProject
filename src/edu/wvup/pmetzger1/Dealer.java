package edu.wvup.pmetzger1;

/**
 * Have the Dealer extend Player
 *
 * @author (Preston Metzger)
 * @version (2021.06.27)
 */
public class Dealer extends Player
{
    // Create a private property of a Deck
    private Deck deck;


    /**
     * Constructor for objects of class Dealer
     */
    public Dealer(int stash)
    {
        // Call the parent class' constructor with the name "Dealer" and the stash provided as a parameter
        super("Dealer", stash);
        // Initialize the Deck property by calling its constructor
        deck = new Deck();

        //name = "Dealer";


    }

    /**
     * deal will return a card from the deck
     * @return Card a card from the deck
     */
    public Card deal()
    {
        // Deal a card from the deck and return it.
        return deck.deal();
    }

    /**
     * resetDeck replaces the deck with a new shuffled 52 card deck
     */
    public void resetDeck()
    {
        // Initialize the deck as a new instance of the Deck to reset it to 52 random cards again.
        deck = new Deck();
    }

    /**
     * Show the dealers cards
     */
    public void showCards()
    {
        getCardPanel().removeAll();
        for(Card card : getHand())
        {
            card.show();
            getCardPanel().add(card.getCardLabel());
        }
    }
}
