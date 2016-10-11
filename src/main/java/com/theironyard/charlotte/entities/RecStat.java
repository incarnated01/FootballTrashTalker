package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mfahrner on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecStat {

    String name;

    String receptions;

    String yards;

    String touchdowns;

    public RecStat() {
    }

    public RecStat(String name, String receptions, String yards, String touchdowns) {
        this.name = name;
        this.receptions = receptions;
        this.yards = yards;
        this.touchdowns = touchdowns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceptions() {
        return receptions;
    }

    public void setReceptions(String receptions) {
        this.receptions = receptions;
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
