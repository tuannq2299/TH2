package demo.client.control;

import com.mysql.cj.conf.ConnectionUrlParser;
import demo.client.model.FriendsList;
import demo.client.model.Package;
import demo.client.model.Users;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Lenovo
 */
public class ClientControl {
    private Socket mySocket;
    private String serverHost="localhost";
    private int serverPort=1234;
    ObjectOutputStream os;
    ObjectInputStream is;
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
    public boolean sendData(Package p){
        try{
            os=new ObjectOutputStream(mySocket.getOutputStream());
            os.writeObject(p);
            System.out.println(p.getCheck());
            os.flush();
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
            is=new ObjectInputStream(mySocket.getInputStream());
            Object o=is.readObject();
            if(o instanceof String){
                rs=(String)o;
                System.out.println(rs);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return rs;
    }
    public FriendsList receiveFL(){
        System.out.println("Da nhan");
        FriendsList fl=new FriendsList();
        try{
            is=new ObjectInputStream(mySocket.getInputStream());
            Object o=is.readObject();
            if(o instanceof FriendsList){
                fl=(FriendsList) o;
                System.out.println(fl.getUser().getHoten());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return fl;
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
