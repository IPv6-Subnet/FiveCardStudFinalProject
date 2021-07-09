package edu.wvup.pmetzger1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/**
 * The BlackJack class presents the controls for the game of BlackJack
 * a user can bet, hit, or stay.
 *
 * @author [Preston Metzger]
 * @version [2021-06-27]
 */
public class FiveCardStud implements ActionListener
{
    // Fields
    private int bet;
    private int pot;
    private Player player;
    private Dealer dealer;

    private JFrame frame;
    private JLabel potLabel;
    private JLabel statusLabel;
    private JTextField betInput;
    private JButton betButton;


    //private JButton hitButton;
    //private JButton stayButton;

    //New
    private JButton checkButton;
    private JButton foldButton;

    private JMenu helpMenu;

    private JButton playButton;
    private JButton endButton;
    private JButton aboutButton;

    private GameState state;

    private JMenuBar menuBar;
    public static final Color foregroundColor = new Color(240, 240, 240); // All caps?
    public static final Color foregroundColor2 = new Color(0, 0, 0);
    public static final Font fontSize = new Font("Calibri", Font.BOLD, 24);
    // https://stackoverflow.com/questions/24278648/how-to-change-font-in-java-gui-application


    //private Deck deck = new Deck(); // For testing @@@


    /**
     * Creates a Blackjack instance with a player and dealer. @@@
     */
    public FiveCardStud()
    {
        resetAll();
    }

    /**
     * Get the bet
     * @return The player's bet
     */
    public int getBet()
    {
        return bet;
    }

    /**
     * Get the total amount in the pot
     * @return The player's pot
     */
    public int getPot()
    {
        return pot;
    }

    /**
     * Get the frame for the GUI
     * @return the GUI's frame
     */
    public JFrame getFrame()
    {
        return frame;
    }

    /**
     * Get the pot's label
     * @return The label for the pot
     */
    public JLabel getPotLabel()
    {
        return potLabel;
    }

    /**
     * Get the status label
     * @return The label's status
     */
    public JLabel getStatusLabel()
    {
        return statusLabel;
    }

    /**
     * Get the bet button
     * @return The user's bet button
     */
    public JButton getBetButton()
    {
        return betButton;
    }

    /**
     * Get the check button
     * @return The user's check button
     */
    public JButton getCheckButton()
    {
        return checkButton;
    }

    /**
     * Get the fold button
     * @return The user's fold button
     */
    public JButton getFoldButton()
    {
        return foldButton;
    }

    /**
     * Get the player
     * @return The player
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Get the dealer
     * @return The dealer
     */
    public Dealer getDealer()
    {
        return dealer;
    }


    /**
     * Takes a players bet, parses into an Integer, and updates pot.
     * @param bet The Players bet
     * @throws IllegalBetException If argument is in incorrect format
     */
    public void takeBet(String bet) throws IllegalBetException
    {
        // Try to parse the user’s input from betInput to an integer
        // Verify the round
        try
        {
            this.bet = Integer.parseInt(bet);
            if(this.bet < 0)
            {
                throw new IllegalBetException("Cannot use a negative number");
            }
            else if(this.bet > 0 && this.bet <= player.getStash())
            {
                player.setStash(player.getStash() - this.bet);
                if(dealer.getStash() < this.bet)
                {
                    pot += dealer.getStash() + this.bet;
                    dealer.setStash(0);
                }
                else
                {
                    pot += this.bet * 2;
                    dealer.setStash(dealer.getStash() - this.bet);
                }
                alterPotLabel(pot + "");
            }
            else
            {
                throw new IllegalBetException("Cannot use zero or " +
                        "amount greater than what you have");
            }

        }
        catch(NumberFormatException e)
        {
            throw new IllegalBetException(bet + " is not a number.");
        }
    }



    /**
     * Declare the winner for this round.
     */
    public Player declareWinner()
    {
        if(player.getHand().size() == 5 && dealer.getHand().size() == 5) {
            dealer.showCards();
            frame.repaint(); // refresh cards


            Player winner = checkWinner();
            state = GameState.ENDED;
            if (winner == null) {
                alterStatusLabel("Round was a tie!");
                JOptionPane.showMessageDialog(frame, " Round ended in a tie.");
                playAgain("Round ended in a tie. The pot has been refunded to the players.", "Round over");
            } else {
                updateLabels(winner);
                return winner;
            }
        }
       return null;
    }



