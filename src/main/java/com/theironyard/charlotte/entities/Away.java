package com.theironyard.charlotte.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

/**
 * Created by mfahrner on 10/10/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Away {

    String team;

    String opponent;

    String totfd;

    String totyds;

    String pyds;

    String ryds;

    String pen;

    String penyds;

    String trnovr;

    String pt;

    String ptyds;

    String ptavg;

    HashMap<String, Drive> drives;

    public Away() {
    }

    public Away(String team, String opponent, String totfd, String totyds, String pyds, String ryds, String pen, String penyds, String trnovr, String pt, String ptyds, String ptavg, HashMap<String, Drive> drives) {
        this.team = team;
        this.opponent = opponent;
        this.totfd = totfd;
        this.totyds = totyds;
        this.pyds = pyds;
        this.ryds = ryds;
        this.pen = pen;
        this.penyds = penyds;
        this.trnovr = trnovr;
        this.pt = pt;
        this.ptyds = ptyds;
        this.ptavg = ptavg;
        this.drives = drives;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getTotfd() {
        return totfd;
    }

    public void setTotfd(String totfd) {
        this.totfd = totfd;
    }

    public String getTotyds() {
        return totyds;
    }

    public void setTotyds(String totyds) {
        this.totyds = totyds;
    }

    public String getPyds() {
        return pyds;
    }

    public void setPyds(String pyds) {
        this.pyds = pyds;
    }

    public String getRyds() {
        return ryds;
    }

    public void setRyds(String ryds) {
        this.ryds = ryds;
    }

    public String getPen() {
        return pen;
    }

    public void setPen(String pen) {
        this.pen = pen;
    }

    public String getPenyds() {
        return penyds;
    }

    public void setPenyds(String penyds) {
        this.penyds = penyds;
    }

    public String getTrnovr() {
        return trnovr;
    }

    public void setTrnovr(String trnovr) {
        this.trnovr = trnovr;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getPtyds() {
        return ptyds;
    }

    public void setPtyds(String ptyds) {
        this.ptyds = ptyds;
    }

    public String getPtavg() {
        return ptavg;
    }

    public void setPtavg(String ptavg) {
        this.ptavg = ptavg;
    }

    public HashMap<String, Drive> getDrives() {
        return drives;
    }

    public void setDrives(HashMap<String, Drive> drives) {
        this.drives = drives;
    }
}
