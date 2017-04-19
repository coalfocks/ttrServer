import server.*;

import server.Database.dao.IGameDAO;
import server.Database.dao.IUserDAO;
import server.factory.FactoryFactory;
import server.interfaces.IDaoFactory;

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
        //MongoTester.runTest();
        //MongoTester.runTysTest();
        //server.run();

        String dbType = args[0];
        IDaoFactory daoFactory = FactoryFactory.createFactory(dbType);

        if (daoFactory != null)
        {
            IGameDAO gameDAO = daoFactory.createGameDAO();
//            IUserDAO userDAO = daoFactory.createUserDAO();
//
//            GameUserManager.getInstance().setGameDAO(gameDAO);
//            GameUserManager.getInstance().setUserDAO(userDAO);
//
//            server.run();
            IGameDAO gameDAO = daoFactory.createGameDAO();
            IUserDAO userDAO = daoFactory.createUserDAO();

            //GameUserManager.getInstance().setGameDAO(gameDAO);
            //GameUserManager.getInstance().setUserDAO(userDAO);

            server.run();
        }
        else
        {
            System.out.println("Please enter the database type (-s or -m)");
        }
    }
}
