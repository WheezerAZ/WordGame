import java.util.Scanner;
import java.util.Random;

public class Turn {    
    public Turn() {        
    }
    
    // This function controls the turn functionality    
    public boolean takeTurn (Players currentPlayer, Hosts currentHost) {
        Scanner scnr = new Scanner(System.in);
        String guess;        
        Random generateRandom = new Random();
        boolean guessStatus = false;

        // Prize Type is 0 for money and 1 for physical
        int prizeType = generateRandom.nextInt(2);
        
        System.out.println();
        System.out.print(currentHost.getCurrentUserName() + " is asking ");
        System.out.print(currentPlayer.getCurrentUserName() + ", to guess a letter? ");
        guess = scnr.nextLine();  

        try { 
            guessStatus = currentHost.findLetters(guess);

            if ( guessStatus == false ) {
                System.out.println("Not all letters guessed!");                
            } else {
                System.out.println("Current Prize Type " + prizeType);
                if ( prizeType == 0 ) {
                    Money cashPrize = new Money();                
                    currentPlayer.setCurrentMoney(currentPlayer.getCurrentMoney()+
                                                  cashPrize.displayWinnings(currentPlayer,guessStatus));
                    System.out.println(currentPlayer);
                } else {
                    Physical physicalPrize = new Physical();                
                    currentPlayer.setCurrentMoney(currentPlayer.getCurrentMoney()+
                                                  physicalPrize.displayWinnings(currentPlayer, guessStatus));    
                    System.out.println(currentPlayer);
                }
            }
            
        } catch (MultipleLettersException mle) {
            System.out.println(mle.getMessage());                    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return guessStatus;        
    }
    
}
