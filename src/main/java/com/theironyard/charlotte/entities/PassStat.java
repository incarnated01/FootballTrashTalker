package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mfahrner on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PassStat {
    String name;

    String attempts;

    String completions;

    String yards;

    String touchdowns;

    String interceptions;

    public PassStat() {
    }

    public PassStat(String name, String attempts, String completions, String yards, String touchdowns, String interceptions) {
        this.name = name;
        this.attempts = attempts;
        this.completions = completions;
        this.yards = yards;
        this.touchdowns = touchdowns;
        this.interceptions = interceptions;
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

    public String getCompletions() {
        return completions;
    }

    public void setCompletions(String completions) {
        this.completions = completions;
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

    public String getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(String interceptions) {
        this.interceptions = interceptions;
    }
}
