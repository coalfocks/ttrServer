package server.interfaces;

import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;

/**
 * Created by Trevor on 4/17/2017.
 */

public interface IDaoFactory {

    IUserDAO createUserDAO();
    IGameDAO createGameDAO();
}
