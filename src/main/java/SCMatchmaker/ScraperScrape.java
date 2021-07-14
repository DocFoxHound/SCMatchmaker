package SCMatchmaker;

import discord4j.core.object.entity.Message;
import discord4j.discordjson.Id;

public class ScraperScrape {
    public static void scrape(Message message){
        String userName = message.getUserData().username();
        Id userID = message.getUserData().id();
        message.getChannel().block().createMessage("Username is... " + userName.toString()).block();
        message.getChannel().block().createMessage("UserID is... " + userID.toString()).block();
    }
}
