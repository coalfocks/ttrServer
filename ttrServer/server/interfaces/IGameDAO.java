package server.interfaces;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import server.Database.DAO;
import server.Database.Database;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by tyudy on 4/18/17.
 */
public interface IGameDAO {

    int createGame(int ownerID);

    TTRGame getGame(int gameID);

    ArrayList<TTRGame> getGames(int gameID);

    boolean startGame(int ownerID);

    boolean endGame(int ownerID);

    int getGameStatus(int gameID);

    int getNumPlayers(int gameID);

    int getGameID (String gstring) throws SQLException;

    /**
     * chatMessage is the message that the user sent to the server. Add it to the database
     * @param username - the username of the player sending the chat
     * @param gameID - the game to which the chat belongs
     * @param chatMessage - String in the format "userName: some message for example Cole is lame and Ty is cool"
     */
    boolean addChatMessage(String username, int gameID, String chatMessage);

    boolean updateGame(TTRGame game);

    TTRGame getGameByOwner(int ownerID);

}
