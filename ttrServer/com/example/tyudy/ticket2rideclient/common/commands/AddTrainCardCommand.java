package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by colefox on 3/10/17.
 */

public class AddTrainCardCommand extends Command implements iCommand, Serializable {
    public AddTrainCardCommand(){}

    @Override
    public DataTransferObject execute()
    {
        TTRServerFacade facade = new TTRServerFacade();
        data = facade.addTrainCard(data);
        return data;
    }

<<<<<<< HEAD
=======

    public void setData(DataTransferObject d)
    {
        this.data = d;
    }

    public DataTransferObject getData(){
        return data;
    }
>>>>>>> master
}
