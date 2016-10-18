package com.theironyard.charlotte.entities;

/**
 * Created by mfahrner on 10/18/16.
 */
public class ChatMessage implements Sendable {

    String messageName;

    String text;

    public ChatMessage() {
    }

    public ChatMessage(String messageName, String text) {
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
