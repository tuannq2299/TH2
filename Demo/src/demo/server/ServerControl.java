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
    private Connection con;
    private ServerSocket myServer;
    private int serverPort=5555;
    
    public ServerControl(){
        getDBConnection("root","conga22071999");
        openServer(serverPort);
        while(true){
            listening();
        }
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
    public ArrayList<Users> listFr(FriendsList fl){
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
            return lf;
        }
}
