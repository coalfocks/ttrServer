package server.Database.dao;

import com.example.tyudy.ticket2rideclient.common.User;

import server.Database.Database;

/**
 * Created by Trevor on 4/17/2017.
 */

public interface IUserDAO {

    boolean addPlayerToGame(int gameID, String game);
    boolean addUser(User user);
    boolean reset();
    boolean setDB(Database database);
    boolean updatePlayerGame(int gameID, int userID);
    User getUser(String username);
    User getUser(int userID);
    IUserDAO getInstance();
    Database getDB();
}
