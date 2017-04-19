package server.interfaces;

/**
 * Created by Trevor on 4/17/2017.
 */

public interface IDaoFactory {

    IUserDAO createUserDAO();
    IGameDAO createGameDAO();
}
