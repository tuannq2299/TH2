package demo.client.control;

import demo.client.model.FriendsList;
import demo.client.model.Users;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class ClientControl {
    private Socket mySocket;
    private String serverHost="localhost";
    private int serverPort=22222;

    public ClientControl() {
    }
    public Socket openConnection(){
        try{
            mySocket=new Socket(serverHost,serverPort);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return mySocket;
    }
    public boolean sendData(Users u){
        try{
            ObjectOutputStream os=new ObjectOutputStream(mySocket.getOutputStream());
            os.writeObject(u);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public String receiveData(){
        String rs=null;
        try{
            ObjectInputStream is=new ObjectInputStream(mySocket.getInputStream());
            Object o=is.readObject();
            if(o instanceof String){
                rs=(String)o;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return rs;
    }
    public FriendsList receiveData1(){
        FriendsList rs=null;
        try{
            ObjectInputStream is=new ObjectInputStream(mySocket.getInputStream());
            Object o=is.readObject();
            if(o instanceof FriendsList){
                rs=(FriendsList)o;
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return rs;
    }
    public boolean closeConnection(){
        try{
            mySocket.close();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
        
    }
}
