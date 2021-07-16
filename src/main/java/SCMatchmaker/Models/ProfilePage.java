import java.text.SimpleDateFormat;
import java.util.Date;

public Class ProfilePage {
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
}


public ProfilePage(){}

public ProfilePage(int rating, int score, int playtime, int damageTaken, int damageDealt, int matches, int wins, int losses, int kills, int deaths) {
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


public int getRating(){
    return this.rating;
}

public void setRating(int rating){
    this.rating = rating;
}

public int getScoreMinute(){
    return this.scoreMinute;
}

public void setScoreMinute(int scoreMinute) {
    this.scoreMinute = scoreMinute;
}

public int getScore() {
    return this.score;
}

public void setScore(int score) {
    this.score = score;
}

public double getDamageRatio() {
    return this.damageRatio;
}

public void setDamageRatio(double damageRatio) {
    this.damageRatio = damageRatio;
}

public double getKillDeath() {
    return this.killDeath;
}

public void setKillDeath(double killDeath) {
    this.killDeath = killDeath;
}

public Date getPlaytime() {
    return this.playtime;
}

public void setPlaytime(Date playtime) {
    this.playtime = playtime;
}

public int getMatches() {
    return matches;
}

public void setMatches(int matches) {
    this.matches = matches;
}

public Date getAvgMatch() {
    return this.avgMatch;
}

public void setAvgMatch(Date avgMatch) {
    this.avgMatch = avgMatch;
}

public int getWins() {
    return this.wins;
}

public void setWins(int wins) {
    this.wins = wins;
}

public int getLosses() {
    return this.losses;
}

public void setLosses() {
    this.losses = losses;
}

public double getWinLoss() {
    return this.winLoss;
}

public void setWinLoss(double winLoss) {
    this.winLoss = winLoss;
}

public int getDamageTaken() {
    return this.damageTaken;
}

public void setDamageTaken(int damageTaken) {
    this.damageTaken = damageTaken;
}

public int getDamageDealt() {
    return this.getDamageDealt;
}

public void setDamageDealt(int damageDealt) {
    this.damageDealt = damageDealt;
}

public int getKills() {
    return this.kills;
}

public void setKills(int kills) {
    this.kills = kills;
}

public int getDeaths() {
    return this.deaths;
}

public void setDeaths(int deaths) {
    this.deaths = deaths;
}