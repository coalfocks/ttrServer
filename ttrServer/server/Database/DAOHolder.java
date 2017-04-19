package server.Database;

import com.mongodb.DB;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

/**
 * Created by colefox on 4/18/17.
 */
public class DAOHolder {
    private static final DAOHolder instance = new DAOHolder();
    private IUserDAO userDAO;
    private IGameDAO gameDAO;
    private DB db;

    private DAOHolder() {
        return;
    }

    public static DAOHolder getInstance() {
        return instance;
    }

    public IUserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(IUserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public IGameDAO getGameDAO()
    {
        return gameDAO;
    }

    public void setGameDAO(IGameDAO gameDAO)
    {
        this.gameDAO = gameDAO;
    }

    public DB getDb()
    {
        return db;
    }

    public void setDb(DB db)
    {
        this.db = db;
    }
}
