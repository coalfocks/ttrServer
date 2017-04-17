package server.Database.dao;

import com.example.tyudy.ticket2rideclient.common.TTRGame;

import java.util.ArrayList;

import server.Database.Database;

/**
 * Created by Trevor on 4/17/2017.
 */

public interface IGameDAO {

    boolean addChatMessage(String username, int gameID, String message);
    boolean reset();
    boolean setDB(Database database);
    boolean endGame(int ownerID);
    boolean updateGame(TTRGame game);
    boolean startGame(int ownerID);
    TTRGame getGame(int gameID);
    TTRGame getGameByOwner(int ownerID);
    int createGame(int ownerID);
    int getGameID(String game);
    int getGameStatus(int gameID);
    int getNumPlayers(int gameID);
    ArrayList<TTRGame> getGames(int gameID);
    Database getDB();
    IGameDAO getInstance();
}
