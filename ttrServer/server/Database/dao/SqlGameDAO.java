package server.Database.dao;

import com.example.tyudy.ticket2rideclient.common.TTRGame;

import java.util.ArrayList;

import server.Database.Database;

/**
 * Created by Trevor on 4/17/2017.
 */

public class SqlGameDAO implements IGameDAO {

    @Override
    public boolean addChatMessage(String username, int gameID, String message) {
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
    public boolean endGame(int ownerID) {
        return false;
    }

    @Override
    public boolean updateGame(TTRGame game) {
        return false;
    }

    @Override
    public TTRGame createGame(int ownerID) {
        return null;
    }

    @Override
    public TTRGame getGame(int gameID) {
        return null;
    }

    @Override
    public TTRGame getGameByOwner(int ownerID) {
        return null;
    }

    @Override
    public TTRGame startGame(int ownerID) {
        return null;
    }

    @Override
    public int getGameID(String game) {
        return 0;
    }

    @Override
    public int getGameStatus(int gameID) {
        return 0;
    }

    @Override
    public int getNumPlayers(int gameID) {
        return 0;
    }

    @Override
    public ArrayList<TTRGame> getGames() {
        return null;
    }

    @Override
    public Database getDB() {
        return null;
    }

    @Override
    public IGameDAO getInstance() {
        return null;
    }
}
