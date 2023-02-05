import java.util.Scanner;
import java.util.Random;

public class Turn {    
    public Turn() {        
    }
    
    // This function controls the turn functionality    
    public boolean takeTurn (Players currentPlayer, Hosts currentHost) {
        Scanner scnr = new Scanner(System.in);
        int guess;        
        Random generateRandom = new Random();
        boolean guessStatus = false;

        // Prize Type is 0 for money and 1 for physical
        int prizeType = generateRandom.nextInt(2);
        
        System.out.println();
        System.out.print(currentHost.getCurrentUserName() + " is asking ");
        System.out.print(currentPlayer.getCurrentUserName() + ", to guess a number between 0 and 100? ");
        

        guess = scnr.nextInt();            

        // First we only process valid guesses. Invalid guesses are reported as wrong
        // and user can go again without penalty
        if( guess >= 0 && guess <= 100 ) {
            // Let's set whether the person has guessed correctly or not
            guessStatus = ( guess == currentHost.getRandomNumValue() );

            // Prize Type of 0 is cash prize, prize type of 1 is physical prize
            // Cash Prize will award user based on guessStatus
            // Physical Prize will award user based on guessStatus
            // Finally if the guess is wrong a hint for the player will be given
            if ( prizeType == 0 ) {
                Money cashPrize = new Money();                
                currentPlayer.setCurrentMoney(currentPlayer.getCurrentMoney()+
                                              cashPrize.displayWinnings(currentPlayer,guessStatus));
                if ( guessStatus == false ) {
                    if ( guess < currentHost.getRandomNumValue() ) {
                        System.out.println("Guess was too low!");
                    } else {
                        System.out.println("Guess was too high!");
                    }
                }         
                System.out.println(currentPlayer.toString());
            } else if ( prizeType == 1 ) {
                Physical physicalPrize = new Physical();                
                currentPlayer.setCurrentMoney(currentPlayer.getCurrentMoney()+
                                              physicalPrize.displayWinnings(currentPlayer, guessStatus));
                if ( guessStatus == false ) {
                    if ( guess < currentHost.getRandomNumValue() ) {
                        System.out.println("Guess was too low!");
                    } else {
                        System.out.println("Guess was too high!");
                    }
                }
                System.out.println(currentPlayer.toString());
            }
        } else {
            System.out.println("Your guess was not between 0 and 100!");
        }        

        return guessStatus;        
    }
    
}
