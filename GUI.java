import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GUI extends JFrame implements ActionListener {
    private static Hosts host;
    private String windowTitle = "Word Play";
    private JFrame mainWindow;
    private int windowHeight = 500;
    private int windowWidth = 800;        
    private JLabel[] playerNames = new JLabel[3];
    private JPanel playerPanel = new JPanel();
    private JLabel player1Label = new JLabel("Player 1: ");
    private JLabel player2Label = new JLabel("Player 2: ");
    private JLabel player3Label = new JLabel("Player 3: ");
    private JPanel hostPanel = new JPanel();
    private JLabel hostLabel = new JLabel("Current Host: ");
    private JLabel hostName = new JLabel("No host name");
    private JPanel gamePanel = new JPanel();
    private JLabel phraseLabel = new JLabel("Current Phrase: ");
    private JLabel phraseDisplay = new JLabel("NO PHRASE DEFINED");
    private JButton addPlayer;
    private JButton addHost;    
    private JCheckBox saveMessages = new JCheckBox();
    private JTextArea message = new JTextArea();    
    private JButton playerGuess;
    private Players[] currentPlayers = new Players[3];            
    private int currentPlayerCount;        
    private int gameRound = 0;
    private int currentPlayer;
    private JMenuBar mainMenu;    
    private JMenu gameMenu;
    private JMenu aboutMenu;
    private JMenuItem addHostMenuItem;
    private JMenuItem addPlayerMenuItem;
    private JMenuItem layoutMenuItem;
    
    // Setup the page and all the elements for the game
    // The events will determine flow of the game
    public GUI() {
        int i;
        int playerCount;        

        mainWindow = new JFrame(windowTitle);
        mainWindow.setSize(windowWidth, windowHeight);                
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        // Lets add our menu bar
        mainMenu = new JMenuBar();
        gameMenu = new JMenu("Game");
        addHostMenuItem = new JMenuItem("Add Host");
        addPlayerMenuItem = new JMenuItem("Add Player");

        aboutMenu = new JMenu("About");
        layoutMenuItem = new JMenuItem("Layout");

        mainWindow.setJMenuBar(mainMenu);        
        mainMenu.add(gameMenu);
        gameMenu.add(addHostMenuItem);
        gameMenu.add(addPlayerMenuItem);
        mainMenu.add(aboutMenu);
        aboutMenu.add(layoutMenuItem);

        gameMenu.setMnemonic('G');
        aboutMenu.setMnemonic('A');

        // At the beginning we don't know our player names
        for(i = 0; i < playerNames.length; i++) {
            playerCount=i + 1;
            playerNames[i] = new JLabel("Unknown Player " + playerCount);            
        }                 

        // Setup our buttons
        addPlayer = new JButton("Add Player 1");
        addHost = new JButton("Add Host");
        playerGuess = new JButton("WAITING");

        // Setting our default message        
        message.setEditable(false);              
        message.setWrapStyleWord(true);
        JScrollPane messageScrollPane = new JScrollPane(message, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        messageScrollPane.setPreferredSize(new Dimension(600,150));                
        message.setText("Please enter players, host and game phrase to play.\n");                                                                
      
        // Setup playerPanel as a 3 by 2 grid
        playerPanel.setLayout(new GridLayout(3,2));
        playerPanel.setBackground(Color.GREEN);
        playerPanel.setPreferredSize(new Dimension(250,100 ));

        // Setup hostPanel as 1 by 2 grid
        hostPanel.setLayout(new GridLayout(1,2));
        hostPanel.setBackground(Color.CYAN);
        
        // Add in our elements
        mainWindow.setLayout(new BorderLayout(10, 5));

        JPanel fullPlayerPanel = new JPanel(new FlowLayout());
        fullPlayerPanel.setPreferredSize(new Dimension(250,500));
        mainWindow.add(fullPlayerPanel, BorderLayout.WEST);
        
        fullPlayerPanel.add(playerPanel);
        playerPanel.add(player1Label);
        playerPanel.add(playerNames[0]);
        playerPanel.add(player2Label);
        playerPanel.add(playerNames[1]);
        playerPanel.add(player3Label);
        playerPanel.add(playerNames[2]); 
        
        JPanel playerFillPanel = new JPanel();
        
        playerFillPanel.setPreferredSize(new Dimension(250,400));
        fullPlayerPanel.add(playerFillPanel);

        JPanel fullHostPanel = new JPanel(new FlowLayout());
        fullHostPanel.setPreferredSize(new Dimension(250,500));
        mainWindow.add(fullHostPanel, BorderLayout.EAST);
        
        fullHostPanel.add(hostPanel);
        hostPanel.setPreferredSize(new Dimension(250,50));
        hostPanel.add(hostLabel);
        hostPanel.add(hostName);     
        
        JPanel hostFillPanel = new JPanel();
        hostFillPanel.setPreferredSize(new Dimension(250,450));
        fullHostPanel.add(hostFillPanel);

        mainWindow.add(gamePanel, BorderLayout.CENTER);
        gamePanel.add(phraseLabel);
        gamePanel.add(phraseDisplay);        
        gamePanel.add(playerGuess);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveMessages.setText("Save Messages");
        saveMessages.setToolTipText("Click here if you want messages saved as you play.");
        messagePanel.add(saveMessages);
        messagePanel.add(messageScrollPane);
        messagePanel.setPreferredSize(new Dimension(800,200));
        messagePanel.setBackground(Color.WHITE);
        mainWindow.getContentPane().add(messagePanel, BorderLayout.SOUTH);
        
        mainWindow.setVisible(true);

        // Link buttons to the actionListener
        addPlayerMenuItem.addActionListener(this);
        addHostMenuItem.addActionListener(this);        
        layoutMenuItem.addActionListener(this);
        playerGuess.addActionListener(this);

    }

    // This gets us our prize information. If the person is a winner and it is cash it will
    // increase their total. 
    public String getPrizeString(Boolean guessStatus) {
        // This is a winner so lets let them know they one
        // Prize Type is 0 for money and 1 for physical
        Random generateRandom = new Random();
        int prizeType = generateRandom.nextInt(2);
        String prizeMessageDialog = new String();

        if ( prizeType == 0 ) {
            Money cashPrize = new Money();                

            // We only change their money if they have guessed correctly
            if ( guessStatus == true ) {
                currentPlayers[currentPlayer].setCurrentMoney(currentPlayers[currentPlayer].getCurrentMoney()+
                                                          cashPrize.returnWinningsValue(currentPlayers[currentPlayer],guessStatus));
            }
            

            prizeMessageDialog = "$" + cashPrize.returnWinningsValue(currentPlayers[currentPlayer],true) +
                                 ". Your total winnings are $" + currentPlayers[currentPlayer].getCurrentMoney() + "!";                                              
        } else {
            Physical physicalPrize = new Physical();                                        
            prizeMessageDialog = physicalPrize.returnWinningsValue(currentPlayers[currentPlayer],guessStatus) +
                                 ". Your total winnings are $" + currentPlayers[currentPlayer].getCurrentMoney() + "!";
        }

        return prizeMessageDialog;
    }
    
    // This will look at what event source and route the request appropriately
    public void actionPerformed(ActionEvent e) {        
        if ( e.getSource() == addPlayerMenuItem ) {
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
        } else if ( e.getSource() == addHostMenuItem ) {           
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
                    message.setText(">>>>>> Round " + gameRound + " <<<<<<<<<\n");
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
        } else if ( e.getSource() == playerGuess ) {            
            if ( currentPlayerCount == 3 && host != null) {
                String playerGuessString = new String();
                String playerGuessDialog = currentPlayers[currentPlayer].getPlayerName() + ", what letter is your guess?";
                playerGuessString = JOptionPane.showInputDialog(null, playerGuessDialog );

                try {
                    String letterGuessed = host.checkLetterGuess(playerGuessString);
                    Boolean guessStatus = host.findLetters(playerGuessString);

                    // Update Display of phrase
                    phraseDisplay.setText(host.getPlayingPhrase());

                    if ( guessStatus == false ) {                        
                        String newText = new String();
                        newText = currentPlayers[currentPlayer].getPlayerName();                        

                        if ( letterGuessed == "no" ) {
                            newText += " incorrectly guessed " + playerGuessString + ".";        
                        } else if ( letterGuessed == "yes" ) {
                            newText += " correctly guessed " + playerGuessString + ".";
                        } else if ( letterGuessed == "repeat" ) {
                            newText += " guessed " + playerGuessString + ", which was already found.";                            
                        }                                                
                        newText += "\nYou would have won " + getPrizeString(guessStatus);

                        if ( saveMessages.isSelected() ) {                                  
                            message.setText(message.getText() + "\n" + newText );
                        } else {
                            message.setText(newText);
                        }

                        // If this is player 3 then we are going to next round
                        if ( currentPlayer == 2 ) {
                            gameRound++;

                            if ( saveMessages.isSelected() ) {
                                message.setText(message.getText() + "\n\n" + 
                                                ">>>>>> Round " + gameRound + " <<<<<<<<<" );
                            } else {
                                message.setText(">>>>>> Round " + gameRound + " <<<<<<<<<" );                                                
                            }                            
                            currentPlayer = 0;
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");                        
                        } else {
                            currentPlayer++;
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");                        
                        }                    
                    } else {                                                                        
                        String prizeMessage = new String();
                        prizeMessage = "\n\n>>>>>>>>>>>>>>>>> WINNER <<<<<<<<<<<<<<<<<<<\n\n " +
                                       "Congratulations " + currentPlayers[currentPlayer].getPlayerName() +
                                       ", you won " + getPrizeString(guessStatus);
                        
                        if ( saveMessages.isSelected() ) {
                            message.setText(message.getText() + "\n\n" + prizeMessage );                                            
                        } else {
                            message.setText(prizeMessage);
                        }                                                    

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
                            
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");  

                            gameRound++;
                            message.setText(">>>>>> Round " + gameRound + " <<<<<<<<<" );
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
        } else if ( e.getSource() == layoutMenuItem ) {
            System.out.println("Is this matching");
            JOptionPane.showMessageDialog(null, 
                "The base layout uses border layout. West contains the player list.\n" +
                "East contains the host. Center contains the main game information.\n" +
                "Finally, south contains the message dialog.\n\n"+
                "Within the east and west are JPanels using flow layout. Then with in those\n" +
                "the player list uses a Grid Layout.\n\n" +
                "I chose this arrangement because the border layout makes a good rough arrangement.\n" +
                "Then flow layout I can controlled by setPreferredSize. Finally, the column layout \n"+
                "ensured my player names would make 3 rows.");
        }
    }

    
}
