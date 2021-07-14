package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//This is the queue method, which will handle a lot of stuff.
public class QueueService {
    public static void queuing(Message message){

        //String userName = message.getUserData().username();
        //Id userID = message.getUserData().id();

        //connect to the database server
        try
        {
            //connection string and connection
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String query = "SELECT * FROM ACBattleRoyal WHERE discordid = '319210987246977037'"; //hand-jammed Mirky's discordID

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(query);

            //parse the data. You can use this to store several people if the query gets multiple people.
            while (rs.next()){
                int id = rs.getInt("id");
                String scHandle = rs.getString("schandle");
                int ueeCitizenRecord = rs.getInt("ueecitizenrecord");
                String discordUsername = rs.getString("discordusername");
                long discordID = rs.getLong("discordid");
                String scOrgSID = rs.getString("scorgsid");
                int rating = rs.getInt("rating");
                double scorePerMinute = rs.getDouble("scoreperminute");
                double killDeath = rs.getDouble("killdeath");
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Time playTime = rs.getTime("playtime");
                double ELO = rs.getDouble("elo");

                Bot.sendMessage(message, "SC Handle: " + scHandle);
                Bot.sendMessage(message, "UEE Citizen Record: " + ueeCitizenRecord);
                Bot.sendMessage(message, "Discord Username: " + discordUsername);
                Bot.sendMessage(message, "Discord ID: " + discordID);
                Bot.sendMessage(message, "SC Org SID: " + scOrgSID);
                Bot.sendMessage(message, "Rating: " + rating);
                Bot.sendMessage(message, "Score Per Minute: " + scorePerMinute);
                Bot.sendMessage(message, "Kill/Death: " + killDeath);
                Bot.sendMessage(message, "Play Time: " + playTime);
                Bot.sendMessage(message, "ELO: " +ELO);
            }



        } catch (SQLException e) {
            //if the connection fails send this message
            Bot.sendMessage(message, "//Database connection failed: " + e);
        }
    }
}