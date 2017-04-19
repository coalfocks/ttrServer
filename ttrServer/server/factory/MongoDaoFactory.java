package server.factory;

import com.mongodb.MongoClient;
import com.sun.jdi.ClassNotPreparedException;
import server.Database.DAOHolder;
import server.Database.MongoGameDAO;
import server.Database.MongoUserDAO;
import server.interfaces.IDaoFactory;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

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
        IUserDAO userDao = (MongoUserDAO) DAOHolder.getInstance().getUserDAO();
        if (userDao.getClass() != MongoUserDAO.class){
            throw new ClassNotPreparedException();
        }
        return userDao;
    }

    @Override
    public IGameDAO createGameDAO() {
        IGameDAO gameDao = (MongoGameDAO) DAOHolder.getInstance().getGameDAO();
        if (gameDao.getClass() != MongoGameDAO.class){
            throw new ClassNotPreparedException();
        }
        return gameDao;
    }
}
