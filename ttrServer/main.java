import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.commands.SendChatCommand;
import server.*;
import server.Database.Database;

/**
 * Created by colefox on 2/9/17.
 */
public class main
{
    /* add username to User data field,
       only list games not in progress,
       start game when users >= 5,
       can't join if game in progress
       method to get gameID (maybe)*/

    public static void main(String[] args)
    {
        ServerCommunicator server = new ServerCommunicator();
        server.run();

//        SendChatCommand command = new SendChatCommand();
//        DataTransferObject dto = new DataTransferObject("sendChat", "hello test chat", "", null);
//        dto.setPlayerID(9);
//        command.setData(dto);
//        command.execute();

        //server.stop();
    }
}
