package server.Database.dao;

import com.example.tyudy.ticket2rideclient.common.User;

import server.Database.Database;

/**
 * Created by Trevor on 4/17/2017.
 */

public class MongoUserDAO implements IUserDAO {

    @Override
    public boolean addPlayerToGame(int userID, String game) {
        return false;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public boolean setDB(Database database) {
        return false;
    }

    @Override
    public boolean updatePlayerGame(int gameID, int userID) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public User getUser(int userID) {
        return null;
    }

    @Override
    public IUserDAO getInstance() {
        return null;
    }

    @Override
    public Database getDB() {
        return null;
    }
}
