package com.theironyard.charlotte.entities;

/**
 * Created by mfahrner on 10/4/16.
 */

public class Message {

    public enum MessageType {
        chat,
        score,
        stat
    }

    private Sendable body;

    public Message(Sendable payload) {
        this.body = payload;
    }

    public Sendable getBody() {
        return body;
    }

    public void setBody(Sendable body) {
        this.body = body;
    }

    public MessageType getMessageType() {
        if (body instanceof UpdateMessage) {
            return MessageType.score;
        }
        else if (body instanceof StatMessage){
            return MessageType.stat;
        }
        else {
            return MessageType.chat;
        }
    }
}
