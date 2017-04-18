package server.Database;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import server.Serializer;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by tyudy on 4/17/17.
 */
public class MongoObjectConverter {
    public static final MongoObjectConverter SINGLETON = new MongoObjectConverter();

    private MongoObjectConverter(){

    }

    public User dbObjectToUser(DBObject obj){
        int playerID = (int)obj.get("_id");
        String username = (String)obj.get("username");
        String password = (String)obj.get("password");
        //int inGame = (int)obj.get("inGame");

        User u = new User();
        u.setUsername(username);
        u.setPlayerID(playerID);
        u.setPassword(password);
        //u.setInGame(inGame);

        return u;
    }

    public DBObject userToDBObject(User user){
        return new BasicDBObject("_id", user.getPlayerID())
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("inGame", user.getInGame());
    }

    public DBObject gameToDBObject(TTRGame game){
        String gameData = "";
        try {
            gameData = Serializer.serialize(game);
        } catch (Exception e){
            e.printStackTrace();
            System.err.print("ERROR SERIALIZING GAME IN gameToDBObject function in MongoObjectConverter Class");
        }
        return new BasicDBObject("_id", game.getGameID())
                .append("owner", game.getOwnerID())
                .append("inProgress", game.getInProgress())
                .append("game", gameData);
    }

    public TTRGame dbObjectToGame(DBObject obj){

        TTRGame g = new TTRGame();

        try {
            g = (TTRGame) Serializer.deserialize((String)obj.get("game"));
        } catch (Exception e){
            e.printStackTrace();
            System.err.print("There was an error in the dbObjectToGame func converting DBObject in the MongoObjectConverter Class");
        }
        return g;
    }



}
