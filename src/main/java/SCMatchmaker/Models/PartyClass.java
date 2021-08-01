package SCMatchmaker.Models;

import java.util.List;

public class PartyClass {
    private List<PlayerClass> players;
    private double eloStart;
    private long timeStarted;

    public PartyClass(List<PlayerClass> players, double eloStart, long timeStarted) {
        this.players = players;
        this.eloStart = eloStart;
        this.timeStarted = timeStarted;
    }

    public List<PlayerClass> getPlayers() {
        return players;
    }

    public void addPlayer(PlayerClass player) {
        players.add(player);
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

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }
}
