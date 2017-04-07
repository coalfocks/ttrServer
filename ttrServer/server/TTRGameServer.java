package server;

import com.example.tyudy.ticket2rideclient.common.ColorENUM;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cards.FaceUpCards;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCardCollection;
import server.Database.DAO;

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
    public DataTransferObject endGame(DataTransferObject data, TTRGame game)
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
    public DataTransferObject changeTurn(DataTransferObject data) {
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

    @Override
    public DataTransferObject changeToLastTurn(DataTransferObject data) {
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

    public TrainCardCollection addTrainCard(int playerID, int gameID) {
        TTRGame game = dao.getGame(gameID);
        TrainCardCollection card = null;
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
                    u.getTrainCards().set(i, new TrainCardCollection(ColorENUM.COLORLESS));
                }
                for (int i = 0; i < u.getDestCards().size(); i++) {
                    u.getDestCards().set(i, new DestinationCard(null, 0));
                }
            }
        }
        return game;
    }

    public void sendBackDestCards(ArrayList<DestinationCard> toReturn, ArrayList<DestinationCard> toUpdate, int playerID, boolean init) {
        try
        {
            User user = DAO.getInstance().getUser(playerID);
            TTRGame game = DAO.getInstance().getGame(user.getInGame());
            for (DestinationCard card : toReturn)
            {
                game.getDestDiscardDeck().addCard(card);
            }

            for (User u : game.getUsers())
            {
                if (u.getPlayerID() == playerID)
                {
                    if (init)
                    {
                        u.setDestCards(new ArrayList<DestinationCard>());
                    }
                    for (DestinationCard card : toUpdate)
                    {
                        u.addDestinationCard(card);
                    }
                }
            }
            DAO.getInstance().updateGame(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FaceUpCards getFaceUps(int gameID) {
        TTRGame game = dao.getGame(gameID);
        return game.getMyTrainDeck().getFaceUpCards();
    }

    public FaceUpCards selectTrainCard(int gameID, int playerID, int cardID) {
        TTRGame game = dao.getGame(gameID);
        TrainCardCollection card;
        switch (cardID) {
            case 1 :
                card = game.getMyTrainDeck().getFaceUpCards().getCard1();
                break;
            case 2 :
                card = game.getMyTrainDeck().getFaceUpCards().getCard2();
                break;
            case 3 :
                card = game.getMyTrainDeck().getFaceUpCards().getCard3();
                break;
            case 4 :
                card = game.getMyTrainDeck().getFaceUpCards().getCard4();
                break;
            case 5 :
                card = game.getMyTrainDeck().getFaceUpCards().getCard5();
                break;
            default:
                card = new TrainCardCollection();
                break;
        }

        for (User u : game.getUsers()) {
            if (u.getPlayerID() == playerID) {
                u.addTrainCard(card);
            }
        }

        game.getMyTrainDeck().swapFaceUpCard(cardID);
        dao.updateGame(game);
        return game.getMyTrainDeck().getFaceUpCards();
    }
}
