package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

import server.CommandQueue;

/**
 * Created by Trevor on 3/24/2017.
 */

public class NextTurnCommand extends Command implements iCommand, Serializable {
    private DataTransferObject data;

    @Override
    public DataTransferObject execute()
    {
        TTRServerFacade ttrServerFacade = new TTRServerFacade();
        data = ttrServerFacade.updateGameplay(data);
        CommandQueue.SINGLETON.addCommand(this);
        return data;
    }

}
