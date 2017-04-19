package server.factory;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

/**
 * Created by Trevor on 4/17/2017.
 */

public class FactoryFactory {

    public static IDaoFactory createFactory(String type){
        if (type.equals("-m"))
        {
            ClassLoader cl = new ClassLoader();

            try {
//                Class c = cl.loadClass("server.factory.MongoDaoFactory");
                String className = "server.factory.MongoDaoFactory";
                Class aClass = Class.forName(className);
                System.out.print("YOU GUYS ARE SO SMART!");
                return (MongoDaoFactory)aClass.newInstance();
            } catch (Exception e){
                e.printStackTrace();
                System.err.print("You aren't as smart as you thought...");
            }
            return null;

        }
        else if (type.equals("-s"))
        {
            return new SqlDaoFactory();
        }
        else
        {
            return null;
        }
    }
}
