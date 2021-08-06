package SCMatchmaker;

import SCMatchmaker.Commands.LF_BR;
import SCMatchmaker.Commands.LF_Register;
import SCMatchmaker.Commands.LF_Update;
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
            else if(command.startsWith("lf_help")){
                MessageServices.sendMessage(message, "This is the help menu, which is not yet developed.");
                return;
            }
            else if(command.startsWith("lf_register")){
                MessageServices.sendMessage(message, "**ADDING NEW USER**" +
                        "\nPlease wait...");
                LF_Register.newUser(message);
                return;
            }
            else if(command.startsWith("lf_update")){
                MessageServices.sendMessage(message, "**UPDATING USER**" +
                        "\nPease wait...");
                LF_Update.LF_Update(message);
                return;
            }
            else if(command.startsWith("lfbr") || command.startsWith("lf_br")){
                MessageServices.sendMessage(message, "Looking For...**BATTLE ROYAL**");
                LF_BR.LFBR(message, user);
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
