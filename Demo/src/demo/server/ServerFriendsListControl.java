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
    private void listening() {
        try{
            Socket clientSocket=myServer.accept();
            ObjectInputStream is=new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream os=new ObjectOutputStream(clientSocket.getOutputStream());
            Object o=is.readObject();
            
            if(o instanceof Users){
                Users u=(Users) o;
                FriendsList fl=new FriendsList(u);
                listFr(fl);
                os.writeObject(fl);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void listFr(FriendsList fl){
            String sql1="SELECT * FROM isfriend,users WHERE users.id=isfriend.id1 AND users.id=?";
            String sql2="SELECT * FROM isfriend,users WHERE users.id=isfriend.id2 AND users.id=?";
            ArrayList<Users>lf=fl.getLf();
            Users u=fl.getUser();
            try{
                PreparedStatement ps1=con.prepareStatement(sql1);
                ps1.setInt(1, u.getId());
                ResultSet rs1=ps1.executeQuery();
                ArrayList<Integer>temp1=new ArrayList<>();
                while(rs1.next()){
                    temp1.add(rs1.getInt("id2"));
                }
                
                PreparedStatement ps2=con.prepareStatement(sql2);
                ps2.setInt(1, u.getId());
                ResultSet rs2=ps2.executeQuery();
                
                while(rs2.next()){
                    temp1.add(rs2.getInt("id1"));
                }
                for(Integer i:temp1){
                    String sql3="SELECT * FROM users  WHERE id=?";
                    PreparedStatement ps3=con.prepareStatement(sql3);
                    ps3.setInt(1, i);
                    ResultSet rs3=ps3.executeQuery();
                    if(rs3.next()){
                        Users u3=new Users();
                        u3.setHoten(rs3.getString("hoten"));
                        u3.setPoints(rs3.getFloat("points"));
                        u3.setIsOnl(rs3.getInt("isOnl"));
                        lf.add(u3);
                    }  
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    @Override
    public void run() {
        while(true){
            listening();
        }
    }
}
