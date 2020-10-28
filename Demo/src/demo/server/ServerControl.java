package demo.server;

import demo.client.model.FriendsList;
import demo.client.model.Package;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ServerControl {

    protected Connection con;
    protected ServerSocket myServer;
    protected int serverPort = 1234;
    ObjectInputStream is;
    ObjectOutputStream os;
    ArrayList<Users> cmd;

    public ServerControl() {

        getDBConnection("root", "conga22071999");
        openServer(serverPort);
        while (true) {
            listening();
            //System.out.println(1);
        }

    }

    private void getDBConnection(String username, String pass) {
        String dbURL = "jdbc:mysql://localhost:3306/th1";
        String dbclass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbclass);
            con = DriverManager.getConnection(dbURL, username, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openServer(int portNumber) {
        try {
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listening() {
        try {
            Socket clientSocket = myServer.accept();
            is = new ObjectInputStream(clientSocket.getInputStream());
            os = new ObjectOutputStream(clientSocket.getOutputStream());
            Package temp = (Package) is.readObject();
            System.out.println(temp.getU().getHoten()+temp.getCheck());
            String check = temp.getCheck();
            Users u = temp.getU();
            if (check.equals("1")) {
                if (checkUser(u)) {
                    os.flush();
                    FriendsList fl = new FriendsList(u);
                    fl.setLf(listFr(u));
                    os.writeObject(fl);
                    os.flush();
                } else {
                    os.writeObject("false");
                    os.flush();
                }
            }
            else if (check.equals("2")) {
                os.flush();
                    FriendsList fl = new FriendsList(u);
                    fl.setLf(listFr(u));
                    os.writeObject(fl);
                    os.flush();
            }
            else if (check.equals("3")) {
                if (!checkUserExist(u)) {
                    os.writeObject("ok");
                    os.flush();

                } else {
                    os.writeObject("false");
                    os.flush();
                }
                System.out.println(check);
            }
            else if(check.equals("4")){
                if(addFriend(temp)){
                    os.writeObject("ok");
                    os.flush();
                }
                else{
                    os.writeObject("false");
                    os.flush();
                }
            }
            else if(check.equals("off")){
                logOut(temp);
                System.out.println("aaa");
            }
            else if(check.equals("challenge")){
                
            }
            System.out.println(check);
            //clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean checkUser(Users u) {
        String sql = "SELECT * FROM users WHERE username=? AND pass=?";
        //System.out.println(u.getu());
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPass());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setHoten(rs.getString("hoten"));
                String sql1="UPDATE users SET isOnl=1 WHERE id=?";
                PreparedStatement ps1=con.prepareStatement(sql1);
                ps1.setInt(1, rs.getInt("id"));
                ps1.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private void logOut(Package p){
        String sql="UPDATE users SET isOnl=0 WHERE hoten=?";
        try{
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, p.getU().getHoten());
            ps.executeUpdate();
        }
        catch(Exception e){
            
        }
        
        
    }
    
    private ArrayList<Users> listFr(Users u) {
        String sql1 = "SELECT * FROM isfriend,users WHERE users.id=isfriend.id1 AND users.id=?";
        String sql2 = "SELECT * FROM isfriend,users WHERE users.id=isfriend.id2 AND users.id=?";
        ArrayList<Users> lf = new ArrayList<>();
        //System.out.println(u.getHoten());
        try {
            PreparedStatement ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, u.getId());
            ResultSet rs1 = ps1.executeQuery();
            ArrayList<Integer> temp1 = new ArrayList<>();
            while (rs1.next()) {
                temp1.add(rs1.getInt("id2"));
                //System.out.println(rs1.getInt("id2"));
            }

            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, u.getId());
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                temp1.add(rs2.getInt("id1"));
                //System.out.println(rs2.getInt("id1"));
            }
            for (Integer i : temp1) {
                String sql3 = "SELECT * FROM users  WHERE id=?";
                PreparedStatement ps3 = con.prepareStatement(sql3);
                ps3.setInt(1, i);
                ResultSet rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    Users u3 = new Users();
                    u3.setHoten(rs3.getString("hoten"));
                    u3.setPoints(rs3.getFloat("points"));
                    u3.setIsOnl(rs3.getInt("isOnl"));
                    //System.out.println(u3.getHoten());
                    lf.add(u3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lf;
    }

    private boolean checkUserExist(Users u) {
        String sql = "SELECT * FROM users WHERE username=?";
        //System.out.println(u.getu());
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                String sql1 = "INSERT INTO users(hoten,username,pass,points) VALUES(?,?,?,?)";
                PreparedStatement ps1 = con.prepareStatement(sql1);
                ps1.setString(1, u.getHoten());
                ps1.setString(2, u.getUsername());
                ps1.setString(3, u.getPass());
                ps1.setFloat(4, 0);
                ps1.executeUpdate();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean addFriend(Package p){
        try {
            String sql="SELECT * FROM users WHERE hoten=?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, p.getU2().getHoten());
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString("hoten"));
                System.out.println(rs.getInt("id"));
                if(p.getFl()!=null){
                    System.out.println("aa");
                    for(Users i:p.getFl().getLf()){
                        if(p.getU2().getHoten().equals(i.getHoten()))
                            return false;
                    }
                    int temp=rs.getInt("id");
                    String sql1="INSERT INTO isfriend VALUES(?,?)";
                    PreparedStatement ps1=con.prepareStatement(sql1);
                    System.out.println(p.getU().getId()+" "+temp);
                    ps1.setInt(1, p.getU().getId());
                    ps1.setInt(2, temp);
                    ps1.executeUpdate();
                    return true;
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
}
