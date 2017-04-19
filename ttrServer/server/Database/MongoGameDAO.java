package server.Database;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import server.interfaces.IGameDAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by colefox on 4/18/17.
 */
public class MongoGameDAO implements IGameDAO {

    @Override
    public int createGame(int ownerID)
    {
        return 0;
    }

    @Override
    public TTRGame getGame(int gameID)
    {
        return null;
    }

    @Override
    public ArrayList<TTRGame> getGames(int gameID)
    {
        return null;
    }

    @Override
    public boolean startGame(int ownerID)
    {
        return false;
    }

    @Override
    public boolean endGame(int ownerID)
    {
        return false;
    }

    @Override
    public int getGameStatus(int gameID)
    {
        return 0;
    }

    @Override
    public int getNumPlayers(int gameID)
    {
        return 0;
    }

    @Override
    public int getGameID(String gstring) throws SQLException
    {
        return 0;
    }

    @Override
    public boolean addChatMessage(String username, int gameID, String chatMessage)
    {
        return false;
    }

    @Override
    public boolean updateGame(TTRGame game)
    {
        return false;
    }

    @Override
    public TTRGame getGameByOwner(int ownerID)
    {
        return null;
    }
}
