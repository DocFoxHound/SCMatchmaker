package SCMatchmaker.Models;

import discord4j.core.object.entity.Message;

public class ProfileClass {
    private Message message;
    private String citizenURL;
    private String discordID;
    private String handle;
    private String ueeCitizenRecord;
    private String discordUsername;
    private String orgID;
    //BR_ denotes Battle Royal
    private double BR_ELO;
    private int BR_rating;
    private long BR_score;
    private int BR_playtime;
    private double BR_scoreMinute;
    private long BR_damageDealt;
    private long BR_damageTaken;
    private double BR_damageRatio;
    private int BR_matches;
    private int BR_avgMatch;
    private int BR_wins;
    private int BR_losses;
    private double BR_winLoss;
    private int BR_kills;
    private int BR_deaths;
    private double BR_killDeath;
    //SB_ denotes Squadron Battle
    private double SB_ELO;
    private int SB_rating;
    private long SB_score;
    private int SB_playtime;
    private double SB_scoreMinute;
    private long SB_damageDealt;
    private long SB_damageTaken;
    private double SB_damageRatio;
    private int SB_matches;
    private int SB_avgMatch;
    private int SB_wins;
    private int SB_losses;
    private double SB_winLoss;
    private int SB_kills;
    private int SB_deaths;
    private double SB_killDeath;
    //D_ denotes Duel, which doesn't exist on the leaderboards yet.
    private double D_ELO;
    private int D_rating;
    private long D_score;
    private int D_playtime;
    private double D_scoreMinute;
    private long D_damageDealt;
    private long D_damageTaken;
    private double D_damageRatio;
    private int D_matches;
    private int D_avgMatch;
    private int D_wins;
    private int D_losses;
    private double D_winLoss;
    private int D_kills;
    private int D_deaths;
    private double D_killDeath;

    public ProfileClass(){}

    public ProfileClass(Message message,
                        String citizenURL,
                        String discordID,
                        String handle,
                        String ueeCitizenRecord,
                        String discordUsername,
                        String orgID,
                        //Battle Royal
                        double BR_ELO,
                        int BR_rating,
                        long BR_score,
                        int BR_playtime,
                        double BR_scoreMinute,
                        long BR_damageDealt,
                        long BR_damageTaken,
                        double BR_damageRatio,
                        int BR_matches,
                        int BR_avgMatch,
                        int BR_wins,
                        int BR_losses,
                        double BR_winLoss,
                        int BR_kills,
                        int BR_deaths,
                        double BR_killDeath,
                        //Squadron Battle
                        double SB_ELO,
                        int SB_rating,
                        long SB_score,
                        int SB_playtime,
                        double SB_scoreMinute,
                        long SB_damageDealt,
                        long SB_damageTaken,
                        double SB_damageRatio,
                        int SB_matches,
                        int SB_avgMatch,
                        int SB_wins,
                        int SB_losses,
                        double SB_winLoss,
                        int SB_kills,
                        int SB_deaths,
                        double SB_killDeath,
                        //Duel
                        double D_ELO,
                        int D_rating,
                        long D_score,
                        int D_playtime,
                        double D_scoreMinute,
                        long D_damageDealt,
                        long D_damageTaken,
                        double D_damageRatio,
                        int D_matches,
                        int D_avgMatch,
                        int D_wins,
                        int D_losses,
                        double D_winLoss,
                        int D_kills,
                        int D_deaths,
                        double D_killDeath) {
        this.message = message;
        this.citizenURL = citizenURL;
        this.discordID = discordID;
        this.handle = handle;
        this.ueeCitizenRecord = ueeCitizenRecord;
        this.discordUsername = discordUsername;
        this.orgID = orgID;
        //Battle Royal
        this.BR_ELO = BR_ELO;
        this.BR_rating = BR_rating;
        this.BR_score = BR_score;
        this.BR_playtime = BR_playtime;
        this.BR_scoreMinute = BR_scoreMinute;
        this.BR_damageTaken = BR_damageTaken;
        this.BR_damageDealt = BR_damageDealt;
        this.BR_damageRatio = BR_damageRatio;
        this.BR_matches = BR_matches;
        this.BR_avgMatch = BR_avgMatch;
        this.BR_wins = BR_wins;
        this.BR_losses = BR_losses;
        this.BR_winLoss = BR_winLoss;
        this.BR_kills = BR_kills;
        this.BR_deaths = BR_deaths;
        this.BR_killDeath = BR_killDeath;
        //Squadron Battle
        this.SB_ELO = SB_ELO;
        this.SB_rating = SB_rating;
        this.SB_score = SB_score;
        this.SB_playtime = SB_playtime;
        this.SB_scoreMinute = SB_scoreMinute;
        this.SB_damageTaken = SB_damageTaken;
        this.SB_damageDealt = SB_damageDealt;
        this.SB_damageRatio = SB_damageRatio;
        this.SB_matches = SB_matches;
        this.SB_avgMatch = SB_avgMatch;
        this.SB_wins = SB_wins;
        this.SB_losses = SB_losses;
        this.SB_winLoss = SB_winLoss;
        this.SB_kills = SB_kills;
        this.SB_deaths = SB_deaths;
        this.SB_killDeath = SB_killDeath;
        //Duel
        this.D_ELO = D_ELO;
        this.D_rating = D_rating;
        this.D_score = D_score;
        this.D_playtime = D_playtime;
        this.D_scoreMinute = D_scoreMinute;
        this.D_damageTaken = D_damageTaken;
        this.D_damageDealt = D_damageDealt;
        this.D_damageRatio = D_damageRatio;
        this.D_matches = D_matches;
        this.D_avgMatch = D_avgMatch;
        this.D_wins = D_wins;
        this.D_losses = D_losses;
        this.D_winLoss = D_winLoss;
        this.D_kills = D_kills;
        this.D_deaths = D_deaths;
        this.D_killDeath = D_killDeath;
    }

