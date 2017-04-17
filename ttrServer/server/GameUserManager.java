package server;


import com.example.tyudy.ticket2rideclient.common.*;
import com.example.tyudy.ticket2rideclient.common.cities.Path;
import com.example.tyudy.ticket2rideclient.common.decks.DestinationCardDeck;
import com.example.tyudy.ticket2rideclient.common.decks.TrainCardDeck;
import server.Database.DAO;
import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.Database.dao.SqlGameDAO;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by colefox on 2/6/17.
 */
public class GameUserManager
{
    private static GameUserManager instance;
    private DAO dao = DAO.getInstance();
    
    private IGameDAO gameDAO;
    private IUserDAO userDAO;

    private GameUserManager(){}

    public void setGameDAO(IGameDAO dao) { gameDAO = dao; }

    public void setUserDAO(IUserDAO dao) { userDAO = dao; }

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
             user = userDAO.getUser(s);
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

            userDAO.addUser(u);
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
            return gameDAO.createGame(ownerID);

        } catch (Exception e)
        {
            return 0;
        }
    }

    public ArrayList<TTRGame> getGames(int playerID)
    {
        try
        {
            User u = userDAO.getUser(playerID);
            return gameDAO.getGames(u.getInGame());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean joinGame(String gstring, int playerID)
    {
        try
        {
            TTRGame gameIn = (TTRGame) Serializer.deserialize(gstring);
            int gameID = gameIn.getGameID();
            //int gameID = dao.getGameID(gstring);

            if (gameDAO.getGameStatus(gameID) == 1)
            {
                return false;
            }
            TTRGame game = gameDAO.getGame(gameID);
            User player = userDAO.getUser(playerID);
            game.addPlayer(player);
            userDAO.addPlayerToGame(gameID, Serializer.serialize(game));

            if (userDAO.updatePlayerGame(gameID, playerID))
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

    public boolean joinGame(int gameID, int playerID)
    {
        try
        {
            //int gameID = dao.getGameID(gstring);
            if (gameDAO.getGameStatus(gameID) == 1)
            {
                return false;
            }
            TTRGame game = gameDAO.getGame(gameID);
            User player = userDAO.getUser(playerID);
            game.addPlayer(player);
            userDAO.addPlayerToGame(gameID, Serializer.serialize(game));
            if (userDAO.updatePlayerGame(gameID, playerID))
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
        return gameDAO.getGame(gameID);
    }

    public boolean startGame(int ownerID)
    {
        DataTransferObject dto = new DataTransferObject();
        try
        {
            gameDAO.startGame(ownerID);
        } catch (Exception e) {
            dto.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public boolean endGame(int ownerID)
    {
        return gameDAO.endGame(ownerID);
    }

    public int getNumPlayers(int gameID)
    {
        return gameDAO.getNumPlayers(gameID);
    }



    public TTRGame initializeGame(TTRGame game) {
        game.setMyTrainDeck( new TrainCardDeck());
        game.setMyDestDeck( new DestinationCardDeck());
//        game.setInProgress(1);
        ArrayList<User> myUsers = new ArrayList<User> (game.getUsers());
        for (User u : myUsers) {
            for (int i = 0; i < 3; i++) {
                game.dealDestCard(u);
            }
            for (int i = 0; i < 4; i++) {
                game.dealTrainCard(u.getPlayerID());
            }
            u.setmTrains(new PlasticTrainCollection());
        }

        for(int i = 0; i < myUsers.size(); i++) {
            switch (i) {
                case 0:
                    myUsers.get(i).setColor(ColorENUM.RED);
                    break;
                case 1:
                    myUsers.get(i).setColor(ColorENUM.YELLOW);
                    break;
                case 2:
                    myUsers.get(i).setColor(ColorENUM.PURPLE);
                    break;
                case 3:
                    myUsers.get(i).setColor(ColorENUM.BLUE);
                    break;
                case 4:
                    myUsers.get(i).setColor(ColorENUM.GREEN);
                    break;
            }
        }

        game.setUsers(new TreeSet<User>(myUsers));
        gameDAO.updateGame(game);
        return game;
    }

    public Path claimPath(int playerID, Path path) {
        try
        {
            User user = userDAO.getUser(playerID);
            int gameID = user.getInGame();
            TTRGame game = gameDAO.getGame(gameID);
            for(User u: game.getUsers()){
                if(u.getPlayerID() == playerID){
                    path.setOwner(u);
                    u.claimPath(path);
                    u.getmTrains().removeTrains(path.getDistance());
                }
            }

//            //game.updateClaimedPath(path);
//
//            // Update the points for whoever claimed the path
//            for (User u : game.getUsers()) {
//                if (u.getPlayerID() == path.getOwner().getPlayerID()) {
//                    u.addPoints(path.getPoints());
//                }
//            }
            gameDAO.updateGame(game);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
