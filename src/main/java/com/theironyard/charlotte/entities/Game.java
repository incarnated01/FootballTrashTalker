package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mfahrner on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    String nfl_id;

    public Game() {
    }

    public Game(String nfl_id) {
        this.nfl_id = nfl_id;
    }

    public String getNfl_id() {
        return nfl_id;
    }

    public void setNfl_id(String nfl_id) {
        this.nfl_id = nfl_id;
    }
}
