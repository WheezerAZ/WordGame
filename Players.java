public class Players extends Person {
    private double currentMoney;

    public Players() {
        currentMoney = 1000.00;
    }
    
    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double inputMoney) {
        currentMoney = inputMoney;
    }

    public void toString () {
        System.out.println(firstName + " " + lastName + " has a total of $" + 
                            currentMoney + ".");
    }

}
