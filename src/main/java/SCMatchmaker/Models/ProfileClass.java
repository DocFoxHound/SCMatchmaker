package SCMatchmaker.Models;

import discord4j.core.object.entity.Message;

public class ProfileClass {
    private Message message;
    private String citizenURL;
    private String discordID;
    private String handle; //db
    private String ueeCitizenRecord; //db
    private String discordUsername; //db
    private String orgID; //db
    private double ELO; //db
    private int rating; //db
    private long score; //db
    private int playtime; //db
    private double scoreMinute; //db
    private long damageDealt; //db
    private long damageTaken; //db
    private double damageRatio; //db
    private int matches; //db
    private int avgMatch; //db
    private int wins; //db
    private int losses; //db
    private double winLoss; //db
    private int kills; //db
    private int deaths; //db
    private double killDeath; //db

    public ProfileClass(){}

    public ProfileClass(Message message,
                        String citizenURL,
                        String discordID,
                        String handle,
                        String ueeCitizenRecord,
                        String discordUsername,
                        String orgID,
                        double ELO,
                        int rating,
                        long score,
                        int playtime,
                        double scoreMinute,
                        long damageDealt,
                        long damageTaken,
                        double damageRatio,
                        int matches,
                        int avgMatch,
                        int wins,
                        int losses,
                        double winLoss,
                        int kills,
                        int deaths,
                        double killDeath) {
        this.message = message;
        this.citizenURL = citizenURL;
        this.discordID = discordID;
        this.handle = handle;
        this.ueeCitizenRecord = ueeCitizenRecord;
        this.discordUsername = discordUsername;
        this.orgID = orgID;
        this.ELO = ELO;
        this.rating = rating;
        this.score = score;
        this.playtime = playtime;
        this.scoreMinute = scoreMinute;
        this.damageTaken = damageTaken;
        this.damageDealt = damageDealt;
        this.damageRatio = damageRatio;
        this.matches = matches;
        this.wins = wins;
        this.losses = losses;
        this.winLoss = winLoss;
        this.kills = kills;
        this.deaths = deaths;
        this.killDeath = killDeath;
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

    public String getUeeCitizenRecord(){
        return this.ueeCitizenRecord;
    }

    public String getDiscordUsername(){
        return this.discordUsername;
    }

    public String getOrgID(){
        return this.orgID;
    }

    public double getELO(){
        return this.ELO;
    }

    public int getRating(){
        return this.rating;
    }

    public double getScoreMinute(){
        return this.scoreMinute;
    }

    public long getScore() {
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

    public int getAvgMatch() {
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

    public long getDamageTaken() {
        return this.damageTaken;
    }

    public long getDamageDealt() {
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

    public void setUeeCitizenRecord(String newUeeCitizenRecord){
        this.ueeCitizenRecord = newUeeCitizenRecord;
    }

    public void setDiscordUsername(String newDiscordUsername){
        this.discordUsername = newDiscordUsername;
    }

    public void setOrgID(String newOrgID){
        this.orgID = newOrgID;
    }

    public void setELO(Double newELO){
        this.ELO = newELO;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public void setScoreMinute(double scoreMinute) {
        this.scoreMinute = scoreMinute;
    }

    public void setScore(long score) {
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

    public void setAvgMatch(int newAvgMatch) {
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

    public void setDamageTaken(long damageTaken) {
        this.damageTaken = damageTaken;
    }

    public void setDamageDealt(long damageDealt) {
        this.damageDealt = damageDealt;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
