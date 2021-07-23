package SCMatchmaker;

import SCMatchmaker.Models.ProfileClass;
import discord4j.core.object.entity.Message;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class NewUser {
    public static void newUser(Message message){
        //make a new profileclass object
        ProfileClass user = new ProfileClass();

        //Get the userID of who is calling the command
        //String userDiscordID = message.getUserData().id().toString();
        user.setDiscordID(message.getUserData().id().toString()); //using the profileclass in parallel while testing

        //getting the text of the user's command
        user.setCitizenURL(message.getContent().toString());

        //replace !newuser with nothing, leaving only the URL
        user.setCitizenURL(user.getCitizenURL().replaceAll("!newuser", "")); //removes the !newuser part of the string
        user.setCitizenURL(user.getCitizenURL().replaceAll(" ", ""));//removes the !newuser part of the string

        //set the message in the user object
        user.setMessage(message);
        message = null; //erase the previous message

        //check that they actually put text/url after the command
        if(user.getCitizenURL().equals("")){
            Bot.sendMessage(user.getMessage(), "Please use the new user command with your discord profile" +
                    "link afterwards. It should look similar to the following: \n" +
                    "!newuser https://robertsspaceindustries.com/citizens/YOUR_HANDLE_HERE");
        }else{
            //check DataBase for DiscordID
            if (SQLServices.existsDiscordID(user.getDiscordID(), user.getMessage()) == false) { //if the DiscordID doesn't exist, continue adding user
                //this checks if the user exists and returns their handle
                List<String> userPage = CheckProfilePage.checkPage(user.getCitizenURL(), user.getMessage()); //inputs a String and Message, outputs a string list

                //if the userPage is empty then that means the verification process did not finish. Another error is also thrown.
                if(userPage.size()==0){
                    Bot.sendMessage(user.getMessage(), "Error at Verifying Userpage.");
                }
                else{//if userPage has data that means the check finished and the user was verified
                    //this scrapes the leaderboard into a List of Strings. Inputs Message and a String (Star Citizen Handle)
                    List<String> leaderboard = ScraperScrape.scrape(user.getMessage(), userPage.get(0)); //that userPage index is the SC Handle

                    //loading the user object
                    user.setHandle(userPage.get(0));
                    //TODO more loading

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
                                "elo," + //14
                                "lastupdated)" //15
                                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //its similar but different from other DB languages

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
                        preparedStmt.setInt    (13, Integer.parseInt(leaderboard.get(5)));
                        preparedStmt.setInt    (14, 10);
                        preparedStmt.setTimestamp    (15, sqlNow);

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
                            Bot.sendMessage(message, "This Star Citizen account already exists in the database! " +
                                    "If you need to update your Discord and Star Citizen accounts, please use the " +
                                    "!updateme command like this:" +
                                    "!updateme https://robertsspaceindustries.com/citizens/**YOUR_HANDLE_HERE**");
                        }else{ //all other errors just spit it out into discord
                            //if the connection fails send this message
                            Bot.sendMessage(message, "//SQL Error: " + e);
                        }
                    }
                }
                return;
            }else{//if the DiscordID DOES exist, halt
                return;
            }
        }
    }
}
