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

        //set the message in the user object
        user.setMessage(message);
        message = null;

        //assign the discordID
        user.setDiscordID(user.getMessage().getUserData().id().toString());

        //Get the userID of who is calling the command
        //String userDiscordID = message.getUserData().id().toString();
        user.setDiscordID(user.getMessage().getUserData().id().toString()); //using the profileclass in parallel while testing

        //getting the text of the user's command
        user.setCitizenURL(user.getMessage().getContent().toString());

        //replace !newuser with nothing, leaving only the URL
        user.setCitizenURL(user.getCitizenURL().replaceAll("!newuser", "")); //removes the !newuser part of the string
        user.setCitizenURL(user.getCitizenURL().replaceAll(" ", ""));//removes the !newuser part of the string

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
                    user.setUeeCitizenRecord(userPage.get(1));
                    user.setDiscordUsername(user.getMessage().getUserData().username());
                    user.setOrgID(userPage.get(2));
                    user.setRating(Integer.parseInt(leaderboard.get(0)));
                    user.setScoreMinute(Double.parseDouble(leaderboard.get(1)));
                    user.setScore(Long.parseLong(leaderboard.get(2)));
                    user.setDamageRatio(Double.parseDouble(leaderboard.get(3)));
                    user.setKillDeath(Double.parseDouble(leaderboard.get(4)));
                    user.setKills(Integer.parseInt(leaderboard.get(13)));
                    user.setDeaths(Integer.parseInt(leaderboard.get(14)));
                    user.setPlaytime(Integer.parseInt(leaderboard.get(5)));
                    //user.setELO(10.00); //we need an equation for ELO
                    user.setDamageDealt(Long.parseLong(leaderboard.get(12)));
                    user.setDamageTaken(Long.parseLong(leaderboard.get(11)));
                    user.setMatches(Integer.parseInt(leaderboard.get(6)));
                    user.setAvgMatch(Integer.parseInt(leaderboard.get(7)));
                    user.setWins(Integer.parseInt(leaderboard.get(8)));
                    user.setLosses((Integer.parseInt(leaderboard.get(9))));
                    user.setWinLoss(Double.parseDouble(leaderboard.get(10)));

                    //remove lists
                    leaderboard = null;
                    userPage = null;

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
                        preparedStmt.setDouble(6, user.getELO());
                        preparedStmt.setInt(7, user.getRating());
                        preparedStmt.setLong(8, user.getScore());
                        preparedStmt.setInt(9, user.getPlaytime());
                        preparedStmt.setDouble(10, user.getScoreMinute());
                        preparedStmt.setLong(11, user.getDamageDealt());
                        preparedStmt.setLong(12, user.getDamageTaken());
                        preparedStmt.setDouble(13, user.getDamageRatio());
                        preparedStmt.setInt(14, user.getMatches());
                        preparedStmt.setDouble(15, user.getAvgMatch());
                        preparedStmt.setInt(16, user.getWins());
                        preparedStmt.setInt(17, user.getLosses());
                        preparedStmt.setDouble(18, user.getWinLoss());
                        preparedStmt.setInt(19, user.getKills());
                        preparedStmt.setInt(20, user.getKills());
                        preparedStmt.setDouble(21, user.getKillDeath());
                        preparedStmt.setTimestamp    (22, sqlNow); //lastupdated

                        // execute the preparedstatement and cram it all in there
                        preparedStmt.execute();

                        //tell the user what just happened and close the connection
                        Bot.sendMessage(user.getMessage(), "User added to database.");
                        conn.close();
                    } catch (SQLException e) { //here we catch the errors
                        //if the error is a duplicate error, let the user know what to do next
                        if(e.toString().contains("Duplicate entry")){
                            Bot.sendMessage(user.getMessage(), "This Star Citizen account already exists in the database! " +
                                    "If you need to update your Discord and Star Citizen accounts, please use the " +
                                    "!updateme command like this:" +
                                    "!updateme https://robertsspaceindustries.com/citizens/**YOUR_HANDLE_HERE**");
                        }else{ //all other errors just spit it out into discord
                            //if the connection fails send this message
                            Bot.sendMessage(user.getMessage(), "//SQL Error: " + e);
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
