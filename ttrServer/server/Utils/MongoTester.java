package server.Utils;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.mongodb.*;
import server.Database.MongoObjectConverter;

import java.net.UnknownHostException;

/**
 * Created by tyudy on 4/17/17.
 */
public final class MongoTester {

    public static void runTest(){

        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient(); // This is generally a singleton somewhere
        } catch (UnknownHostException e){
            System.err.print("UNKNOWN HOST");
        }

        if(mongoClient == null){
            System.err.print("The mongo database variable is null");
        } else {
            System.out.print("The mongo db was initialized!\n");
        }


        /**
         * =====================================================================================
         * The following is code to use to create/open a database, create/retrieve a collection,
         * and add an object to the collection
         * =====================================================================================
         */


        // CREATE (first time) OR GET A NEW DATABASE--------------------------------------------------------------------
        DB mongoDB = mongoClient.getDB("TysTestDB"); // Will be created if it doesn't already exist
        //TODO: Figure out where the above DB was created.

        // CREATE (first time)OR GET A NEW COLLECTION (like a table but not)--------------------------------------------
        DBCollection tysUserCollection = mongoDB.getCollection("users");
        DBCollection tysGameCollection = mongoDB.getCollection("games");

        // Create user and game that we will store in the database
        User ty = new User();
        TTRGame tysGame = new TTRGame();

        // Set all the fields the user that we end up storing in the database
        ty.setInGame(1);
        ty.setPlayerID(5);
        ty.setPassword("pass");
        ty.setUsername("T-swizzle");

        tysGame.setGameID(1738);
        tysGame.setOwnerID(5);
        tysGame.setInProgress(1);

        // Use custom made class to convert user to a DBObject and insert it into the table
        DBObject userDBObject = MongoObjectConverter.SINGLETON.userToDBObject(ty);
        DBObject gameDBObject = MongoObjectConverter.SINGLETON.gameToDBObject(tysGame);
        tysUserCollection.insert(userDBObject);
        tysGameCollection.insert(gameDBObject);

        /**
         * =========================================================================================
         * The following is code to retrieve data from the table and create a user or game
         * object from it. Tested by printing out the user and games values with System.out,
         * followed by the close() call that must be made when we are done accessing the MongoClient
         * =========================================================================================
         */

        DBObject userQuery = new BasicDBObject("_id", ty.getPlayerID());
        DBObject gameQuery = new BasicDBObject("_id", tysGame.getGameID());

        DBCursor userCursor = tysUserCollection.find(userQuery); // position the cursor at the user with the given id
        DBCursor gameCursor = tysGameCollection.find(gameQuery);

        DBObject userDBObjectFromDB = userCursor.one();
        DBObject gameDBObjectFromDB = gameCursor.one();

        User userFromDB = MongoObjectConverter.SINGLETON.dbObjectToUser(userDBObjectFromDB);
        TTRGame gameFromDB = MongoObjectConverter.SINGLETON.dbObjectToGame(gameDBObjectFromDB);

        System.out.print(userFromDB.getUsername() + " found in the database!\n");
        System.out.print("Game " + gameFromDB.getGameID() + " found in the database!\n");


        mongoClient.close(); // Closes the connection for good as long as the program is running, only do this after all read/writes

    }
}
