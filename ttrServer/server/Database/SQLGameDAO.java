package server.Database;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import server.Serializer;
import server.interfaces.IGameDAO;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by colefox on 4/18/17.
 */
public class SQLGameDAO implements IGameDAO {

    private SQLUserDao userDAO;
    private static Database db;
    private static SQLGameDAO instance;

    private SQLGameDAO () {
        this.db = new Database();
    }

    public SQLGameDAO getInstance() {
        if (instance == null) {
            instance = new SQLGameDAO();
        }
        return instance;
    }

    @Override
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
            stmt2.setString(1, g);
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
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        finally {
            db.closeTransaction(true);
        }

        return gameID;
    }

    @Override
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

    @Override
    public ArrayList<TTRGame> getGames(int gameID) {

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

    @Override
    public boolean startGame(int ownerID) {
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

    @Override
    public boolean endGame(int ownerID) {
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

    @Override
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

    @Override
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

    @Override
    public int getGameID(String gstring) throws SQLException
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
        return gameID;    }

    @Override
    public boolean addChatMessage(String username, int gameID, String chatMessage)
    {
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

        return true;    }

    @Override
    public boolean updateGame(TTRGame game)
    {
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

    @Override
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
