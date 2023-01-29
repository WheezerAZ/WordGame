import java.util.Scanner;

public class GamePlay {    
    private static Players player;    
    private static Hosts host;
    public static void main(String[] args) {            
        String inputFirstName;
        String inputLastName;
        String lastNameCheck;
        String playAgain;
        Turn gameTurn = new Turn();
        Scanner scnr = new Scanner(System.in);                       
        boolean guessed;
        boolean playingGame = true;
        int guess;
        //player = new Players();

        // Lets setup the host and initialize their values
        host = new Hosts("Monty","Hall");        
        
        // Lets Prompt the user for their name
        System.out.println("What is your first name?");        
        inputFirstName = scnr.nextLine();

        // Check if they want enter their lastname?
        System.out.println("Do you want to enter your lastname(Y/N)?");
        lastNameCheck = scnr.nextLine();           

        if ( lastNameCheck.equalsIgnoreCase("Y") ) {
            System.out.println("What is your last name?");        
            inputLastName = scnr.nextLine();
            player = new Players(inputFirstName, inputLastName);
        } else {
            player = new Players(inputFirstName);
        }      

        while ( playingGame ) {
            host.randomizeNum();

            // Setup the guessing loop until its guessed
            guessed = false;
            while ( ! guessed ) {
                guessed=gameTurn.takeTurn(player,host);
            }

            // See if the user wants to play again
            System.out.println("Do you want to play again(Y/N)?");
            playAgain = scnr.nextLine();
            if ( playAgain.equalsIgnoreCase("N") ) {
                playingGame = false;
            }
        }
        
    }
}
