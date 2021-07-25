package SCMatchmaker.Commands;

import SCMatchmaker.Bot;
import SCMatchmaker.Models.ProfileClass;
import SCMatchmaker.SQLServices;
import SCMatchmaker.ScraperServices;
import discord4j.core.object.entity.Message;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NewUser {
    public static void newUser(Message message){
        //make a new profileclass object
        ProfileClass user = new ProfileClass();

        //set the message in the user object
        user.setMessage(message);
        message = null;

        //assign the discordID
        user.setDiscordID(user.getMessage().getUserData().id().toString());

        //Get the userID of who is calling the command
        //String userDiscordID = message.getUserData().id().toString();
        user.setDiscordID(user.getMessage().getUserData().id().toString()); //using the profileclass in parallel while testing

        //getting the text of the user's command
        user.setCitizenURL(user.getMessage().getContent().toString());

        //replace !newuser with nothing, leaving only the URL
        user.setCitizenURL(user.getCitizenURL().replaceAll("!newuser", "")); //removes the !newuser part of the string
        user.setCitizenURL(user.getCitizenURL().replaceAll(" ", ""));//removes the !newuser part of the string

        //check that they actually put text/url after the command
        if(user.getCitizenURL().equals("")){
            Bot.sendMessage(user.getMessage(), ":small_red_triangle: Please use the new user command with your discord profile" +
                    "link afterwards. It should look similar to the following: \n" +
                    "``!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE``" +
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

            //check DataBase for DiscordID
            if (SQLServices.existsDiscordID(user.getDiscordID(), user.getMessage()) == false) { //if the DiscordID doesn't exist, continue adding user
                //this checks if the user exists and returns their handle
                List<String> userPage = ScraperServices.checkPageNewUser(user.getCitizenURL(), user.getMessage(), driver, wait); //inputs a String and Message, outputs a string list

                //if the userPage is empty then that means the verification process did not finish. Another error is also thrown.
                if(userPage.size()==0){
                    Bot.sendMessage(user.getMessage(), ":small_red_triangle: **PROCESS ABORTED**.");
                    //close the browser
                    driver.close();
                    return;
                }
                else{//if userPage has data that means the check finished and the user was verified
                    //set the user's handle
                    user.setHandle(userPage.get(0));

                    //this scrapes the leaderboard into a List of Strings. Inputs Message and a String (Star Citizen Handle)
                    List<String> BR_leaderboard = ScraperServices.scrapeBattleRoyal(user.getMessage(), user.getHandle(), driver, wait);
                    List<String> SB_leaderboard = ScraperServices.scrapeSquadronBattle(user.getMessage(), user.getHandle(), driver, wait);
                    //List<String> D_leaderboard = ScraperScrape.scrapeDuel(user.getMessage(), user.getHandle());

                    //close the browser
                    driver.close();

                    //loading the user object
                    user.setUeeCitizenRecord(userPage.get(1));
                    user.setDiscordUsername(user.getMessage().getUserData().username());
                    user.setOrgID(userPage.get(2));
                    //Battle Royal
                    user.setBR_Rating(Integer.parseInt(BR_leaderboard.get(0)));
                    user.setBR_ScoreMinute(Double.parseDouble(BR_leaderboard.get(1)));
                    user.setBR_Score(Long.parseLong(BR_leaderboard.get(2)));
                    user.setBR_DamageRatio(Double.parseDouble(BR_leaderboard.get(3)));
                    user.setBR_KillDeath(Double.parseDouble(BR_leaderboard.get(4)));
                    user.setBR_Kills(Integer.parseInt(BR_leaderboard.get(13)));
                    user.setBR_Deaths(Integer.parseInt(BR_leaderboard.get(14)));
                    user.setBR_Playtime(Integer.parseInt(BR_leaderboard.get(5)));
                    user.setBR_ELO(10.00); //we need an equation for ELO
                    user.setBR_DamageDealt(Long.parseLong(BR_leaderboard.get(12)));
                    user.setBR_DamageTaken(Long.parseLong(BR_leaderboard.get(11)));
                    user.setBR_Matches(Integer.parseInt(BR_leaderboard.get(6)));
                    user.setBR_AvgMatch(Integer.parseInt(BR_leaderboard.get(7)));
                    user.setBR_Wins(Integer.parseInt(BR_leaderboard.get(8)));
                    user.setBR_Losses((Integer.parseInt(BR_leaderboard.get(9))));
                    //Squadron Battle
                    user.setSB_Rating(Integer.parseInt(SB_leaderboard.get(0)));
                    user.setSB_ScoreMinute(Double.parseDouble(SB_leaderboard.get(1)));
                    user.setSB_Score(Long.parseLong(SB_leaderboard.get(2)));
                    user.setSB_DamageRatio(Double.parseDouble(SB_leaderboard.get(3)));
                    user.setSB_KillDeath(Double.parseDouble(SB_leaderboard.get(4)));
                    user.setSB_Kills(Integer.parseInt(SB_leaderboard.get(13)));
                    user.setSB_Deaths(Integer.parseInt(SB_leaderboard.get(14)));
                    user.setSB_Playtime(Integer.parseInt(SB_leaderboard.get(5)));
                    user.setSB_ELO(10.00); //we need an equation for ELO
                    user.setSB_DamageDealt(Long.parseLong(SB_leaderboard.get(12)));
                    user.setSB_DamageTaken(Long.parseLong(SB_leaderboard.get(11)));
                    user.setSB_Matches(Integer.parseInt(SB_leaderboard.get(6)));
                    user.setSB_AvgMatch(Integer.parseInt(SB_leaderboard.get(7)));
                    user.setSB_Wins(Integer.parseInt(SB_leaderboard.get(8)));
                    user.setSB_Losses((Integer.parseInt(SB_leaderboard.get(9))));
                    //Duel
                    /*
                    user.setD_Rating(Integer.parseInt(D_leaderboard.get(0)));
                    user.setD_ScoreMinute(Double.parseDouble(D_leaderboard.get(1)));
                    user.setD_Score(Long.parseLong(D_leaderboard.get(2)));
                    user.setD_DamageRatio(Double.parseDouble(D_leaderboard.get(3)));
                    user.setD_KillDeath(Double.parseDouble(D_leaderboard.get(4)));
                    user.setD_Kills(Integer.parseInt(D_leaderboard.get(13)));
                    user.setD_Deaths(Integer.parseInt(D_leaderboard.get(14)));
                    user.setD_Playtime(Integer.parseInt(D_leaderboard.get(5)));
                    //user.setD_ELO(10.00); //we need an equation for ELO
                    user.setD_DamageDealt(Long.parseLong(D_leaderboard.get(12)));
                    user.setD_DamageTaken(Long.parseLong(D_leaderboard.get(11)));
                    user.setD_Matches(Integer.parseInt(D_leaderboard.get(6)));
                    user.setD_AvgMatch(Integer.parseInt(D_leaderboard.get(7)));
                    user.setD_Wins(Integer.parseInt(D_leaderboard.get(8)));
                    user.setD_Losses((Integer.parseInt(D_leaderboard.get(9))));*/

                    //remove lists
                    BR_leaderboard = null;
                    userPage = null;

                    //process the database and return the readout to the user
                    Bot.sendMessage(user.getMessage(), SQLServices.populateACBattleRoyal(user));
                    Bot.sendMessage(user.getMessage(), SQLServices.populateACSquadronBattle(user));
                    //Bot.sendMessage(user.getMessage(), SQLServices.populateACDuel(user));

                    Bot.sendMessage(user.getMessage(), "**PROCESS COMPLETE**");
                }
                return;
            }else{//if the DiscordID DOES exist, halt
                //close the browser
                driver.close();
                return;
            }
        }
    }
}
