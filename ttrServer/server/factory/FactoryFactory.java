package server.factory;

/**
 * Created by Trevor on 4/17/2017.
 */

public class FactoryFactory {

    public static IDaoFactory createFactory(String type){
        if (type.equals("-m"))
        {
            System.out.print("Using MongoDB\n");
            return new MongoDaoFactory();
        }
        else if (type.equals("-s"))
        {
            System.out.print("Using SQLite\n");
            return new SqlDaoFactory();
        }
        else
        {
            return null;
        }
    }
}
