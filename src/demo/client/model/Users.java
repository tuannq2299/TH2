package demo.client.model;

import java.io.Serializable;

/**
 *
 * @author Lenovo
 */
public class Users implements Serializable{
    private int id;
    private String hoten;
    private String username;
    private String pass;
    private int isOnl;
    private float points;

    public Users(String hoten, String username, String pass,float points) {
        this.hoten = hoten;
        this.username = username;
        this.pass = pass;
        this.points=points;
    }

    public Users() {
        points=0;
    }

    
    public int getIsOnl() {
        return isOnl;
    }

    public void setIsOnl(int isOnl) {
        this.isOnl = isOnl;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    
}
