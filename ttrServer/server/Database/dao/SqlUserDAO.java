package server.Database.dao;

import com.example.tyudy.ticket2rideclient.common.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.Database.Database;
import server.Database.SqlDatabase;

/**
 * Created by Trevor on 4/17/2017.
 */

public class SqlUserDAO implements IUserDAO {

    private static IUserDAO instance;
    private static SqlDatabase db;

    // DON'T HAVE THIS METHOD IN CURRENT DAO,
    // (do we need it?)
    @Override
    public boolean addPlayerToGame(int userID, String game) {
        return false;
    }

    @Override
    public boolean addUser(User user) {
        if (user == null)
        {
            return false;
        }

        PreparedStatement stmt;
        try
        {
            String sql = "INSERT OR IGNORE INTO users (username, password, inGame)" +
                    "VALUES (?,?,?" +
                    ");";
            db.startTransaction();
            stmt = db.connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
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
    public boolean reset() {
        try
        {
            PreparedStatement stmt;
            db.startTransaction();
            String sql = "DROP TABLE IF EXISTS users;";
            stmt = db.connection.prepareStatement(sql);
            stmt.executeUpdate();

            String sql2 = "CREATE TABLE IF NOT EXISTS users"+
                    "("+
                    "playerID INTEGER PRIMARY KEY autoincrement," +
                    "username varchar(64),"+
                    "password varchar(64),"+
                    "inGame INTEGER" +
                    ");";

            PreparedStatement stmt2 = db.connection.prepareStatement(sql2);
            stmt2.executeUpdate();

        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean setDB(Database database) {
        if (db == null) {
            db = (SqlDatabase) database;
            return true;
        }
        else {
            System.out.println("Current SqlDatabase is attempting to be overridden!\n" +
                "SqlUserDAO");
            return false;
        }
    }

    @Override
    public boolean updatePlayerGame(int gameID, int userID) {
        if (gameID == 0 || userID == 0)
        {
            return false;
        }

        PreparedStatement stmt;
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

    @Override
    public User getUser(String username) {
        if (username == null)
        {
            return null;
        }

        PreparedStatement stmt;
        ResultSet rs;
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
//            if(stmt != null)
//                stmt.close();
//            if (rs != null)
//                rs.close();
            db.closeTransaction(true);
        }

        return user;
    }

    @Override
    public User getUser(int userID) {
        if (userID == 0)
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
            stmt.setInt(1, userID);
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
//            if(stmt != null)
//                stmt.close();
//            if (rs != null)
//                rs.close();

            db.closeTransaction(true);
        }
        return user;
    }

    @Override
    public IUserDAO getInstance() {
        if (instance == null)
            instance = new SqlUserDAO();

        return instance;
    }

    @Override
    public Database getDB() {
        return db;
    }
}
