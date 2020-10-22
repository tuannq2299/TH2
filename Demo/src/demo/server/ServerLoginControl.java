package demo.server;

import demo.client.model.Users;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Lenovo
 */
public class ServerLoginControl extends ServerControl implements Runnable{

    public ServerLoginControl() {
        super();
    }
        
    
    private void listening() {
        try{
            Socket clientSocket=myServer.accept();
            ObjectInputStream is=new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream os=new ObjectOutputStream(clientSocket.getOutputStream());
            Object o=is.readObject();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void run() {
        while(true){
            listening();
        }
    }
}
