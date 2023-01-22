import java.util.Scanner;

public class GamePlay {    
    private static Person player;    
    public static void main(String[] args) {            
        String inputFirstName;
        String inputLastName;
        String lastNameCheck;
        Scanner scnr = new Scanner(System.in);        
        Numbers numbersValue = new Numbers();        
        boolean guessed;
        int guess;
        player = new Person();
        
        // Lets Prompt the user for their name
        System.out.println("What is your first name?");        
        inputFirstName = scnr.nextLine();

        // Check if they want enter their lastname?
        System.out.println("Do you want to enter your lastname(Y/N)?");
        lastNameCheck = scnr.nextLine();   

        if ( lastNameCheck.equalsIgnoreCase("Y") ) {
            System.out.println("What is your last name?");        
            inputLastName = scnr.nextLine();
            player.setName(inputFirstName, inputLastName);
        } else {
            player.setName(inputFirstName);
        }      

        // initialize our number to be guessed
        numbersValue.generateNumber();        
      
        // Setup the guessing loop until its guessed
        guessed = false;
        while ( ! guessed ) {
            System.out.println(player.getCurrentUserName() + ", please guess a number from 0 to 100?");
            guess = scnr.nextInt();
            if( guess >= 0 && guess <= 100 ) {
                guessed = numbersValue.compareNumber(guess);
            }            
        }


    }
}
