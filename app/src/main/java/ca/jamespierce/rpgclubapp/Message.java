package ca.jamespierce.rpgclubapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by James Pierce on 2017-01-17.
 */

public class Message {

    // Declare variables
    private String name;
    private String timeSent;
    private String content;
    private int avatar;

    // Constructor
    public Message(String name, String timeSent, String content, int avatar) {
        this.name = name;
        this.timeSent = timeSent;
        this.content = content;
        this.avatar = avatar;
    }

    // toString()
    public String toString() { return getName();}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
