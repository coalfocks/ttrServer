import com.example.tyudy.ticket2rideclient.common.*;
import com.example.tyudy.ticket2rideclient.common.cards.DestinationCard;
import com.example.tyudy.ticket2rideclient.common.cities.City;
import com.example.tyudy.ticket2rideclient.common.cities.Path;
import com.example.tyudy.ticket2rideclient.common.commands.SendChatCommand;
import server.*;
import server.Database.Database;
import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.factory.FactoryFactory;
import server.factory.IDaoFactory;

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

        String dbType = args[0];
        IDaoFactory daoFactory = FactoryFactory.createFactory(dbType);

        if (daoFactory != null)
        {
            IGameDAO gameDAO = daoFactory.createGameDAO();
            IUserDAO userDAO = daoFactory.createUserDAO();

            GameUserManager.getInstance().setGameDAO(gameDAO);
            GameUserManager.getInstance().setUserDAO(userDAO);

            server.run();
        }
        else
        {
            System.out.println("Please enter the database type (-s or -m)");
        }

        //server.stop();
    }
}
