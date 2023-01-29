public class Hosts extends Person {
    private Numbers randomNum = new Numbers();
 
    public Hosts() {        
    }

    public Hosts(String inputFirstName) {
        this.setFirstName(inputFirstName);
        this.setLastName(null);
    }

    public Hosts(String inputFirstName, String inputLastName) {
        this.setFirstName(inputFirstName);        
        this.setLastName(inputLastName);
    }

    public int getRandomNum() {        
        return randomNum.getRandomNum();
    }

    public void setRandomNum(int inputNum) {
        randomNum.setRandomNum(inputNum);
    }

    public void randomizeNum() {        
        randomNum.generateNumber();        
    }
    
}
