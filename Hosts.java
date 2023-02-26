public class Hosts extends Person {     
    private Phrases gamePhrase = new Phrases();  
 
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

    public String getGamingPhrase() {        
        return gamePhrase.getGamePhrase();
    }

    public void setGamePhrase(String inputPhrase) {
        gamePhrase.setGamePhrase(inputPhrase);
    }

    public String getPlayingPhrase() {        
        return gamePhrase.getPlayingPhrase();
    }

    public void setPlayingPhrase(String inputPhrase) {
        gamePhrase.setPlayingPhrase(inputPhrase);
    }

    public boolean findLetters(String inputGuess) throws MultipleLettersException, Exception {
        return gamePhrase.findLetters(inputGuess);
    }

    public String getHostName() {
        String fullname;
        
        if ( this.getLastName() == null ) {
            fullname = this.getFirstName(); 
        } else {
            fullname = this.getFirstName() + " " + this.getLastName();
        }

        return fullname;
    }
    
}
