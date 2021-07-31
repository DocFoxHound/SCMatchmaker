package SCMatchmaker.Models;

import java.util.List;

public class PartyClass {
    private List<PlayerClass> players;
    private double eloStart;
    private double eloMax;
    private double eloMinimum;
    private long timeStarted = System.currentTimeMillis();

    public PartyClass(List<PlayerClass> players, double eloStart, double eloMax, double eloMinimum, long timeStarted) {
        this.players = players;
        this.eloStart = eloStart;
        this.eloMax = eloMax;
        this.eloMinimum = eloMinimum;
        this.timeStarted = timeStarted;
    }

    public List<PlayerClass> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerClass> players) {
        this.players = players;
    }

    public double getEloStart() {
        return eloStart;
    }

    public void setEloStart(double eloStart) {
        this.eloStart = eloStart;
    }

    public double getEloMax() {
        return eloMax;
    }

    public void setEloMax(double eloMax) {
        this.eloMax = eloMax;
    }

    public double getEloMinimum() {
        return eloMinimum;
    }

    public void setEloMinimum(double eloMinimum) {
        this.eloMinimum = eloMinimum;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }
}
