package server.Database;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import server.Serializer;
import server.interfaces.IGameDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by colefox on 4/18/17.
 */
public class SQLGameDAO implements IGameDAO {

    private SQLUserDao userDAO;

    @Override
    public int createGame(int ownerID)
    {
        int gameID = 0;

        if (ownerID == 0)
        {
            return 0;
        }

        TTRGame game = new TTRGame();
        game.setOwnerID(ownerID);
        game.setInProgress(0);
        game.setGameID(gameID);

        ResultSet rs = null;
        PreparedStatement stmt = null;
        try
        {
            User u = getUser(ownerID);
            game.setOwnerUsername(u.getUsername());
            String g = Serializer.serialize(game);
            String sql = "INSERT OR IGNORE INTO games (owner, inProgress, game)" +
                    "VALUES (?,?,?);";
            db.startTransaction();
            stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, ownerID);
            stmt.setInt(2, 0);
            stmt.setString(3, g);

            stmt.executeUpdate();

            String sql2 = "SELECT gameID FROM games WHERE games.game = ?";
            PreparedStatement stmt2 = db.connection.prepareStatement(sql2);
            stmt2.setString(1,g);
            rs = stmt2.executeQuery();
            gameID = rs.getInt(1);
            db.closeTransaction(true);

            game.setGameID(gameID);
            String sql3 = "UPDATE games" +
                    " SET game = ?" +
                    " WHERE gameID = ?";
            db.startTransaction();
            stmt = db.connection.prepareStatement(sql3);
            stmt.setString(1, Serializer.serialize(game));
            stmt.setInt(2, gameID);

            stmt.executeUpdate();

        }

    @Override
    public TTRGame getGame(int gameID)
    {
        return null;
    }

    @Override
    public ArrayList<TTRGame> getGames(int gameID)
    {
        return null;
    }

    @Override
    public boolean startGame(int ownerID)
    {
        return false;
    }

    @Override
    public boolean endGame(int ownerID)
    {
        return false;
    }

    @Override
    public int getGameStatus(int gameID)
    {
        return 0;
    }

    @Override
    public int getNumPlayers(int gameID)
    {
        return 0;
    }

    @Override
    public int getGameID(String gstring) throws SQLException
    {
        return 0;
    }

    @Override
    public boolean addChatMessage(String username, int gameID, String chatMessage)
    {
        return false;
    }

    @Override
    public boolean updateGame(TTRGame game)
    {
        return false;
    }

    @Override
    public TTRGame getGameByOwner(int ownerID)
    {
        return null;
    }
}
