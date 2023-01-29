public class Players extends Person {
    private double currentMoney;

    public Players() {
        currentMoney = 1000.00;
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
     
    public double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(double inputMoney) {
        currentMoney = inputMoney;
    }

    public String toString () {
        return getFirstName() + " " + getLastName() + " has a total of $" + currentMoney + ".";
    }

}
