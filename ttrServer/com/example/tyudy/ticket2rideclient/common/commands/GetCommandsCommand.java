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
            if (index <= CommandQueue.SINGLETON.getCurrentIndex())
            {
                for (int i = index; i < CommandQueue.SINGLETON.getCurrentIndex(); i++)
                {
                    //TODO: check for correct game, only 1 init
                    commands.add(CommandQueue.SINGLETON.getCommand(i));
                }
                data.setData(Serializer.serialize(commands));

            }
            else {
                ResetIndexCommand reset = new ResetIndexCommand();
                data.setCommand("reset");
                String newIndex = String.valueOf(CommandQueue.SINGLETON.getCurrentIndex());
                if (Integer.parseInt(newIndex) < 0) {
                    newIndex = "0";
                }
                data.setData(newIndex);
                reset.setData(data);
                commands.add(reset);
            }
        } catch (Exception e) {
            data.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return data;
    }
}
