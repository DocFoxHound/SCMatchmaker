package SCMatchmaker.Models;

import discord4j.core.object.entity.Message;

public class PlayerClass {

    private Message message;
    private String discordID;
    private String handle;
    private double elo;

    public PlayerClass(){}
    public PlayerClass(Message message, String discordID, String handle, double elo) {
        this.message = message;
        this.discordID = discordID;
        this.handle = handle;
        this.elo = elo;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public double getElo() {
        return elo;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }


}
