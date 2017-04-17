package server.factory;

import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.Database.dao.SqlGameDAO;
import server.Database.dao.SqlUserDAO;

/**
 * Created by Trevor on 4/17/2017.
 */

public class SqlDaoFactory implements IDaoFactory {

    @Override
    public IGameDAO createGameDAO() {
        return new SqlGameDAO();
    }

    @Override
    public IUserDAO createUserDAO() {
        return new SqlUserDAO();
    }
}
