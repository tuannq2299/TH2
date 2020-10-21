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
            if(o instanceof Users){
                Users u=(Users)o;
                if(checkUser(u)){
                    os.writeObject("ok");
                }
                else{
                    os.writeObject("false");
                }
            }
//            if(o instanceof FriendsList){
//                FriendsList fl=(FriendsList)o;
//                fl=
//            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean checkUser(Users u) {
        String sql="SELECT * FROM users WHERE username=? AND pass=?";
        try{
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPass());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while(true){
            listening();
        }
    }
}
