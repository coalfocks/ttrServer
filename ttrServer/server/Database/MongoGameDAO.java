package server.Database;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.mongodb.*;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by colefox on 4/18/17.
 */
public class MongoGameDAO implements IGameDAO {

    private static final MongoGameDAO instance = new MongoGameDAO();
    private IUserDAO userDAO = DAOHolder.getInstance().getUserDAO();
    private DB mongoDB;
    private DBCollection gamesCollection;
    private DBCollection chatsCollection;

    private MongoGameDAO() {
        mongoDB = DAOHolder.getInstance().getDb();
        gamesCollection = mongoDB.getCollection("games");
        chatsCollection = mongoDB.getCollection("chats");
    }

    private IGameDAO getInstance () {
        return instance;
    }

    @Override
    public int createGame(int ownerID)
    {
        if (ownerID == 0) {
            return 0;
        }

        User u = userDAO.getUser(ownerID);
        TTRGame game = new TTRGame();
        game.setOwnerID(ownerID);
        game.setInProgress(0);
        int count = (int) gamesCollection.getCount() + 1;
        game.setGameID(count);
        game.setOwnerUsername(u.getUsername());
        DBObject obj = MongoObjectConverter.SINGLETON.gameToDBObject(game);
        gamesCollection.insert(obj);
        return count;
    }

    @Override
    public TTRGame getGame(int gameID)
    {
        DBObject gameQuery = new BasicDBObject("_id", gameID);
        DBCursor gameCursor = gamesCollection.find(gameQuery);
        DBObject obj = gameCursor.one();
        return MongoObjectConverter.SINGLETON.dbObjectToGame(obj);
    }

    @Override
    public ArrayList<TTRGame> getGames(int gameID)
    {
        DBObject progressQuery = new BasicDBObject("inProgress", 0);
        DBObject idQuery = new BasicDBObject("game", gameID);
        ArrayList<TTRGame> games = gamesCollection.find($or:)
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
