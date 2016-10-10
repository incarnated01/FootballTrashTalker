package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by mfahrner on 10/10/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drive {

    String nfl_id;

    String drive_id;

    String quarter;

    String posteam;

    String opponent;

    String result;

    String penyds;

    String ydsgained;

    String numplays;

    String postime;

    ArrayList<Play> plays;

    public Drive() {
    }

    public Drive(String nfl_id, String drive_id, String quarter, String posteam, String opponent, String result, String penyds, String ydsgained, String numplays, String postime, ArrayList<Play> plays) {
        this.nfl_id = nfl_id;
        this.drive_id = drive_id;
        this.quarter = quarter;
        this.posteam = posteam;
        this.opponent = opponent;
        this.result = result;
        this.penyds = penyds;
        this.ydsgained = ydsgained;
        this.numplays = numplays;
        this.postime = postime;
        this.plays = plays;
    }

    public String getNfl_id() {
        return nfl_id;
    }

    public void setNfl_id(String nfl_id) {
        this.nfl_id = nfl_id;
    }

    public String getDrive_id() {
        return drive_id;
    }

    public void setDrive_id(String drive_id) {
        this.drive_id = drive_id;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getPosteam() {
        return posteam;
    }

    public void setPosteam(String posteam) {
        this.posteam = posteam;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPenyds() {
        return penyds;
    }

    public void setPenyds(String penyds) {
        this.penyds = penyds;
    }

    public String getYdsgained() {
        return ydsgained;
    }

    public void setYdsgained(String ydsgained) {
        this.ydsgained = ydsgained;
    }

    public String getNumplays() {
        return numplays;
    }

    public void setNumplays(String numplays) {
        this.numplays = numplays;
    }

    public String getPostime() {
        return postime;
    }

    public void setPostime(String postime) {
        this.postime = postime;
    }

    public ArrayList<Play> getPlays() {
        return plays;
    }

    public void setPlays(ArrayList<Play> plays) {
        this.plays = plays;
    }
}
