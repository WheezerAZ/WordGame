import java.util.Scanner;

public class GamePlay {    
    private static Players player;    
    private static Hosts host;

    public static void main(String[] args) {            
        Players[] currentPlayers = new Players[3];        
        boolean[] playerGuesses = new boolean[3];
        String playAgain;
        Turn gameTurn = new Turn();
        Scanner scnr = new Scanner(System.in);                       
        boolean guessed;
        boolean playingGame = true;                
        int gameRound;
        String phraseToGuess;

        // Lets setup the host and initialize their values
        host = new Hosts("Monty","Hall");                        
                
        // Go through each player and get their name
        for (var i = 0; i < currentPlayers.length; i++ ) {
            currentPlayers[i] = new Players();
            currentPlayers[i].queryPlayerName(i+1);
        } 
        
        // This is the game loop. It determines if user is in game or not
        while ( playingGame ) {
            // Lets get our phrase to guess
            System.out.println();
            System.out.print(host.getCurrentUserName() + ", enter phrase to guess: ");
            phraseToGuess = scnr.nextLine();
            host.setGamePhrase(phraseToGuess);


            // We have a loop based on correct guess or not
            // Inner loop will in turn have each player take a guess          
            // First player to guess will exit the loop
            guessed = false;
            gameRound = 1;
            while ( ! guessed ) {                
                System.out.println();
                System.out.println(">>>>>>>>>Round " + gameRound + "<<<<<<<<<<");                
                gameRound++;
                for (var i = 0; i < currentPlayers.length; i++ ) {
                    System.out.println("Current Puzzle: " + host.getPlayingPhrase());
                    playerGuesses[i] = gameTurn.takeTurn(currentPlayers[i], host);
                    if ( playerGuesses[i] == true ) {
                        guessed = true; 
                        break;
                    }
                }                                
            }

            // See if the user wants to play again
            System.out.print("Do you want to play again(Y/N)? ");
            playAgain = scnr.nextLine();
            if ( playAgain.equalsIgnoreCase("N") ) {
                playingGame = false;
            }
        }
        
        
    }
}
