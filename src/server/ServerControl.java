package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.Package;
import client.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
/**
 *
 * @author Lenovo
 */
public class ServerControl {
    private Connection con;
    private int port=567;
    private ServerSocket svSocket;

    public ServerControl() {
        getDBConnection("root", "conga22071999");
        openServer(port);
        while(true){
            listening();
        }
    }
    private void getDBConnection(String username,String pass){
        try {
            String dbClass="com.mysql.jdbc.Driver";
            Class.forName(dbClass);
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/th2",username,pass ); 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }catch (SQLException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void openServer(int port){
        try {
            svSocket=new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void listening(){
        try {
            Socket clientSocket=svSocket.accept();
            ObjectInputStream is=new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream os=new ObjectOutputStream(clientSocket.getOutputStream());
            client.Package p=(Package) is.readObject();
            //System.out.println(p.getRq()+" "+p.getCmt()+" "+p.getTypeSearch());
            if(p.getRq().equals("1")){
                Package p1=new Package();
                if(checkStudent(p)){
                    System.out.println(p.getStudent().getName());
                    p1.setCmt("ok");
                    os.writeObject(p1);
                    os.flush();
                    System.out.println(p1.getCmt());
                }
                else{
                    p1.setCmt("false");
                    os.writeObject(p1);
                    os.flush();
                }
                    
            }
            else if(p.getRq().equals("2")){
                Package p1=new Package();
                System.out.println("rq2");
                p1.setVector(searchStudents(p));
                os.writeObject(p1);
                os.flush();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkStudent(Package p) {
        try {
            String sql="INSERT into student(name,yearofbirth,placeofbirth,classroom) VALUES(?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, p.getStudent().getName());
            ps.setInt(2, p.getStudent().getYearOfBirth());
            ps.setString(3, p.getStudent().getPlaceOfBirth());
            ps.setString(4, p.getStudent().getClassroom());
            ps.executeUpdate();
            return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    private Vector searchStudents(Package p){
        Vector v=new Vector();
        try {
            if(p.getTypeSearch().equals("Name")){
                String sql="SELECT * FROM student WHERE name LIKE ?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1, "%"+p.getCmt()+"%");
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    Vector vec=new Vector();
                    vec.add(rs.getInt("id"));
                    vec.add(rs.getString("name"));
                    vec.add(rs.getInt("yearofbirth"));
                    vec.add(rs.getString("placeofbirth"));
                    vec.add(rs.getString("classroom"));
//                    Student temp=new Student(rs.getString("name"), rs.getInt("yearofbirth"), rs.getString("placeofbirth"), rs.getString("classroom"));
//                    System.out.println(temp.getName());
                    v.add(vec);
                }
            }
            else{
                String sql="SELECT * FROM student WHERE yearofbirth LIKE ?";
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1, "%"+p.getCmt()+"%");
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    Vector vec=new Vector();
                    vec.add(rs.getInt("id"));
                    vec.add(rs.getString("name"));
                    vec.add(rs.getInt("yearofbirth"));
                    vec.add(rs.getString("placeofbirth"));
                    vec.add(rs.getString("classroom"));
                    v.add(vec);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
    }
}
