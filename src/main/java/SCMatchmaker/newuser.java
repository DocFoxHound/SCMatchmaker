package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.sql.*;
import java.util.List;

public class newuser {
    public static void newUser(Message message){
        //Get the userID of who is calling the command
        String userID = message.getUserData().id().toString();

        //getting the user's Star Citizen profile URL
        String citizenURL = message.getContent().toString();
        citizenURL = citizenURL.replaceAll("!newuser ", "");

        //this checks if the user exists and returns their handle
        List<String> userPage = CheckProfilePage.checkPage(citizenURL, message);

        //checking if this userpage exists, and if not...
        if(userPage.isEmpty()){
            Bot.sendMessage(message, "Error at Verifying User.");
        }
        else{//if it does exist, however...
            //get the scraper data that we need to upload to the database
            List<String> leaderboard = ScraperScrape.scrape(message, userPage.get(0));

            try
            {
                //connection string and connection
                String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
                Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

                //the SQL query to insert info into
                String query = " INSERT INTO ACBattleRoyal (" +
                        "schandle, " + //1
                        "ueecitizenrecord, " + //2
                        "discordusername, " + //3
                        "discordid," + //4
                        "scorgsid, " + //5
                        "rating,  " + //6
                        "scoreperminute, " + //7
                        "score, " + //8
                        "damageratio, " + //9
                        "killdeath, " + //10
                        "kills, " + //11
                        "deaths, " + //12
                        "playtime, " + //13
                        "elo)" //14
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // create the mysql insert preparedstatement and assign all the appropriate values from my two lists
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, userPage.get(0));
                preparedStmt.setInt (2, Integer.parseInt(userPage.get(1)));
                preparedStmt.setString   (3, message.getUserData().username());
                preparedStmt.setLong   (4, Long.parseLong(message.getUserData().id().toString()));
                preparedStmt.setString    (5, userPage.get(2));
                preparedStmt.setInt    (6, Integer.parseInt(leaderboard.get(0)));
                preparedStmt.setDouble    (7, Double.parseDouble(leaderboard.get(1)));
                preparedStmt.setLong    (8, Long.parseLong(leaderboard.get(2)));
                preparedStmt.setDouble    (9, Double.parseDouble(leaderboard.get(3)));
                preparedStmt.setDouble    (10, Double.parseDouble(leaderboard.get(4)));
                preparedStmt.setInt    (11, Integer.parseInt(leaderboard.get(13)));
                preparedStmt.setInt    (12, Integer.parseInt(leaderboard.get(14)));
                preparedStmt.setTime    (13, Time.valueOf(leaderboard.get(5)));
                preparedStmt.setInt    (14, 10);

                // execute the preparedstatement
                preparedStmt.execute();

                //update user and close connection
                Bot.sendMessage(message, "User added to database.");
                conn.close();

                //cleanup, though I don't think its needed since Java handles it.
                leaderboard = null;
                userPage = null;

            } catch (SQLException e) {
                //if the error is a duplicate error, let the user know what to do next
                if(e.toString().contains("Duplicate entry")){
                    Bot.sendMessage(message, "Your account already exists in the database. To update your account, " +
                            "please use the !updateme command.");
                }else{
                    //if the connection fails send this message
                    Bot.sendMessage(message, "//SQL Error: " + e);
                }

            }
        }

        //connect to the database server

    }
}
