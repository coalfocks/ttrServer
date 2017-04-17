package com.example.tyudy.ticket2rideclient.common.factory;

/**
 * Created by Trevor on 4/17/2017.
 */

public class FactoryFactory {

    public static IDaoFactory createFactory(String type){
        if (type.equals("m"))
        {
            return new MongoDaoFactory();
        }
        else if (type.equals("s"))
        {
            return new SqlDaoFactory();
        }
        else
        {
            return null;
        }
    }
}
