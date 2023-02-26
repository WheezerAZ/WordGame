import java.util.Scanner;

public class Players extends Person {
    private int currentMoney;

    public Players() {
        currentMoney = 0;
    }

    public Players(String inputFirstName) {
        currentMoney = 0;
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
