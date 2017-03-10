package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.Command;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by tyudy on 3/9/17.
 */
public class ClaimPathCommand extends Command implements iCommand, Serializable {
    public ClaimPathCommand(){

    }

    private DataTransferObject data;

    @Override
    public DataTransferObject execute()
    {
        TTRServerFacade facade = new TTRServerFacade();
        data = facade.createGame(data);
        return data;
    }


    public void setData(DataTransferObject d)
    {
        this.data = d;
    }
}
