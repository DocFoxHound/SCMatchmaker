package SCMatchmaker;

import SCMatchmaker.Models.ProfileClass;
import discord4j.core.object.entity.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class SQLServices {
    //check the battle royal database for the user's DiscordID. There's no instance in which a user would exist in one
    //but not the other database. The way I scrape the leaderboards is that if the player doesn't exist in
    //one leaderboard they are populated with all number one's.
    public static boolean existsDiscordID(Message message) {
        //connection string and connection initiation
        Connection conn = null;

        //set the discord ID
        String DiscordID = message.getUserData().username().toString()+"#"+message.getUserData().discriminator();

        try {
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String BR_Query = "SELECT count(*) FROM ACBattleRoyal WHERE discordid = " + DiscordID;

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet BR_rs = st.executeQuery(BR_Query);

            //rotate the resultset counter forward
            BR_rs.next();

            //so ResultSets do not have .isEmpty or .isNull properties, so this is how you check...
            //by the way, this is checking to see if said user exists.
            if (BR_rs.getInt(1) == 1 && BR_rs.getInt(1) == 1) {
                MessageServices.sendMessage(message, ":small_red_triangle: Your DiscordID already exists in the database! If you need to update your " +
                        "Discord and Star Citizen accounts, please use the !updateme command like this:\n" +
                        "`` !updateme https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE  ``");
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            if(e.toString().contains("Unknown column"))
            {
                return false;
            }
            MessageServices.sendMessage(message, ":small_red_triangle: Connection error: " + e);
            return false;
        }
    }

    //check the battle royal database for the user's UEE number. There's no instance in which a user would exist in one
    //but not the other database. The way I scrape the leaderboards is that if the player doesn't exist in
    //one leaderboard they are populated with all number one's.
    public static boolean existsUEENumber(String UEENumber) {
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
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            System.out.printf("Database Connection Error: " + e);
            return true;
        }
    }

    //TODO reduce the amount of data kept to only that which we need
    //load BattleRoyal
    public static String setBR_Data(ProfileClass player){
        //get date data
        LocalDateTime now = LocalDateTime.now();
        Timestamp sqlNow = Timestamp.valueOf(now);

        //now we add them to the database
        try
        {
            //connection string
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query. this, essentially, tells the Database what it's going to be inserting.
            String query = " INSERT INTO ACBattleRoyal (" +
                    "discordid," + //1
                    "schandle," + //2
                    "ueecitizenrecord," + //3
                    "discordusername," + //4
                    "br_elo," + //5
                    "lastupdated)" //6
                    + " values (?, ?, ?, ?, ?, ?)"; //its similar but different from other DB languages

            // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong(1, Long.parseLong(player.getDiscordID()));
            preparedStmt.setString(2, player.getHandle());
            preparedStmt.setInt(3, Integer.parseInt(player.getUeeCitizenRecord()));
            preparedStmt.setString(4, player.getDiscordUsername());
            preparedStmt.setDouble(5, player.getBR_ELO());
            preparedStmt.setTimestamp(6, sqlNow); //lastupdated

            // execute the preparedstatement and cram it all in there
            preparedStmt.execute();

            //tell the user what just happened and close the connection
            conn.close();
            return ":small_blue_diamond: Welcome, new user!.";
        } catch (SQLException e) { //here we catch the errors
            //if the error is a duplicate error, let the user know what to do next
            if(e.toString().contains("Duplicate entry")){
                return ":small_orange_diamond: This Star Citizen account already exists in the Battle Royal database.";
            }else{ //all other errors just spit it out into discord
                //if the connection fails send this message
                System.out.printf("\nERROR: " + e.toString());
                return ":small_red_triangle: SQL Error";
            }
        }
    }

    //TODO only works for BR right now
    //update databases
    public static String updateAlldBData(ProfileClass player){
        //get date data
        LocalDateTime now = LocalDateTime.now();
        Timestamp sqlNow = Timestamp.valueOf(now);

        //now we add them to the database
        try
        {
            //connection string
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query. this, essentially, tells the Database what it's going to be inserting.
            String query = "UPDATE ACBattleRoyal SET schandle = ?, discordid = ?, discordusername = ?, scorgsid = ?, lastupdated = ? WHERE ueecitizenrecord = ?";

            // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, player.getHandle());
            preparedStmt.setLong(2, Long.parseLong(player.getDiscordID()));
            preparedStmt.setString(3, player.getDiscordUsername());
            preparedStmt.setString(4, player.getOrgID());
            preparedStmt.setTimestamp    (5, sqlNow); //lastupdated
            preparedStmt.setLong   (6, Long.parseLong(player.getUeeCitizenRecord()));

            // execute the preparedstatement and cram it all in there
            preparedStmt.execute();

            //tell the user what just happened and close the connection
            conn.close();
            return ":small_blue_diamond: User updated in the Arena Commander: Battle Royal database.";
        } catch (SQLException e) { //here we catch the errors
            //if the error is a duplicate error, let the user know what to do next
            return e.toString();
        }
    }

    //get BattleRoyal
    public static ProfileClass getBR_Data(ProfileClass player){
        //connection string and connection initiation
        Connection conn = null;
        try{
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String Query = "SELECT br_elo, " + //1
                    " FROM ACBattleRoyal WHERE ueecitizenrecord = " + player.getUeeCitizenRecord();

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(Query);

            //rotate next?
            rs.next();

            player.setBR_ELO(rs.getDouble(1));

            //return the list
            return player;

        }catch(SQLException e){
            System.out.printf("\nERROR: " + e.toString());
            player.setHandle("--CONNECTION ERROR--");
            return player;
        }
    }

    //set the BR elo for a player
    public static String setBR_Elo(ProfileClass player){
        //get date data
        LocalDateTime now = LocalDateTime.now();
        Timestamp sqlNow = Timestamp.valueOf(now);

        //now we add them to the database
        try
        {
            //connection string
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query. this, essentially, tells the Database what it's going to be inserting.
            String query = "UPDATE ACBattleRoyal SET br_elo = ?, lastupdated = ? WHERE handle = ?";

            // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDouble(1, player.getBR_ELO());
            preparedStmt.setTimestamp    (2, sqlNow); //lastupdated
            preparedStmt.setString(3, player.getHandle());

            // execute the preparedstatement and cram it all in there
            preparedStmt.execute();

            //tell the user what just happened and close the connection
            conn.close();
            return "Player " + player.getDiscordUsername() + "'s Elo was successfully updated.";
        } catch (SQLException e) { //here we catch the errors
            //if the error is a duplicate error, let the user know what to do next
            return "There was an error loading the newly calculated Elo of player: " + player.getDiscordUsername();
        }
    }

    //return Elo median for BR
    public static double BR_EloMedian(){
        double result = 0;

        //store my values in an array
        ArrayList<Double> array = new ArrayList<>();

        //connection string and connection initiation
        Connection conn = null;
        try{
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String Query = "SELECT br_elo FROM ACBattleRoyal ";

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(Query);

            //iterate through all the Battle Royal Elo values and add them to an array
            while(rs.next()){
                Double value=rs.getDouble("br_elo");
                array.add(value);
            }

            //iterate through the array, adding them all up
            for(int i = 0; i<array.size(); i++){
                result = result+array.get(i);
            }

            //find the median score
            result = result/array.size();

            //return that as the new Elo for a new player
            return result;

        }catch(SQLException e){
            return 500;
        }
    }

    //remove a user from all databases
    public static String removeUser(String ueeCitNum){
        try{
            //connection string
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            String deleteBR = "DELETE FROM ACBattleRoyal where ueecitizenrecord = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(deleteBR);
            preparedStmt.setInt(1, Integer.parseInt(ueeCitNum));
            preparedStmt.execute();

            String deleteSB = "DELETE FROM ACSquadronBattle where ueecitizenrecord = ?";
            PreparedStatement preparedStmt2 = conn.prepareStatement(deleteSB);
            preparedStmt2.setInt(1, Integer.parseInt(ueeCitNum));
            preparedStmt2.execute();

            /*
            String deleteD = "DELETE FROM ACDuel where ueecitizenrecord = ?";
            PreparedStatement preparedStmt3 = conn.prepareStatement(deleteD);
            preparedStmt3.setInt(1, Integer.parseInt(ueeCitNum));
            preparedStmt3.execute();

             */

            conn.close();
            return ":small_blue_diamond: Database cleared of user record, repopulating...";
        }catch(SQLException e){
            return ":small_red_diamond: There was an error removing the user from the database: " + e;
        }
    }


}
