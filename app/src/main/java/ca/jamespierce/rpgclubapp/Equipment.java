package ca.jamespierce.rpgclubapp;

/**
 * Created by web on 2017-02-09.
 */

public class Equipment {

    // Declare variables
    private String name;
    private String details;
    private String link;

    //Constructor
    public Equipment(String name, String details, String link) {
        this.name = name;
        this.details = details;
        this.link = link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // toString()
    public String toString() { return getName();}
}
