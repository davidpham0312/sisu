package fi.tuni.prog3.model;

public abstract class User {
    private String firstName;
    private String lastName;
    private String password;

    protected User(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean verifyPassword(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }
}
