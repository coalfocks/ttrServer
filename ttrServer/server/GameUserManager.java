package server;


import com.example.tyudy.ticket2rideclient.common.Color;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import server.Database.DAO;

import java.util.ArrayList;

/**
 * Created by colefox on 2/6/17.
 */
public class GameUserManager
{
    private static GameUserManager instance;
    private DAO dao = DAO.getInstance();

    private GameUserManager(){}

    public static GameUserManager getInstance()
    {
        if (instance == null)
        {
            instance = new GameUserManager();
        }
        return instance;
    }

    public User getUser(String s)
    {
        User user = null;
        try
        {
             user = dao.getUser(s);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public boolean addUser(String username, String password)
    {
        try
        {
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);

            dao.addUser(u);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public int createGame(int ownerID)
    {
        try
        {
            int gameID = dao.createGame(ownerID);
            return gameID;

        } catch (Exception e)
        {
            return 0;
        }
    }

    public ArrayList<TTRGame> getGames()
    {
        return dao.getGames();
    }

    public boolean joinGame(String gstring, int playerID)
    {
        try
        {
            TTRGame gameIn = (TTRGame) Serializer.deserialize(gstring);
            int gameID = gameIn.getGameID();
            //int gameID = dao.getGameID(gstring);
            if (dao.getGameStatus(gameID) == 1)
            {
                return false;
            }
            TTRGame game = dao.getGame(gameID);
            User player = dao.getUser(playerID);
            if (player.getInGame() == 0 && dao.updatePlayerGame(gameID, playerID))
            {
                int turn = game.getNumPlayers() + 1;

                switch(turn)
                {
                    case 1:
                        player.setColor(-65536);
                        break;
                    case 2:
                        player.setColor(-256);
                        break;
                    case 3:
                        player.setColor(-65281);
                        break;
                    case 4:
                        player.setColor(-16711936);
                        break;
                    case 5:
                        player.setColor(-16776961);
                        break;
                }

                player.setInGame(gameID);
                game.addPlayer(player);
                dao.addPlayerToGame(gameID, Serializer.serialize(game));
            }
            if (game.getNumPlayers() >= 5)
            {
                startGame(game.getOwnerID());
            }
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean joinGame(int gameID, int playerID)
    {
        try
        {
            //int gameID = dao.getGameID(gstring);
            if (dao.getGameStatus(gameID) == 1)
            {
                return false;
            }
            TTRGame game = dao.getGame(gameID);
            User player = dao.getUser(playerID);
            int turn = game.getNumPlayers() + 1;

            switch(turn)
            {
                case 1:
                    player.setColor(-65536);
                    break;
                case 2:
                    player.setColor(-256);
                    break;
                case 3:
                    player.setColor(-65281);
                    break;
                case 4:
                    player.setColor(-16711936);
                    break;
                case 5:
                    player.setColor(-16776961);
                    break;
            }
            game.addPlayer(player);
            dao.addPlayerToGame(gameID, Serializer.serialize(game));
            if (dao.updatePlayerGame(gameID, playerID))
            {
                player.setInGame(gameID);
            }
            if (game.getNumPlayers() >= 5)
            {
                startGame(game.getOwnerID());
            }
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public TTRGame getGame(int gameID)
    {
        return dao.getGame(gameID);
    }

    public boolean startGame(int ownerID)
    {
        return dao.startGame(ownerID);
    }

    public boolean endGame(int ownerID)
    {
        return dao.endGame(ownerID);
    }

    public int getNumPlayers(int gameID)
    {
        return dao.getNumPlayers(gameID);
    }
}
