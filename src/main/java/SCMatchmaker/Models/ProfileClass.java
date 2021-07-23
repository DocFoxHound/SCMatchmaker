package SCMatchmaker.Models;

import discord4j.core.object.entity.Message;

public class ProfileClass {
    private Message message;
    private String citizenURL;
    private String discordID;
    private String handle;
    private int rating;
    private int score;
    private int playtime; //playtime recorded in terms of minutes
    private int scoreMinute;
    private int damageDealt;
    private int damageTaken;
    private double damageRatio = this.damageDealt / this.damageTaken;
    private int matches;
    private double avgMatch;
    private int wins;
    private int losses;
    private double winLoss = this.wins / this.losses;
    private int kills;
    private int deaths;
    private double killDeath = this.kills / this.deaths;

    public ProfileClass(){}

    public ProfileClass(Message message, String citizenURL, String discordID, String handle, int rating, int score, int playtime, int damageTaken, int damageDealt, int matches, int wins, int losses, int kills, int deaths) {
        this.message = message;
        this.citizenURL = citizenURL;
        this.discordID = discordID;
        this.handle = handle;
        this.rating = rating;
        this.score = score;
        this.playtime = playtime;
        this.scoreMinute = this.score / this.playtime;
        this.damageTaken = damageTaken;
        this.damageDealt = damageDealt;
        this.matches = matches;
        this.wins = wins;
        this.losses = losses;
        this.kills = kills;
        this.deaths = deaths;
    }

    //-----------------------------------------------------------------
    //all the getters
    public Message getMessage(){
        return this.message;
    }

    public String getCitizenURL(){
        return this.citizenURL;
    }

    public String getDiscordID(){
        return this.discordID;
    }

    public String getHandle(){
        return this.handle;
    }

    public int getRating(){
        return this.rating;
    }

    public int getScoreMinute(){
        return this.scoreMinute;
    }

    public int getScore() {
        return this.score;
    }

    public double getDamageRatio() {
        return this.damageRatio;
    }

    public double getKillDeath() {
        return this.killDeath;
    }

    public int getPlaytime() {
        return this.playtime;
    }

    public int getMatches() {
        return matches;
    }

    public double getAvgMatch() {
        return this.avgMatch;
    }

    public int getWins() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses;
    }

    public double getWinLoss() {
        return this.winLoss;
    }

    public int getDamageTaken() {
        return this.damageTaken;
    }

    public int getDamageDealt() {
        return this.damageDealt;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    //-----------------------------------------------------------------
    //all the setters
    public void setMessage(Message newMessage){
        this.message = newMessage;
    }

    public void setCitizenURL(String newURL){
        this.citizenURL = newURL;
    }

    public void setDiscordID(String newID) {
        this.discordID = newID;
    }

    public void setHandle(String newHandle){
        this.handle = newHandle;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public void setScoreMinute(int scoreMinute) {
        this.scoreMinute = scoreMinute;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDamageRatio(double damageRatio) {
        this.damageRatio = damageRatio;
    }

    public void setKillDeath(double killDeath) {
        this.killDeath = killDeath;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public void setAvgMatch(double newAvgMatch) {
        this.avgMatch = newAvgMatch;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int newLosses) {
        this.losses = losses;
    }

    public void setWinLoss(double winLoss) {
        this.winLoss = winLoss;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