    //-----------------------------------------------------------------
    //all the getters
    //-----------------------------------------------------------------

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
    //Battle Royal
    public double getBR_ELO(){
        return this.BR_ELO;
    }
    public int getBR_Rating(){
        return this.BR_rating;
    }
    public double getBR_ScoreMinute(){
        return this.BR_scoreMinute;
    }
    public long getBR_Score() {
        return this.BR_score;
    }
    public double getBR_DamageRatio() {
        return this.BR_damageRatio;
    }
    public double getBR_KillDeath() {
        return this.BR_killDeath;
    }
    public int getBR_Playtime() {
        return this.BR_playtime;
    }
    public int getBR_Matches() {
        return BR_matches;
    }
    public int getBR_AvgMatch() {
        return this.BR_avgMatch;
    }
    public int getBR_Wins() {
        return this.BR_wins;
    }
    public int getBR_Losses() {
        return this.BR_losses;
    }
    public double getBR_WinLoss() {
        return this.BR_winLoss;
    }
    public long getBR_DamageTaken() {
        return this.BR_damageTaken;
    }
    public long getBR_DamageDealt() { return this.BR_damageDealt; }
    public int getBR_Kills() { return this.BR_kills; }
    public int getBR_Deaths() { return this.BR_deaths; }
    //Squadron Battle
    public double getSB_ELO(){ return this.SB_ELO; }
    public int getSB_Rating(){ return this.SB_rating; }
    public double getSB_ScoreMinute(){ return this.SB_scoreMinute; }
    public long getSB_Score() { return this.SB_score; }
    public double getSB_DamageRatio() { return this.SB_damageRatio; }
    public double getSB_KillDeath() { return this.SB_killDeath; }
    public int getSB_Playtime() { return this.SB_playtime; }
    public int getSB_Matches() { return SB_matches; }
    public int getSB_AvgMatch() { return this.SB_avgMatch; }
    public int getSB_Wins() { return this.SB_wins; }
    public int getSB_Losses() { return this.SB_losses; }
    public double getSB_WinLoss() { return this.SB_winLoss; }
    public long getSB_DamageTaken() { return this.SB_damageTaken; }
    public long getSB_DamageDealt() { return this.SB_damageDealt; }
    public int getSB_Kills() { return this.SB_kills; }
    public int getSB_Deaths() { return this.SB_deaths; }
    //Duel
    public double getD_ELO(){ return this.D_ELO; }
    public int getD_Rating(){ return this.D_rating; }
    public double getD_ScoreMinute(){ return this.D_scoreMinute; }
    public long getD_Score() { return this.D_score; }
    public double getD_DamageRatio() { return this.D_damageRatio; }
    public double getD_KillDeath() { return this.D_killDeath; }
    public int getD_Playtime() { return this.D_playtime; }
    public int getD_Matches() { return D_matches; }
    public int getD_AvgMatch() { return this.D_avgMatch; }
    public int getD_Wins() { return this.D_wins; }
    public int getD_Losses() { return this.D_losses; }
    public double getD_WinLoss() { return this.D_winLoss; }
    public long getD_DamageTaken() { return this.D_damageTaken; }
    public long getD_DamageDealt() { return this.D_damageDealt; }
    public int getD_Kills() { return this.D_kills; }
    public int getD_Deaths() { return this.D_deaths; }

    //-----------------------------------------------------------------
    //all the setters
    //-----------------------------------------------------------------

