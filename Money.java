public class Money implements Award {
    private int incorrectGuess;
    private int correctGuess;

    public Money() {
        incorrectGuess = 10;
        correctGuess = 10;
    }

    public int getIncorrectGuess() {
        return incorrectGuess;    
    }

    public void setIncorrectGuess(int inputAmount) {
        incorrectGuess = inputAmount;
    }

    public int getCorrectGuess() {
        return correctGuess;    
    }

    public void setCorrectGuess(int inputAmount) {
        correctGuess = inputAmount;        
    }

    public int returnWinningsValue(Players inputPlayer, boolean inputGuessCorrect ) {
        int awardAmount = 0;

        if ( inputGuessCorrect ) {            
            awardAmount = this.correctGuess;
        } else {            
            awardAmount = this.incorrectGuess * -1;        
        }

        return awardAmount;
    }

    public int displayWinnings(Players inputPlayer, boolean inputGuessCorrect ) {
        int awardAmount = 0;

        if ( inputGuessCorrect ) {
            System.out.print("Congratulations, " + inputPlayer.getPlayerName());            
            System.out.println(" you are a winner!");
            awardAmount = this.correctGuess;
        } else {
            System.out.print("I'm sorry " + inputPlayer.getPlayerName());            
            System.out.println(", your guess was incorrect!");
            awardAmount = this.incorrectGuess * -1;        
        }

        return awardAmount;
    }
}
