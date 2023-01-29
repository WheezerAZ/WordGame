import java.util.Scanner;

public class Turn {
    private double incorrectGuess;
    private double correctGuess;

    public Turn() {
        incorrectGuess = 10;
        correctGuess = 10;
    }

    public double getIncorrectGuess() {
        return incorrectGuess;    
    }

    public void setIncorrectGuess(double inputAmount) {
        incorrectGuess = inputAmount;
    }

    public double getCorrectGuess() {
        return correctGuess;    
    }

    public void setCorrectGuess(double inputAmount) {
        correctGuess = inputAmount;
    }

    public boolean takeTurn (Players currentPlayer, Hosts currentHost) {
        Scanner scnr = new Scanner(System.in);
        int guess;

        System.out.print(currentHost.getCurrentUserName() + " is asking ");
        System.out.println(currentPlayer.getCurrentUserName() + " , to guess a number between 0 and 100? ");

        guess = scnr.nextInt();
        scnr.close();

        if( guess >= 0 && guess <= 100 ) {
            if ( guess == currentHost.getRandomNum() ) {
                currentPlayer.setCurrentMoney(currentPlayer.getCurrentMoney() + correctGuess);
                System.out.println("Congratulations, you are a winner!");
                System.out.println(currentPlayer.toString());
                return true;
            } else {
                currentPlayer.setCurrentMoney(currentPlayer.getCurrentMoney() - incorrectGuess);
                if ( guess > currentHost.getRandomNum() ) {
                    System.out.println("I'm sorry. Your guess was too high!");
                } else {
                    System.out.println("I'm sorry. Your guess was too low!");
                }
                System.out.println(currentPlayer.toString());

                return false;
            }
        } else {
            System.out.println("Your guess was not between 0 and 100!");
        }        

        return true;        
    }
    
}
