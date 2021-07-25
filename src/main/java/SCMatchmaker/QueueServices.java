package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.sql.*;

//This is the queue method, which will handle a lot of stuff.
public class QueueServices {
    public static void queuing(Message message){

        //Get the userID of who is calling the command
        String userID = message.getUserData().id().toString();

        //connect to the database server
        try
        {
            //connection string and connection
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String query = "SELECT * FROM ACBattleRoyal WHERE discordid = " + userID;

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(query);

            //so ResultSets do not have .isEmpty or .isNull properties, so this is how you check...
            //by the way, this is checking to see if said user exists.
            if (rs.next() == false)
            {
                Bot.sendMessage(message, "Please use the new user command with your discord profile" +
                        "link afterwards. It should look similar to the following: \n" +
                        "!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE");
                conn.close();
            }else{//if the user exists...
                //start the queue logic
                while (rs.next()){
                    String scHandle = rs.getString("schandle");
                    String discordUsername = rs.getString("discordusername");
                    long discordID = rs.getLong("discordid");
                    double ELO = rs.getDouble("elo");
                    conn.close();
                }
            }
        } catch (SQLException e) {
            //if the connection fails send this message
            Bot.sendMessage(message, "//SQL Error: " + e);
        }
    }
}