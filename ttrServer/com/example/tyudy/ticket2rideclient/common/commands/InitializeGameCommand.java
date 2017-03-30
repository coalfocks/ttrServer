package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by tyudy on 3/3/17.
 */
public class InitializeGameCommand extends Command implements iCommand, Serializable {

    @Override
    public DataTransferObject execute() {

        TTRServerFacade facade = new TTRServerFacade();
        data = facade.initializeGame(data);
        return data;

    }

}
