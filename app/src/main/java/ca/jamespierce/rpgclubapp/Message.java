package ca.jamespierce.rpgclubapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by James Pierce on 2017-01-17.
 */

public class Message {

    // Declare variables
    private int id;
    private String timeSent;
    private String content;
    private int user_id;

    // Constructors
    public Message() {

    }

    public Message(String timeSent, String content, int user_id) {
        this.timeSent = timeSent;
        this.content = content;
        this.user_id = user_id;
    }

    public Message(int id, String timeSent, String content, int user_id) {
        this.id = id;
        this.timeSent = timeSent;
        this.content = content;
        this.user_id = user_id;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
