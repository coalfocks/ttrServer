package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.User;
import com.example.tyudy.ticket2rideclient.common.cards.TrainCardCollection;
import com.example.tyudy.ticket2rideclient.common.iCommand;
import server.CommandQueue;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by colefox on 3/10/17.
 */

public class DiscardTrainCardsCommand extends Command implements iCommand, Serializable {
    public DiscardTrainCardsCommand(){}

    @Override
    public DataTransferObject execute()
    {
        CommandQueue.SINGLETON.addCommand(this);
        TTRServerFacade facade = new TTRServerFacade();
        facade.discardTrainCards(data);
        return data;
    }

}
