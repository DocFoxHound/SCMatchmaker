package SCMatchmaker;

import discord4j.core.object.entity.Message;

import java.sql.*;
import java.util.List;

public class NewUser {
    public static void newUser(Message message){
        //Get the userID of who is calling the command
        String userID = message.getUserData().id().toString();

        //getting the user's Star Citizen profile URL
        String citizenURL = message.getContent().toString(); //gets the content of the user's post

        //replace any spaces with nothing
        citizenURL.replaceAll("\\s+","");

        //replace !newuser with nothing
        citizenURL = citizenURL.replaceAll("!newuser", ""); //removes the !newuser part of the string

        //check that they actually put text/url after the command
        if(citizenURL.equals("")){
            Bot.sendMessage(message, "Please use the new user command with your discord profile" +
                    "link afterwards. It should look similar to the following: \n" +
                    "!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE");
        }else{
            //this checks if the user exists and returns their handle
            List<String> userPage = CheckProfilePage.checkPage(citizenURL, message); //inputs a String and Message, outputs a string list.

            //if the userPage is empty then that means the verification process did not finish. Another error is also thrown.
            if(userPage.size()==1){
                Bot.sendMessage(message, "Error at Verifying User.");
            }
            else{//if userPage has data that means the check finished and the user was verified
                //this scrapes the leaderboard into a List of Strings. Inputs Message and a String (Star Citizen Handle)
                List<String> leaderboard = ScraperScrape.scrape(message, userPage.get(0)); //that userPage index is the SC Handle

                try
                {
                    //connection string and connection initiation
                    String connectionUrl = "jdbc:mysql://na01-sql.pebblehost.com:3306/customer_203228_users";
                    Connection conn = DriverManager.getConnection(connectionUrl, "customer_203228_users", "PRoA@fS6TXRhBn0QXWYy");

                    //the SQL query. this, essentially, tells the Database what it's going to be inserting.
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
                            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //its similar but different from other DB languages

                    // create the mysql insert preparedstatement and assign all the appropriate values from my two lists (userPage and leaderboard) into the database
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

                    // execute the preparedstatement and cram it all in there
                    preparedStmt.execute();

                    //tell the user what just happened and close the connection
                    Bot.sendMessage(message, "User added to database.");
                    conn.close();

                    //cleanup, though I don't think its needed since Java handles it by itself
                    leaderboard = null;
                    userPage = null;
                    message = null;

                } catch (SQLException e) { //here we catch the errors
                    //if the error is a duplicate error, let the user know what to do next
                    if(e.toString().contains("Duplicate entry")){
                        Bot.sendMessage(message, "Your account already exists in the database. To update your account, " +
                                "please use the !updateme command.");
                    }else{ //all other errors just spit it out into discord
                        //if the connection fails send this message
                        Bot.sendMessage(message, "//SQL Error: " + e);
                    }
                }
            }
        }
    }
}
