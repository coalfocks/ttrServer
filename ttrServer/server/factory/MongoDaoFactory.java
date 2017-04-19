package server.factory;

import server.Database.DAOHolder;
import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.Database.dao.MongoGameDAO;
import server.Database.dao.MongoUserDAO;

/**
 * Created by Trevor on 4/17/2017.
 */

public class MongoDaoFactory implements IDaoFactory {

    public MongoDaoFactory() {
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
