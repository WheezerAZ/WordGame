import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GUI extends JFrame implements ActionListener {
    private static Hosts host;
    private String windowTitle = "Word Play";
    private JFrame mainWindow;
    private int windowHeight = 550;
    private int windowWidth = 800;        
    private JLabel[] playerNames = new JLabel[3];
    private JPanel playerPanel = new JPanel();
    private JLabel player1Label = new JLabel("Player 1: ");
    private JLabel player2Label = new JLabel("Player 2: ");
    private JLabel player3Label = new JLabel("Player 3: ");
    private JPanel hostPanel = new JPanel();
    private JLabel hostLabel = new JLabel("Current Host: ");
    private JLabel hostName = new JLabel("No host name");
    private GamePanel gamePanel = new GamePanel();
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
    private JMenuItem imageAttributionMenuItem;
    private JMenuItem soundAttributionMenuItem;
    private Timer crowdTimer;    
    
    // Setup the page and all the elements for the game
    // The events will determine flow of the game
    public GUI() {
        int i;
        int playerCount;        
        crowdTimer = new Timer(10, this); // Setup for animation

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
        imageAttributionMenuItem = new JMenuItem("Image Attribution");
        soundAttributionMenuItem = new JMenuItem("Sound Attribution");

        mainWindow.setJMenuBar(mainMenu);        
        mainMenu.add(gameMenu);
        gameMenu.add(addHostMenuItem);
        gameMenu.add(addPlayerMenuItem);
        mainMenu.add(aboutMenu);
        aboutMenu.add(layoutMenuItem);
        aboutMenu.add(imageAttributionMenuItem);
        aboutMenu.add(soundAttributionMenuItem);

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
        playerPanel.setBounds(0,0,250,100);

        // Setup hostPanel as 1 by 2 grid
        hostPanel.setLayout(new GridLayout(1,2));
        hostPanel.setBackground(Color.CYAN);
        hostPanel.setBounds(550,0,250,100);
        
        // Set mainWindow to have no layout manager
        // Panels are arranged using setBounds functionality
        mainWindow.setLayout(null);
        
        // Add in playerPanel
        mainWindow.add(playerPanel);        
        playerPanel.add(player1Label);
        playerPanel.add(playerNames[0]);
        playerPanel.add(player2Label);
        playerPanel.add(playerNames[1]);
        playerPanel.add(player3Label);
        playerPanel.add(playerNames[2]); 
        
        // Add in hostPanel
        mainWindow.add(hostPanel);
        hostPanel.setPreferredSize(new Dimension(250,50));
        hostPanel.add(hostLabel);
        hostPanel.add(hostName);     
                
        // Add in headGamePanel for game info
        JPanel headGamePanel = new JPanel();        
        headGamePanel.setPreferredSize(new Dimension(300,100));        
        headGamePanel.add(phraseLabel);
        headGamePanel.add(phraseDisplay);        
        headGamePanel.add(playerGuess);
        headGamePanel.setBounds(250,0,300,100);
        headGamePanel.setBackground(Color.PINK);        
        mainWindow.add(headGamePanel);

        // gamePanel is an extention of JPanel and supports our images and animations
        gamePanel.setPreferredSize(new Dimension(800,200));
        gamePanel.setBounds(0,100,800,200);
        gamePanel.setBackground(Color.YELLOW);
        mainWindow.add(gamePanel);
        
        // Setting our messagePanel
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveMessages.setText("Save Messages");
        saveMessages.setToolTipText("Click here if you want messages saved as you play.");
        messagePanel.add(saveMessages);
        messagePanel.add(messageScrollPane);
        messagePanel.setPreferredSize(new Dimension(800,200));
        messagePanel.setBounds(0,300,800,200);
        messagePanel.setBackground(Color.WHITE);        
        mainWindow.add(messagePanel);
                
        mainWindow.setVisible(true);

        // Link buttons to the actionListener
        addPlayerMenuItem.addActionListener(this);
        addHostMenuItem.addActionListener(this);        
        layoutMenuItem.addActionListener(this);
        imageAttributionMenuItem.addActionListener(this);
        soundAttributionMenuItem.addActionListener(this);
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
        
            // If the person has one a prize we can display here
            if ( guessStatus )  {
                gamePanel.setShowMoney(true);
                mainWindow.repaint();
                SoundHandler.RunSound("sounds\\money.wav");
            }                                
        } else {
            Physical physicalPrize = new Physical();                                        
            prizeMessageDialog = physicalPrize.returnWinningsValue(currentPlayers[currentPlayer],guessStatus) +
                                 ". Your total winnings are $" + currentPlayers[currentPlayer].getCurrentMoney() + "!";

            // If the person has one a prize we can display here
            if ( guessStatus ) {
                gamePanel.setCurrentPrize(physicalPrize.getCurrentPrizeNumber());
                gamePanel.setShowPrize(true);
                mainWindow.repaint();
                SoundHandler.RunSound(physicalPrize.getCurrentPrizeSound());
            }
            
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
                    message.setText("Players take your seats!");
                    message.setText(message.getText() + "\n\n" + ">>>>>> Round " + gameRound + " <<<<<<<<<\n");
                    currentPlayer = 0;
                    int playerNumber = currentPlayer + 1;
                    playerGuess.setText("Player " + playerNumber + " Guess");
                    addHost.setText("Host Added");                    
                    crowdTimer.start();
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
                            SoundHandler.RunSound("sounds\\wrong.wav");
                        } else if ( letterGuessed == "yes" ) {
                            newText += " correctly guessed " + playerGuessString + ".";
                            SoundHandler.RunSound("sounds\\applause.wav");
                        } else if ( letterGuessed == "repeat" ) {
                            newText += " guessed " + playerGuessString + ", which was already found.";                            
                            SoundHandler.RunSound("sounds\\wrong.wav");
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
                            gamePanel.setShowPrize(false);
                            gamePanel.setShowMoney(false);
                            mainWindow.repaint();

                            String newPhrase = new String();
                            newPhrase = JOptionPane.showInputDialog(null, "What the new phrase to guess?");

                            // Lets set the game phrase
                            host.setGamePhrase(newPhrase);
                            phraseDisplay.setText(host.getPlayingPhrase());                            

                            crowdTimer.start();
                            
                            int playerNumber = currentPlayer + 1;
                            playerGuess.setText("Player " + playerNumber + " Guess");  

                            gameRound++;
                            message.setText("Players take your seats!");
                            message.setText(message.getText() + "\n\n" + ">>>>>> Round " + gameRound + " <<<<<<<<<" );
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
            JOptionPane.showMessageDialog(null, 
                "The base layout does not use any layout. Each container is defined using setbounds\n" +
                "to place them. The player and host panels  JPanels using flow layout. Then with in those\n" +
                "the player list uses a Grid Layout.\n\n" +
                "I chose this arrangement because I wanted define sets of panels depending on their\n" +
                "function and location desired. Then with in those panels I could use layout managers.\n" +
                "Finally, the column layout ensured my player names would make 3 rows.");
        } else if ( e.getSource() == imageAttributionMenuItem ) {                
            JOptionPane.showMessageDialog(null, 
                "The following images were created using an AI image generator at www.craiyon.com:\n" +
                "\tcat_duck.png\n"+
                "\tcdplayer.png\n"+
                "\tparrot_mime.png\n"+
                "\tpotted_plant.png\n\n"+
                "The following image was sourced from www.clipartmax.com:\n" +
                "\tchocolate_bar.png\n\n"+
                "The following image was sourced from www.clipart-library.com:\n" +                
                "\tcrowd.jpg\n\n"+
                "The following image was sourced from www.pixabay.com:\n" +
                "\tmoney.png\n\n");
        } else if ( e.getSource() == soundAttributionMenuItem ) {
            System.out.println("Is this matching");
            JOptionPane.showMessageDialog(null, 
                "The following sounds were sourced from freesound.org:\n" + 
                "\tapplause.wav\n"+
                "\tbackwards.wav\n"+
                "\tdisgust.wav\n"+
                "\tduck.wav\n"+
                "\thungry.wav\n"+
                "\tmoney.wav\n"+
                "\trunning.wav\n"+
                "\twow.wav\n"+
                "\twrong.wav\n");                        
        } else if ( e.getSource() == crowdTimer ) {
            // This is all about animating the starting of each new round of game play.
            // A time is created and it will move the image from left to right by 2 pixels
            // at a time
            int currentGamePanelX = gamePanel.getCrowdImageX();            
            if ( currentGamePanelX < 700 ) {
                if ( currentGamePanelX == 50 ) {
                    SoundHandler.RunSound("sounds\\running.wav");
                }
                gamePanel.setCrowdImageX(currentGamePanelX + 2);
                gamePanel.setShowCrowd(true);                
            } else {
                gamePanel.setCrowdImageX(50);
                gamePanel.setShowCrowd(false);
                crowdTimer.stop();
            }

            mainWindow.repaint();
        }
    }

    
}
