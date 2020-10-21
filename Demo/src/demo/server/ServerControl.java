package demo.server;

import demo.client.model.FriendsList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import demo.client.model.Users;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ServerControl{
    protected Connection con;
    protected ServerSocket myServer;
    protected int serverPort=22222;
    
    public ServerControl(){
        getDBConnection("root","conga22071999");
        openServer(serverPort);
//        while(true){
//            listening();
//        }
    }

    private void getDBConnection(String username, String pass) {
        String dbURL="jdbc:mysql://localhost:3306/th1";
        String dbclass="com.mysql.jdbc.Driver";
        try{
            Class.forName(dbclass);
            con=DriverManager.getConnection(dbURL, username, pass);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private void openServer(int portNumber){
        try{
            myServer=new ServerSocket(portNumber);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
