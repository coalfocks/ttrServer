package server.Database;

import com.example.tyudy.ticket2rideclient.common.User;
import server.interfaces.IUserDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tyudy on 4/18/17.
 */
public class SQLUserDAO implements IUserDAO {

    private static Database db;
    private static SQLUserDAO instance;

    private SQLUserDAO(){
        db = Database.getInstance();
    }

    public static IUserDAO getInstance() {
        if (instance == null) {
            instance = new SQLUserDAO();
        }
        return instance;
    }

    @Override
    public boolean addUser(User u) {
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

    @Override
    public User getUser(String username) throws SQLException{

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

    @Override
    public User getUser(int playerID) throws SQLException{
        if (playerID == 0)
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
            stmt.setInt(1, playerID);
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

    @Override
    public boolean updatePlayerGame(int gameID, int userID) {
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
}
