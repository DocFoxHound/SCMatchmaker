package SCMatchmaker;

import SCMatchmaker.Commands.NewUser;
import SCMatchmaker.Commands.QueueBR;
import SCMatchmaker.Commands.UpdateMe;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

import java.util.Locale;


public class CommandServices {
    public static void onMessage(Message message, User user){
        //if the message (passed onto this method from Bot.java) starts with cmdChar(also passed from Bot.java),
        //then we do commands and stuff.
        if (message.getContent().toLowerCase(Locale.ROOT).startsWith(String.valueOf(Bot.cmdChar()))) {
            //removes the command character from the front of the string. In this case, it removes the "!" char
            String command = message.getContent().toString().substring(1);

            //recognizes a command and then does something with it.
            if (command.startsWith("ping")){
                MessageServices.sendPrivateMessage(user, "Pong!");
                MessageServices.sendMessage(message, "Pong!");
                return;
            }
            else if(command.startsWith("help")){
                MessageServices.sendMessage(message, "This is the help menu, which is not yet developed.");
                return;
            }
            //here we are recognizing the queue command and passing off the message data to another method to handle.
            else if(command.startsWith("queue_br")){
                MessageServices.sendMessage(message, "Queueing for Battle Royal...");
                QueueBR.queuing(message, user);
                return;
            }
            else if(command.startsWith("newuser")){
                MessageServices.sendMessage(message, "**ADDING NEW USER**" +
                        "\nPlease wait...");
                NewUser.newUser(message);
                return;
            }
            else if(command.startsWith("updateme")){
                MessageServices.sendMessage(message, "**UPDATING USER**" +
                        "\nPease wait...");
                UpdateMe.UpdateMe(message);
                return;
            }
            else{
                return;
            }
        }
        else{
            return;
        }
    }
}
