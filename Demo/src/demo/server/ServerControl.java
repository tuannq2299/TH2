package demo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Lenovo
 */
public class ServerControl{
    protected Connection con;
    protected ServerSocket myServer;
    protected int serverPort=22;
    
    public ServerControl(){
        getDBConnection("root","551134");
        openServer(serverPort);
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
