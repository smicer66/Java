/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RegistryValue;
import ca.beq.util.win32.registry.RootKey;
import ca.beq.util.win32.registry.ValueType;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.codec.binary.Hex;
import org.json.simple.JSONObject;
import sun.security.util.Password;

/**
 *
 * @author xkalibaer
 */
public class SetUpShop extends javax.swing.JPanel {
    private final Connection con;
    private final Mysql mysql;
    private JSONObject editDetails;
    private File fileSelected;
    private String fileSelectedPath;
    private AppAccess appAccess;
    private int id;
    private HomeScreen hs;
    private String savePath="";
    

    /**
     * Creates new form SetUpShop
     */
    public SetUpShop(Connection con, Mysql mysql, HomeScreen hs) {
        this.hs=hs;
        this.con=con;
        this.mysql=mysql;
        initComponents();
        jPanel1.setBackground(new Color(23, 21, 21));
        jPanel3.setBackground(new Color(23, 21, 21));
    }
    
    
    public JPanel getAllComponents(String savePath)
    {
        this.savePath=savePath;
        System.out.println("Save Path=" + savePath);
        HeaderPanel header=new HeaderPanel();
        if(this.editDetails!=null)
        {
            System.out.println("editDetails = " + this.editDetails.toJSONString());
        }
        BoxLayoutType bxt=new BoxLayoutType(BoxLayoutType.VERTICAL, 0, 20);
        this.setLayout(bxt);
        this.setBackground(new java.awt.Color(23, 21, 21));
        PreparedStatement pr3;
        this.jComboBox3.removeAllItems();
        this.jComboBox3.addItem("-Select One-");
        try {
            pr3 = this.con.prepareStatement("Select * from security_questions");
            ResultSet rs1=mysql.query(pr3);

            

            while (rs1.next()) { 
                this.jComboBox3.addItem(rs1.getString("question"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jButton2.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    JFileChooser chooser = new JFileChooser();
                    
                    FileFilter filter1 = new ExtensionFileFilter("JPG and JPEG and PNG", new String[] { "JPG", "JPEG", "PNG" });
                    chooser.setFileFilter(filter1);
                    int returnVal = chooser.showOpenDialog(SetUpShop.this.jButton2);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        try {
                            System.out.println("You chose to open this file: " +
                                chooser.getSelectedFile().getName() + "" + chooser.getSelectedFile().getCanonicalPath()
                                 + "" + chooser.getSelectedFile().getPath());
                            SetUpShop.this.fileSelectedPath=chooser.getSelectedFile().getCanonicalPath();
                            SetUpShop.this.fileSelected=chooser.getSelectedFile();
                        } catch (IOException ex) {
                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                catch(UnsupportedOperationException ex)
                {
                    
                }
            }
            
        });
        
        if(this.editDetails!=null)
        {
            
            System.out.println("editDetails = " + this.editDetails.toJSONString());
            System.out.println("editTitle=" + this.editDetails.get("title").toString());
            this.jTextField1.setText(this.editDetails.get("title").toString());
            this.jTextField3.setText(this.editDetails.get("title").toString());
            this.jTextField5.setText(this.editDetails.get("type").toString());
            this.jTextField6.setText(this.editDetails.get("price").toString());
            
        }
        
        this.add(header.getAllComponents(this.con, this.mysql,this.savePath), 0);
        return this;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPasswordField4 = new javax.swing.JPasswordField();
        jPasswordField3 = new javax.swing.JPasswordField();
        jTextField6 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(42, 42, 42));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel55.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(204, 204, 204));
        jLabel55.setText("Set Up Your Shop!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel55)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel55))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Shop Name:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Contact Email Address:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Contact Mobile Number:");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Address Line 1:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Address Line 2:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Details about your store");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Create An Administrator Account");

