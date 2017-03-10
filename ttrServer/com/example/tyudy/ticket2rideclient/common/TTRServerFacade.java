package com.example.tyudy.ticket2rideclient.common;

import com.example.tyudy.ticket2rideclient.common.cities.Path;
import com.example.tyudy.ticket2rideclient.common.commands.ClaimPathCommand;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import server.*;
import server.Database.DAO;

import javax.xml.crypto.Data;
import java.io.IOException;
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
        try
        {
            int gameID = gameUserManager.createGame(data.getPlayerID());
            data.setData(Serializer.serialize(gameUserManager.getGame(gameID)));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return createjoinGame(data);
    }

    @Override
    public DataTransferObject startGame(DataTransferObject data)
    {
        if (gameUserManager.startGame(data.getPlayerID()))
        {
            try
            {
                TTRGame game = DAO.getInstance().getGameByOwner(data.getPlayerID());
                game = GameUserManager.getInstance().initializeGame(game);
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

    public DataTransferObject createjoinGame(DataTransferObject data)
    {
        try
        {
            TTRGame thegame = (TTRGame) Serializer.deserialize(data.getData());
            boolean added = gameUserManager.joinGame(data.getData(), data.getPlayerID());
            TTRGame game = (TTRGame) Serializer.deserialize(data.getData());
            if (game.getNumPlayers() >= 5)
            {
                return startGame(data);
            }
            if (added)
            {
                game = gameUserManager.getGame(game.getGameID());
                data.setData(Serializer.serialize(game));
            } else
            {
                data.setData("");
                data.setErrorMsg("An error occurred, game not joined");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public DataTransferObject joinGame(DataTransferObject data)
    {
        try
        {
            int gameID = Integer.parseInt(data.getData());
            boolean added = gameUserManager.joinGame(gameID, data.getPlayerID());
            TTRGame game = gameUserManager.getGame(gameID);
            if (game.getNumPlayers() >= 5)
            {
                return startGame(data);
            }
            if (added)
            {
                data.setData(Serializer.serialize(game));
            } else
            {
                data.setData("");
                data.setErrorMsg("An error occurred, game not joined");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
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
        userInfo.setCommand("register");
        return userInfo;
    }

    @Override
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

    @Override
    public DataTransferObject initializeGame(DataTransferObject data) {
        //we need to get the game the person is in from here and then find it in the database
        TTRGame game = null;
        try {
            game = (TTRGame) Serializer.deserialize(data.getData());
            game = gameUserManager.initializeGame(game);
            data = new DataTransferObject("initialize", Serializer.serialize(game), "", null);
            return data;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }


    @Override
    public DataTransferObject sendChatMessage(DataTransferObject data) {
        String chatMessage = data.getData();
        if (chatMessage.equals("")){
            data.setErrorMsg("The string sent to the server is empty.");
        } else {
            gameServer.addChat(chatMessage, data.getPlayerID());
        }
        return data;
    }

    @Override
    public DataTransferObject updateGameplay(DataTransferObject data) {
        //IMPLEMENT ME!
        return null;
    }

    @Override
    public DataTransferObject claimPath(DataTransferObject data) {
        try {
            int playerID = data.getPlayerID();
            Path path = gson.fromJson(data.getData(), Path.class);
            path = gameUserManager.claimPath(playerID, path);
            ClaimPathCommand command = new ClaimPathCommand();
            data.setData(Serializer.serialize(path));
            command.setData(data);
            CommandQueue.SINGLETON.addCommand(command);
        }
        catch (Exception e) {
            data.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public DataTransferObject getCommands(DataTransferObject data) {
        return null;
    }

}
