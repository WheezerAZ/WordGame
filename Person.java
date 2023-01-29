public class Person {
    private String firstName;
    private String lastName;

    public Person() {
        firstName = null;
        lastName = null;        
    }

    public Person(String inputFirstName) {
        firstName = inputFirstName;
        lastName = null;
    }

    public Person(String inputFirstName, String inputLastName) {
        firstName = inputFirstName;
        lastName = inputLastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String inputFirstName) {
        firstName = inputFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String inputLastName) {
        lastName = inputLastName;
    }

    public void setName(String inputFirstName) {
        firstName = inputFirstName;
        lastName = null;
    }

    public void setName (String inputFirstName, String inputLastName) {
        firstName = inputFirstName;
        lastName = inputLastName;
    }

    public String getCurrentUserName() {
        return firstName + " " + lastName;
    }
}