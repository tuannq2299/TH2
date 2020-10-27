package demo.client.model;

import java.io.Serializable;

/**
 *
 * @author Lenovo
 */
public class Package implements Serializable{
    private Users u;
    private String check;

    public Package(Users u, String check) {
        this.u = u;
        this.check = check;
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
    
}
