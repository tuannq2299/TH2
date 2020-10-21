package demo.server;

import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class ServerView {

    public ServerView() {
        Thread t1=new Thread(new ServerLoginControl());
        Thread t2=new Thread(new ServerFriendsListControl());
        t1.start();
       // t2.start();
       // System.out.println("TCP server is running...");
    }

}
