package server.interfaces;

import com.example.tyudy.ticket2rideclient.common.User;

import java.sql.SQLException;

/**
 * Created by tyudy on 4/18/17.
 */
public interface IUserDAO {
    boolean addUser(User u);
    User getUser(String username) throws SQLException;
    User getUser(int playerID) throws SQLException;

    /**
     * Updates the game that the user is currently in to be gameID
     * @param gameID - game id of the new game that the user should be in
     * @param userID - id of the user who's current game we need to switch
     * @return - true if successful, false otherwise
     */
    boolean updatePlayerGame(int gameID, int userID);
    void removeAll();
}
