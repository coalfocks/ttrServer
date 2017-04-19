package server.Database;

/**
 * Created by colefox on 2/6/17.
 */
import server.Serializer;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class DAO
    {
        private static DAO instance;
        private static Database db;

        private DAO()
        {
            this.db = new Database();
        }

        /**
         * gets the instance, if exists. if not, creates instance and initializes it
         *
         * @return the instance
         */
        public static DAO getInstance()
        {
            if (instance == null)
            {
                instance = new DAO();
            }
            return instance;
        }
        //set db method
        public void setDB(Database sqLiteDatabase)
        {
            this.db = sqLiteDatabase;
        }

        public Database getDB()
        {
            return this.db;
        }

        public User getUser(String username) throws SQLException
        {
            if (username == null)
            {
                return null;
            }

            PreparedStatement stmt = null;
            ResultSet rs = null;
            User user = null;
            try
            {
                db.startTransaction();
                String sql = "SELECT * FROM users WHERE users.username = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();

                while(rs.next())
                {
                    user = new User();
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setPlayerID(rs.getInt(1));
                    user.setInGame(rs.getInt(4));
                }
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            finally
            {
                if(stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();

                db.closeTransaction(true);
            }
            return user;
        }

        public User getUser(int playerid) throws SQLException
        {
            if (playerid == 0)
            {
                return null;
            }

            PreparedStatement stmt = null;
            ResultSet rs = null;
            User user = null;
            try
            {
                db.startTransaction();
                String sql = "SELECT * FROM users WHERE users.playerID = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, playerid);
                rs = stmt.executeQuery();

                while(rs.next())
                {
                    user = new User();
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setPlayerID(rs.getInt(1));
                    user.setInGame(rs.getInt(4));
                }
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            finally
            {
                if(stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();
                db.closeTransaction(true);
            }
            return user;
        }

       public boolean addUser(User u)
       {
           if (u == null)
           {
               return false;
           }

           PreparedStatement stmt = null;
           try
           {
               String sql = "INSERT OR IGNORE INTO users (username, password, inGame)" +
                       "VALUES (?,?,?" +
                        ");";
               db.startTransaction();
               stmt = db.connection.prepareStatement(sql);
               stmt.setString(1, u.getUsername());
               stmt.setString(2, u.getPassword());
               stmt.setInt(3, 0);

               stmt.executeUpdate();

           }
           catch(SQLException e)
           {
               System.out.println(e.getMessage());
               return false;
           }
           finally
           {
               db.closeTransaction(true);
           }

           return true;

       }

       public int createGame(int ownerID)
       {
           int gameID = 0;

           if (ownerID == 0)
           {
               return 0;
           }

           TTRGame game = new TTRGame();
           game.setOwnerID(ownerID);
           game.setInProgress(0);
           game.setGameID(gameID);

           ResultSet rs = null;
           PreparedStatement stmt = null;
           try
           {
               User u = getUser(ownerID);
               game.setOwnerUsername(u.getUsername());
               String g = Serializer.serialize(game);
               String sql = "INSERT OR IGNORE INTO games (owner, inProgress, game)" +
                       "VALUES (?,?,?);";
               db.startTransaction();
               stmt = db.connection.prepareStatement(sql);
               stmt.setInt(1, ownerID);
               stmt.setInt(2, 0);
               stmt.setString(3, g);

               stmt.executeUpdate();

               String sql2 = "SELECT gameID FROM games WHERE games.game = ?";
               PreparedStatement stmt2 = db.connection.prepareStatement(sql2);
               stmt2.setString(1,g);
               rs = stmt2.executeQuery();
               gameID = rs.getInt(1);
               db.closeTransaction(true);

               game.setGameID(gameID);
               String sql3 = "UPDATE games" +
                       " SET game = ?" +
                       " WHERE gameID = ?";
               db.startTransaction();
               stmt = db.connection.prepareStatement(sql3);
               stmt.setString(1, Serializer.serialize(game));
               stmt.setInt(2, gameID);

               stmt.executeUpdate();


           }
           catch(SQLException e)
           {
               System.out.println(e.getMessage());
               return 0;
           } catch(IOException e)
           {
               System.out.println(e.getMessage());
               return 0;
           }
           finally
           {
               db.closeTransaction(true);
           }

           return gameID;
       }

       public TTRGame getGame(int gameID)
       {
           if (gameID == 0)
           {
               return null;
           }

           PreparedStatement stmt = null;
           ResultSet rs = null;
           TTRGame game = null;
           try
           {
               db.startTransaction();
               String sql = "SELECT * FROM games WHERE games.gameID = ?";
               stmt = db.connection.prepareStatement(sql);
               stmt.setInt(1, gameID);
               rs = stmt.executeQuery();

               while (rs.next())
               {
                   String g = rs.getString(4);
                   game = (TTRGame) Serializer.deserialize(g);
               }
           }
           catch(SQLException e)
           {
                e.printStackTrace();
           } catch (ClassNotFoundException e)
           {
               e.printStackTrace();
               return null;
           }  catch (IOException e)
           {
               e.printStackTrace();
           }
           finally
           {
               try
               {
                   if (stmt != null)
                       stmt.close();
                   if (rs != null)
                       rs.close();
                   db.closeTransaction(true);
               } catch (SQLException e)
               {
                   e.printStackTrace();
               }
           }
           return game;
       }

       public void reset()
       {
           try
           {
               PreparedStatement stmt = null;
               db.startTransaction();
               String sql = "DROP TABLE IF EXISTS users;" +
                       "DROP TABLE IF EXISTS games;";
               stmt = db.connection.prepareStatement(sql);
               stmt.executeUpdate();

               String sql3 = "CREATE TABLE IF NOT EXISTS users"+
                       "("+
                       "playerID INTEGER PRIMARY KEY autoincrement," +
                       "username varchar(64),"+
                       "password varchar(64),"+
                       "inGame INTEGER" +
                       ");";

               PreparedStatement stmt2 = db.connection.prepareStatement(sql3);
               stmt2.executeUpdate();

               String sql2 = "CREATE TABLE IF NOT EXISTS games"+
                       "("+
                       "gameID INTEGER PRIMARY KEY autoincrement," +
                       "owner varchar(64)," +
                       "inProgress TINYINT," +
                       "game TEXT" +
                       ");";

               PreparedStatement stmt3 = db.connection.prepareStatement(sql2);
               stmt3.executeUpdate();
           } catch(Exception e)
           {
               e.printStackTrace();
           }

       }

       public boolean updatePlayerGame(int gameID, int userID)
       {

           if (gameID == 0 || userID == 0)
           {
               return false;
           }

           PreparedStatement stmt = null;
           try
           {
               String sql = "UPDATE users" +
                       " SET inGame = ?" +
                       " WHERE playerID = ?";
               db.startTransaction();
               stmt = db.connection.prepareStatement(sql);
               stmt.setInt(1, gameID);
               stmt.setInt(2, userID);

               stmt.executeUpdate();

           }
           catch(SQLException e)
           {
               System.out.println(e.getMessage());
               return false;
           }
           db.closeTransaction(true);

           return true;
       }

        public boolean addPlayerToGame(int gameID, String game)
        {

            if (gameID == 0 || game == null)
            {
                return false;
            }

            PreparedStatement stmt = null;
            try
            {
                String sql = "UPDATE games" +
                        " SET game = ?" +
                        " WHERE gameID = ?";
                db.startTransaction();
                stmt = db.connection.prepareStatement(sql);
                stmt.setString(1, game);
                stmt.setInt(2, gameID);

                stmt.executeUpdate();

            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
            db.closeTransaction(true);

            return true;
        }

        public ArrayList<TTRGame> getGames(int gameID)
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            TTRGame game = null;
            ArrayList<TTRGame> games = new ArrayList<>();
            try
            {
                db.startTransaction();
                String sql = "SELECT game FROM games" +
                        " WHERE games.inProgress = 0" +
                        " OR games.gameID = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, gameID);
                rs = stmt.executeQuery();

                while (rs.next())
                {
                    String g = rs.getString(1);
                    game = (TTRGame) Serializer.deserialize(g);
                    games.add(game);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }  catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                db.closeTransaction(true);
            }
            return games;
        }

        public boolean startGame(int ownerID)
        {

            if (ownerID == 0)
            {
                return false;
            }

            PreparedStatement stmt = null;
            try
            {
                String sql = "UPDATE games" +
                        " SET inProgress = 1" +
                        " WHERE owner = ?";
                db.startTransaction();
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, ownerID);

                stmt.executeUpdate();

            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
            db.closeTransaction(true);

            return true;
        }

        public boolean endGame(int ownerID)
        {

            if (ownerID == 0)
            {
                return false;
            }

            PreparedStatement stmt = null;
            try
            {
                String sql = "UPDATE games" +
                        " SET inProgress = 0" +
                        " WHERE owner = ?";
                db.startTransaction();
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, ownerID);

                stmt.executeUpdate();

            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
            db.closeTransaction(true);

            return true;
        }

        public int getGameStatus(int gameID)
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            ArrayList<TTRGame> games = new ArrayList<>();
            try
            {
                db.startTransaction();
                String sql = "SELECT inProgress FROM games WHERE games.gameID = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1,gameID);
                rs = stmt.executeQuery();

                return rs.getInt(1);
            }
            catch(SQLException e)
            {
                e.printStackTrace();
                return 1;
            }
            finally
            {
                try
                {
                    if (stmt != null)
                        stmt.close();
                    if (rs != null)
                        rs.close();
                    db.closeTransaction(true);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public int getNumPlayers(int gameID)
        {
            if (gameID == 0)
            {
                return 0;
            }

            PreparedStatement stmt = null;
            ResultSet rs = null;
            TTRGame game = null;
            try
            {
                db.startTransaction();
                String sql = "SELECT game FROM games WHERE games.gameID = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, gameID);
                rs = stmt.executeQuery();

                while (rs.next())
                {
                    String g = rs.getString(1);
                    game = (TTRGame) Serializer.deserialize(g);
                }

                return game.getNumPlayers();

            }
            catch(SQLException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }  catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (stmt != null)
                        stmt.close();
                    if (rs != null)
                        rs.close();
                    db.closeTransaction(true);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            return 0;
        }

        public int getGameID (String gstring) throws SQLException
        {
            if (gstring == null)
            {
                return 0;
            }

            PreparedStatement stmt = null;
            ResultSet rs = null;
            int gameID = 0;
            try
            {
                db.startTransaction();
                String sql = "SELECT gameID FROM games WHERE games.game = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setString(1, gstring);
                rs = stmt.executeQuery();
                gameID = rs.getInt(1);
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            finally
            {
                if(stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();
                db.closeTransaction(true);
            }
            return gameID;
        }

        /**
         * chatMessage is the message that the user sent to the server. Add it to the database
         * @param username - the username of the player sending the chat
         * @param gameID - the game to which the chat belongs
         * @param chatMessage - String in the format "userName: some message for example Cole is lame and Ty is cool"
         */
        public boolean addChatMessage(String username, int gameID, String chatMessage){
            if (username == null || gameID == 0 || chatMessage == null)
            {
                return false;
            }

            PreparedStatement stmt = null;
            try
            {
                String sql = "INSERT OR IGNORE INTO chat (gameID, player, chatMessage)" +
                        "VALUES (?,?,?" +
                        ");";
                db.startTransaction();
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, gameID);
                stmt.setString(2, username);
                stmt.setString(3, chatMessage);

                stmt.executeUpdate();

            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
            finally
            {
                db.closeTransaction(true);
            }

            return true;
        }

        public boolean updateGame(TTRGame game) {
            if (game == null) {
                return false;
            }

            ResultSet rs = null;
            PreparedStatement stmt = null;
            try
            {
                String sql = "UPDATE games" +
                        " SET game = ?" +
                        " WHERE gameID = ?";
                db.startTransaction();
                stmt = db.connection.prepareStatement(sql);
                stmt.setString(1, Serializer.serialize(game));
                stmt.setInt(2, game.getGameID());

                stmt.executeUpdate();
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
                return false;
            } catch(IOException e)
            {
                System.out.println(e.getMessage());
                return false;
            }
            finally
            {
                db.closeTransaction(true);
            }

            return true;
        }

        public TTRGame getGameByOwner(int ownerID)
        {
            if (ownerID == 0)
            {
                return null;
            }

            PreparedStatement stmt = null;
            ResultSet rs = null;
            TTRGame game = null;
            try
            {
                db.startTransaction();
                String sql = "SELECT * FROM games WHERE games.owner = ?";
                stmt = db.connection.prepareStatement(sql);
                stmt.setInt(1, ownerID);
                rs = stmt.executeQuery();

                while (rs.next())
                {
                    String g = rs.getString(4);
                    game = (TTRGame) Serializer.deserialize(g);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
                return null;
            }  catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (stmt != null)
                        stmt.close();
                    if (rs != null)
                        rs.close();
                    db.closeTransaction(true);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            return game;
        }

    }
