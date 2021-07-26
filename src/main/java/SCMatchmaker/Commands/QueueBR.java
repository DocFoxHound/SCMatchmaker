package SCMatchmaker.Commands;

import SCMatchmaker.Bot;
import SCMatchmaker.Models.ProfileClass;
import SCMatchmaker.SQLServices;
import discord4j.core.object.entity.Message;

import java.util.List;

//This is the queue method, which will handle a lot of stuff.
public class QueueBR {
    public static void queuing(Message message){
        //make the player
        ProfileClass user = new ProfileClass();
        user.setDiscordID(message.getUserData().id().toString());

        //match it with the database and get the handle, ELO
        List<String> userInfo = SQLServices.getBattleRoyal(user.getDiscordID());

        //check if userInfo came back with two data fields or one. One indicates an error
        if(userInfo.size() == 1){ //its an error...
            Bot.sendMessage(message, userInfo.get(0));
        }
        else{ //it was successful...
            //return the handle and ELO
            user.setHandle(userInfo.get(0)); //handle
            user.setBR_ELO(Double.parseDouble(userInfo.get(1))); //elo

            Bot.sendMessage(message, "Profile acquired, entering Battle Royal queue...");
        }

        //TODO pass off to queueing service
        // which needs the following:
        // 1. A way to store and retrieve the channels players sent messages from
        // 2. A way to sift through the active seekers to find those LFG
        // 3. A way to notify all users of the matchmaking status

    }
}