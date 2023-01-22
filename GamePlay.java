import java.util.Scanner;

public class GamePlay {
    public static void main(String[] args) {
        private Person player = new Person();
        String inputFirstName;
        String inputLastName;
        String lastNameCheck;
        Scanner scnr = new Scanner(System.in);

        // Lets Prompt the user for their name
        System.out.println("What is your first name?");        
        inputFirstName = scnr.nextLine();

        // Check if they want enter their lastname?
        System.out.println("Do you want to enter your lastname(Y/N)?");
        lastNameCheck = scnr.nextLine();

        if ( lastNameCheck.equals('Y') ) {
            System.out.println("What is your last name?");        
            inputLastName = scnr.nextLine();
            player.setName(inputFirstName, inputLastName);
        } else {
            player.setName(inputFirstName);
        }



    }
}
