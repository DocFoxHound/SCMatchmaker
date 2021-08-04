package SCMatchmaker;

import SCMatchmaker.Models.PartyClass;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Bot {

    //create the lists of matchmaking parties
    public static List<PartyClass> BR_parties = new ArrayList<PartyClass>();
    public static List<PartyClass> SB_parties = new ArrayList<PartyClass>();
    public static List<PartyClass> D_parties = new ArrayList<PartyClass>();

    //Execute command??
    public interface Command {
        void execute(MessageCreateEvent event);
    }

    public static void main(String[] args) {
        //starting the asynchronous queue function
        new Thread (() -> {
            QueueServices.BR_queuePartyManager();
        }).start();
        System.out.printf("Battle Royal Queue Party Manager online.\n");
        System.out.printf("Matchmaker Running.\n");

        //logging into the bot and keeping it logged in
        String token = "ODU4Mzk1MTU4MjUxOTYyMzg4.YNdgyQ.6GLFlhhhE1HSGJfw0Fh_wvTurGQ";
        GatewayDiscordClient client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();

        //prints out to console that the bot logged in/started up. Not needed, but nice to have.
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    final User self = event.getSelf();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator()
                    );
                });

        //check for the ! command
        client.getEventDispatcher().on(MessageCreateEvent.class)
                // subscribe is like block, in that it will *request* for action
                // to be done, but instead of blocking the thread, waiting for it
                // to finish, it will just execute the results asynchronously.
                .subscribe(event -> {
                    // 3.1 Message.getContent() is a String.
                    //Create the data to send to the external method
                    final String content = event.getMessage().getContent();

                    //capture the MessageCreateEvent as a... Message that we can send to the method.
                    //The reason why is because the Message Data Type contains info such as the discord
                    //server, the message content, and the sender, which can all be useful when
                    //processing commands
                    Message message = event.getMessage();
                    User user = new User(message.getClient(),message.getUserData());
                    CommandServices.onMessage(message, user); //this is the method we're sending the message to.
                });
        //prevents disconnect. I hear if I have a daemon thread running I wont need to use this,
        //but I'm not sure how to do that. Also I may need to use .subscribe on login if I wanted
        //to use a daemon thread?
        client.onDisconnect().block();
    }

    //this is the command each server can setup to customize their bot, if need be. Right now it is static.
    //I don't actually want to do this because its time consuming and can become burdensome on the bot if not handled
    //correctly, which I don't think I can haha. We can talk about this later.
    public static String cmdChar(){
        return("!");
    }

}
