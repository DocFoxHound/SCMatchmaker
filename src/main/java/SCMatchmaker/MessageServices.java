package SCMatchmaker;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

public class MessageServices {
    //I just decided to make my own method for handling sending messages back to Discord. Its completely unnecessary,
    //but it is shorter than typing it all out. the Return block shows the full way to send a message, just replace
    //text with "strings go here" to send a message yourself.
    public static Message sendMessage(Message message, String text){
        return (message.getChannel().block().createMessage(text).block());
    }

    public static void sendPrivateMessage(User user, String message) {
        if (user != null) {
            user.getPrivateChannel().doOnSuccess(channel ->
                    channel.getRecipients().take(1).singleOrEmpty().doOnSuccess(user_ -> {
                        String uname;
                        String udisc;

                        if (user_ != null) {
                            uname = user_.getUsername().toLowerCase();
                            udisc = user_.getDiscriminator();
                        } else {
                            uname = "";
                            udisc = "";
                        }
                        channel.createMessage(message).doOnSuccess(m -> System.out.println("\nRPIVATE MESSAGE SENT: [@" + uname + "#" + udisc + "] '" + message + "'")).subscribe();
                    }).subscribe()).subscribe();
        } else {
            throw new IllegalArgumentException("user object was null");
        }
    }
}
