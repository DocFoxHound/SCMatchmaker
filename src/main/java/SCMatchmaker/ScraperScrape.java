package SCMatchmaker;

import discord4j.core.object.entity.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ScraperScrape {
    public static void scrape(Message message){
        try{
            //setting properties for the driver
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            //ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless");
            //options.addArguments("--disable-gpu");
            //options.addArguments("--window-size=1400,800");

            //initializing the firefox webbrowser driver
            ChromeDriver driver = new ChromeDriver();

            //initializing the wait driver (allows things to load) with maximum wait time in int
            WebDriverWait wait = new WebDriverWait(driver, 30);

            //navigate to a website
            driver.navigate().to("https://robertsspaceindustries.com/community/leaderboards/all?mode=BR");

            //click the dropdown button
            Select season = new Select(driver.findElement(By.name("Season")));

            //save the location of the button
            String seasonButton = "//button[text()='v 3.13']";

            //we wait until the button is loaded
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(seasonButton)));

            //click on the button
            driver.findElement(By.xpath(seasonButton)).click();
            //driver.findElement(By.linkText("v 3.13")).click();

            //save the location of the data that we want
            String languagesParagraphXpath = "//*[@id=\"page1\"]/div[2]/div[5]";

            //wait until that location is loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(languagesParagraphXpath)));

            //Retrieve a list of WebElements (representing a <p>) that contains languages
            List<WebElement> languageNamesList = driver.findElements(By.xpath("//*[@id=\"page1\"]/div[2]/div[5]/p"));

            //close the window
            driver.close();

            //iterate through details
            for (int i = 0; i < languageNamesList.size(); i++){
                Bot.sendMessage(message, languageNamesList.get(i).toString());
            }

        }catch(Exception e){
            Bot.sendMessage(message, "Scraping Error: " + e);
        }





    }
}
