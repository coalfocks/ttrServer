package com.example.tyudy.ticket2rideclient.common.commands;


import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.iCommand;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import server.CommandQueue;

import java.io.Serializable;

/**
 * Created by Colefox on 2/10/2017.
 */
public class LastTurnCommand extends Command implements iCommand, Serializable {

    public LastTurnCommand(){}

    @Override
    public DataTransferObject execute()
    {
        TTRServerFacade facade = new TTRServerFacade();
        facade.changeTurn(data);
        return data;
    }

}
