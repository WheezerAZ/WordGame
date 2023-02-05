import java.util.Scanner;

public class Players extends Person {
    private int currentMoney;

    public Players() {
        currentMoney = 1000;
    }

    public Players(String inputFirstName) {
        currentMoney = 1000;
        this.setFirstName(inputFirstName);
        this.setLastName(null);
    }

    public Players(String inputFirstName, String inputLastName) {
        currentMoney = 1000;
        this.setFirstName(inputFirstName);
        this.setLastName(inputLastName);
    }
     
    public int getCurrentMoney() {
        return currentMoney;
    }

    public void queryPlayerName(int playerNumber) {
        String inputFirstName;
        String inputLastName;
        String lastNameCheck;
        Scanner scnr = new Scanner(System.in);

        // Lets Prompt the user for their name
        System.out.print("Player " + playerNumber + ", what is your first name? ");        
        inputFirstName = scnr.nextLine();

        // Check if they want enter their lastname?
        System.out.print("Do you want to enter your lastname(Y/N)? ");
        lastNameCheck = scnr.nextLine();

        if ( lastNameCheck.equalsIgnoreCase("Y") ) {
            System.out.print("What is your last name? ");        
            inputLastName = scnr.nextLine();
            this.setFirstName(inputFirstName);
            this.setLastName(inputLastName);            
        } else {
            this.setFirstName(inputFirstName);
        }      
    }

    public void setCurrentMoney(int inputMoney) {
        currentMoney = inputMoney;
    }

    public String toString () {       
        return getPlayerName() + " has a total of $" + currentMoney + ".";
    }

    public String getPlayerName() {
        String fullname;
        
        if ( this.getLastName() == null ) {
            fullname = this.getFirstName(); 
        } else {
            fullname = this.getFirstName() + " " + this.getLastName();
        }

        return fullname;
    }

}
