package SCMatchmaker;

import discord4j.core.object.entity.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class ScraperScrape {
    public static List<String> scrape(Message message, String handle){
        try{
            //setting properties for the headless driver
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1400,800", "--ignore-certificate-errors");

            //initializing the Chrome webbrowser driver
            WebDriver driver = new ChromeDriver(options);

            //initializing the wait driver (allows things to load) with maximum wait time in int
            WebDriverWait wait = new WebDriverWait(driver, 5);

            //navigate to a website
            driver.navigate().to("https://robertsspaceindustries.com/community/leaderboards/all?mode=BR");

            //save the xPath of the season dropdown menu
            String seasonButton = "//*[@id=\"leaderboard-filters\"]/div[3]/div[2]/fieldset[3]/a";

            //wait until the dropdown menu is loaded on the page before clicking it
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(seasonButton)));

            //click the dropdown menu
            driver.findElement(By.xpath(seasonButton)).click();

            //get the xPath value of 3.11
            String onlyWorkingSeason = "//*[@id=\"leaderboard-filters\"]/div[3]/div[2]/fieldset[3]/a/ul/li[3]";

            //click 3.11
            driver.findElement(By.xpath(onlyWorkingSeason)).click();

            //get the Find Player textbox
            WebElement findPlayer = driver.findElement(By.xpath("//*[@id=\"leaderboard-filters\"]/div[3]/div[2]/fieldset[1]/div/div/input"));

            //look for the user
            findPlayer.sendKeys(handle);

            //save the xPath of the season dropdown menu
            String userPopOut = "//*[@id=\"leaderboard-filters\"]/div[3]/div[2]/fieldset[1]/div/div/div";

            //wait until the user is visible as it sometimes takes a moment
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(userPopOut)));

            //click the user and wait for it to load
            driver.findElement(By.xpath(userPopOut)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active")));

            //Retrieve a list of WebElements starting at the cssSelector
            //these are the stats visible on the standard non-expanded grid.
            //The reason I am NOT saving these as any datatype right now is because some return as "-" thanks to CiG,
            //and converting can be try/catched later.
            String rating = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div:nth-child(3)")).getText();
            String scoreminute = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div:nth-child(4)")).getText();
            String score = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div:nth-child(5)")).getText();
            String damageratio = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div:nth-child(6)")).getText();
            String killdeath = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div:nth-child(7)")).getText();
            String playtime = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div:nth-child(8)")).getText();
            //below are inside the expanded box. You have to remove some text before they can be stored.
            String matches = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(1) > div.stats > li:nth-child(1)")).getText().replaceAll("MATCHES:", "");
            String avgmatch = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(1) > div.stats > li:nth-child(2)")).getText().replaceAll("AVG MATCH:", "");
            String wins = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(1) > div.stats > li:nth-child(3)")).getText().replaceAll("WINS:", "");
            String losses = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(1) > div.stats > li:nth-child(4)")).getText().replaceAll("LOSSES:", "");
            String winloss = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(1) > div.stats > li:nth-child(5)")).getText().replaceAll("WIN/LOSS:", "");
            String damagetaken = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(2) > div.stats > li:nth-child(1)")).getText().replaceAll("DAMAGE TAKEN:", "");
            String damagedealt = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(2) > div.stats > li:nth-child(2)")).getText().replaceAll("DAMAGE DEALT:", "");
            String kills = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(2) > div.stats > li:nth-child(3)")).getText().replaceAll("KILLS:", "");
            String deaths = driver.findElement(By.cssSelector("#leaderboard-data > li.row.clearfix.trans-02s.animDone.active > div.stats-wrap.trans-05s.dogfighting.account.open > div:nth-child(2) > div.stats > li:nth-child(4)")).getText().replaceAll("DEATHS:", "");

            //close the browser
            driver.close();

            //place the results in an array list
            List<String> resultsArray = new ArrayList<>();
            resultsArray.add(rating); //index: 0
            resultsArray.add(scoreminute); //index: 1
            resultsArray.add(score); //index: 2
            resultsArray.add(damageratio); //index: 3
            resultsArray.add(killdeath); //index: 4
            resultsArray.add(playtime); //index: 5
            resultsArray.add(matches); //index: 6
            resultsArray.add(avgmatch); //index: 7
            resultsArray.add(wins); //index: 8
            resultsArray.add(losses); //index: 9
            resultsArray.add(winloss); //index: 10
            resultsArray.add(damagetaken); //index: 11
            resultsArray.add(damagedealt); //index: 12
            resultsArray.add(kills); //index: 13
            resultsArray.add(deaths); //index: 14

            //update user
            Bot.sendMessage(message, "Leaderboard Verified...");

            //return the results as an array. There are 16 results total
            return resultsArray;

        }catch(Exception e){
            Bot.sendMessage(message, "Scraping Error: " + e);
        }
        return null;
    }
}
