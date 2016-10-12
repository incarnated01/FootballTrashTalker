package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mfahrner on 10/10/16.
 */
@Entity
@Table(name = "schedule")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column (nullable = true)
    String game_Id;

    @Column (nullable = false)
    String home;

    @Column (nullable = false)
    String away;

    @Column (nullable = false)
    String day;

    @Column (nullable = false)
    String month;

    public Schedule() {
    }

    public Schedule(String game_Id, String home, String away, String day, String month) {
        this.game_Id = game_Id;
        this.home = home;
        this.away = away;
        this.day = day;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGame_Id() {
        return game_Id;
    }

    public void setGame_Id(String game_Id) {
        this.game_Id = game_Id;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
