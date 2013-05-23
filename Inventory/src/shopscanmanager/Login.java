/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.awt.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONObject;

/**
 *
 * @author xkalibaer
 */
public class Login extends javax.swing.JPanel {

    Connection con;
    Mysql mysql;
    HomeScreen hs;
    String installPath;
    
    /**
     * Creates new form Login
     */
    public Login(Mysql mysql, Connection con, HomeScreen hs, String installPath) {
        this.installPath=installPath;
        this.mysql=mysql;
        this.con=con;
        this.hs=hs;
        try
        {
            con=mysql.mysqlConnectToDb("localhost", "root", "", UtilUse.DATABASE);
        }
        catch(Exception ex)
        {
            
        }
        initComponents();
        this.jTextField1.setText("smicer66@jasdsd.com");
        this.jPasswordField1.setText("chelsea");
        this.getJLabel3_().setForeground(new Color(23, 21, 21));
        //this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); 
        //this.jPanel1.setBorder(new EtchedBorder());
        this.jPanel1.setPreferredSize(new Dimension(300, 180));
        this.jPanel1.setLayout(new GridBagLayout());
        //this.setPreferredSize(new Dimension(300, 300));
    }
    
    
    public JLabel getJLabel4_()
    {
        return jLabel4;
    }
    
    
    public JLabel getJLabel5_()
    {
        return jLabel5;
    }
    
    public JButton getJButton1_()
    {
        return jButton1;
    }
    
    public JLabel getJLabel3_()
    {
        return jLabel3;
    }
    
    
    public String doLogin(Connection con, Mysql mysql)
    {
        String accessKey="";
        String email=jTextField1.getText().toString();
        char[] password="".toCharArray();
        try
        {
            password=jPasswordField1.getPassword();
        }catch(NullPointerException e)
        {
            password="".toCharArray();
        }
        
        int continue_1=1;
        Calendar cal = Calendar.getInstance();
        Calendar currentcal = Calendar.getInstance();
        cal.set(2013, Calendar.DECEMBER, 15);
        currentcal.set(currentcal.get(Calendar.YEAR), currentcal.get(Calendar.MONTH), currentcal.get(Calendar.DAY_OF_MONTH));
        
        if(cal.before(currentcal))
        {
                continue_1=0;
                
        }
        if((password!=null) && (password.length>0) && (continue_1==1))
        {
            PreparedStatement pr;
            try {
                MessageDigest messageDigest;
                try {
                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.reset();
                    messageDigest.update(String.valueOf(password).getBytes(Charset.forName("UTF8")));
                    final byte[] resultByte = messageDigest.digest();
                    final String result = new String(Hex.encodeHex(resultByte));
                    System.out.println("md5 password = "+ result);

                    pr = this.con.prepareStatement("SELECT * from user_profile WHERE email_address = ? AND password = ?");
                    pr.setString(1, email);
                    pr.setString(2, result);
                    ResultSet rs=mysql.query(pr);
                    System.out.println(rs.getStatement().toString());


                    while (rs.next()) { 
                        accessKey=rs.getString("key");
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Hashtable processResult=new Hashtable();
                    processResult.put("success", true);
                    processResult.put("message", "Settings Saved");
                    processResult.put("operation", "Setting Up Application...");
                    this.hs.displaySetUpShop(this.con, this.mysql, this.installPath);
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (NullPointerException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            
        }
        return accessKey;
    }
    
    
    public JPanel getAllComponents() {
        JPanel panel=new JPanel();
        this.jPanel1.setBackground(new Color(23, 21, 21));
        this.jPanel2.setBackground(new Color(23, 21, 21));
        this.jPanel3.setBackground(new Color(23, 21, 21));
        panel.setBackground(new Color(23, 21, 21));
        this.setBackground(new Color(23, 21, 21));
        panel.add(this);
        return this;
    }
    
    /*public static void main(String[] str)
    {
        /*Login login=new Login();
        login.setBounds(0,0,100, 150);
        login.setVisible(true);*/
    //}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Login", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Arial", 1, 18), new java.awt.Color(204, 204, 204))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Email Address:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Password:");

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Create A New Account");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });
        jLabel4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jLabel4FocusLost(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Login");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("I cant Log In!");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addComponent(jPasswordField1)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(250, 51, 0));
        jLabel3.setText("Username and password do not match");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addComponent(jLabel3))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel4FocusGained
        // TODO add your handling code here:
        System.out.println("Gained");
        JLabel label=((JLabel)evt.getComponent());
        label.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_jLabel4FocusGained

    private void jLabel4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel4FocusLost
        // TODO add your handling code here:
        System.out.println("Gained");
        JLabel label=((JLabel)evt.getComponent());
        label.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_jLabel4FocusLost

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        System.out.println("Gained");
        JLabel label=((JLabel)evt.getComponent());
        label.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        System.out.println("Lost");
        JLabel label=((JLabel)evt.getComponent());
        label.setForeground(new java.awt.Color(204, 204, 204));
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
