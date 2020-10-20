package demo.client.model;

import java.util.ArrayList;
import demo.client.model.Users;

/**
 *
 * @author Lenovo
 */
public class FriendsList {
    private Users user;
    private ArrayList<Users>lf;

    public FriendsList() {
        lf=new ArrayList<>();
    }

    public FriendsList(Users user) {
        this.user = user;
        lf=new ArrayList<>();
    }

    public Users getUser() {
        return user;
    }

    public ArrayList<Users> getLf() {
        return lf;
    }
    
}
