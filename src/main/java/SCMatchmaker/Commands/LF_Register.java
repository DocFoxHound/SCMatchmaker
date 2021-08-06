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

public class LF_Register {
    public static void newUser(Message message){
        //make a new profileclass object
        ProfileClass player = new ProfileClass();

        //set the message field in the player class
        player.setMessage(message);
        player.setDiscordID(message.getUserData().id().toString());
        message.delete();

        //getting the text of the user's command
        player.setCitizenURL(player.getMessage().getContent().toString());

        //take the extra out of the command so that we're left with the URL
        if(player.getCitizenURL().contains("!lf_register")){
            //replace !lf_register with nothing, leaving only the URL
            player.setCitizenURL(player.getCitizenURL().replaceAll("!lf_register", "")); //removes the !newuser part of the string
            player.setCitizenURL(player.getCitizenURL().replaceAll(" ", ""));//removes the !newuser part of the string
        }

        //check that they actually put text/url after the command
        if(player.getCitizenURL().equals("")){
            MessageServices.sendMessage(player.getMessage(), ":small_red_triangle: Please use the new user command with your discord profile" +
                    "link afterwards. It should look similar to the following: \n" +
                    "```!LF_Register https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE```" +
                    "\n:small_red_triangle: **PROCESS ABORTED**");
            return;
        }else{
            //-----------------------------
            //setup the headless browser before we run our scrapers. This makes the scraper faster
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); //driver text
            ChromeOptions options = new ChromeOptions(); //make the chrome driver
            options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors", "--blink-settings=imagesEnabled=false"); //make it headless
            WebDriver driver = new ChromeDriver(options);//initializing the Chrome webbrowser driver
            WebDriverWait wait = new WebDriverWait(driver, 5);//wait 5 seconds before timing out
            //-----------------------------

            //check DataBase for DiscordID
            if (!SQLServices.existsDiscordID(player.getMessage())) { //if the DiscordID doesn't exist, continue adding user
                //this checks if the user exists and returns their handle
                player = ScraperServices.checkPageNewUser(player, driver, wait); //inputs a String and Message, outputs a string list

                //if the userPage is empty then that means the verification process did not finish. Another error is also thrown.
                if(!player.getHandle().isEmpty()){
                    MessageServices.sendMessage(player.getMessage(), ":small_red_triangle: **PROCESS ABORTED**.");
                    //close the browser
                    driver.close();
                    return;
                }
                else{
                    //if userPage has data that means the check finished and the user was verified
                    //this scrapes the leaderboard into a ProfileClass.
                    player = ScraperServices.scrapeBattleRoyal(player, driver, wait);

                    //close the browser
                    driver.close();

                    //process into the database and return the readout to the user
                    MessageServices.sendMessage(player.getMessage(), SQLServices.setBR_Data(player));

                    //and we're done adding a new player
                    MessageServices.sendMessage(player.getMessage(), "**PROCESS COMPLETE**");
                    return;
                }
            }else{//if the DiscordID DOES exist, halt
                //close the browser
                driver.close();
            }
            return;
        }
    }
}
