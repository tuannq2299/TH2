package demo.server;

import demo.client.model.FriendsList;
import demo.client.model.Users;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Lenovo
 */
public class ServerControl{
    protected Connection con;
    protected ServerSocket myServer;
    protected int serverPort=2222;
    ObjectInputStream is;
    ObjectOutputStream os;
    ArrayList<Users>cmd;
    public ServerControl(){
        cmd=new ArrayList<>();
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
            is=new ObjectInputStream(clientSocket.getInputStream());
            os=new ObjectOutputStream(clientSocket.getOutputStream());
            Object o=is.readObject();
            int check=0;
            if(o instanceof Users &&check==0){
                Users u=(Users)o;
                if(checkUser(u)){
                    os.writeObject("ok");
                    os.flush();
                    check=1;
                }
                else{
                    os.writeObject("false");
                }
            }
            if(o instanceof Users &&check==1){
                os=new ObjectOutputStream(clientSocket.getOutputStream());
                Users u=(Users) o;
                FriendsList fl=new FriendsList(u);
                fl.setLf(listFr(u));
                os.writeObject(fl);
//                for(Users i:fl.getLf()){
//                    System.out.println(i.getHoten());
//                }
                os.flush();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private boolean checkUser(Users u) {
        String sql="SELECT * FROM users WHERE username=? AND pass=?";
        //System.out.println(u.getu());
        try{
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPass());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                u.setId(rs.getInt("id"));
                u.setHoten(rs.getString("hoten"));
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<Users> listFr(Users u){
            String sql1="SELECT * FROM isfriend,users WHERE users.id=isfriend.id1 AND users.id=?";
            String sql2="SELECT * FROM isfriend,users WHERE users.id=isfriend.id2 AND users.id=?";
            ArrayList<Users>lf=new ArrayList<>();
            //System.out.println(u.getHoten());
            try{
                PreparedStatement ps1=con.prepareStatement(sql1);
                ps1.setInt(1, u.getId());
                ResultSet rs1=ps1.executeQuery();
                ArrayList<Integer>temp1=new ArrayList<>();
                while(rs1.next()){
                    temp1.add(rs1.getInt("id2"));
                    //System.out.println(rs1.getInt("id2"));
                }
                
                PreparedStatement ps2=con.prepareStatement(sql2);
                ps2.setInt(1, u.getId());
                ResultSet rs2=ps2.executeQuery();
                
                while(rs2.next()){
                    temp1.add(rs2.getInt("id1"));
                    //System.out.println(rs2.getInt("id1"));
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
                        //System.out.println(u3.getHoten());
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
