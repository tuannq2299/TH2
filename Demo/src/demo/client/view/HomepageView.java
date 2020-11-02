package demo.client.view;

import com.sun.imageio.plugins.jpeg.JPEG;
import demo.client.control.ClientControl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComponent;
import demo.client.model.Users;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import demo.client.model.FriendsList;
import demo.client.model.Package;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ObjectInputStream;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Lenovo
 */
public class HomepageView extends javax.swing.JFrame{

    /**
     * Creates new form HomepageView
     */
    Package p;
    ArrayList<Users> lf;
    FriendsList fl;
    DefaultTableModel mod;
    JLabel lbIsOnl;
    JTable tabFr;
    JScrollPane js;
    Vector vcHead;
    Vector vcData;

    public HomepageView(Package p){
        
        this.p = p;
        mod = new DefaultTableModel();
        initComponents();
        setLocationRelativeTo(this);
        friendsPane.setLayout(new BorderLayout(20, 20));
        lbIsOnl = new JLabel();
        lbIsOnl.setFont(new Font(lbIsOnl.getName(), Font.PLAIN, 18));
        JPanel pTop = new JPanel(new GridLayout(2, 1, 10, 20));
        JPanel pTop1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel lblA = new JLabel("ADD FRIEND");
        lblA.setFont(new Font(lblA.getName(), Font.PLAIN, 16));
        pTop1.add(lblA);
        JTextField txt1 = new JTextField(40);
        JButton btnAddFr = new JButton("ADD");
        JPanel top2=new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton btnRf=new JButton("REFRESS");
        btnRf.addActionListener((ActionEvent e) -> {
            process();
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        top2.add(lbIsOnl);
        top2.add(btnRf);
        pTop1.add(txt1);
        pTop1.add(btnAddFr);
        pTop.add(pTop1);
        pTop.add(top2);
        friendsPane.add(pTop, BorderLayout.NORTH);
        vcHead = new Vector();
        vcHead.add("Player");
        vcHead.add("Points");
        vcHead.add("Status");
        tabFr = new JTable(mod) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
                    int columnIndex) {
                JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);

                if (getValueAt(rowIndex, 2).toString().equalsIgnoreCase("Online")) {
                    component.setBackground(Color.GREEN);
                } else if (getValueAt(rowIndex, 2).toString().equalsIgnoreCase("Offline")) {
                    component.setBackground(Color.lightGray);
                }

                return component;
            }
        };
        tabFr.setFont(new Font("Arial", Font.PLAIN, 16));
        js = new JScrollPane(tabFr);
        js.setSize(this.getWidth(), this.getHeight());
        friendsPane.add(js, BorderLayout.CENTER);
        process();
        btnAddFr.addActionListener((e) -> {
            if (txt1.getText().equals(p.getU().getHoten())) {
                JOptionPane.showMessageDialog(rootPane, "Invalid");
            } else {
                Users u = new Users();
                u.setHoten(txt1.getText());
                System.out.println(u.getHoten());
                Package p1 = new Package(p.getU(), "4");
                p1.setU2(u);
                //if(p1.getFl()!=null)
                p1.setFl(fl);
                ClientControl control1 = new ClientControl();
                control1.openConnection();
                control1.sendData(p1);
                String rs = control1.receiveData();
                if (rs.equals("ok")) {
                    JOptionPane.showMessageDialog(rootPane, "Success");
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Invalid");
                }
                control1.closeConnection();
            }

        });
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                super.windowClosed(e);
                ClientControl control=new ClientControl();
                control.openConnection();
                Package temp=new Package();
                temp.setU(p.getU());
                control.sendData(temp);
                control.closeConnection();
            }
        });
        tabFr.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                int row=tabFr.getSelectedRow();
                String hoten=(String) tabFr.getModel().getValueAt(row, 0);
                Package rq=new Package();
                Users u=new Users();
                u.setHoten(hoten);
                rq.setU2(u);
                rq.setU(p.getU());
                rq.setCheck("challenge");
                ClientControl control=new ClientControl();
                control.openConnection();
                control.sendData(rq);
                
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        contentpane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        friendsPane = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel1.setText("FRIENDS LIST");

        javax.swing.GroupLayout friendsPaneLayout = new javax.swing.GroupLayout(friendsPane);
        friendsPane.setLayout(friendsPaneLayout);
        friendsPaneLayout.setHorizontalGroup(
            friendsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
        );
        friendsPaneLayout.setVerticalGroup(
            friendsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout contentpaneLayout = new javax.swing.GroupLayout(contentpane);
        contentpane.setLayout(contentpaneLayout);
        contentpaneLayout.setHorizontalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(friendsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        contentpaneLayout.setVerticalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(friendsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentpane;
    private javax.swing.JPanel friendsPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
//    public static TableCellRenderer createCellRenderer(final Vector data) {
//        return new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component c =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                if (column == 2 && ("Online").equals((String) value)) {
//                    c.setBackground(Color.GREEN);
//                }
//                if (column == 2 && ("Offline").equals((String) value)) {
//                    c.setBackground(Color.RED);
//                }
//                
//
//                return c;
//            }
//        };
//    }
//
//    public static void setCellRenderer(JTable table,final Vector data) {
//        TableCellRenderer cellRenderer = createCellRenderer(data);
//        table.setDefaultRenderer(Object.class, cellRenderer);
//    }
    public void process() {

        try {
            Package tmp = new Package(p.getU(), "2");
            ClientControl control=new ClientControl();
            control.openConnection();
            control.sendData(tmp);
//                if(control.receiveFL()!=null)
            fl = control.receiveFL();
            control.closeConnection();
            if (fl != null) {
                lf = fl.getLf();
                if (fl.getLf().size() == 0) {
                    lbIsOnl.setText("  You have no friend                                                                          ");
                } else {
                    int count = 0;
                    for (Users u : lf) {
                        if (u.getIsOnl() == 1) {
                            count++;
                        }
                    }
                    lbIsOnl.setText("  " + count + " FRIENDS IS ONLINE                                                                         ");

                    tabFr.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
                    vcData = new Vector();

                    for (Users u : lf) {
                        if (u.getHoten() != p.getU().getHoten()) {
                            Vector row = new Vector();
                            row.add(u.getHoten());
                            row.add(u.getPoints());
                            if (u.getIsOnl() == 1) {
                                row.add("Online");
                            } else {
                                row.add("Offline");
                            }
                            vcData.add(row);
                        }
                        System.out.println(u.getHoten() + u.getIsOnl());
                    }

                    tabFr.setModel(new DefaultTableModel(vcData, vcHead));
//                        tabFr.getColumnModel().getColumn(0).setPreferredWidth(500);
//                        tabFr.getColumnModel().getColumn(1).setPreferredWidth(400);
//                        tabFr.getColumnModel().getColumn(2).setPreferredWidth(400);

                    mod.setRowCount(0);

                }
            }
            control.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   
}
