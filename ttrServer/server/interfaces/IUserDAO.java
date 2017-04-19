package server.interfaces;

import com.example.tyudy.ticket2rideclient.common.User;

/**
 * Created by tyudy on 4/18/17.
 */
public interface IUserDAO {
    boolean addUser(User u);
    IUserDAO getInstance();
    User getUser(String username);
    User getUser(int playerID);
    boolean updatePlayerGame(int gameID, int userID);
}
