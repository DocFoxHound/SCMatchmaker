package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.util.Locale;


public class CommandService {
    public static void onMessage(Message message){
        //if the message (passed onto this method from Bot.java) starts with cmdChar(also passed from Bot.java),
        //then we do commands and stuff.
        if (message.getContent().toLowerCase(Locale.ROOT).startsWith(String.valueOf(Bot.cmdChar()))) {
            //removes the command character from the front of the string. In this case, it removes the "!" char
            String command = message.getContent().toString().substring(1);

            //recognizes a command and then does something with it.
            if (command.startsWith("test")){
                Bot.sendMessage(message, "Bed!");
            }
            else if(command.startsWith("ping")){
                Bot.sendMessage(message, "Pong!");
            }
            else if(command.startsWith("help")){
                Bot.sendMessage(message, "This is the help menu, which is not yet developed.");
            }
            //here we are recognizing the queue command and passing off the message data to another method to handle.
            else if(command.startsWith("queue")){
                Bot.sendMessage(message, "//Testing getting info from the database...");
                QueueService.queuing(message);
            }
            else if(command.startsWith("scrape")){
                Bot.sendMessage(message, "//Testing some scraping...");
                ScraperScrape.scrape(message);
            }
            else{
                Bot.sendMessage(message, "Command unrecognized.");
            }
        }
        else{
            return;
        }
    }
}
