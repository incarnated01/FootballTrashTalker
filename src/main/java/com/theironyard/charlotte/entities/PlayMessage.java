package com.theironyard.charlotte.entities;

/**
 * Created by mfahrner on 10/16/16.
 */
public class PlayMessage {
    String messageName;

    String type;

    Play currentPlay;


    public PlayMessage() {
    }

    public PlayMessage(String messageName, String type, Play currentPlay) {
        this.messageName = messageName;
        this.type = type;
        this.currentPlay = currentPlay;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Play getCurrentPlay() {
        return currentPlay;
    }

    public void setCurrentPlay(Play currentPlay) {
        this.currentPlay = currentPlay;
    }
}
