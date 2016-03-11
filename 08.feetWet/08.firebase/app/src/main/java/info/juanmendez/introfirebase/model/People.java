package info.juanmendez.introfirebase.model;

/**
 * Created by Juan on 2/28/2016.
 */
public class People {

    private int birthYear;
    private String fullName;

    public People() {
    }

    public People(String fullName, int birthYear) {

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