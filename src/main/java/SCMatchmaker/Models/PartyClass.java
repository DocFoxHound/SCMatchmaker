package SCMatchmaker.Models;

import java.util.List;

public class PartyClass {
    private List<ProfileClass> players;
    private double eloStart;
    private double eloMax;
    private double eloMinimum;
    private long timeStarted;
    private double eloModifier;

    private ProfileClass partyLeader;

    public PartyClass(List<ProfileClass> players, double eloStart, double eloMax, double eloMinimum, long timeStarted, ProfileClass partyLeader) {
        this.players = players;
        this.eloStart = eloStart;
        this.eloMax = eloMax;
        this.eloMinimum = eloMinimum;
        this.timeStarted = timeStarted;
    }

    public List<ProfileClass> getPlayers() {
        return players;
    }

    public void addPlayer(ProfileClass player) {
        players.add(player);
    }

    public void setPlayers(List<ProfileClass> players) {
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

    public double getEloModifier() {
        return eloModifier;
    }

    public void setEloModifier(double eloModifier) {
        this.eloModifier = eloModifier;
    }

    public ProfileClass getPartyLeader() {
        return partyLeader;
    }

    public void setPartyLeader(ProfileClass partyLeader) {
        this.partyLeader = partyLeader;
    }
}
