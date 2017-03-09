package server;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRGame;

/**
 * Created by colefox on 2/5/17.
 */
public interface iTTRServer {
    public DataTransferObject createGame(DataTransferObject data);

    public DataTransferObject startGame(DataTransferObject data);

    public DataTransferObject endGame(DataTransferObject data);

    public DataTransferObject joinGame(DataTransferObject data);

    public DataTransferObject login(DataTransferObject data);

    public DataTransferObject register(DataTransferObject data);

    public DataTransferObject listGames(DataTransferObject data);

    public DataTransferObject initializeGame(DataTransferObject data);

    public DataTransferObject sendChatMessage(DataTransferObject data);

    public DataTransferObject updateGameplay(DataTransferObject data);

}
