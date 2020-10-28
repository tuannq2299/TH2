package demo.client.model;

import java.io.Serializable;

/**
 *
 * @author Lenovo
 */
public class Package implements Serializable{
    private Users u;
    private Users u2;
    private String check;
    private FriendsList fl;
    public Package(Users u, String check) {
        this.u = u;
        this.check = check;
    }

    public FriendsList getFl() {
        return fl;
    }

    public void setFl(FriendsList fl) {
        this.fl = fl;
    }
    
    public Users getU() {
        return u;
    }

    public void setU(Users u) {
        this.u = u;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Users getU2() {
        return u2;
    }

    public void setU2(Users u2) {
        this.u2 = u2;
    }
    
}