    public void setMessage(Message newMessage){ this.message = newMessage; }
    public void setCitizenURL(String newURL){ this.citizenURL = newURL; }
    public void setDiscordID(String newID) { this.discordID = newID; }
    public void setHandle(String newHandle){ this.handle = newHandle; }
    public void setUeeCitizenRecord(String newUeeCitizenRecord){ this.ueeCitizenRecord = newUeeCitizenRecord; }
    public void setDiscordUsername(String newDiscordUsername){ this.discordUsername = newDiscordUsername; }
    public void setOrgID(String newOrgID){ this.orgID = newOrgID; }
    //Battle Royal
    public void setBR_ELO(Double newBR_ELO){ this.BR_ELO = newBR_ELO; }
    public void setBR_Rating(int newBR_rating){ this.BR_rating = newBR_rating; }
    public void setBR_ScoreMinute(double newBR_scoreMinute) { this.BR_scoreMinute = newBR_scoreMinute; }
    public void setBR_Score(long newBR_score) { this.BR_score = newBR_score; }
    public void setBR_DamageRatio(double newBR_damageRatio) { this.BR_damageRatio = newBR_damageRatio; }
    public void setBR_KillDeath(double newBR_killDeath) { this.BR_killDeath = newBR_killDeath; }
    public void setBR_Playtime(int newBR_playtime) { this.BR_playtime = newBR_playtime; }
    public void setBR_Matches(int newBR_matches) { this.BR_matches = newBR_matches; }
    public void setBR_AvgMatch(int newBR_AvgMatch) { this.BR_avgMatch = newBR_AvgMatch; }
    public void setBR_Wins(int newBR_wins) { this.BR_wins = newBR_wins; }
    public void setBR_Losses(int newBR_Losses) { this.BR_losses = newBR_Losses; }
    public void setBR_WinLoss(double newBR_winLoss) { this.BR_winLoss = newBR_winLoss; }
    public void setBR_DamageTaken(long newBR_damageTaken) { this.BR_damageTaken = newBR_damageTaken; }
    public void setBR_DamageDealt(long newBR_damageDealt) { this.BR_damageDealt = newBR_damageDealt; }
    public void setBR_Kills(int newBR_kills) { this.BR_kills = newBR_kills; }
    public void setBR_Deaths(int newBR_deaths) { this.BR_deaths = newBR_deaths; }
    //Squadron Battle
    public void setSB_ELO(Double newSB_ELO){ this.SB_ELO = newSB_ELO; }
    public void setSB_Rating(int newSB_rating){ this.SB_rating = newSB_rating; }
    public void setSB_ScoreMinute(double newSB_scoreMinute) { this.SB_scoreMinute = newSB_scoreMinute; }
    public void setSB_Score(long newSB_score) { this.SB_score = newSB_score; }
    public void setSB_DamageRatio(double newSB_damageRatio) { this.SB_damageRatio = newSB_damageRatio; }
    public void setSB_KillDeath(double newSB_killDeath) { this.SB_killDeath = newSB_killDeath; }
    public void setSB_Playtime(int newSB_playtime) { this.SB_playtime = newSB_playtime; }
    public void setSB_Matches(int newSB_matches) { this.SB_matches = newSB_matches; }
    public void setSB_AvgMatch(int newSB_AvgMatch) { this.SB_avgMatch = newSB_AvgMatch; }
    public void setSB_Wins(int newSB_wins) { this.SB_wins = newSB_wins; }
    public void setSB_Losses(int newSB_Losses) { this.SB_losses = newSB_Losses; }
    public void setSB_WinLoss(double newSB_winLoss) { this.SB_winLoss = newSB_winLoss; }
    public void setSB_DamageTaken(long newSB_damageTaken) { this.SB_damageTaken = newSB_damageTaken; }
    public void setSB_DamageDealt(long newSB_damageDealt) { this.SB_damageDealt = newSB_damageDealt; }
    public void setSB_Kills(int newSB_kills) { this.SB_kills = newSB_kills; }
    public void setSB_Deaths(int newSB_deaths) { this.SB_deaths = newSB_deaths; }
    //Duel
    public void setD_ELO(Double newD_ELO){ this.D_ELO = newD_ELO; }
    public void setD_Rating(int newD_rating){ this.D_rating = newD_rating; }
    public void setD_ScoreMinute(double newD_scoreMinute) { this.D_scoreMinute = newD_scoreMinute; }
    public void setD_Score(long newD_score) { this.D_score = newD_score; }
    public void setD_DamageRatio(double newD_damageRatio) { this.D_damageRatio = newD_damageRatio; }
    public void setD_KillDeath(double newD_killDeath) { this.D_killDeath = newD_killDeath; }
    public void setD_Playtime(int newD_playtime) { this.D_playtime = newD_playtime; }
    public void setD_Matches(int newD_matches) { this.D_matches = newD_matches; }
    public void setD_AvgMatch(int newD_AvgMatch) { this.D_avgMatch = newD_AvgMatch; }
    public void setD_Wins(int newD_wins) { this.D_wins = newD_wins; }
    public void setD_Losses(int newD_Losses) { this.D_losses = newD_Losses; }
    public void setD_WinLoss(double newD_winLoss) { this.D_winLoss = newD_winLoss; }
    public void setD_DamageTaken(long newD_damageTaken) { this.D_damageTaken = newD_damageTaken; }
    public void setD_DamageDealt(long newD_damageDealt) { this.D_damageDealt = newD_damageDealt; }
    public void setD_Kills(int newD_kills) { this.D_kills = newD_kills; }
    public void setD_Deaths(int newD_deaths) { this.D_deaths = newD_deaths; }
}
