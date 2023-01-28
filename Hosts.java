public class Hosts extends Person {

    public int randomizeNum() {
        Numbers randomNum = new Numbers();
        randomNum.generateNumber();

        return randomNum.getRandomNum();
    }
    
}
