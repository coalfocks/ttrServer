package server;


import com.example.tyudy.ticket2rideclient.common.Color;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.example.tyudy.ticket2rideclient.common.commands.InitializeGameCommand;
import com.example.tyudy.ticket2rideclient.common.decks.DestinationCardDeck;
import com.example.tyudy.ticket2rideclient.common.decks.TrainCardDeck;
import server.Database.DAO;

import java.util.ArrayList;
import java.util.TreeSet;

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
        DataTransferObject dto = new DataTransferObject();
        try
        {
            dao.startGame(ownerID);
            TTRGame game = dao.getGameByOwner(ownerID);
            game = initializeGame(game);
            InitializeGameCommand command = new InitializeGameCommand();
            dto.setCommand("initGame");
            dto.setData(Serializer.serialize(game));
            dto.setPlayerID(-1);
            command.setData(dto);
            CommandQueue.SINGLETON.addCommand(command);

        } catch (Exception e) {
            dto.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public boolean endGame(int ownerID)
    {
        return dao.endGame(ownerID);
    }

    public int getNumPlayers(int gameID)
    {
        return dao.getNumPlayers(gameID);
    }


    public TTRGame initializeGame(TTRGame game) {
        game.setMyTrainDeck( new TrainCardDeck());
        game.setMyDestDeck( new DestinationCardDeck());


        ArrayList<User> myUsers = new ArrayList<User> (game.getUsers());
        for (User u : myUsers) {
            for(int i = 0; i < 3; i++) {
                game.dealDestCard(u);
            }
            for(int i = 0; i < 5; i++) {
                game.dealTrainCard(u);
            }
        }

        for(int i = 0; i < myUsers.size(); i++) {
            switch (i) {
                case 0:
                    myUsers.get(i).setColor(Color.RED);
                    break;
                case 1:
                    myUsers.get(i).setColor(Color.YELLOW);
                    break;
                case 2:
                    myUsers.get(i).setColor(Color.PURPLE);
                    break;
                case 3:
                    myUsers.get(i).setColor(Color.BLUE);
                    break;
                case 4:
                    myUsers.get(i).setColor(Color.GREEN);
                    break;
            }
        }

        game.setUsers(new TreeSet<User>(myUsers));

        return game;

    }
}
