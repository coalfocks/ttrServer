package server;

import com.example.tyudy.ticket2rideclient.common.ColorENUM;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCard;
import com.example.tyudy.ticket2rideclient.common.decks.DestinationCardDeck;
import server.Database.DAO;
import sun.security.krb5.internal.crypto.Des;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by colefox on 2/5/17.
 */
public class TTRGameServer implements iTTRServer
{

    private static TTRGameServer instance;
    private DAO dao = DAO.getInstance();

    private TTRGameServer() {}

    public static TTRGameServer getInstance()
    {
        if (instance == null)
        {
            instance = new TTRGameServer();
        }
        return instance;
    }

    @Override
    public DataTransferObject createGame(DataTransferObject data)
    {
        return null;
    }

    @Override
    public DataTransferObject startGame(DataTransferObject data)
    {
        return null;
    }

    @Override
    public DataTransferObject endGame(DataTransferObject data)
    {
        return null;
    }

    @Override
    public DataTransferObject joinGame(DataTransferObject data)
    {
        return null;
    }

    @Override
    public DataTransferObject login(DataTransferObject data)
    {
        return null;
    }

    @Override
    public DataTransferObject register(DataTransferObject data)
    {
        return null;
    }

    @Override
    public DataTransferObject listGames(DataTransferObject data) { return null; }

    @Override
    public DataTransferObject initializeGame(DataTransferObject data) {
        return null;
    }


    @Override
    public DataTransferObject sendChatMessage(DataTransferObject data) {
        return null;
    }

    @Override
    public DataTransferObject updateGameplay(DataTransferObject data) {
        return null;
    }

    @Override
    public DataTransferObject claimPath(DataTransferObject data) {
        return null;
    }

    @Override
    public DataTransferObject getCommands(DataTransferObject data) {
        return null;
    }

    @Override
    public DataTransferObject drawDestCard(DataTransferObject data)
    {
        return null;
    }

    public void addChat(String chatMessage, int playerID) {
        try
        {
            User u = dao.getUser(playerID);
            String username = u.getUsername();
            int gameID = u.getInGame();
            dao.addChatMessage(username, gameID, chatMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public TrainCard addTrainCard(int playerID, int gameID) {
        TTRGame game = dao.getGame(gameID);
        TrainCard card = null;
        for (User u : game.getUsers()) {
            if (u.getPlayerID() == playerID) {
                game.dealTrainCard(u.getPlayerID());
            }
        }
        dao.updateGame(game);
        return card;
    }

    public TTRGame maskGame(TTRGame game, int playerID) {
        for (User u : game.getUsers()) {
            if (u.getPlayerID() != playerID) {
                for (int i = 0; i < u.getTrainCards().size(); i++) {
                    u.getTrainCards().set(i, new TrainCard(ColorENUM.COLORLESS));
                }
                for (int i = 0; i < u.getDestCards().size(); i++) {
                    u.getDestCards().set(i, new DestinationCard(null, 0));
                }
            }
        }
        return game;
    }

    public void sendBackDestCards(ArrayList<DestinationCard> toReturn, ArrayList<DestinationCard> toUpdate, int playerID) {
        TTRGame game = DAO.getInstance().getGameByOwner(playerID);
        for (DestinationCard card : toReturn) {
            game.getMyDestDeck().addCard(card);
        }

        int size = 0;
        for (User u : game.getUsers()) {
            if (u.getPlayerID() == playerID) {
                for (DestinationCard card : toUpdate) {
                    u.addDestinationCard(card);
                }
            }
        }

        DAO.getInstance().updateGame(game);
    }
}
