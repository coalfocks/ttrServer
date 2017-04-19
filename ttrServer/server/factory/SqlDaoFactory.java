package server.factory;

import com.sun.jdi.ClassNotPreparedException;
import server.Database.DAOHolder;
import server.Database.MongoUserDAO;
import server.Database.SQLGameDAO;
import server.Database.SQLUserDAO;
import server.interfaces.IDaoFactory;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

/**
 * Created by Trevor on 4/17/2017.
 */

public class SqlDaoFactory implements IDaoFactory {

    public SqlDaoFactory() {
        DAOHolder.getInstance().setGameDAO(server.Database.SQLGameDAO.getInstance());
        DAOHolder.getInstance().setUserDAO(server.Database.SQLUserDAO.getInstance());
    }

    @Override
    public IGameDAO createGameDAO() {
        IGameDAO gameDAO = (SQLGameDAO) DAOHolder.getInstance().getGameDAO();
        if (gameDAO.getClass() != SQLGameDAO.class){
            throw new ClassNotPreparedException();
        }
        return gameDAO;
    }

    @Override
    public IUserDAO createUserDAO() {
        IUserDAO userDAO = (SQLUserDAO) DAOHolder.getInstance().getUserDAO();
        if (userDAO.getClass() != SQLUserDAO.class){
            throw new ClassNotPreparedException();
        }
        return userDAO;
    }
}
