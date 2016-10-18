package com.theironyard.charlotte.entities;

import java.util.Collection;

/**
 * Created by mfahrner on 10/16/16.
 */
public class UpdateMessage implements Sendable {

    int homeScore;

    int awayScore;

    public UpdateMessage() {
    }

    public UpdateMessage(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
