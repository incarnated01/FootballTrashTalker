package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mfahrner on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Play {

    String quarter;

    String down;

    String time;

    String yrdln;

    String ydstogo;

    String ydsnet;

    String description;

    String note;

    public Play() {
    }

    public Play(String quarter, String down, String time, String yrdln, String ydstogo, String ydsnet, String description, String note) {
        this.quarter = quarter;
        this.down = down;
        this.time = time;
        this.yrdln = yrdln;
        this.ydstogo = ydstogo;
        this.ydsnet = ydsnet;
        this.description = description;
        this.note = note;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYrdln() {
        return yrdln;
    }

    public void setYrdln(String yrdln) {
        this.yrdln = yrdln;
    }

    public String getYdstogo() {
        return ydstogo;
    }

    public void setYdstogo(String ydstogo) {
        this.ydstogo = ydstogo;
    }

    public String getYdsnet() {
        return ydsnet;
    }

    public void setYdsnet(String ydsnet) {
        this.ydsnet = ydsnet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
