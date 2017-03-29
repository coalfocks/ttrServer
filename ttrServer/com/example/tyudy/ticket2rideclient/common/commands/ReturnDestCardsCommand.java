package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by colefox on 3/10/17.
 */

public class ReturnDestCardsCommand extends Command implements iCommand, Serializable {
    public ReturnDestCardsCommand(){}

    @Override
    public DataTransferObject execute()
    {
        try
        {
            TTRServerFacade facade = new TTRServerFacade();
            facade.sendBackDestCards(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
