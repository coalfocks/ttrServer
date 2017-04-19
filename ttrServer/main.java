import server.*;

import server.Database.DAOHolder;
import server.Utils.MongoTester;
import server.factory.FactoryFactory;
import server.interfaces.IDaoFactory;
import server.interfaces.IGameDAO;
import server.interfaces.IUserDAO;

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
//            IGameDAO gameDAO = daoFactory.createGameDAO();
//            IUserDAO userDAO = daoFactory.createUserDAO();
//
//            GameUserManager.getInstance().setGameDAO(gameDAO);
//            GameUserManager.getInstance().setUserDAO(userDAO);
//
//            server.run();
            // Store the new DAOs in the DAOHolder class singleton
            daoFactory.createGameDAO();
            daoFactory.createUserDAO();

            MongoTester.runTysTest();

            // Check to see if we need to clear the database
            if (args.length >= 2 && args[1].equals("-w")){
                clearDatabase();
            }

            //GameUserManager.getInstance().setGameDAO(gameDAO);
            //GameUserManager.getInstance().setUserDAO(userDAO);

            //server.run();
        }
        else
        {
            System.out.println("Please enter the database type (-s or -m)");
        }
    }

    private static void clearDatabase(){
        DAOHolder.getInstance().getGameDAO().removeAll();
        DAOHolder.getInstance().getUserDAO().removeAll();
        System.out.print("Cleared the database!");
    }
}
