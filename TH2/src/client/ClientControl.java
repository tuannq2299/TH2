package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ClientControl {
    private Socket mySocket;
    private int port=567;
    private String host="localhost";

    public ClientControl() {
    }
    public Socket openConnection(){
        try {
            mySocket=new Socket(host, port);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mySocket;
    }
    public void sendData(Package p){
        try {
            ObjectOutputStream os=new ObjectOutputStream(mySocket.getOutputStream());
            System.out.println(p.getRq()+" "+p.getCmt());
            os.writeObject(p);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public Package receiveData(){
        Package p=null;
        try {
            ObjectInputStream is=new ObjectInputStream(mySocket.getInputStream());
            p=(Package) is.readObject();
            return p;
             
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return p;
    }
    public void closeConnection(){
        try {
            mySocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
