public class Person {
    private string firstName;
    private string lastName;

    public string getFirstName() {
        return firstName;
    }

    public void setFirstName(string inputFirstName) {
        firstName = inputFirstName;
    }

    public string getLastName() {
        return lastName;
    }

    public void setLastName(string inputLastName) {
        lastName = inputLastName;
    }

    public void setName(string inputFirstName) {
        firstName = inputFirstName;
        lastName = ' ';
    }

    public void setName (string inputFirstName, string inputLastName) {
        firstName = inputFirstName;
        lastName = inputLastName;
    }
}