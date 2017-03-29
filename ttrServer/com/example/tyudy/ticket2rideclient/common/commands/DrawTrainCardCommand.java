package com.example.tyudy.ticket2rideclient.common.commands;


import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by colefox on 3/10/17.
 */

public class DrawTrainCardCommand extends Command implements iCommand, Serializable {
    public DrawTrainCardCommand(){}
    private DataTransferObject data;

    @Override
    public DataTransferObject execute()
    {
        try
        {
            TTRServerFacade facade = new TTRServerFacade();
            data = facade.drawTrainCard(data);
        } catch (Exception e) {
        }
        return data;
    }


    public void setData(DataTransferObject d)
    {
        super.setData(d);
        this.data = d;
    }
}
