package server.Utils;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.mongodb.*;
import server.Database.DAOHolder;
import server.Database.MongoGameDAO;
import server.Database.MongoObjectConverter;
import server.Database.MongoUserDAO;

import java.net.UnknownHostException;
import java.util.ArrayList;

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
        //DB mongoDB = mongoClient.getDB("TysTestDB"); // Will be created if it doesn't already exist
        //TODO: Figure out where the above DB was created.

        // CREATE (first time)OR GET A NEW COLLECTION (like a table but not)--------------------------------------------
        //DBCollection tysUserCollection = mongoDB.getCollection("users");
        //DBCollection tysGameCollection = mongoDB.getCollection("games");

        // Create user and game that we will store in the database
        // Set all the fields the user that we end up storing in the database
        DAOHolder.getInstance().setDb(mongoClient.getDB("TysTestDB"));
        DAOHolder.getInstance().setUserDAO(MongoUserDAO.getInstance());
        User cole = new User();
        cole.setUsername("cole");
        cole.setPlayerID(69);
        User ty = new User();
        ty.setUsername("ty");
        ty.setPlayerID(17);
        MongoUserDAO.getInstance().addUser(cole);
        MongoUserDAO.getInstance().addUser(ty);
        MongoGameDAO.getInstance().createGame(69);
        MongoGameDAO.getInstance().createGame(17);
        // Use custom made class to convert user to a DBObject and insert it into the table

        /**
         * =========================================================================================
         * The following is code to retrieve data from the table and create a user or game
         * object from it. Tested by printing out the user and games values with System.out,
         * followed by the close() call that must be made when we are done accessing the MongoClient
         * =========================================================================================
         */


        ArrayList<TTRGame> games = MongoGameDAO.getInstance().getGames(12);
        System.out.print(games.size());


       // mongoClient.close(); // Closes the connection for good as long as the program is running, only do this after all read/writes

    }

    public static void runTysTest(){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient();
        } catch (Exception e){
            e.printStackTrace();
        }
        DAOHolder.getInstance().setDb(mongoClient.getDB("TysTestDB"));
        MongoUserDAO dao = (MongoUserDAO) MongoUserDAO.getInstance();
        User ty = new User();
        ty.setInGame(5);
        ty.setUsername("Tyler");
        ty.setPassword("myPass");
        ty.setPlayerID(22);

        dao.addUser(ty);
        dao.updatePlayerGame( 1700, ty.getPlayerID());

        User retUser = dao.getUser(ty.getUsername());

        System.out.print("Username: " + retUser.getUsername() + "\n");
        System.out.print("Password: " + retUser.getPassword() + "\n");
        System.out.print("inGame: " + retUser.getInGame() + "\n");
        System.out.print("Player ID: " + retUser.getPlayerID() + "\n");
    }
}
