import java.text.SimpleDateFormat;
import java.util.Date;

public Class ProfilePage {
    private int rating;
    private int scoreMinute;
    private int score;
    private double damageRatio;
    private double killDeath;
    private Date playtime;
    private int matches;
    private Date avgMatch;
    private int wins;
    private int losses;
    private double winLoss;
    private int damageTaken;
    private int damageDealt;
    private int kills;
    private int deaths;
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

