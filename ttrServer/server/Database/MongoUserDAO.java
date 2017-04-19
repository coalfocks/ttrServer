package server.Database;

import com.example.tyudy.ticket2rideclient.common.User;
import com.mongodb.*;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

import java.net.UnknownHostException;

/**
 * Created by tyudy on 4/18/17.
 */
public class MongoUserDAO implements IUserDAO {

    private static final MongoUserDAO instance = new MongoUserDAO();
    private DB mongoDB;
    private DBCollection usersCollection;

    private MongoUserDAO(){
        mongoDB = DAOHolder.getInstance().getDb();
        usersCollection = mongoDB.getCollection("users");
    }

    public static IUserDAO getInstance() {
        return instance;
    }

    // TESTED AND WORKS
    @Override
    public boolean addUser(User user) {
        DBObject userDBObject = MongoObjectConverter.SINGLETON.userToDBObject(user);
        usersCollection.insert(userDBObject);
        return true;
    }

    // TESTED AND WORKS
    @Override
    public User getUser(String username) {
        DBObject userQuery = new BasicDBObject("username", username);
        DBCursor userCursor = usersCollection.find(userQuery);
        DBObject userDBObject = userCursor.one();
        User user = MongoObjectConverter.SINGLETON.dbObjectToUser(userDBObject);
        return user;
    }


    @Override
    public User getUser(int playerID) {
        DBObject userQuery = new BasicDBObject("_id", playerID);
        DBCursor userCursor = usersCollection.find(userQuery);
        DBObject userDBObject = userCursor.one();
        User user = MongoObjectConverter.SINGLETON.dbObjectToUser(userDBObject);
        return user;
    }

    @Override
    public boolean updatePlayerGame(int gameID, int userID){
        // Get user out from the database
        DBObject userQuery = new BasicDBObject("_id", userID);
        DBCursor userCursor = usersCollection.find(userQuery);
        DBObject userDBObject = userCursor.one();
        User user = MongoObjectConverter.SINGLETON.dbObjectToUser(userDBObject);

        // Change the user inGame field
        user.setInGame(gameID);

        // Put the user back in the database
        userDBObject = MongoObjectConverter.SINGLETON.userToDBObject(user);
        usersCollection.insert(userDBObject);
        return true;
    }
}
