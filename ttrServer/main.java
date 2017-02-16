import server.*;

/**
 * Created by colefox on 2/9/17.
 */
public class
main
{
    /* add username to User data field,
       only list games not in progress,
      TODO: start game when users >= 5,
       can't join if game in progress
       method to get gameID (maybe)*/

    public static void main(String[] args)
    {
        ServerCommunicator server = new ServerCommunicator();
        server.run();

        //server.stop();
    }
}
