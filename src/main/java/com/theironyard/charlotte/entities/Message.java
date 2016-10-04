package com.theironyard.charlotte.entities;

/**
 * Created by mfahrner on 10/4/16.
 */

public class Message {

    private String messageName;
    private String text;

    public Message() {
    }

    public Message(String messageName, String text) {
        this.messageName = messageName;
        this.text = text;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
