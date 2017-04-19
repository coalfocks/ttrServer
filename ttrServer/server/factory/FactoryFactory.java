package server.factory;

import server.interfaces.IDaoFactory;

/**
 * Created by Trevor on 4/17/2017.
 */

public class FactoryFactory {

    public static IDaoFactory createFactory(String type){
        if (type.equals("-m"))
        {

            try {
                String className = "server.factory.MongoDaoFactory";
                Class aClass = Class.forName(className);
                return (MongoDaoFactory)aClass.newInstance();
            } catch (Exception e){
                e.printStackTrace();
                System.err.print("You aren't as smart as you thought...");
            }
            return null;

        }
        else if (type.equals("-s"))
        {
            try {
                String className = "server.factory.SqlDaoFactory";
                Class aClass = Class.forName(className);
                return (MongoDaoFactory)aClass.newInstance();
            } catch (Exception e){
                e.printStackTrace();
                System.err.print("You aren't as smart as you thought...");
            }
            return null;
        }
        else
        {
            return null;
        }
    }
}