        jButton1.setText("Create User Account");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Security Question:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Answer:");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("First Name:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("Last Name:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("Other Name:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("Email Address:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Password:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setText("Confirm Password:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("Mobile Number:");

        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Select Shop Logo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField5)
                        .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addComponent(jScrollPane1))
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jPasswordField4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel16)
                    .addComponent(jButton1)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addGap(11, 11, 11)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1214, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 232, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 232, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    public Hashtable setUpShopProcess(Connection con, Mysql mysql, String savePath)
    {
       
        if(this.jTextField1!=null)
        {
            System.out.println("Is Null");
        }
        String shopName=this.jTextField1.getText().toString();
        String email=this.jTextField2.getText().toString();
        String mobile=this.jTextField3.getText().toString();
        String addressLine1=this.jTextField9.getText().toString();
        String addressLine2=this.jTextField5.getText().toString();
        String details=this.jTextArea1.getText().toString();
       
        
        
        //admin details
        String firstName=this.jTextField4.getText().toString();
        String lastName=this.jTextField10.getText().toString();
        String otherName=this.jTextField11.getText().toString();
        String userMobile=this.jTextField6.getText().toString();
        String userEmail=this.jTextField7.getText().toString();
        char[] userPassword=this.jPasswordField3.getPassword();
        char[] userConfirmPassword=this.jPasswordField4.getPassword();
        
        int securityQuestion=this.jComboBox3.getSelectedIndex();
        String securityQuestionAnswer=this.jTextField8.getText().toString();
        String uniqIdImage;
        
        
        Hashtable processResult=new Hashtable();
        System.out.println("Add/Edit new item1");
        
        
        if(shopName.length()>0)
        {
            System.out.println("Add/Edit new item2");
            if(email.length()>0)
            {
                if(mobile.length()>0)
                {
                    System.out.println("Add/Edit new item3");
                    //System.out.println("userPassword = " + userPassword);
                    //System.out.println("userConfirmPassword = " + userConfirmPassword);
                    if(firstName.length()>0)
                    {
                        if(lastName.length()>0)
                        {
                            if(userMobile.length()>0)
                            {
                                if(userEmail.length()>0)
                                {
                                    if(userPassword.length>0)
                                    {
                                        if(userConfirmPassword.length>0)
                                        {
                                            if(new UtilUse().comparePasswords(userPassword, userConfirmPassword))
                                            {
                                                if(securityQuestionAnswer.length()>0)
                                                {
                                                    if(securityQuestion>0)
                                                    {
            PreparedStatement pr;
            String qr_code="";
            try {
                System.out.println("Add/Edit new item4");
                //insert operation
                if(this.editDetails==null)
                {
                    System.out.println("Inserrt Process");
                    int fileId=-1;
                    if(fileSelectedPath!=null)
                    {
                        uniqIdImage=UUID.randomUUID().toString();
                        try {
                            FileInputStream fileInputStream=new FileInputStream(new File(fileSelectedPath));
                            byte[] buffer = new byte[fileInputStream.available()];
                            int bytesRead = fileInputStream.read(buffer, 0, fileInputStream.available());
                            int bytesAvailable;
                            int maxBufferSize=2*1048576;
                            System.out.println(savePath + "\\shop\\" + uniqIdImage);
                            FileOutputStream fileOutputsteam=new FileOutputStream(new File(savePath + "\\shop\\" + uniqIdImage));

                            int count=0;
                            while (bytesRead > 0) {
                                fileOutputsteam.write(buffer, count++, 1);
                                bytesRead--;
                            }
                            fileInputStream.close();
                            fileOutputsteam.flush();
                            fileOutputsteam.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                            processResult.put("success", false);
                            processResult.put("message", "Process failed. Try again");
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                            processResult.put("success", false);
                            processResult.put("message", "Process failed. Try again");
                        }

                       // System.out.println("appAccess.getAccessKey()=" + appAccess.getAccessKey());
                        pr=con.prepareStatement("INSERT INTO files (`unique_id`, `fileName`, `dateUploaded`, `status`) VALUES "
                                + "(?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS);

                        pr.setString(1, uniqIdImage);
                        pr.setString(2, fileSelected.getName());
                        pr.setInt(3, 1);
                        int addCount = pr.executeUpdate();

                        ResultSet res = pr.getGeneratedKeys();
                        System.out.println(res.getStatement().toString());
                        if (res.next()){
                            fileId=(int)res.getInt(1);}
                            //fileId=String.valueOf(id);}
                    }

                    String uniqId=UUID.randomUUID().toString();
                    pr=con.prepareStatement("DELETE FROM shop_details");
                    
                    pr.executeUpdate();
                    
                    pr=con.prepareStatement("INSERT INTO shop_details (`id`, `shopName`, `emailAddress`, `mobileNumber`, `addressLine1`, `uniqueId`, `addressLine2`, `details`, `dateCreated`) VALUES "
                            + "(null, ?, ?, ?, ?, ?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
                    if(fileId>-1)
                    {
                        pr=con.prepareStatement("INSERT INTO shop_details (`id`, `shopName`, `emailAddress`, `mobileNumber`, `addressLine1`, `uniqueId`, `addressLine2`, `details`, `dateCreated`, `logoId`) VALUES "
                            + "(null, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS);
                    }
                    pr.setString(1, shopName);
                    pr.setString(2, email);
                    pr.setString(3, mobile);
                    pr.setString(4, addressLine1);
                    pr.setString(5, uniqId);
                    pr.setString(6, addressLine2);
                    pr.setString(7, details);
                    
                    
                   
                    if(fileId>-1)
                    {
                        pr.setInt(8, fileId);
                    }

                    int shopItemCount=0;
                    pr.executeUpdate();
                    
                    ResultSet res = pr.getGeneratedKeys();
                    System.out.println(res.getStatement().toString());
                    
//                    System.out.println(res.getMetaData().getColumnName(0));
                    if (res.next()){
                        shopItemCount=res.getInt(1);}
                    System.out.println("Insert Id = " + shopItemCount);
                        
                    if(shopItemCount>0)
                    {
                         String uniqCode=UUID.randomUUID().toString();
                        uniqCode=uniqCode.substring(uniqCode.length()-5);
                        pr.setString(8, uniqCode);
                        
                        pr=con.prepareStatement("DELETE FROM user_profile");
                    
                        pr.executeUpdate();
                    
                    
                        pr=con.prepareStatement("INSERT INTO user_profile (`id`, `first_name`, `last_name`, `other_name`, "
                                + "`email_address`, `mobile_number`, `password`, `securityQuestionId`, "
                                + "`securityQuestionAnswer`, `date_created`, `key`, `userTypeId`,`confirmationCode`) VALUES "
                            + "(null, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, (SELECT `id` FROM user_type WHERE "
                                + "`usertype` = 'Administrator'), ?)", Statement.RETURN_GENERATED_KEYS);
                        
                        MessageDigest messageDigest;
                        messageDigest = MessageDigest.getInstance("MD5");
                        messageDigest.reset();
                        messageDigest.update(String.valueOf(userPassword).getBytes(Charset.forName("UTF8")));
                        final byte[] resultByte = messageDigest.digest();
                        final String result = new String(Hex.encodeHex(resultByte));
                        System.out.println("md5 password = "+ result);
                        String userPassword1=String.valueOf(userPassword);
                        String key= userPassword1.substring(0, 1) + userMobile.substring(4, 8) + userPassword1.substring(1, 2) + 
                                userEmail.substring(0, 2) + userPassword1.substring(2, 3) + userEmail.substring(0, 4) + 
                                userPassword1.substring(1, 2) + userMobile.substring(4, 6) + 
                                (Math.floor(9000000000.0D * Math.random()) + 1000000000L);
                        pr.setString(1, firstName);
                        pr.setString(2, lastName);
                        pr.setString(3, otherName);
                        pr.setString(4, userEmail);
                        pr.setString(5, userMobile);
                        pr.setString(6, result);
                        pr.setInt(7, securityQuestion);
                        pr.setString(8, securityQuestionAnswer);
                        pr.setString(9, key);
                        pr.setString(10, uniqCode);
                        
                        pr.executeUpdate();
                        res = pr.getGeneratedKeys();
                        System.out.println(res.getStatement().toString());
                        int insertId=-1;
                        if (res.next()){
                            insertId=(int)res.getInt(1);}
        
                        if(insertId>0)
                        {
                            processResult.put("success", true);
                            processResult.put("message", "Shop Account Created! Your confirmation code is " + uniqCode);
                            
                        }
                        else
                        {
                            processResult.put("success", false);
                            processResult.put("message", "Shop Account Could not Be Created! Try again");
                        }
                    }
                    else
                    {
                        processResult.put("success", false);
                        processResult.put("message", "Shop Account Creation Failed!");
                    }
                }
                //update items
                else
                {
                    System.out.println("Edit Process");
                    int fileId=-1;
                    String sql="";
                    if(fileSelectedPath==null)
                    {
                        sql="UPDATE shop_items SET `qr_code` = ?, `title` = ?, `price` = ?, `details` = ?, `type` = ?, `status` = ?, `dateUploaded` = NOW() WHERE `id` = " + this.editDetails.get("id");
                    }
                    else
                    {
                        uniqIdImage=UUID.randomUUID().toString();
                        try {
                            FileInputStream fileInputStream=new FileInputStream(new File(fileSelectedPath));
                            byte[] buffer = new byte[fileInputStream.available()];
                            int bytesRead = fileInputStream.read(buffer, 0, fileInputStream.available());
                            int bytesAvailable;
                            int maxBufferSize=2*1048576;
                            FileOutputStream fileOutputsteam=new FileOutputStream(new File(savePath + "/shop/" + uniqIdImage));

                            int count=0;
                            while (bytesRead > 0) {
                                fileOutputsteam.write(buffer, count++, 1);
                                bytesRead--;
                            }
                            fileInputStream.close();
                            fileOutputsteam.flush();
                            fileOutputsteam.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                            processResult.put("success", false);
                            processResult.put("message", "Process failed. Try again");
                        }
                        catch (IOException ex)
                        {
                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                            processResult.put("success", false);
                            processResult.put("message", "Process failed. Try again");
                        }

                        //System.out.println("appAccess.getAccessKey()=" + appAccess.getAccessKey());
                        pr=con.prepareStatement("INSERT INTO files (`id`, `unique_id`, `fileName`, `source_id`, `dateUploaded`, `status`, `receipient_id`) VALUES "
                                + "('', ?, ?, (SELECT `id` FROM user_profile where `key` = ?), NOW(), ?, "
                                + "(SELECT `id` FROM user_profile where `key` = ?))", Statement.RETURN_GENERATED_KEYS);

                        pr.setString(1, uniqIdImage);
                        pr.setString(2, fileSelected.getName());
                        pr.setString(3, appAccess.getAccessKey());
                        pr.setInt(4, 1);
                        pr.setString(5, appAccess.getAccessKey());
                        int addCount = pr.executeUpdate();

                        ResultSet res = pr.getGeneratedKeys();
                        if (res.next()){
                            fileId=res.getInt(1);}

                        if(fileId>-1)
                        {
                            sql="UPDATE shop_details SET `shopName` = ?, `emailAddress` = ?, `mobileNumber` = ?, "
                                    + "`addressLine1` = ?, `addressLine2` = ?, `details` = ?, `dateCreated` = NOW(), "
                                    + "`logoId` = ? WHERE `id` = "  + this.editDetails.get("id");
                        }
                        else
                        {
                            sql="UPDATE shop_details SET `shopName` = ?, `emailAddress` = ?, `mobileNumber` = ?, "
                                    + "`addressLine1` = ?, `addressLine2` = ?, `details` = ?, `dateCreated` = NOW() "
                                    + "WHERE `id` = "  + this.editDetails.get("id");
                        }

                    }
                    pr=con.prepareStatement(sql);
                    pr.setString(1, shopName);
                    pr.setString(2, email);
                    pr.setString(3, mobile);
                    pr.setString(4, addressLine1);
                    pr.setString(5, addressLine2);
                    pr.setString(6, details);
                    
                    
                    if(fileId>-1)
                    {
                        pr.setInt(7, fileId);
                    }

                    int shopItemCount = pr.executeUpdate();



                    if(shopItemCount>0)
                    {
                        
                        System.out.println("Successful");
                        processResult.put("success", true);
                        processResult.put("message", "Upload successful!");
                    }
                    else
                    {
                        System.out.println("Failed");
                        processResult.put("success", false);
                        processResult.put("message", "Process failed. Try again");
                    }
                }
            }                                           catch (NoSuchAlgorithmException ex) {
                                                            Logger.getLogger(SetUpShop.class.getName()).log(Level.SEVERE, null, ex);
                                                        } catch (SQLException ex) {
                Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                processResult.put("success", false);
                processResult.put("message", "Process failed. Try again");
            }

                                                    }
                                                    else
                                                    {
                                                         processResult.put("success", false);
                                                         processResult.put("message", "Select A security Question");
                                                    }
                                                }
                                                else
                                                {
                                                    processResult.put("success", false);
                                                    processResult.put("message", "Provide a security Answer");
                                                }
                                            }
                                            else
                                            {
                                                processResult.put("success", false);
                                                processResult.put("message", "Passwords do not match");
                                            }
                                        }
                                        else
                                        {
                                            processResult.put("success", false);
                                            processResult.put("message", "Retype your password in the confirmation password");
                                        }
                                    }
                                    else
                                    {
                                        processResult.put("success", false);
                                        processResult.put("message", "Provide a password. Passwords should exceed 6 characters");
                                    }
                                }
                                else
                                {
                                    processResult.put("success", false);
                                    processResult.put("message", "Administrators email Address should be provided");
                                }
                            }
                            else
                            {
                                processResult.put("success", false);
                                processResult.put("message", "Administrators mobile number should be provided");
                            }
                        }
                        else
                        {
                            processResult.put("success", false);
                            processResult.put("message", "Provide Administrators last Name");
                        }
                    }
                    else
                    {
                        processResult.put("success", false);
                        processResult.put("message", "Provide Administrators First Name");
                    }
                }
                else
                {
                    processResult.put("success", false);
                    processResult.put("message", "Provide contact mobile number for the shop");
                }
            }
            else
            {
                processResult.put("success", false);
                processResult.put("message", "Provide contact email address for the shop");
            }
        }
        else
        {
            processResult.put("success", false);
            processResult.put("message", "Provide a valid shop Name");
        }
        return processResult;
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Hashtable h1=SetUpShop.this.setUpShopProcess(SetUpShop.this.con, SetUpShop.this.mysql, this.savePath);
        /*Frame[] f=HomeScreen.getFrames();
        
        
        for(int count5=0; count5<f.length; count5++)
        {
            System.out.println(f[count5].toString());
            HomeScreen h=((HomeScreen)f[count5]);*/
            if(h1.get("success")==true)
            {
                /*create reg key
                RegistryKey r=new RegistryKey(RootKey.HKEY_CURRENT_USER,"Software\\ShopScan Manager");
                RegistryValue v=new RegistryValue("setUpFinished",ValueType.REG_SZ,"1");
                r.setValue(v);*/
                this.hs.displayProcessMessage(h1);
            }
            else
            {
                this.hs.displayProcessMessage(h1);
            }
        //}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JPasswordField jPasswordField4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
