package SCMatchmaker;

import SCMatchmaker.Models.ProfileClass;
import discord4j.core.object.entity.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Integer.parseInt;

public class SQLServices {
    //check the battle royal database for the user's DiscordID. There's no instance in which a user would exist in one
    //but not the other database. The way I scrape the leaderboards is that if the player doesn't exist in
    //one leaderboard they are populated with all number one's.
    public static boolean existsDiscordID(String DiscordID, Message message) {
        //connection string and connection initiation
        Connection conn = null;
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
                Bot.sendMessage(message, ":small_red_triangle: Your DiscordID already exists in the database! If you need to update your " +
                        "Discord and Star Citizen accounts, please use the !updateme command like this:\n" +
                        "`` !updateme https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE  ``");
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            Bot.sendMessage(message, ":small_red_triangle: Connection error: " + e);
            return true;
        }
    }

    //check the battle royal database for the user's UEE number. There's no instance in which a user would exist in one
    //but not the other database. The way I scrape the leaderboards is that if the player doesn't exist in
    //one leaderboard they are populated with all number one's.
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
                Bot.sendMessage(message, ":small_red_triangle: This StarCitizen account already exists in the database! If you need to update your " +
                        "Discord and Star Citizen accounts, please use the !updateme command like this:\n" +
                        "`` !updateme https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE ``");
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException e) {
            Bot.sendMessage(message, ":small_red_triangle: Connection error: " + e);
            return true;
        }
    }

    //load BattleRoyal
    public static String populateACBattleRoyal(ProfileClass user){
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
                    "scorgsid," + //5
                    "elo," + //6
                    "rating," + //7
                    "score," + //8
                    "playtime," + //9
                    "scoreperminute," + //10
                    "damagedealt," + //11
                    "damagetaken," + //12
                    "damageratio," + //13
                    "matches," + //14
                    "avgmatch," + //15
                    "wins," + //16
                    "losses," + //17
                    "winloss," + //18
                    "kills," + //19
                    "deaths," + //20
                    "killdeath," + //21
                    "lastupdated)" //22
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //its similar but different from other DB languages

            // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong(1, Long.parseLong(user.getDiscordID()));
            preparedStmt.setString(2, user.getHandle());
            preparedStmt.setInt(3, Integer.parseInt(user.getUeeCitizenRecord()));
            preparedStmt.setString(4, user.getDiscordUsername());
            preparedStmt.setString(5, user.getOrgID());
            preparedStmt.setDouble(6, user.getBR_ELO());
            preparedStmt.setInt(7, user.getBR_Rating());
            preparedStmt.setLong(8, user.getBR_Score());
            preparedStmt.setInt(9, user.getBR_Playtime());
            preparedStmt.setDouble(10, user.getBR_ScoreMinute());
            preparedStmt.setLong(11, user.getBR_DamageDealt());
            preparedStmt.setLong(12, user.getBR_DamageTaken());
            preparedStmt.setDouble(13, user.getBR_DamageRatio());
            preparedStmt.setInt(14, user.getBR_Matches());
            preparedStmt.setDouble(15, user.getBR_AvgMatch());
            preparedStmt.setInt(16, user.getBR_Wins());
            preparedStmt.setInt(17, user.getBR_Losses());
            preparedStmt.setDouble(18, user.getBR_WinLoss());
            preparedStmt.setInt(19, user.getBR_Kills());
            preparedStmt.setInt(20, user.getBR_Deaths());
            preparedStmt.setDouble(21, user.getBR_KillDeath());
            preparedStmt.setTimestamp    (22, sqlNow); //lastupdated

            // execute the preparedstatement and cram it all in there
            preparedStmt.execute();

            //tell the user what just happened and close the connection
            conn.close();
            return ":small_blue_diamond: (4/5) User added to Arena Commander: Battle Royal database.";
        } catch (SQLException e) { //here we catch the errors
            //if the error is a duplicate error, let the user know what to do next
            if(e.toString().contains("Duplicate entry")){
                return ":small_orange_diamond: (4/5) This Star Citizen account already exists in the Battle Royal database.";
            }else{ //all other errors just spit it out into discord
                //if the connection fails send this message
                return ":small_red_triangle: SQL Error: " + e.toString();
            }
        }
    }

    //load SquadronBattle
    public static String populateACSquadronBattle(ProfileClass user){
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
            String query = " INSERT INTO ACSquadronBattle (" +
                    "discordid," + //1
                    "schandle," + //2
                    "ueecitizenrecord," + //3
                    "discordusername," + //4
                    "scorgsid," + //5
                    "elo," + //6
                    "rating," + //7
                    "score," + //8
                    "playtime," + //9
                    "scoreperminute," + //10
                    "damagedealt," + //11
                    "damagetaken," + //12
                    "damageratio," + //13
                    "matches," + //14
                    "avgmatch," + //15
                    "wins," + //16
                    "losses," + //17
                    "winloss," + //18
                    "kills," + //19
                    "deaths," + //20
                    "killdeath," + //21
                    "lastupdated)" //22
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //its similar but different from other DB languages

            // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong(1, Long.parseLong(user.getDiscordID()));
            preparedStmt.setString(2, user.getHandle());
            preparedStmt.setInt(3, Integer.parseInt(user.getUeeCitizenRecord()));
            preparedStmt.setString(4, user.getDiscordUsername());
            preparedStmt.setString(5, user.getOrgID());
            preparedStmt.setDouble(6, user.getSB_ELO());
            preparedStmt.setInt(7, user.getSB_Rating());
            preparedStmt.setLong(8, user.getSB_Score());
            preparedStmt.setInt(9, user.getSB_Playtime());
            preparedStmt.setDouble(10, user.getSB_ScoreMinute());
            preparedStmt.setLong(11, user.getSB_DamageDealt());
            preparedStmt.setLong(12, user.getSB_DamageTaken());
            preparedStmt.setDouble(13, user.getSB_DamageRatio());
            preparedStmt.setInt(14, user.getSB_Matches());
            preparedStmt.setDouble(15, user.getSB_AvgMatch());
            preparedStmt.setInt(16, user.getSB_Wins());
            preparedStmt.setInt(17, user.getSB_Losses());
            preparedStmt.setDouble(18, user.getSB_WinLoss());
            preparedStmt.setInt(19, user.getSB_Kills());
            preparedStmt.setInt(20, user.getSB_Deaths());
            preparedStmt.setDouble(21, user.getSB_KillDeath());
            preparedStmt.setTimestamp    (22, sqlNow); //lastupdated

            // execute the preparedstatement and cram it all in there
            preparedStmt.execute();

            //tell the user what just happened and close the connection
            conn.close();
            return ":small_blue_diamond: (5/5) User added to Arena Commander: Squadron Battle database.";
        } catch (SQLException e) { //here we catch the errors
            //if the error is a duplicate error, let the user know what to do next
            if(e.toString().contains("Duplicate entry")){
                return ":small_orange_diamond: (5/5) This Star Citizen account already exists in the Squadron Battle database.";
            }else{ //all other errors just spit it out into discord
                //if the connection fails send this message
                return ":small_red_triangle: SQL Error: " + e.toString();
            }
        }
    }

    //TODO load Duel. UNUSED.
    public static String populateACDuel(ProfileClass user){
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
            String query = " INSERT INTO ACSquadronBattle (" +
                    "discordid," + //1
                    "schandle," + //2
                    "ueecitizenrecord," + //3
                    "discordusername," + //4
                    "scorgsid," + //5
                    "elo," + //6
                    "rating," + //7
                    "score," + //8
                    "playtime," + //9
                    "scoreperminute," + //10
                    "damagedealt," + //11
                    "damagetaken," + //12
                    "damageratio," + //13
                    "matches," + //14
                    "avgmatch," + //15
                    "wins," + //16
                    "losses," + //17
                    "winloss," + //18
                    "kills," + //19
                    "deaths," + //20
                    "killdeath," + //21
                    "lastupdated)" //22
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //its similar but different from other DB languages

            // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong(1, Long.parseLong(user.getDiscordID()));
            preparedStmt.setString(2, user.getHandle());
            preparedStmt.setInt(3, Integer.parseInt(user.getUeeCitizenRecord()));
            preparedStmt.setString(4, user.getDiscordUsername());
            preparedStmt.setString(5, user.getOrgID());
            preparedStmt.setDouble(6, user.getSB_ELO());
            preparedStmt.setInt(7, user.getSB_Rating());
            preparedStmt.setLong(8, user.getSB_Score());
            preparedStmt.setInt(9, user.getSB_Playtime());
            preparedStmt.setDouble(10, user.getSB_ScoreMinute());
            preparedStmt.setLong(11, user.getSB_DamageDealt());
            preparedStmt.setLong(12, user.getSB_DamageTaken());
            preparedStmt.setDouble(13, user.getSB_DamageRatio());
            preparedStmt.setInt(14, user.getSB_Matches());
            preparedStmt.setDouble(15, user.getSB_AvgMatch());
            preparedStmt.setInt(16, user.getSB_Wins());
            preparedStmt.setInt(17, user.getSB_Losses());
            preparedStmt.setDouble(18, user.getSB_WinLoss());
            preparedStmt.setInt(19, user.getSB_Kills());
            preparedStmt.setInt(20, user.getSB_Deaths());
            preparedStmt.setDouble(21, user.getSB_KillDeath());
            preparedStmt.setTimestamp    (22, sqlNow); //lastupdated

            // execute the preparedstatement and cram it all in there
            preparedStmt.execute();

            //tell the user what just happened and close the connection
            conn.close();
            return ":small_blue_diamond: User added to Duel database.";
        } catch (SQLException e) { //here we catch the errors
            //if the error is a duplicate error, let the user know what to do next
            if(e.toString().contains("Duplicate entry")){
                return ":small_orange_diamond: This Star Citizen account already exists in the Duel database.";
            }else{ //all other errors just spit it out into discord
                //if the connection fails send this message
                return ":small_red_triangle: SQL Error: " + e.toString();
            }
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

    //get BattleRoyal
    public static List<String> getBattleRoyal(String discordID){
        //the list we need to return
        List<String> results = null;

        //connection string and connection initiation
        Connection conn = null;
        try{
            String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
            conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

            //the SQL query to get the whole database by DiscordID
            String Query = "SELECT schandle, rating FROM ACBattleRoyal WHERE discordid = " + discordID;

            //create the java statement that we'll save this as
            Statement st = conn.createStatement();

            //execute query and get a java resultset
            ResultSet rs = st.executeQuery(Query);

            //rotate next?
            rs.next();

            String handle = rs.getString(1);
            String elo = rs.getString(2);

            //add the SCHandle
            results.add(handle);


            //add the ELO
            results.add(elo);

            //return the list
            return results;

        }catch(SQLException e){
            results.add(e.toString());
            return results;
        }
    }
}
