package com.example.tyudy.ticket2rideclient.common;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by colefox on 1/20/17.
 */
public class DataTransferObject implements Serializable
{
    private int playerID;
    private String command;
    private String data;
    private String errorMsg;
    private JsonObject obj;

    public DataTransferObject(String c, String d, String e, JsonObject o)
    {
        command = c;
        data = d;
        errorMsg = e;
        obj = o;
    }

    public DataTransferObject(DataTransferObject d)
    {
        if (d != null)
        {
            command = d.getCommand();
            data = d.getData();
            errorMsg = d.getErrorMsg();
            playerID = d.getPlayerID();
            obj = d.getJsonObj();
        }
        else
        {
            command = "";
            data = "";
            errorMsg = "";
            obj = null;
        }
    }

    public DataTransferObject()
    {
        command = "";
        data = "";
        errorMsg = "";
        obj = null;
    }

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public int getPlayerID()
    {
        return playerID;
    }
    
    public void setPlayerID(int id)
    {
        this.playerID = id;
    }

    public JsonObject getJsonObj() { return obj; }
}
