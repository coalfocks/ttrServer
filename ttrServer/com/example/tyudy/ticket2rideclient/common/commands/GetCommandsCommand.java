package com.example.tyudy.ticket2rideclient.common.commands;

import com.example.tyudy.ticket2rideclient.common.Command;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;

import com.example.tyudy.ticket2rideclient.common.iCommand;
import server.CommandQueue;
import server.Serializer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by colefox on 3/3/17.
 */
public class GetCommandsCommand extends Command implements iCommand, Serializable {
    private DataTransferObject data;

    @Override
    public void setData(DataTransferObject d) {
        data = d;
    }

    @Override
    public DataTransferObject execute() {
        try {
            ArrayList<Command> commands = new ArrayList<>();
            int index = Integer.parseInt(data.getData());
            for (int i = index; i < CommandQueue.SINGLETON.getCurrentIndex(); i++)
            {
                commands.add(CommandQueue.SINGLETON.getCommand(i));
            }
            data.setData(Serializer.serialize(commands));
        } catch (Exception e) {
            data.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
}
