package info.juanmendez.introfirebase.model;

/**
 * Created by Juan on 2/28/2016.
 */
public class User {

    private int birthYear;
    private String fullName;

    public User() {
    }

    public User(String fullName, int birthYear) {

        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public long getBirthYear() {
        return birthYear;
    }

    public String getFullName() {
        return fullName;
    }
}