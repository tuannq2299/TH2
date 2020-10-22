package demo.server;

import demo.client.model.FriendsList;
import demo.client.model.Users;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class ServerFriendsListControl extends ServerControl implements Runnable{
    public ServerFriendsListControl() {
        super();   
    }
    

    @Override
    public void run() {
        while(true){
            
        }
    }
}
