import java.util.Random;

public class Physical implements Award {
    private String[] prizeList = new String[5];
    private int currentPrizeNumber;
    private String[] prizeSoundList = new String[5];

    public Physical() {
        this.prizeList[0] = "a Potted Plant";
        this.prizeList[1] = "Half a Chocolate Bar";
        this.prizeList[2] = "a cat that quacks";
        this.prizeList[3] = "a CD Player that runs backwards";
        this.prizeList[4] = "a Parrot that mimes";  
        this.prizeSoundList[0] ="sounds\\hungry.wav";
        this.prizeSoundList[1] ="sounds\\disgust.wav";
        this.prizeSoundList[2] ="sounds\\duck.wav";
        this.prizeSoundList[3] ="sounds\\backwards.wav";
        this.prizeSoundList[4] ="sounds\\wow.wav";
    }

    public void setPrize ( String inputPrize, int prizeNumber ) {
        prizeList[prizeNumber]=inputPrize;
    }

    public String getPrize ( int prizeNumber ) {
        return prizeList[prizeNumber];
    }

    public int getCurrentPrizeNumber() {
        return this.currentPrizeNumber;
    }

    public String getCurrentPrizeSound() {
        return this.prizeSoundList[this.currentPrizeNumber];
    }

    public String returnWinningsValue ( Players inputPlayer, boolean inputGuessCorrect ) {
        String prizeName;        

        if ( inputGuessCorrect ) {
            prizeName = getPrize(getRandomPrize());            
        } else {
            prizeName = getPrize(getRandomPrize());
        }

        return prizeName;
    }

    public int displayWinnings ( Players inputPlayer, boolean inputGuessCorrect ) {

        if ( inputGuessCorrect ) {
            System.out.print("Congratulations, " + inputPlayer.getPlayerName());            
            System.out.println(" you are a winner!");
            System.out.println("You've won " + getPrize(getRandomPrize()) + "!!!!");
        } else {
            System.out.print("I'm sorry " + inputPlayer.getPlayerName());            
            System.out.println(", your guess was incorrect!");
            System.out.println("You would have won " + getPrize(getRandomPrize()) + "!!!!");
        }

        return 0;
    }

    public int getRandomPrize() {
        int numberOfPrizes = this.prizeList.length;
        Random generateRandom = new Random();
        int winningPrizeNumber = generateRandom.nextInt(numberOfPrizes);
        this.currentPrizeNumber = winningPrizeNumber;

        return winningPrizeNumber;
    }
}
