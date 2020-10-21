package demo.client.model;

import java.util.ArrayList;
import demo.client.model.Users;

/**
 *
 * @author Lenovo
 */
public class FriendsList {
    private Users user;
    private ArrayList<Users>lf=new ArrayList<>();

    public FriendsList() {
        
    }

    public FriendsList(Users user) {
        this.user = user;
        
    }

    public Users getUser() {
        return user;
    }

    public ArrayList<Users> getLf() {
        return lf;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setLf(ArrayList<Users> lf) {
        this.lf = lf;
    }
    
}
