package SCMatchmaker;

import discord4j.core.object.entity.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScraperScrape {
    public static void scrape(Message message){
        try{
            //setting properties for the headless driver
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            //ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless");
            //options.addArguments("--disable-gpu");
            //options.addArguments("--window-size=1400,800");

            //initializing the firefox webbrowser driver
            ChromeDriver driver = new ChromeDriver();

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

            //look for Mirky
            findPlayer.sendKeys("MirkyWater"); //this will be replaced with the user's SCHandle from the database

            //save the xPath of the season dropdown menu
            String userPopOut = "//*[@id=\"leaderboard-filters\"]/div[3]/div[2]/fieldset[1]/div/div/div";

            //wait until the user is visible as it sometimes takes a moment
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(userPopOut)));

            //click the user
            driver.findElement(By.xpath(userPopOut)).click();



            //Retrieve a list of WebElements (representing a <p>) that contains languages
            //List<WebElement> languageNamesList = driver.findElements(By.xpath("//*[@id=\"page1\"]/div[2]/div[5]/p"));

            //close the window
            //driver.close();

            //iterate through details
            //for (int i = 0; i < languageNamesList.size(); i++){
            //    Bot.sendMessage(message, languageNamesList.get(i).toString());
            //}

        }catch(Exception e){
            Bot.sendMessage(message, "Scraping Error: " + e);
        }





    }
}
