package SCMatchmaker.Commands;

import SCMatchmaker.Bot;
import SCMatchmaker.MessageServices;
import SCMatchmaker.Models.PartyClass;
import SCMatchmaker.Models.ProfileClass;
import SCMatchmaker.QueueServices;
import SCMatchmaker.SQLServices;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

//This is the queue method, which will handle a lot of stuff.
public class QueueBR {
    public static void queuing(Message message, User user){
        /*//make the player
        ProfileClass player = new ProfileClass();
        player.setDiscordID(message.getUserData().id().toString());
        player.setMessage(message);

        //match it with the database and get the handle, ELO
        List<String> userInfo = SQLServices.getBR_Data(player.getDiscordID());

         */

        //create the user as a ProfileClass
        ProfileClass player = SQLServices.getBR_Data(message);

        //set the user field in ProfileClass
        player.setUser(user);

        //check if userInfo came back with this error
        if(player.getHandle().contains("--CONNECTION ERROR--")){ //its an error...
            MessageServices.sendMessage(message, "There was a database connection error.");
        }
        else{ //it was successful...
            message.delete(); //delete the message -- garbage control

            //a boolean to help me figure out if a player exists because I can't think of a better way to do it rn
            boolean playerExists = false;
            String partyPlayerExistsIn = "";

            //make sure player isn't already in ANY list
            for(PartyClass party : Bot.BR_parties){ //check Battle Royal parties
                if(party.getPlayers().contains(player)){
                    playerExists = true;
                    partyPlayerExistsIn = "Battle Royal.";
                    break;
                }
            }
            for(PartyClass party : Bot.SB_parties){ //check Squadron Battle parties
                if(party.getPlayers().contains(player)){
                    playerExists = true;
                    partyPlayerExistsIn = "Squadron Battle.";
                    break;
                }
            }
            for(PartyClass party : Bot.D_parties){ //check Duel parties
                if(party.getPlayers().contains(player)){
                    playerExists = true;
                    partyPlayerExistsIn = "Duel.";
                    break;
                }
            }

            //okay. NOW if the player exists in a list, we say so and end.
            if (playerExists == true){
                MessageServices.sendMessage(message, "You already exist in a queue for: " + partyPlayerExistsIn);
                return;

            //otherwise, if the player isn't in a list, we go ahead and shuffle off to the BRqueue function in QueueServices
            }else{
                //tell whats going on
                MessageServices.sendMessage(message, "Profile acquired, entering Battle Royal queue...");

                //insert player into BRqueue
                QueueServices.BR_queue(player);
                return;
            }
        }
    }
}