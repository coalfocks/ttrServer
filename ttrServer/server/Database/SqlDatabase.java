package server.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Trevor on 4/17/2017.
 */

public class SqlDatabase extends Database {
    public Connection connection;

    public SqlDatabase()
    {
        loadDriver();
    }

    /**
     * Load the driver to talk to the database
     */
    public void loadDriver()
    {
        try
        {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            System.out.print("Class Not found error\n");
        }
    }
    /**
     * Open a connection with the data base
     */
    public void openConnection()
    {
        //String dbName = "database.sqlite";
        File directory = new File("db");
        if(!directory.exists())
        {
            try
            {
                directory.mkdirs();
            }
            catch(SecurityException se)
            {
                System.out.println("Error creating DB folder... program will not execute");
                return;
            }
        }

        String dbName = "db" + File.separator + "database.sqlite";
        String connectionURL = "jdbc:sqlite:" + dbName;
        connection = null;

        try
        {
            connection = DriverManager.getConnection(connectionURL);
            createIfNotExist();
        }
        catch(SQLException e)
        {
            System.out.print("SQL error\n");
            e.printStackTrace();
            System.out.print(e);
        }
        return;


    }

    public void startTransaction()
    {
        openConnection();
        try
        {
            connection.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            System.out.print("turn off auto commit error");
            e.printStackTrace();
        }
    }

    public void closeTransaction(boolean commit)
    {
        try
        {
            if(commit)
            {
                connection.commit();
            }
            else
            {
                connection.rollback();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.print("Close transaction commit error\n");
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch (SQLException e) {
                System.out.println("Cant close connection");
                e.printStackTrace();
            }
        }
        connection = null;
    }

    public void createIfNotExist() throws SQLException
    {

        String sql = "CREATE TABLE IF NOT EXISTS users"+
                "("+
                "playerID INTEGER PRIMARY KEY autoincrement," +
                "username varchar(64),"+
                "password varchar(64),"+
                "inGame INTEGER" +
                ");";

        PreparedStatement stmt = this.connection.prepareStatement(sql);
        stmt.executeUpdate();

        String sql2 = "CREATE TABLE IF NOT EXISTS games"+
                "("+
                "gameID INTEGER PRIMARY KEY autoincrement," +
                "owner varchar(64)," +
                "inProgress TINYINT," +
                "game TEXT" +
                ");";

        PreparedStatement stmt2 = this.connection.prepareStatement(sql2);
        stmt2.executeUpdate();

        String sql3 = "CREATE TABLE IF NOT EXISTS chat"+
                "("+
                "gameID INTEGER," +
                "player varchar(64)," +
                "chatMessage TEXT" +
                ");";

        PreparedStatement stmt3 = this.connection.prepareStatement(sql3);
        stmt3.executeUpdate();

    }
}
