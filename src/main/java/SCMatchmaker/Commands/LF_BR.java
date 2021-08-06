package SCMatchmaker.Commands;

import SCMatchmaker.Bot;
import SCMatchmaker.MessageServices;
import SCMatchmaker.Models.ProfileClass;
import SCMatchmaker.QueueServices;
import SCMatchmaker.SQLServices;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class LF_BR {
    public static void LFBR(Message message, User user){
        //create the player ProfileClass to load our data into
        ProfileClass player = new ProfileClass();
        player.setMessage(message);
        player.setUser(user); //this is required for sending private messages
        player.setDiscordID(message.getUserData().id().toString());
        player.setDiscordUsername(message.getUserData().username() + "#" + message.getUserData().discriminator());

        //garbage
        message.delete();
        user = null;

        //check if player exists in database
        player = SQLServices.searchByDiscordId(player);
        boolean playerExistsInDB = !player.getHandle().isEmpty();

        if(playerExistsInDB == true){//if the player exists, start a queue
            //get the player's database data
            player = SQLServices.getBR_Data(player);

            //Check if there are any parties in existence already. If the list isn't empty, then we iterate through the list
            if(!Bot.BR_parties.isEmpty()){
                //iterate through the list
                for(int i = 0; i<Bot.BR_parties.size();){
                    //check if a party exists with the elo range of the player and that the party isn't full
                    if((player.getBR_ELO() >= Bot.BR_parties.get(i).getEloMinimum()) &&
                            (player.getBR_ELO() <= Bot.BR_parties.get(i).getEloMax()) &&
                            Bot.BR_parties.get(i).getPlayers().size() < 10)
                    {
                        Bot.BR_parties.get(i).addPlayer(player);
                        MessageServices.sendMessage(player.getMessage(), "You have successfully queued for Battle Royal.");
                        return;
                        //if we find a party that fits BUT it already has 10 players already
                    }else if((player.getBR_ELO() >= Bot.BR_parties.get(i).getEloMinimum()) &&
                            (player.getBR_ELO() <= Bot.BR_parties.get(i).getEloMax()) &&
                            Bot.BR_parties.get(i).getPlayers().size() >= 10){

                        //launch the 10 player party
                        QueueServices.BR_launchParty(i);

                        //start a new party
                        QueueServices.BR_createParty(player);

                        //exit iterator
                        return;
                    }else if(i+1 == Bot.BR_parties.size()){//if we are at the end of the list, that means no parties were found
                        //start a new party
                        QueueServices.BR_createParty(player);

                        //exit iterator
                        return;
                    }else{//if we aren't at the end of the list, just keep iterating
                        i++;
                    }
                }
            }else{//if there are no parties, we will make one with our player and fire it off
                QueueServices.BR_createParty(player);
                return;
            }
            return;
        }else{//if they don't exist, they need to be entered
            MessageServices.sendMessage(player.getMessage(), "You need to register to use this bot. In order" +
                    "to register, please use the following command:" +
                    "\n```!LF_Register https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE```");
            return;
        }
    }
}
