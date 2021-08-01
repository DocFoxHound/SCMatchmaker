package SCMatchmaker;

import SCMatchmaker.Models.PartyClass;
import SCMatchmaker.Models.PlayerClass;

import java.util.List;

public class QueueServices {
    public static void BRqueue(PlayerClass player){
        //Check if there are any parties in existence already. If the list isn't empty, then we iterate through the list
        if(!Bot.BRparties.isEmpty()){
            //iterate through the list
            for(int i = 0; i<Bot.BRparties.size();){
                //if a party exists within the player's window of ELO (400 points) then we add them to the party
                if((player.getElo() >= Bot.BRparties.get(i).getEloMinimum()) && (player.getElo() < Bot.BRparties.get(i).getEloMax()))
                {
                        Bot.BRparties.get(i).addPlayer(player);
                        Bot.sendMessage(player.getMessage(), "You have successfully queued for Battle Royal.");
                        return;
                //create a party
                }else if((i+1) > Bot.BRparties.size()){
                    //create a party
                    //make a list of our one player
                    List<PlayerClass> players = null;
                    players.add(player);

                    //create a party if there are none
                    PartyClass newParty = new PartyClass(players, player.getElo(), player.getElo()+200, player.getElo()-199, System.currentTimeMillis());

                    //add the new party to the BRparties list
                    Bot.BRparties.add(newParty);

                    //send a message
                    Bot.sendMessage(player.getMessage(), "You have successfully queued for Battle Royal (PARTY LEADER)");

                    //exit iterator
                    return;
                //if we aren't at the end of the list, just keep iterating
                }else{
                    i++;
                }
                //if no party has the elo area for the player, create one

            }
        }else{
            //make a list of our one player
            List<PlayerClass> players = null;
            players.add(player);

            //create a party if there are none
            PartyClass newParty = new PartyClass(players, player.getElo(), player.getElo()+200, player.getElo()-199, System.currentTimeMillis());

            //add the new party to the BRparties list
            Bot.BRparties.add(newParty);

            //send a message
            Bot.sendMessage(player.getMessage(), "You have successfully queued for Battle Royal (PARTY LEADER)");
        }


    }
}
