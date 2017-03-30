package com.example.tyudy.ticket2rideclient.common.commands;


import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.iCommand;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;

import java.io.Serializable;

/**
 * Created by Trevor on 2/11/2017.
 */
public class ListGamesCommand extends Command implements iCommand, Serializable
{
    public ListGamesCommand(){}

    @Override
    public DataTransferObject execute()
    {
        TTRServerFacade facade = new TTRServerFacade();
        data = facade.listGames(data);
        return data;
    }

<<<<<<< HEAD
=======

    public void setData(DataTransferObject d)
    {
        super.setData(d);
        this.data = d;
    }

    public DataTransferObject getData(){
        return data;
    }
>>>>>>> master
}
