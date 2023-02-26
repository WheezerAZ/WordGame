import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GUI extends JFrame implements ActionListener {
    private static Hosts host;
    private String windowTitle = "Word Play";
    private JFrame mainWindow;
    private int windowHeight = 500;
    private int windowWidth = 500;    
    private JLabel[] playerNames = new JLabel[3];
    private JLabel player1Label = new JLabel("Player 1: ");
    private JLabel player2Label = new JLabel("Player 2: ");;
    private JLabel player3Label = new JLabel("Player 3: ");;
    private JLabel hostLabel = new JLabel("Current Host: ");;
    private JLabel hostName = new JLabel("No host name");
    private JLabel phraseLabel = new JLabel("Current Phrase: ");
    private JLabel phraseDisplay = new JLabel("NO PHRASE DEFINED");
    private JButton addPlayer;
    private JButton addHost;    
    private JLabel message = new JLabel("");
    private JLabel currentRoundDisplay = new JLabel("Game Not Started");
    private JButton playerGuess;
    private Players[] currentPlayers = new Players[3];            
    private int currentPlayerCount;        
    private int gameRound = 0;
    private int currentPlayer;    

    // Setup the page and all the elements for the game
    // The events will determine flow of the game
    public GUI() {
        int i;
        int playerCount;        

        mainWindow = new JFrame(windowTitle);
        mainWindow.setSize(windowWidth, windowHeight);                
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        // At the beginning we don't know our player names
        for(i = 0; i < playerNames.length; i++) {
            playerCount=i + 1;
            playerNames[i] = new JLabel("Unknown Player " + playerCount);            
        }                 

        // Setup our buttons
        addPlayer = new JButton("Add Player 1");
        addHost = new JButton("Add Host");
        playerGuess = new JButton("WAITING");

        // Add in our elements
        mainWindow.setLayout(new FlowLayout());
        mainWindow.add(player1Label);
        mainWindow.add(playerNames[0]);
        mainWindow.add(player2Label);
        mainWindow.add(playerNames[1]);
        mainWindow.add(player3Label);
        mainWindow.add(playerNames[2]);
        mainWindow.add(addPlayer);
        mainWindow.add(hostLabel);
        mainWindow.add(hostName);
        mainWindow.add(addHost);
        mainWindow.add(phraseLabel);
        mainWindow.add(phraseDisplay);
        mainWindow.add(currentRoundDisplay);
        mainWindow.add(playerGuess);
        mainWindow.add(message);
        mainWindow.setVisible(true);

        // Link buttons to the actionListener
        addPlayer.addActionListener(this);
        addHost.addActionListener(this);        
        playerGuess.addActionListener(this);

    }
    
    // This will look at what event source and route the request appropriately
    public void actionPerformed(ActionEvent e) {
        JButton sourceEvent = (JButton) e.getSource();

        if ( sourceEvent == addPlayer ) {
            if ( currentPlayerCount < 3 ) {
                String playerName = new String();
                int playerNumber = currentPlayerCount + 1;            
                playerName = JOptionPane.showInputDialog(null, "Player " + playerNumber + " what is your name(First Last)?");

                // If there are more than 3 characters and there is a space we are assuming we have a first and last name
                // Currently not handling leading or trailing spaces or multiple spaces
                // If there is multiple spaces then on first and second "name" is used
                currentPlayers[currentPlayerCount] = new Players();
                if ( playerName.length() > 3 && playerName.indexOf(" ") > 0 ) {
                    String[] nameParts = playerName.split(" ");                    
                    currentPlayers[currentPlayerCount].setFirstName(nameParts[0]);
                    currentPlayers[currentPlayerCount].setLastName(nameParts[1]); 
                } else {
                    currentPlayers[currentPlayerCount].setFirstName(playerName);
                }

                // Update Display
                playerNames[currentPlayerCount].setText(currentPlayers[currentPlayerCount].getPlayerName());
                playerNumber++;
                if ( playerNumber <= 3 ) {               
                    addPlayer.setText("Add Player " + playerNumber);
                } else {
                    addPlayer.setText("All Players Added");
                }

                // Increment currentPlayerCount
                currentPlayerCount++;
            } else {
                JOptionPane.showMessageDialog(null,"All Players Added!");
            }
        } else if ( sourceEvent == addHost ) {           
            // Only add host once the players are added and there is no host
            if ( currentPlayerCount == 3 && host == null) {
                String newHost = new String();
            
                newHost = JOptionPane.showInputDialog(this,"Who is the host and their game phrase(First Last, Game Phrase)?");

                // First we have to check a game phrase has been provided
                // A comma determines this. Since we also are providing host name
                // it must be after 1st character
                if ( newHost.indexOf(",") > 1 ) {
                    String[] hostInput = newHost.split(",");

                    // Let's check if host name is first and last
                    if ( hostInput[0].indexOf(" ") > 0 ) {
                        String[] nameParts = hostInput[0].split(" ");
                        host = new Hosts(nameParts[0],nameParts[1]);                    
                    } else {
                        host = new Hosts(hostInput[0]);
                    }
                    hostName.setText(host.getHostName());

                    // Lets set the game phrase
                    host.setGamePhrase(hostInput[1]);
                    phraseDisplay.setText(host.getPlayingPhrase());

                    // Lets make the game display and start the rounds
                    gameRound = 1;
                    currentRoundDisplay.setText(">>>>>> Round " + gameRound + " <<<<<<<<<");
                    currentPlayer = 0;
                    int playerNumber = currentPlayer + 1;
                    playerGuess.setText("Player " + playerNumber + " Guess");
                    addHost.setText("Host Added");
                } else {
                    JOptionPane.showMessageDialog(null,"No game phrase included. Please reenter Host and Game Phrase!");
                }
            } else if ( currentPlayerCount == 3 && host != null ) {
                JOptionPane.showMessageDialog(null,"Host already added!");
            } else {
                JOptionPane.showMessageDialog(null,"Please add all the players first!");
            }
        } else if ( sourceEvent == playerGuess ) {            
            if ( currentPlayerCount == 3 && host != null) {
                String playerGuessString = new String();
                String playerGuessDialog = currentPlayers[currentPlayer].getPlayerName() + ", what letter is your guess?";
                playerGuessString = JOptionPane.showInputDialog(null, playerGuessDialog );

                try {
                    Boolean guessStatus = host.findLetters(playerGuessString);

                    // Update Display of phrase
                    phraseDisplay.setText(host.getPlayingPhrase());

                    if ( guessStatus == false ) {
                        JOptionPane.showMessageDialog(null,"Not All Letters Guessed!");                    

                        // If this is player 3 then we are going to next round
                        if ( currentPlayer == 2 ) {
                            gameRound++;
                            currentRoundDisplay.setText(">>>>>> Round " + gameRound + " <<<<<<<<<");
                            currentPlayer = 0;
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");                        
                        } else {
                            currentPlayer++;
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");                        
                        }                    
                    } else {                    
                        // This is a winner so lets let them know they one
                        // Prize Type is 0 for money and 1 for physical
                        Random generateRandom = new Random();
                        int prizeType = generateRandom.nextInt(2);
                        String winningMessageDialog = new String();

                        if ( prizeType == 0 ) {
                            Money cashPrize = new Money();                
                            currentPlayers[currentPlayer].setCurrentMoney(currentPlayers[currentPlayer].getCurrentMoney()+
                                                          cashPrize.returnWinningsValue(currentPlayers[currentPlayer],guessStatus));

                            winningMessageDialog = "Congratulations, you won $" + 
                                                   cashPrize.returnWinningsValue(currentPlayers[currentPlayer],guessStatus) +
                                                   ". Your total winnings are $" + currentPlayers[currentPlayer].getCurrentMoney() + "!";                                              
                        } else {
                            Physical physicalPrize = new Physical();                                        
                            winningMessageDialog = "Congratulations, you won " + 
                                                    physicalPrize.returnWinningsValue(currentPlayers[currentPlayer],guessStatus) +
                                                    ". Your total winnings are $" + currentPlayers[currentPlayer].getCurrentMoney() + "!";
                        }

                        JOptionPane.showMessageDialog(null,winningMessageDialog);                    

                        gameRound = 0;
                        currentPlayer = 0;

                        // See if they want to play another game
                        int newGame;

                        newGame = JOptionPane.showConfirmDialog(null,"Do you want to play another game?","New Game",JOptionPane.YES_NO_OPTION);


                        if ( newGame == JOptionPane.YES_OPTION ) {
                            String newPhrase = new String();
                            newPhrase = JOptionPane.showInputDialog(null, "What the new phrase to guess?");

                            // Lets set the game phrase
                            host.setGamePhrase(newPhrase);
                            phraseDisplay.setText(host.getPlayingPhrase());

                            currentRoundDisplay.setText(">>>>>> Round " + gameRound + " <<<<<<<<<");
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");  
                        } else {
                            JOptionPane.showMessageDialog(null, "Thanks for playing! Goodbye!");
                            mainWindow.dispose();
                            System.exit(newGame);                            
                        }
                    }                
                } catch (MultipleLettersException mle) {
                    JOptionPane.showMessageDialog(null, mle.getMessage());                    
                } catch (Exception eventMessage) {
                    JOptionPane.showMessageDialog(null, eventMessage.getMessage());                
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please add all players and host first!");
            }                
        } 
    }

    
}
