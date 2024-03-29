package SCMatchmaker;

import SCMatchmaker.Models.PartyClass;
import SCMatchmaker.Models.ProfileClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QueueServices {
    public static void BR_launchParty(int i){
        //make a copy of the party at that location
        PartyClass party = Bot.BR_parties.get(i);

        //starting the asynchronous Elo Manager function
        new Thread (() -> {
            EloManagerServices.BR_EloManager(party);
        }).start();
        System.out.printf("Battle Royal Elo Manager online.\n");

        //make a list of players
        String stringOfPlayers = new String();
        for(ProfileClass player : party.getPlayers()){
            stringOfPlayers = stringOfPlayers + "\n:small_blue_diamond: " + player.getHandle();
        }

        //send the list of names to the queued player
        for(ProfileClass player : party.getPlayers()){
            MessageServices.sendPrivateMessage(player.getUser(), "**MATCHMAKING COMPLETE**" +
                    "\nThe party leader is: **" + Bot.BR_parties.get(i).getPartyLeader() + "**" +
                    "\nAs the Party Leader, **" + Bot.BR_parties.get(i).getPartyLeader() + "** is responsible for adding " +
                    "and inviting all the listed players in Star Citizen to a Battle Royal game. All party members should " +
                    "add **" + Bot.BR_parties.get(i).getPartyLeader() + "** as a friend to help them out." +
                    "\nList of members:" + stringOfPlayers);
        }

        //remove the party from the master list
        Bot.BR_parties.remove(i);
    }

    public static void BR_createParty(ProfileClass player){
        //make a list of our one player
        List<ProfileClass> players = new ArrayList<>();
        players.add(player);

        //create a party
        PartyClass newParty = new PartyClass(players, player.getBR_ELO(), player.getBR_ELO()+200, player.getBR_ELO()-200, System.currentTimeMillis(), player);
        newParty.setPartyLeader(player);

        //add the new party to the BR_parties list
        Bot.BR_parties.add(newParty);

        //send a message
        MessageServices.sendMessage(player.getMessage(), "You have successfully queued for Battle Royal.");
    }

    public static void BR_queuePartyManager(){
        //constants
        int BR_PartySizeLimit = 10;

        //for whatever reason it makes me do a sleep in try-catch... whatever
        while(true){
            try {
                //we'll check on the parties every so often
                TimeUnit.SECONDS.sleep(5);

                //get this iterations date/time
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                //if parties do in fact exist, then we will manage them
                if(Bot.BR_parties.size() > 0){
                    //a little counter/heartbeat that shows me how many active parties there are
                    System.out.printf("\nNumber of BR_Parties: " + Bot.BR_parties.size() + " at: " + formatter.format(date));

                    //for every party within the list, we will manage them
                    for(int i = 0; i < Bot.BR_parties.size();){
                        /* //this is all good stuff, but I only need it as a reference
                        List<ProfileClass> players = new ArrayList<ProfileClass>();
                        players = Bot.BR_parties.get(i).getPlayers();
                        System.out.printf("\nParty: " + i);
                        System.out.printf("\nMembers in Party: ");
                        for (ProfileClass player : players){
                            //do a thing per player
                            System.out.printf("\n" + player.getHandle());
                        }
                        System.out.printf("\n\n");
                        */

                        //get the amount of players
                        int amountOfPlayers = Bot.BR_parties.get(i).getPlayers().size();

                        //get how long a party has been in queue
                        long partyLife = (System.currentTimeMillis() - Bot.BR_parties.get(i).getTimeStarted())/1000;

                        //get te elo start for this party
                        double eloStart = Bot.BR_parties.get(i).getEloStart();

                        //if there are so many players in a party, launch the party
                        if (amountOfPlayers >= BR_PartySizeLimit){
                            BR_launchParty(i);
                            i--;
                            //if the life of the party is over 300 seconds (5 minutes), launch the party
                        }else if(partyLife > 30){
                            BR_launchParty(i);
                            i--;
                        }else{
                            //------------------------
                            //MANAGEMENT STUFF
                            //------------------------

                            //------
                            //FIRST: Party Touching
                            //------
                            //if two parties touch, absorb smaller if they can fit the players by age, otherwise limit expansion
                            //here, we are iterating through a list of parties again, minus ours
                            for(int x = 0; x < Bot.BR_parties.size();){
                                //if its our party, don't compare them
                                if(x == i){
                                    break;
                                }else{
                                    double otherEloMax = Bot.BR_parties.get(x).getEloMax();
                                    double otherEloMin = Bot.BR_parties.get(x).getEloMinimum();

                                    //check if the party touches another party on the upper side
                                    if(((Bot.BR_parties.get(i).getEloMax() - otherEloMin) <= 0) &&
                                            (Bot.BR_parties.get(i).getEloMax() + 100 + partyLife) >= otherEloMin){
                                        //printing the return success or failure from combining the parties
                                        System.out.printf(QueueServices.BR_combineParties(i, x, BR_PartySizeLimit));
                                        i--;

                                        //searches to see if the party will bump another on the low side and merge if possible
                                    }else if(((Bot.BR_parties.get(i).getEloMinimum() - otherEloMax) >= 0) &&
                                            (Bot.BR_parties.get(i).getEloMinimum() - 100 - partyLife) < otherEloMax){
                                        //printing the return success or failure from combining the parties
                                        System.out.printf(QueueServices.BR_combineParties(i, x, BR_PartySizeLimit));
                                        i--;
                                    }
                                    //an incrementer
                                    x++;
                                }
                            }

                            //------
                            //SECOND: Grow Zones
                            //------
                            //increase the range in which the party is searching for members by the life of the party
                            Bot.BR_parties.get(i).setEloMinimum(eloStart - 100 - partyLife);
                            Bot.BR_parties.get(i).setEloMax(eloStart + 100 + partyLife);
                            System.out.printf("\nParty " + i + " Elo Max: " + Bot.BR_parties.get(i).getEloMax());
                            System.out.printf("\nParty " + i + " Elo Min: " + Bot.BR_parties.get(i).getEloMinimum());
                        }

                        //OLD MCDONALD HAD A FARM AND ON THAT FARM HE HAD A FARM AND ON THAT FARM HE HAD A FARM AND ON-
                        i++;
                    }
                    //if there are no parties, we'll just do a heartbeat
                }else{
                    System.out.printf("\nHeartbeat: No BR_Parties as of " + formatter.format(date));
                }
                //this only exists because it has to
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String BR_combineParties(int thisParty, int thatParty, double BR_PartySizeLimit){
        //get their lifespans
        long otherPartyLife = (System.currentTimeMillis() - Bot.BR_parties.get(thatParty).getTimeStarted())/1000;
        long thisPartyLife = (System.currentTimeMillis() - Bot.BR_parties.get(thisParty).getTimeStarted())/1000;

        //get their party sizes
        int otherPartySize = Bot.BR_parties.get(thatParty).getPlayers().size();
        int thisPartySize = Bot.BR_parties.get(thisParty).getPlayers().size();

        //if they can be combined and the other party is older
        if(((otherPartySize + thisPartySize) <= BR_PartySizeLimit) && (otherPartyLife > thisPartyLife)){
            //move players out of this party and into the other
            for (ProfileClass player : Bot.BR_parties.get(thisParty).getPlayers()){
                //do a thing per player
                Bot.BR_parties.get(thatParty).addPlayer(player);
            }
            //remove this party
            Bot.BR_parties.remove(thisParty);

            //DEBUG printing out the new players in this party
            List<ProfileClass> players = Bot.BR_parties.get(thatParty).getPlayers();
            System.out.printf("\nNew players in BR_party " + thatParty);
            for (ProfileClass player : players){
                //do a thing per player
                System.out.printf("\n" + player.getHandle());
            }

            //print to console what happened
            return "\nBR_Party " + thatParty + " absorbed BR_party " + thisParty;

            //if they can be combined and this party is older
        }else if(((otherPartySize + thisPartySize) <= BR_PartySizeLimit) && (otherPartyLife < thisPartyLife)){
            //move players out of other party and into this one
            for (ProfileClass player : Bot.BR_parties.get(thatParty).getPlayers()){
                //do a thing per player
                Bot.BR_parties.get(thisParty).addPlayer(player);
            }
            //remove other party
            Bot.BR_parties.remove(thatParty);

            //DEBUG printing out the new players in this party
            List<ProfileClass> players = Bot.BR_parties.get(thisParty).getPlayers();
            System.out.printf("\nNew players in BR_party " + thisParty);
            for (ProfileClass player : players){
                //do a thing per player
                System.out.printf("\n" + player.getHandle());
            }

            //print to console what happened
            return "BR_Party " + thisParty + " absorbed BR_party " + thatParty;

            //if they can't be combined, then it really isn't a problem anyway as the player
            //is just inserted into whichever one is first on the list and then maybe they
            //combine anyhow. Whatever, not even a minor problem.
        }else{
            return "\nBR_Parties " + thisParty + " and " + thatParty + " are overlapping. (no error)";
        }
    }
}
