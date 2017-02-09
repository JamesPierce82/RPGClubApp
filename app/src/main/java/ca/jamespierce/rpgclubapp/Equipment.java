package ca.jamespierce.rpgclubapp;

/**
 * Created by web on 2017-02-09.
 */

public class Equipment {

    // Declare variables
    private String name;
    private String details;

    //Constructor
    public Equipment(String name, String details) {
        this.name = name;
        this.details = details;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    // toString()
    public String toString() { return getName();}
}
