package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by colefox on 3/10/17.
 */

public class ResetIndexCommand extends Command implements iCommand, Serializable {
    public ResetIndexCommand(){}

    @Override
    public DataTransferObject execute()
    {
        return data;
    }


}
