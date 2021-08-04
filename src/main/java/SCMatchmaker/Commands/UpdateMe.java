package SCMatchmaker.Commands;

import SCMatchmaker.MessageServices;
import SCMatchmaker.Models.ProfileClass;
import SCMatchmaker.SQLServices;
import SCMatchmaker.ScraperServices;
import discord4j.core.object.entity.Message;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UpdateMe {
    public static void UpdateMe(Message message){
        //make a new profileclass object
        ProfileClass player = new ProfileClass();

        //set the message field in the player class
        player.setMessage(message);
        player.setDiscordID(message.getUserData().id().toString());

        //getting the text of the user's command
        player.setCitizenURL(player.getMessage().getContent().toString());

        //replace !newuser with nothing, leaving only the URL
        player.setCitizenURL(player.getCitizenURL().replaceAll("!updateme", "")); //removes the !newuser part of the string
        player.setCitizenURL(player.getCitizenURL().replaceAll(" ", ""));//removes the !newuser part of the string

        //check that they actually put text/url after the command
        if(player.getCitizenURL().equals("")){
            MessageServices.sendMessage(player.getMessage(), ":small_red_triangle: Please use the new user command with your discord profile" +
                    "link afterwards. It should look similar to the following: \n" +
                    "```!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE```" +
                    "\n:small_red_triangle: **PROCESS ABORTED**");
        }else{
            //-----------------------------
            //setup the headless browser before we run our scrapers. This makes the scraper faster
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); //driver text
            ChromeOptions options = new ChromeOptions(); //make the chrome driver
            options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors", "--blink-settings=imagesEnabled=false"); //make it headless
            WebDriver driver = new ChromeDriver(options);//initializing the Chrome webbrowser driver
            WebDriverWait wait = new WebDriverWait(driver, 5);//wait 5 seconds before timing out
            //-----------------------------

            //this checks if the user exists and returns their handle
            List<String> userPage = ScraperServices.checkPageUpdateMe(player.getCitizenURL(), player.getMessage(), driver, wait); //inputs a String and Message, outputs a string list

            //if the userPage is empty then that means the verification process did not finish. Another error is also thrown.
            if(userPage.size()==0){
                MessageServices.sendMessage(player.getMessage(), ":small_red_triangle: **PROCESS ABORTED**.");
                //close the browser
                driver.close();
                return;
            }else{
                //if userPage has data that means the check finished and the user was verified
                //this scrapes the leaderboard into a ProfileClass.
                player = ScraperServices.scrapeBattleRoyal(message, userPage.get(0), driver, wait);

                //close the browser
                driver.close();

                //loading the user object
                player.setHandle(userPage.get(0));
                player.setUeeCitizenRecord(userPage.get(1));
                player.setDiscordUsername(player.getMessage().getUserData().username()+"#"+player.getMessage().getUserData().discriminator());
                player.setOrgID(userPage.get(2));

                userPage = null;

                //process the database and return the readout to the user
                MessageServices.sendMessage(player.getMessage(), SQLServices.updateAlldBData(player));

                MessageServices.sendMessage(player.getMessage(), "**PROCESS COMPLETE**");
            }
            return;
        }
    }
}
