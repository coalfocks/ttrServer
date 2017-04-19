package server.factory;

import server.Database.DAOHolder;
import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.Database.dao.SqlGameDAO;
import server.Database.dao.SqlUserDAO;
import server.interfaces.IDaoFactory;

/**
 * Created by Trevor on 4/17/2017.
 */

public class SqlDaoFactory implements IDaoFactory {

    public SqlDaoFactory() {
        DAOHolder.getInstance().setUserDAO(server.Database.SQLUserDAO.getInstance());
        DAOHolder.getInstance().setGameDAO(server.Database.SQLGameDAO.getInstance());
    }

    @Override
    public IGameDAO createGameDAO() {
        return new SqlGameDAO();
    }

    @Override
    public IUserDAO createUserDAO() {
        return new SqlUserDAO();
    }
}
