public class Phrases {
    private static String gamePhrase;
    private static String playingPhrase;

    public Phrases() {

    }
    
    public String getGamePhrase() {
        return gamePhrase;
    }

    public void setGamePhrase(String inputPhrase) {
        gamePhrase = inputPhrase;             
        createPlayingPhrase();
    }

    public String getPlayingPhrase() {
        return playingPhrase;
    }

    public void setPlayingPhrase(String inputPhrase) {
        playingPhrase = inputPhrase;             
    }

    // This function will take a phrase string. For every characer it points an underline
    // Spaces are repeated
    public void createPlayingPhrase() {        
        playingPhrase = gamePhrase.replaceAll("\\S","_");
    }

    // This function takes a single letter and updates the 
    // the playing phrase for every instance of that letter
    // It will then check if all letters have been guessed. I
    // If it returns true they have.
    public boolean findLetters(String inputLetter) throws MultipleLettersException, Exception {      
        boolean lettersGuessed = false;      
        
        // First lets verify we have 1 letter
        if ( inputLetter.length() > 1 ) {
            throw(new MultipleLettersException());
        } else if ( ! inputLetter.matches("[a-zA-Z]") ) {
            throw(new Exception("Only letters A through Z accepted"));
        } else {
            // Lets loop through the gamePhrase and compare letters. 
            // Where ever it matches inputLetter we'll be updating
            // playingPhrase in some position.
            // We'll force our checks to be upperCase so we are not making
            // a case sensitive check.
            // Additionally, so playing phrase is updated properly we'll pull letter
            // from position in gamePhrase so that case is matched.            
            char[] playingPhraseLetters = playingPhrase.toCharArray();

            for(int i = 0; i < gamePhrase.length(); i++ ) {                
                if ( gamePhrase.toUpperCase().charAt(i) == inputLetter.toUpperCase().charAt(0) ) {
                    playingPhraseLetters[i] = gamePhrase.charAt(i);
                }
            }
            setPlayingPhrase(new String(playingPhraseLetters));

            // Let's check if the user guessed all the letters by looking for an instance of
            // underline in the Playing Phrase
            if ( playingPhrase.indexOf("_") < 0 ) {
                lettersGuessed = true;
            }
        }

        return lettersGuessed;
    }

    // This is run prior to updating playing phrase to let us know if the user guessed a new letter
    public String checkLetterGuess(String inputLetter) throws MultipleLettersException, Exception {      
        String letterGuessed = new String();
        
        // First lets verify we have 1 letter
        if ( inputLetter.length() > 1 ) {
            throw(new MultipleLettersException());
        } else if ( ! inputLetter.matches("[a-zA-Z]") ) {
            throw(new Exception("Only letters A through Z accepted"));
        } else {
            // Lets loop through the gamePhrase and compare letters.                         
            // We'll force our checks to be upperCase so we are not making
            // a case sensitive check.                        

            letterGuessed = "no";
            for(int i = 0; i < gamePhrase.length(); i++ ) {                
                if ( playingPhrase.toUpperCase().charAt(i) == inputLetter.toUpperCase().charAt(0) ) {
                    letterGuessed = "repeat";
                } else if ( gamePhrase.toUpperCase().charAt(i) == inputLetter.toUpperCase().charAt(0) ) {
                    letterGuessed = "yes";
                }
            }            
        }

        return letterGuessed;
    }

}
