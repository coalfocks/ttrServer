package server;

import com.example.tyudy.ticket2rideclient.common.Command;

import java.util.ArrayList;

/**
 * Created by tyudy on 3/6/17.
 */
public class CommandQueue {
    public static final CommandQueue SINGLETON = new CommandQueue();
    ArrayList<Command> commandsSinceGameStarted;

    private CommandQueue(){
        commandsSinceGameStarted = new ArrayList<>();
    }

    public void addCommand(Command newCommand){
        commandsSinceGameStarted.add(newCommand);
    }

    public int getSize(){
        return commandsSinceGameStarted.size();
    }

    public Command getLatestCommand(){
        return commandsSinceGameStarted.get(commandsSinceGameStarted.size() - 1);
    }

}
