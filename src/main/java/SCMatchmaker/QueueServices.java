package SCMatchmaker;

import SCMatchmaker.Models.PartyClass;
import SCMatchmaker.Models.PlayerClass;

import java.util.List;

public class QueueServices {
    public static void BRqueue(PlayerClass player){
        //Check if there are any parties in existence already
        if(Bot.BRparties.size() > 0){
            //Search for an existing party near the player's ELO
            for(int i = 0; i<Bot.BRparties.size(); i++){
                //if a party exists within the player's window of ELO (400 points) then we add them to the party
                if((Bot.BRparties.get(i).getEloStart() > (player.getElo() - 200)) && (Bot.BRparties.get(i).getEloStart() < ((player.getElo()) + 200)))
                {
                    //if the player isn't already in the list, add them
                    if(!Bot.BRparties.get(i).getPlayers().contains(player)){
                        Bot.BRparties.get(i).addPlayer(player);
                        Bot.sendMessage(player.getMessage(), "You have successfully queued for Battle Royal.");
                    }else{
                        Bot.sendMessage(player.getMessage(), "You have already queued.");
                    }
                }
                //if no party has the elo area for the player, create one

            }
        }else{
            //make a list of our one player
            List<PlayerClass> players = null;
            players.add(player);

            //create a party if there are none
            PartyClass newParty = new PartyClass(players, player.getElo(), System.currentTimeMillis());

            //add the new party to the BRparties list
            Bot.BRparties.add(newParty);
        }


    }
}
