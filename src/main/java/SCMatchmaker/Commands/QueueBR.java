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
    public static void BR_queuing(Message message, User user){
        //check if player exists in database
        if(!SQLServices.existsDiscordID(message)){
            //create the user as a ProfileClass
            ProfileClass player = SQLServices.getBR_Data(message);

            //set the user field in ProfileClass
            player.setUser(user);

            //set the player message field
            player.setMessage(message);

            //check if userInfo came back with this error
            if(player.getHandle().contains("--CONNECTION ERROR--")){ //its an error...
                MessageServices.sendMessage(player.getMessage(), "You do not exist in the database, please use the following command:" +
                        "\n```!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE```");
            }else{ //it was successful...
                message.delete(); //delete the message -- garbage control

                //a boolean to help me figure out if a player exists because I can't think of a better way to do it rn
                boolean playerExists = false;
                String partyPlayerExistsIn = "";


                //make sure player isn't already in ANY list
                for(PartyClass party : Bot.BR_parties){ //check Battle Royal parties
                    String listOfNames = null;
                    for(ProfileClass player_ : party.getPlayers()){
                        listOfNames = listOfNames + player_.getHandle();
                    }
                    if(listOfNames.contains(player.getHandle())){
                        playerExists = true;
                        partyPlayerExistsIn = "Battle Royal.";
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
        }else{
            MessageServices.sendMessage(message, "You either have not created an account or your discord profile" +
                    "has changed, please use either of the following commands accordingly:" +
                    "\nTo sign up:" +
                    "\n```!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE```" +
                    "\n\nTo update your accound:" +
                    "\n```!updateme https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE```");
        }


    }
}