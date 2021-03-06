package com.theironyard.charlotte.entities;

import java.util.Collection;

/**
 * Created by mfahrner on 10/19/16.
 */
public class StatMessage implements Sendable {

    Collection<PassStat> homePassStats;

    Collection<RushStat> homeRushStats;

    Collection<RecStat> homeRecStats;

    Collection<PassStat> awayPassStats;

    Collection<RushStat> awayRushStats;

    Collection<RecStat> awayRecStats;

    public StatMessage() {
    }

    public StatMessage(Collection<PassStat> homePassStats, Collection<RushStat> homeRushStats,
                       Collection<RecStat> homeRecStats, Collection<PassStat> awayPassStats,
                       Collection<RushStat> awayRushStats, Collection<RecStat> awayRecStats) {
        this.homePassStats = homePassStats;
        this.homeRushStats = homeRushStats;
        this.homeRecStats = homeRecStats;
        this.awayPassStats = awayPassStats;
        this.awayRushStats = awayRushStats;
        this.awayRecStats = awayRecStats;
    }

    public Collection<PassStat> getHomePassStats() {
        return homePassStats;
    }

    public void setHomePassStats(Collection<PassStat> homePassStats) {
        this.homePassStats = homePassStats;
    }

    public Collection<RushStat> getHomeRushStats() {
        return homeRushStats;
    }

    public void setHomeRushStats(Collection<RushStat> homeRushStats) {
        this.homeRushStats = homeRushStats;
    }

    public Collection<RecStat> getHomeRecStats() {
        return homeRecStats;
    }

    public void setHomeRecStats(Collection<RecStat> homeRecStats) {
        this.homeRecStats = homeRecStats;
    }

    public Collection<PassStat> getAwayPassStats() {
        return awayPassStats;
    }

    public void setAwayPassStats(Collection<PassStat> awayPassStats) {
        this.awayPassStats = awayPassStats;
    }

    public Collection<RushStat> getAwayRushStats() {
        return awayRushStats;
    }

    public void setAwayRushStats(Collection<RushStat> awayRushStats) {
        this.awayRushStats = awayRushStats;
    }

    public Collection<RecStat> getAwayRecStats() {
        return awayRecStats;
    }

    public void setAwayRecStats(Collection<RecStat> awayRecStats) {
        this.awayRecStats = awayRecStats;
    }
}
