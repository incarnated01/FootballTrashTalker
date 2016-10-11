package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

/**
 * Created by mfahrner on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {
    HashMap<String, PassStat> passing;

    HashMap<String, RushStat> rushing;

    HashMap<String, RecStat> receiving;

    public Stat() {
    }

    public Stat(HashMap<String, PassStat> passing, HashMap<String, RushStat> rushing, HashMap<String, RecStat> receiving) {
        this.passing = passing;
        this.rushing = rushing;
        this.receiving = receiving;
    }

    public HashMap<String, PassStat> getPassing() {
        return passing;
    }

    public void setPassing(HashMap<String, PassStat> passing) {
        this.passing = passing;
    }

    public HashMap<String, RushStat> getRushing() {
        return rushing;
    }

    public void setRushing(HashMap<String, RushStat> rushing) {
        this.rushing = rushing;
    }

    public HashMap<String, RecStat> getReceiving() {
        return receiving;
    }

    public void setReceiving(HashMap<String, RecStat> receiving) {
        this.receiving = receiving;
    }
}
