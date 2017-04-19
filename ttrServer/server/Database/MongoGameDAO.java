package server.Database;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.mongodb.*;
import server.Serializer;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        User u = null;
        try {
            u = userDAO.getUser(ownerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        BasicDBList or = new BasicDBList();
        or.add(progressQuery);
        or.add(idQuery);
        DBObject query = new BasicDBObject("$or", or);
        List<DBObject> dbgames = gamesCollection.find(query).toArray();
        ArrayList<TTRGame> games = new ArrayList<>();
        for (DBObject obj : dbgames) {
            games.add(MongoObjectConverter.SINGLETON.dbObjectToGame(obj));
        }
        return games;
    }

    @Override
    public boolean startGame(int ownerID)
    {
        gamesCollection.update(new BasicDBObject("owner", ownerID), new BasicDBObject("$set", new BasicDBObject("inProgress", 1)));
        return true;
    }

    @Override
    public boolean endGame(int ownerID)
    {
        gamesCollection.update(new BasicDBObject("owner", ownerID), new BasicDBObject("$set", new BasicDBObject("inProgress", 0)));
        return true;
    }

    @Override
    public int getGameStatus(int gameID)
    {
         DBCursor cursor = gamesCollection.find(new BasicDBObject("_id", gameID));
         DBObject obj = cursor.one();
         TTRGame game = MongoObjectConverter.SINGLETON.dbObjectToGame(obj);
         return game.getInProgress();
    }

    @Override
    public int getNumPlayers(int gameID)
    {
        DBCursor cursor = gamesCollection.find(new BasicDBObject("_id", gameID));
        DBObject obj = cursor.one();
        TTRGame game = MongoObjectConverter.SINGLETON.dbObjectToGame(obj);
        return game.getUsers().size();
    }

    @Override
    public int getGameID(String gstring) throws SQLException {
        DBCursor cursor = gamesCollection.find(new BasicDBObject("game", gstring));
        DBObject obj = cursor.one();
        TTRGame game = MongoObjectConverter.SINGLETON.dbObjectToGame(obj);
        return game.getGameID();
    }

    @Override
    public boolean addChatMessage(String username, int gameID, String chatMessage)
    {
        DBObject chatDBObject = new BasicDBObject("gameID", gameID)
                .append("player", username)
                .append("chatMessage", chatMessage);//gameID       player      chatMessage
        chatsCollection.insert(chatDBObject);
        return true;
    }

    @Override
    public boolean updateGame(TTRGame newGame)
    {
        String newGameData;
        try {
            newGameData = Serializer.serialize(newGame);
            gamesCollection.update(new BasicDBObject("_id", newGame.getGameID()), new BasicDBObject("$set", new BasicDBObject("game", newGameData)));
            return true;
        } catch (Exception e){
            System.err.print("SERIALIZATION ERROR: happened in updateGame func in MongoGameDAO");
        }

        return false;
    }

    @Override
    public TTRGame getGameByOwner(int ownerID)
    {
        DBObject gameQuery = new BasicDBObject("owner", ownerID);
        DBCursor gameCursor = gamesCollection.find(gameQuery);
        DBObject gameDBObject = gameCursor.one();
        TTRGame game = MongoObjectConverter.SINGLETON.dbObjectToGame(gameDBObject);
        return game;
    }
}
