import com.example.tyudy.ticket2rideclient.common.*;
import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cities.City;
import com.example.tyudy.ticket2rideclient.common.cities.Path;
import com.example.tyudy.ticket2rideclient.common.commands.SendChatCommand;
import server.*;
import server.Database.Database;

/**
 * Created by colefox on 2/9/17.
 */
public class
main
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

        //server.stop();
    }
}
