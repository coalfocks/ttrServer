package com.example.tyudy.ticket2rideclient.common.commands;


import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRServerFacade;
import com.example.tyudy.ticket2rideclient.common.iCommand;

import java.io.Serializable;

/**
 * Created by colefox on 3/10/17.
 */

public class GetFaceUpCardsCommand extends Command implements iCommand, Serializable {
    public GetFaceUpCardsCommand(){}

    @Override
    public DataTransferObject execute()
    {
        try
        {
            TTRServerFacade facade = new TTRServerFacade();
            data = facade.getFaceUps(data);
        } catch (Exception e) {
        }
        return data;
    }
<<<<<<< HEAD

=======
>>>>>>> origin/do-train-cards
}
