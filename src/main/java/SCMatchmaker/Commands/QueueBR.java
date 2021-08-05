package SCMatchmaker.Commands;

import SCMatchmaker.MessageServices;
import SCMatchmaker.Models.ProfileClass;
import SCMatchmaker.QueueServices;
import SCMatchmaker.SQLServices;
import SCMatchmaker.ScraperServices;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

//This is the queue method, which will handle a lot of stuff.
public class QueueBR {
    public static void BR_queuing(Message message, User user){
        //-----------------------------Opening a Web Driver to help scrape-----------------------------//
        //setup the headless browser before we run our scrapers. This makes the scraper faster and keeps it asynchronized
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); //driver text
        ChromeOptions options = new ChromeOptions(); //make the chrome driver
        options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors", "--blink-settings=imagesEnabled=false"); //make it headless
        WebDriver driver = new ChromeDriver(options);//initializing the Chrome webbrowser driver
        WebDriverWait wait = new WebDriverWait(driver, 5);//wait 5 seconds before timing out
        //-------------------------------------------------------------------------------------------//

        //going to save the player's data in a profileclass
        ProfileClass player = new ProfileClass();

        //get the username from their text
        if(message.getContent().toString().contains("lfbr")){
            player.setHandle(message.getContent().replace("lfbr ", ""));
        }else if(message.getContent().toString().contains("lf_br")){
            player.setHandle(message.getContent().replace("lf_br ", ""));
        }else{
            MessageServices.sendMessage(message, "An error occurred at line 20 in QueueBR");
            return;
        }

        //setting some variables
        player.setMessage(message);
        player.setUser(user);
        player.setCitizenURL("https://robertsspaceindustries.com/citizens/" + player.getHandle());
        player.setDiscordUsername(message.getUserData().username()+"#"+message.getUserData().discriminator());
        player.setDiscordID(message.getUserData().id().toString());

        //remove garbage
        message.delete();
        user = null;

        //verify user exists
        player = ScraperServices.checkPageNewUser(player, driver, "!LFBR");

        //doing some bools
        boolean ueeNumInDB = player.getUeeCitizenRecord().isEmpty();
        boolean usernameIsDifferent; //TODO check if the username is different

        //we check if the number came back empty. If it did then the process failed
        if(ueeNumInDB == false){ //if it IS NOT empty
            //TODO check if the player exists in the database. If so, return the player with Elo filled in.
            if(SQLServices.existsUEENumber(player.getUeeCitizenRecord()) == true){
                player = SQLServices.getBR_Data(player); //they have their Elo now.

            //TODO if the ueenumber exists but the handle or discordID/username are different, edit the dB
            }else if(true) {

            }else{
                //TODO if the player doesn't exist, populate them in the database and continue code
                //set the player up in the database and return the feed to the user
                MessageServices.sendMessage(player.getMessage(), SQLServices.setBR_Data(player));
            }
            //pass it off to the QueueServices class to process
            QueueServices.BR_queue(player);
            return;
        }else{ //if it is empty then we'll just end the code here
            return;
        }
    }
}