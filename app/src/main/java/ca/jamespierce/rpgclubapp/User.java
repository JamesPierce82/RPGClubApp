package ca.jamespierce.rpgclubapp;

/**
 * Created by web on 2017-02-16.
 */

public class User {

    // Declare variables
    private int id;
    private String name;
    private int avatar;

    // Constructors
    public User() {

    }

    public User(String name, int avatar) {
        this.name = name;
        this.avatar = avatar;
    }

    public User(int id, String name, int avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
