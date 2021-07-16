package SCMatchmaker;

import discord4j.core.object.entity.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

//verifies that a userpage does, in fact, exist, and returns a list of some details
public class CheckProfilePage {
    public static List<String> checkPage(String url, Message message){

        //setting properties for the headless driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1400,800", "--ignore-certificate-errors");

        //initializing the Chrome webbrowser driver
        WebDriver driver = new ChromeDriver(options);

        //initializing the wait driver (allows things to load) with maximum wait time in int
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //place the results in an array list
        List<String> resultsArray = new ArrayList<>();

        //put it all in a try-catch
        try{
            //validating the URL
            boolean validURL;
            if(url.startsWith("http")){
                validURL = true;
            }else{
                validURL = false;
            }

            //navigate to a website
            driver.navigate().to(url);

            //checking if there's a 404 error and if the verified text is in the Bio
            Boolean isRealUser = driver.findElements(By.cssSelector("#public-profile > div.profile-content.overview-content.clearfix > p > span")).size() > 0;

            //does the person's discordID appear on page?
            //Boolean hasNumber = driver.getPageSource().toLowerCase(Locale.ROOT).contains(message.getId().toString());
            Boolean hasNumber;
            String userDiscordId = message.getUserData().id().toString();
            String bioSection = driver.findElement(By.cssSelector("#public-profile > div.profile-content.overview-content.clearfix > div.right-col > div > div > div")).getText();
            if (bioSection.contains(userDiscordId)){
                hasNumber = true;
            }else{
                hasNumber = false;
            }
            //hasNumber = (driver.findElement(By.cssSelector("#public-profile > div.profile-content.overview-content.clearfix > div.right-col > div > div > div")).getText()==message.getUserData().id().toString());

            if(validURL==true && isRealUser==true && hasNumber==true){
                //get their handle
                String handle = driver.findElement(By.cssSelector("#public-profile > div.profile-content.overview-content.clearfix > div.box-content.profile-wrapper.clearfix > div > div.profile.left-col > div > div.info > p:nth-child(2) > strong")).getText();

                //get their UEE record number
                String ueeCitRecord = driver.findElement(By.cssSelector("#public-profile > div.profile-content.overview-content.clearfix > p > strong")).getText().replaceAll("#", "");; //it literally pulls the hashtag with it, so you gotta dump it.

                //get their Org SID
                String orgSID = driver.findElement(By.cssSelector("#public-profile > div.profile-content.overview-content.clearfix > div.box-content.profile-wrapper.clearfix > div > div.main-org.right-col.visibility-V > div > div.info > p:nth-child(2) > strong")).getText();

                //update user and close driver
                Bot.sendMessage(message, "Profile Page verified...");
                driver.close();

                //place the results in an array list
                resultsArray.add(handle); //index: 0
                resultsArray.add(ueeCitRecord); //index: 1
                resultsArray.add(orgSID); //index: 2

                return resultsArray;
            }else{
                Bot.sendMessage(message, "There was an error, please check your URL and ensure the following text " +
                        "is located inside your StarCitizen Bio:");
                Bot.sendMessage(message, message.getUserData().id().toString());
                driver.close();
            }
        }catch(Exception e){
            Bot.sendMessage(message, "The URL was invalid, please double-check and try again. It should look like the following:");
            Bot.sendMessage(message, "!newuser https://robertsspaceindustries.com/citizens/**YOUR_HANDLE_HERE**");

            resultsArray.add("");
            return resultsArray;
        }
        return resultsArray;
    }
}
