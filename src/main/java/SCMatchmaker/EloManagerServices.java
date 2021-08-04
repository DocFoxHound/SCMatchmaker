package SCMatchmaker;

import SCMatchmaker.Models.PartyClass;
import SCMatchmaker.Models.ProfileClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class EloManagerServices {
    public static void BR_EloManager(PartyClass party){
        //setup some constants to use later
        double sumElo = 0;
        double lowestPartyElo = 0;
        double highestPartyElo = 0;
        for(ProfileClass player : party.getPlayers()){
            sumElo = sumElo + player.getBR_ELO();
            if(player.getBR_ELO() < lowestPartyElo){
                lowestPartyElo = player.getBR_ELO();
            }
            if(player.getBR_ELO() > highestPartyElo){
                highestPartyElo = player.getBR_ELO();
            }
        }
        int numOfPlayers = party.getPlayers().size();
        double averagePartyElo = sumElo / numOfPlayers;

        //the sleep command needs to be in a Try-Catch. I hate that. How could it fail??
        try {
            //wait for 20 minutes
            TimeUnit.MINUTES.sleep(20);

            //-----------------------------
            //setup the headless browser before we run our scrapers. This makes the scraper faster and keeps it asynchronized
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); //driver text
            ChromeOptions options = new ChromeOptions(); //make the chrome driver
            options.addArguments("--headless", "--disable-gpu", "--ignore-certificate-errors", "--blink-settings=imagesEnabled=false"); //make it headless
            WebDriver driver = new ChromeDriver(options);//initializing the Chrome webbrowser driver
            WebDriverWait wait = new WebDriverWait(driver, 5);//wait 5 seconds before timing out
            //-----------------------------

            //iterate through the list of players we started with
            for(ProfileClass oldPlayerStats : party.getPlayers()){
                //grab the new stats since the end of the last round
                ProfileClass newPlayerStats = ScraperServices.scrapeBattleRoyal(oldPlayerStats.getMessage(),oldPlayerStats.getHandle(),driver, wait);

                //check if the player even played the match
                if (oldPlayerStats.getBR_Playtime() != newPlayerStats.getBR_Playtime()){
                    //adjust the player's Elo according to the Elo modifier
                    newPlayerStats.setBR_ELO(oldPlayerStats.getBR_ELO() + calculateMatchElo(newPlayerStats, oldPlayerStats, lowestPartyElo, highestPartyElo, averagePartyElo));
                    MessageServices.sendMessage(oldPlayerStats.getMessage(), SQLServices.setBR_Elo(newPlayerStats));
                }
            }
            //end the thread
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    //calculating the Elo with the average match Elo
    public static double calculateMatchElo(ProfileClass newPlayerStats, ProfileClass oldPlayerStats, double lowestPartyElo, double highestPartyElo, double averagePartyElo){
        double kills = newPlayerStats.getBR_Kills() - oldPlayerStats.getBR_Kills();
        double deaths = newPlayerStats.getBR_Deaths() - oldPlayerStats.getBR_Deaths();
        double differenceEloScale = highestPartyElo - lowestPartyElo; //makes a scale between the lowest and highest Elo scores
        double playersModifiedElo = oldPlayerStats.getBR_ELO() - lowestPartyElo; //reduces the player's score to fit on the scale
        double playersEloRatingInParty = playersModifiedElo/differenceEloScale; //returns a decimal (.01-1.00) percentage of where the player sits within the party
        //double damagePerKill = newPlayerStats.getBR_DamageDealt() - oldPlayerStats.getBR_DamageDealt();
        int timeInMatch = newPlayerStats.getBR_Playtime() - oldPlayerStats.getBR_Playtime();
        double kdRatio = kills/deaths;
        double result = 0;

        //if they had a negative KD ratio...
        if(kdRatio<1){
            //and was in the top 80% of players in the party, they lose full points. etc.
            if(playersEloRatingInParty>80){
                result = -100;
            }else if(playersEloRatingInParty>60 && playersEloRatingInParty<79){
                result = -70;
            }else if(playersEloRatingInParty>40 && playersEloRatingInParty<59){
                result = -50;
            }else if(playersEloRatingInParty>20 && playersEloRatingInParty<39){
                result = -30;
            }else if(playersEloRatingInParty>00 && playersEloRatingInParty<19){
                result = -10;
            }
        //if they had a positive KD ratio...
        }else if (kdRatio>1){
            //and was in the top 80% of players in the party, don't get 1 point. etc.
            if(playersEloRatingInParty>80){
                result = 10;
            }else if(playersEloRatingInParty>60 && playersEloRatingInParty<79){
                result = 30;
            }else if(playersEloRatingInParty>40 && playersEloRatingInParty<59){
                result = 50;
            }else if(playersEloRatingInParty>20 && playersEloRatingInParty<39){
                result = 70;
            }else if(playersEloRatingInParty>00 && playersEloRatingInParty<19){
                result = 100;
            }
        }

        //now for the time bonus. If you stay in the match longer, the more points you get.
        if(timeInMatch > 540){ //540 seconds is 9 minutes, going to do this per minute
            result = result + 25;
        }else if(timeInMatch > 480 && timeInMatch < 539){ //8 minutes
            result = result + 20;
        }else if(timeInMatch > 420 && timeInMatch < 479){ //7 minutes
            result = result + 15;
        }else if(timeInMatch > 360 && timeInMatch < 419){ //6 minutes
            result = result + 10;
        }else if(timeInMatch > 300 && timeInMatch < 359){ //5 minutes
            result = result + 0;
        }else if(timeInMatch > 240 && timeInMatch < 299){ //4 minutes
            result = result - 10;
        }else if(timeInMatch > 180 && timeInMatch < 239){ //3 minutes
            result = result - 20;
        }else if(timeInMatch > 120 && timeInMatch < 179){ //2 minutes
            result = result - 30;
        }else if(timeInMatch > 60 && timeInMatch < 119){ //2 minutes
            result = result - 40;
        }else if(timeInMatch > 0 && timeInMatch < 59){ //1 minute
            result = result - 50;
        }

        return result;
    }

    //calculating the starting Elo
    public static Double calculateInitialElo(){
        double results = SQLServices.BR_EloMedian();
        return results;
    }
}