    /**
     * Awards the pot to the winner of the round.
     * @param player The player who won
     */
    public void awardPotToWinner(Player player)
    {
        if(player == null)
        {
            this.player.setStash(this.player.getStash() + getBet());
            this.dealer.setStash(this.dealer.getStash() + (getPot() - getBet()));
            bet = 0;
            pot = 0;
            alterPotLabel("0");
        }
        else
        {
            player.setStash(player.getStash() + getPot());
            bet = 0;

            alterPotLabel("0");
            pot = 0;
        }
        /*
        if(this.player.getStash() == 0 || this.dealer.getStash() ==0 )
        {
            playAgain();
        }
        else
        {
            startOver();
        }
        */

    }

    /**
     * Begin a new round
     */
    // @@ Test
    public void startOver()
    {
        state = GameState.BETTING;
        alterStatusLabel("Player Betting Round");
        player.clearHand();
        dealer.clearHand();
        dealer.resetDeck();

        //hitButton.setEnabled(false);
        //stayButton.setEnabled(false);
        betButton.setEnabled(true);

        // --> These cause a bug to occur
        //checkButton.setEnabled(false);
        //foldButton.setEnabled(false);

        frame.repaint();
    }

    /**
     * Check to see if the player wants to play again or quit.
     */
    public void playAgain(String message, String title)
    {
        /*
        if(player.getStash() <= 0 || dealer.getStash() <= 0)
        {
            state = GameState.ENDED;
            quit();
        }
        else
        {
            alterStatusLabel("Would you like to play again?");
            //String str = gameInput.nextLine();

            // Create a pop up window
            //JFrame f1 = new JFrame("Play/Quit");
            //JMenu m1 = new JMenu();
            JOptionPane opPane = new JOptionPane();
            //frame.add(opPane);


            playButton = new JButton("Yes");
                stayButton.addActionListener(event -> actionPerformed(event));
            opPane.add(playButton);
            endButton = new JButton("No");
                endButton.addActionListener(event->actionPerformed(event));
            opPane.add(endButton);
            */

            /*
            if(true) // TEST
            {
                start(); // Test
            }
            else
            {
                System.exit(0);
            }
            */

        if(player.getStash() == 0)
        {
            JOptionPane.showMessageDialog(frame, "You are out of money.", "Player Broke", JOptionPane.INFORMATION_MESSAGE);
            quit();
        }
        else if(dealer.getStash() == 0)
        {
            JOptionPane.showMessageDialog(frame, "Game over. Would you like to play again?", "Play again?", JOptionPane.INFORMATION_MESSAGE);
            quit();
        }

        int playAgain = JOptionPane.showConfirmDialog(frame, message + "\nPlay again?", title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(playAgain == JOptionPane.YES_OPTION)
        {
            startOver();
        }
        else
        {
            quit();
        }

     // Need way to inform user that your have folded
        /*
    if(state == GameState.FOLD)
    {
        JOptionPane.showMessageDialog(frame, "Player has folded. Would you like to play again?", "Play again?", JOptionPane.INFORMATION_MESSAGE);
        quit();
    }


         */
    }


    /**
     * Begins the game. Every player will be given 2 cards to start with.
     */
    public void startGame()
    {
        state = GameState.DRAW;
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), false);
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), true);
        frame.repaint(); // TEST @@@

        state = GameState.START_GAME;
        betButton.setEnabled(true);
        foldButton.setEnabled(true);
        checkButton.setEnabled(true);
     //   inProgress = true;
        //continueGame();

    }



    /**
     * The player's turn. During a turn, the player will receive a card
     */
    public void playersTurn()
    {
        alterStatusLabel("Player Drawing Phase");

        player.receiveCard(dealer.deal(), true);
        frame.repaint();

    }

    /**
     * The dealer's turn
     */
    // @@ Test @@
    public void dealersTurn()
    {
        //state = GameState.DEALER_TURN;
        alterStatusLabel("Dealer Drawing Phase");

        dealer.receiveCard(dealer.deal(), true);
        frame.repaint();

    }

    /**
     * Sets up the beginning of the game.
     */
    public void start()
    {
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        state = GameState.BETTING;
    }

    /**
     * Review notifications of an action
     * @param event Details of the action
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == betButton) {
            if(state == GameState.BETTING) {
                try {
                    takeBet(betInput.getText()); // Already parsed
                    startGame();
                } catch (IllegalBetException e) {
                    // Include modal at some point @@@@ TEST @@@@@  --> Menu buttons
                    e.printStackTrace();
                }
            }
            else if(state == GameState.START_GAME) {
                try {
                    takeBet(betInput.getText()); // Already parsed
                    playersTurn();
                    dealersTurn();
                    declareWinner();
                } catch (IllegalBetException e) {
                    // Include modal at some point @@@@ TEST @@@@@  --> Menu buttons
                    e.printStackTrace();
                }

            }
        }
        /*else if(event.getSource() == hitButton && state == GameState.DRAW)
        {
            playersTurn();
        }
        else if(event.getSource() == stayButton && state == GameState.DRAW)
        {
            dealersTurn();
        }

         */
        else if(event.getSource() == playButton)
        {
            startOver(); // Play button from dialog box
        }
        else if(event.getSource() == endButton)
        {
            quit();
        }
        else if(event.getSource() == checkButton)
        {
            check();
        }
        else if(event.getSource() == foldButton)
        {
            fold();
        }

    }

    /**
     * Disposes of the JFrame.
     */
    public void cleanUp()
    {
        frame.dispose();
    }

    /**
     * Check the winner for the given round of 5-card-stud.
     * @return
     */
    public Player checkWinner() {
        if(player.getHand().size() < 5 || dealer.getHand().size() < 5) {
            return null;
        }
        int plScore = player.scoreHand();
        int dlScore = dealer.scoreHand();
        if(plScore > dlScore) {
            return player;
        }
        if(dlScore > plScore) {
            return dealer;
        }

        return null;
    }




    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
        frame = new JFrame("5-card-Stud");
        makeMenuBar();
        Color backgroundColor = new Color(53, 101, 77);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout()); // Anonymous instance
        contentPane.setBackground(backgroundColor); // Evenutally have forground and background
        // Foreground is text == white
        // background is green

        // Since deal extends player, dealer also gets the card panel

        // ____North_____

        JPanel northPanel = new JPanel(new FlowLayout());


        potLabel = new JLabel(""); // create int at some point @@@ Test @@@
        alterPotLabel("0");
        northPanel.add(potLabel);

        statusLabel = new JLabel(""); // Add loction
        alterStatusLabel("Player Betting Round");
        northPanel.add(statusLabel);

        contentPane.add(northPanel,BorderLayout.NORTH);
        statusLabel.setForeground(foregroundColor);
        potLabel.setForeground(foregroundColor);
        statusLabel.setFont(fontSize);
        potLabel.setFont(fontSize);

        // ____WEST_____

        Box westPanel = new Box(BoxLayout.Y_AXIS);

        // Add westPanel first. Add this to left of content pane
        contentPane.add(westPanel, BorderLayout.WEST);
        westPanel.add(Box.createGlue());
        westPanel.add(player.getNameLabel());
        westPanel.add(Box.createVerticalStrut(14));
        westPanel.add(player.getStashLabel());
        //westPanel.add(Box.createVerticalStrut(14));
        //westPanel.add(player.getScoreLabel());
        westPanel.add(Box.createGlue());


        // ____CENTER_____

        JPanel centerPanel = new JPanel(new GridLayout(2,1));

        centerPanel.add(dealer.getCardPanel());
        centerPanel.add(player.getCardPanel());

        contentPane.add(centerPanel,BorderLayout.CENTER);

        //Dummy data @@@ To Modify @@@

        /*dealer.receiveCard(deck.deal(), false);
        dealer.receiveCard(deck.deal(), true);

        player.receiveCard(deck.deal(), true);
        player.receiveCard(deck.deal(), true);
        */

        // ____EAST_____

        Box eastPanel = new Box(BoxLayout.Y_AXIS);

        contentPane.add(eastPanel, BorderLayout.EAST);

        eastPanel.add(Box.createGlue());
        eastPanel.add(dealer.getNameLabel());
        eastPanel.add(Box.createVerticalStrut(14));
        eastPanel.add(dealer.getStashLabel());
        //eastPanel.add(Box.createVerticalStrut(14));
        //eastPanel.add(dealer.getScoreLabel());
        eastPanel.add(Box.createGlue());


        // ____SOUTH_____

        JPanel southPanel = new JPanel(new FlowLayout());

        betInput = new JTextField(50);
        southPanel.add(betInput);
        betButton = new JButton("bet");
        // Take action event as variable, and then pass it in as parameter
        betButton.addActionListener(event -> actionPerformed(event));
        southPanel.add(betButton);
        /*
        hitButton = new JButton("hit");
        hitButton.addActionListener(event -> actionPerformed(event));
        southPanel.add(hitButton);
        stayButton = new JButton("stay");
        stayButton.addActionListener(event -> actionPerformed(event));
        southPanel.add(stayButton);
        //
         */

        // NEW
        checkButton = new JButton("check");
        checkButton.addActionListener(event -> actionPerformed(event));
        southPanel.add(checkButton);

        foldButton = new JButton("fold");
        foldButton.addActionListener(event -> actionPerformed(event));
        southPanel.add(foldButton);

        contentPane.add(southPanel,BorderLayout.SOUTH);

        northPanel.setBackground(backgroundColor);
        centerPanel.setBackground(backgroundColor);
        southPanel.setBackground(backgroundColor);

    }

    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar()
    {
        final int SHORTCUT_MASK =
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); // Shortcuts
        // Used to get command/control. Abstraction, who cares.

        Color backgroundColor = new Color(20, 20, 20);


        menuBar = new JMenuBar();
        //makeMenuBar();

        JMenu fileMenu = new JMenu("File"); // Add menu items

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        newGameItem.addActionListener(event -> newGame()); // Get rid of error, @@@ TEST @@@
        fileMenu.add(newGameItem);

        JMenuItem quitGameItem = new JMenuItem("Quit");
        quitGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        quitGameItem.addActionListener(event -> quit()); //
        fileMenu.add(quitGameItem);

        helpMenu = new JMenu("Help");

        // https://www.youtube.com/watch?v=tymnXOm8lV4
        JMenu aboutMenu= new JMenu();
        JTextArea textArea = new JTextArea();
        textArea.setText("The game of 5-card-stud will be played with a Dealer and Player\n"
                + "The player will set an initial bet called the Ante. Which must be met by the dealer\n"
                + "The player can choose to bet more money, hold their action, or fold and give up the round\n"
                + "A player will win the round based upon the score of their hand."


        );

        textArea.setBounds(50, 50, 500, 300);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(200,200,200));
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setForeground(new Color(0, 0, 0));
        textArea.setFont(new Font("Comic Sans", Font.ITALIC, 12));

        // Disable editable options
        textArea.setEditable(false);


        helpMenu.add(textArea);
        frame.setVisible(true);
        helpMenu.setVisible(true);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        menuBar.setBackground(backgroundColor); // Review
        fileMenu.setBackground(backgroundColor);
        helpMenu.setBackground(backgroundColor);

        newGameItem.setBackground(backgroundColor);
        quitGameItem.setBackground(backgroundColor);

        menuBar.setForeground(foregroundColor);
        fileMenu.setForeground(foregroundColor);
        helpMenu.setForeground(foregroundColor);

        newGameItem.setForeground(foregroundColor);
        quitGameItem.setForeground(foregroundColor);
    }


    /**
     * Close out program.
     */
    private void quit()
    {
        System.exit(0);
    }


    /*
     * Set formated text (part)
     *
     */
    private void alterPotLabel(String detailedInfo)
    {
        potLabel.setText("Pot: $" + detailedInfo + ", ");
    }

    /*
     * Set formated text (part)
     *
     */
    private void alterStatusLabel(String detailedInfo)
    {
        statusLabel.setText("Status: " + detailedInfo);
    }

    /*
     * reset all before startOver()
     */
    private void resetAll()
    {
        player = new Player();
        dealer = new Dealer(2500);
        bet = 0;
        pot = 0;

        if(frame != null)
        {
            frame.removeAll();
        }


       //frame.removeAll();
        makeFrame();
        //frame.repaint();
        startOver();
    }

    /*
    Set's up a new game
     */
    private void newGame()
    {
        player.setStash(500);
        dealer.setStash(2500);
        bet = 0;
        pot = 0;




        //frame.removeAll();

        //frame.repaint();
        startOver();
    }

    /*
    Continue's the game
     */
    private void continueGame() {
        // Return player or dealer, or nothing
        Player winner = null;// checkForBlackjack();
        if(winner != null)
        {
            // Can be player or dealer
            awardPotToWinner(winner);
            alterStatusLabel("Natural Blackjack!!");
            JOptionPane.showMessageDialog(frame, "Natural Blackjack!!");
            playAgain("Round over", winner.getNameLabel().getText() + " has won");
        }
        else
        {
            //hitButton.setEnabled(true);
            //stayButton.setEnabled(true);
            betButton.setEnabled(false);
        }
    }

    /*
        Applies a check, where action will be passed to next player
     */
    private void check()
    {
        if(state == GameState.START_GAME && pot != 0) {
            playersTurn();
            dealersTurn();
            declareWinner();
        }
    }

    /*
        When a player fold's they will forfeit their hand for the round
     */
    private void fold() {
        foldButton.setEnabled(false);
        checkButton.setEnabled(false);
        betButton.setEnabled(true);

        state = GameState.FOLD;
        alterStatusLabel("Player has just folded!");
        updateLabels(dealer);




        frame.repaint();

    }

    /*
    Update all of the Player's labels
     */
    private void updateLabels(Player winner) {
        awardPotToWinner(winner);
        alterStatusLabel(winner.getNameLabel().getText() + " has won this round!");
        JOptionPane.showMessageDialog(frame, winner.getNameLabel().getText() + " has won this round!");
        playAgain("Round over", winner.getNameLabel().getText() + " has won");
    }

}