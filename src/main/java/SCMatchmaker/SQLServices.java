package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.sql.*;

import static java.lang.Integer.parseInt;

public class SQLServices {
    public static boolean existsDiscordID(String DiscordID, Message message) {
        //connection string and connection initiation
        Connection conn = null;
        try {
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String userQuery = "SELECT count(*) FROM ACBattleRoyal WHERE discordid = " + DiscordID;

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(userQuery);

            //rotate the resultset counter forward
            rs.next();

            //so ResultSets do not have .isEmpty or .isNull properties, so this is how you check...
            //by the way, this is checking to see if said user exists.
            if (rs.getInt(1) == 1) {
                Bot.sendMessage(message, "Your DiscordID already exists in the database! If you need to update your " +
                        "Discord and Star Citizen accounts, please use the !updateme command like this:\n" +
                        "!updateme https://robertsspaceindustries.com/citizens/**YOUR_HANDLE_HERE**");
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            Bot.sendMessage(message, "Connection error: " + e);
            return true;
        }
    }

    public static boolean existsUEENumber(String UEENumber, Message message) {
        //convert the string to a number
        int citizenrecord = parseInt(UEENumber);

        //connection string and connection initiation
        Connection conn = null;
        try {
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String userQuery = "SELECT count(*) FROM ACBattleRoyal WHERE ueecitizenrecord = " + citizenrecord;

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(userQuery);

            //rotate the resultset counter forward
            rs.next();

            //so ResultSets do not have .isEmpty or .isNull properties, so this is how you check...
            //by the way, this is checking to see if said user exists.
            if (rs.getInt(1) == 1) {
                Bot.sendMessage(message, "This StarCitizen account already exists in the database! If you need to update your " +
                        "Discord and Star Citizen accounts, please use the !updateme command like this:\n" +
                        "!updateme https://robertsspaceindustries.com/citizens/**YOUR_HANDLE_HERE**");
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            Bot.sendMessage(message, "Connection error: " + e);
            return true;
        }
    }

}
