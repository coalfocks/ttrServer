package server.factory;

import com.mongodb.MongoClient;
import server.Database.DAOHolder;
import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.Database.dao.MongoGameDAO;
import server.Database.dao.MongoUserDAO;
import server.interfaces.IDaoFactory;

import java.net.UnknownHostException;

/**
 * Created by Trevor on 4/17/2017.
 */

public class MongoDaoFactory implements IDaoFactory {

    public MongoDaoFactory() {
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

        DAOHolder.getInstance().setDb(mongoClient.getDB("TysTestDB"));
        DAOHolder.getInstance().setUserDAO(server.Database.MongoUserDAO.getInstance());
        DAOHolder.getInstance().setGameDAO(server.Database.MongoGameDAO.getInstance());
    }

    @Override
    public IUserDAO createUserDAO() {
        return new MongoUserDAO();
    }

    @Override
    public IGameDAO createGameDAO() {
        return new MongoGameDAO();
    }
}
