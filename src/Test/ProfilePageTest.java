import org.junit.*;

public class ProfilePageTest {

    private ProfilePage testObject;

    @Before
    public void reinstantiate() {
        this.testObject = new ProfilePage();
    }

    @Test
    public void profile_page_object_blank_constructor_is_not_null() {
        Assert.assertNotNull(testObject);
    }

    @Test
    public void profile_page_variable_constructor_is_not_null() {
        this.testObject = new ProfilePage(3000, 2000, 1000, 500, 250, 50, 30, 20, 200, 150);
        Assert.assertNotNull(testObject);
    }

    @Test
    public void test_get_rating() {
        Assert.assertEquals(3000, testObject.getRating());
    }

    @Test
    public void test_get_score() {
        Assert.assertEquals(2000, testObject.getScore());
    }

    @Test
    public void test_get_playtime() {
        Assert.assertEquals(1000, testObject.getPlaytime());
    }

    @Test
    public void test_get_score_minute() {
        Assert.assertEquals(2, testObject.getScoreMinute());
    }

    @Test
    public void test_get_damage_dealt() {
        Assert.assertEquals(500, testObject.getDamageDealt());
    }

    @Test
    public void test_get_damage_taken() {
        Assert.assertEquals(250, testObject.getDamageTaken());
    }

    @Test
    public void test_get_damage_ratio() {
        Assert.assertEquals(2.0, testObject.getDamageRatio());
    }

    @Test
    public void test_get_matches() {
        Assert.assertEquals(50, testObject.getMatches());
    }

    @Test
    public void test_get_avg_match() {
        Assert.assertEquals(20, testObject.getAvgMatch());
    }

    @Test
    public void test_get_wins() {
        Assert.assertEquals(30, testObject.getWins());
    }

    @Test
    public void test_get_losses() {
        Assert.assertEquals(20, testObject.getLosses());
    }

    @Test
    public void test_get_win_loss() {
        Assert.assertEquals(1.5, testObject.getWinLoss());
    }

    @Test
    public void test_get_kills() {
        Assert.assertEquals(200, testObject.getKills());
    }

    @Test
    public void test_get_deaths() {
        Assert.assertEquals(50, testObject.getDeaths());
    }

    @Test
    public void test_get_kill_death() {
        Assert.assertEquals(200/150, testObject.getKillDeath());
    }
}