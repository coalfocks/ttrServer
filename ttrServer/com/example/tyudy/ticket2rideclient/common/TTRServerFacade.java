package com.example.tyudy.ticket2rideclient.common;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import server.*;

import java.util.ArrayList;

/**
 * Created by colefox on 2/5/17.
 */
public class TTRServerFacade implements iTTRServer
{
    private GameUserManager gameUserManager = GameUserManager.getInstance();
    private TTRGameServer gameServer = TTRGameServer.getInstance();
    private Gson gson = new Gson();

    @Override
    public DataTransferObject createGame(DataTransferObject data)
    {
        int gameID = gameUserManager.createGame(data.getPlayerID());
        data.setData(String.valueOf(gameID));
        return joinGame(data);

    }

    @Override
    public DataTransferObject startGame(DataTransferObject data)
    {
        data.setCommand("start");
        if (gameUserManager.startGame(data.getPlayerID()))
        {
            try
            {
                TTRGame game = gameUserManager.getGame(data.getPlayerID());
                String gstring = Serializer.serialize(game);
                data.setData(gstring);
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            data.setErrorMsg("An error occurred, couldn't start game");
        }
        return data;
    }

    @Override
    public DataTransferObject endGame(DataTransferObject data)
    {
        if (gameUserManager.endGame(data.getPlayerID()))
        {
            try
            {
                data.setData("Ended game");
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            data.setErrorMsg("An error occurred, couldn't start game");
        }
        return data;
    }

    @Override
    public DataTransferObject joinGame(DataTransferObject data)
    {
        int gameID = Integer.parseInt(data.getData());
        boolean added = gameUserManager.joinGame(gameID, data.getPlayerID());
        if (gameUserManager.getNumPlayers(gameID) >= 5)
        {
            return createGame(data);
        }
        if(added)
        {
            data.setData("Player " + data.getPlayerID() + " joined game " + gameID);
        }
        else
        {
            data.setData("");
            data.setErrorMsg("An error occurred, game not joined");
        }
        return data;
    }

    @Override
    public DataTransferObject login(DataTransferObject data)
    {
        DataTransferObject userInfo = new DataTransferObject();
        try
        {
            User loginUser = gson.fromJson(data.getData(), User.class);
            User u = gameUserManager.getUser(loginUser.getUsername());
            userInfo.setCommand(data.getCommand());
            if (u != null && loginUser.getPassword().equals(u.getPassword()))
            {
                u.setPassword("GOOD");
                userInfo.setPlayerID(u.getPlayerID());
                String s = Serializer.serialize(u);
                userInfo.setData(s);
            }
            else
            {
                userInfo.setErrorMsg("Invalid username or password");
            }
        } catch (Exception e)
        {
            userInfo.setErrorMsg(e.getMessage());
        }

        return userInfo;
    }

    @Override
    public DataTransferObject register(DataTransferObject data)
    {
        DataTransferObject userInfo = new DataTransferObject();
        try
        {
            JsonParser jsonParser = new JsonParser();
            JsonObject o = jsonParser.parse(data.getData()).getAsJsonObject();
            String password = o.get("password").getAsString();
            String username = o.get("username").getAsString();
            User exists = gameUserManager.getUser(username);
            if (exists != null)
            {
                userInfo.setErrorMsg("User already exists!");
            }
            else
            {
                gameUserManager.addUser(username, password);
                userInfo.setData("User created!");
            }
        } catch (Exception e)
        {
            userInfo.setErrorMsg(e.getMessage());
        }

        return userInfo;
    }

    public DataTransferObject listGames(DataTransferObject data)
    {
        try
        {
            ArrayList<TTRGame> games = gameUserManager.getGames();
            data.setData(Serializer.serialize(games));
            return data;
        } catch(Exception e)
        {
            e.printStackTrace();
            data.setErrorMsg("An error occurred - could not get games");
            return data;
        }
    }

}
