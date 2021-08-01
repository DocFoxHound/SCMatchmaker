package SCMatchmaker.Commands;

import SCMatchmaker.Bot;
import SCMatchmaker.Models.PlayerClass;
import SCMatchmaker.QueueServices;
import SCMatchmaker.SQLServices;
import discord4j.core.object.entity.Message;

import java.util.List;

//This is the queue method, which will handle a lot of stuff.
public class QueueBR {
    public static void queuing(Message message){
        //make the player
        PlayerClass player = new PlayerClass();
        player.setDiscordID(message.getUserData().id().toString());
        player.setMessage(message);

        //match it with the database and get the handle, ELO
        List<String> userInfo = SQLServices.getBattleRoyal(player.getDiscordID());

        //check if userInfo came back with two data fields or one. One indicates an error
        if(userInfo.size() == 1){ //its an error...
            Bot.sendMessage(message, userInfo.get(0));
        }
        else{ //it was successful...
            //return the handle and ELO
            player.setHandle(userInfo.get(0)); //handle
            player.setElo(Double.parseDouble(userInfo.get(1))); //elo
            userInfo.clear(); //clear the list

            //tell whats going on
            Bot.sendMessage(message, "Profile acquired, entering Battle Royal queue...");
            message.delete(); //delete the message

            //insert player into BRqueue
            QueueServices.BRqueue(player);
        }
    }
}