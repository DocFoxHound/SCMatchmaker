package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.util.Locale;


public class CommandService {
    public static void onMessage(Message message){
        if (message.getContent().toLowerCase(Locale.ROOT).equals("!test")){
            message.getChannel().block().createMessage("Bed!").block();
        }
        else if(message.getContent().toLowerCase(Locale.ROOT).equals("!ping")){
            message.getChannel().block().createMessage("Pong!").block();
        }
        else if(message.getContent().toLowerCase(Locale.ROOT).equals("!help")){
            message.getChannel().block().createMessage("This is the help menu, which is not yet developed.").block();
        }
        else if(message.getContent().toLowerCase(Locale.ROOT).equals("!queue")){
            message.getChannel().block().createMessage("Queuing for Matchmaking...").block();
            QueueService.queuing(message);
        }
        else{
            return;
        }
    }
}
