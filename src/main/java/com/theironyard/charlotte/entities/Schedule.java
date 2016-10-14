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
    int tableId;

    @Column (nullable = false)
    String id;

    @Column (nullable = false)
    String home;

    @Column (nullable = false)
    String away;

    @Column (nullable = false)
    String day;

    @Column (nullable = false)
    String month;

    @Column (nullable = true)
    int dayOfYear;

    @Column (nullable = false)
    long time;

    public Schedule() {
    }

    public Schedule(String id, String home, String away, String day, String month, int dayOfYear, long time) {
        this.id = id;
        this.home = home;
        this.away = away;
        this.day = day;
        this.month = month;
        this.dayOfYear = dayOfYear;
        this.time = time;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
