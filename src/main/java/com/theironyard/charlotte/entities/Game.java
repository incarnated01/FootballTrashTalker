package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by mfahrner on 10/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    String nfl_id;
    Home home;

    int day;
    int month;
    int week;


//    List<Home> homeObject;

    public Game() {
    }

    public Game(String nfl_id, int day, int month, int week) {
        this.nfl_id = nfl_id;
        this.day = day;
        this.month = month;
        this.week = week;
    }

    public String getNfl_id() {
        return nfl_id;
    }

    public void setNfl_id(String nfl_id) {
        this.nfl_id = nfl_id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}

