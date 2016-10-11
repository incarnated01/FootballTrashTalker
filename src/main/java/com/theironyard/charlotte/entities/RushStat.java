package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mfahrner on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RushStat {

    String name;

    String attempts;

    String yards;

    String touchdowns;

    public RushStat() {
    }

    public RushStat(String name, String attempts, String yards, String touchdowns) {
        this.name = name;
        this.attempts = attempts;
        this.yards = yards;
        this.touchdowns = touchdowns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttempts() {
        return attempts;
    }

    public void setAttempts(String attempts) {
        this.attempts = attempts;
    }

    public String getYards() {
        return yards;
    }

    public void setYards(String yards) {
        this.yards = yards;
    }

    public String getTouchdowns() {
        return touchdowns;
    }

    public void setTouchdowns(String touchdowns) {
        this.touchdowns = touchdowns;
    }
}
