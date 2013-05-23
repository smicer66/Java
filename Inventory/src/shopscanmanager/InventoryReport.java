/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import au.com.bytecode.opencsv.CSVWriter;
import com.michaelbaranov.microba.calendar.DatePicker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author xkalibaer
 */
public class InventoryReport extends javax.swing.JPanel {
    private String installPath="";
    private Connection con;
    private Mysql mysql;
    private HomeScreen hs;
    DatePicker dp;
    DatePicker dp1;
    private Dimension dimension;

    /**
     * Creates new form InventoryReport
     */
    public InventoryReport(Mysql mysql, Connection con, Dimension dimension, String installPath, HomeScreen hs) {
        this.con=con;
        this.mysql=mysql;
        this.dimension=dimension;
        this.installPath=installPath;
        this.hs=hs;
        initComponents();
        dp=new DatePicker();
        dp1=new DatePicker();
    }
    
    
    public JPanel getAllComponents()
    {
        JPanel jPanel5_1=new JPanel();
        jPanel5_1.setBackground(new Color(23, 23, 23));
        FlowLayout layout=new FlowLayout(FlowLayout.LEADING);
        jPanel5_1.setLayout(layout);
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        dp=new DatePicker();
        this.jPanel6.add(dp);
        this.jLabel6.setToolTipText("Click to get calendar");
        this.jLabel6.setToolTipText("Click to get calendar");
        dp1=new DatePicker();
        this.jPanel7.add(dp1);
        System.out.println("IN Inventory Reports Get All items");
        try {
            PreparedStatement pr3;
            pr3 = this.con.prepareStatement("Select * from shop_Item_type");
            ResultSet rs1=this.mysql.query(pr3);

            ArrayList ar=new ArrayList();
            DefaultListModel dlm=new DefaultListModel();
            dlm.addElement("-Types of Items-");
            while (rs1.next()) { 
                System.out.println("User type=" + rs1.getString("name"));
                dlm.addElement(rs1.getString("name"));
            }
            
            this.jList1.setModel(dlm);
            
            
            
            pr3 = this.con.prepareStatement("Select * from reports WHERE report_type = ?");
            pr3.setString(1, "Inventory Reports");
            System.out.println("----" +  + (dimension.width - jPanel3.getPreferredSize().width - 50));
            rs1=this.mysql.query(pr3);

            jPanel4.setPreferredSize(new Dimension((dimension.width - jPanel3.getPreferredSize().width - 50), dimension.height - 100));
            jPanel5.setPreferredSize(new Dimension((dimension.width - jPanel3.getPreferredSize().width - 60), (dimension.height - 100)));
            jPanel5_1.setPreferredSize(new Dimension((dimension.width - jPanel3.getPreferredSize().width - 70), (dimension.height - 120)));
            jPanel5.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            FlowLayout fl=new FlowLayout(FlowLayout.LEADING, 5, 5);
            jPanel5.setLayout(fl);
            BufferedImage bufferedImageQR;
            ImageIcon iicon;
            
            ToolTipManager.sharedInstance().setInitialDelay(0);
            ToolTipManager.sharedInstance().setDismissDelay(500);
            
            while (rs1.next()) { 
                System.out.println(" File type = " + rs1.getString("fileType"));
                JPanel jPaneltmp=new JPanel();
                jPaneltmp.setBackground(new Color(23, 23, 23));
                jPaneltmp.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                
                jPaneltmp.setPreferredSize(new Dimension(60, 60));
                JLabel jLabelTmp=new JLabel();
                if(rs1.getString("fileType").equals("CSV"))
                {
                    jLabelTmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/csv.png")));
                }
                else if(rs1.getString("fileType").equals("PDF"))
                {
                    jLabelTmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/pdf.png")));
                }
                else if(rs1.getString("fileType").equals("XLS"))
                {
                    jLabelTmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/xls.png")));
                }
                
                PreparedStatement pr31 = this.con.prepareStatement("Select * from files WHERE id = ?");
                pr31.setInt(1, rs1.getInt("fileId"));
                System.out.println("----" +  + (dimension.width - jPanel3.getPreferredSize().width - 50));
                ResultSet rs31=this.mysql.query(pr31);
                rs31.next();
                jLabelTmp.setToolTipText(installPath + "\\reports\\" + rs31.getString("fileName") + ".csv");
                MouseListenClass mouseListenClass=new MouseListenClass(installPath + "\\reports\\" + rs31.getString("fileName") + ".csv");
                jLabelTmp.addMouseListener(mouseListenClass);
                jPaneltmp.add(jLabelTmp);            
                ar.add(jPaneltmp);
            }
            
            
            for(int count=0; count<ar.size(); count++)
            {
                System.out.println(" Add number = " + count);
                jPanel5_1.add((JPanel)ar.get(count));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.jPanel5.add(jPanel5_1);
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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(23, 21, 21));

        jPanel1.setBackground(new java.awt.Color(23, 21, 21));

        jPanel3.setBackground(new java.awt.Color(23, 23, 23));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(30, 30, 30), 1, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Generate these reports:");

        jButton1.setBackground(new java.awt.Color(40, 40, 40));
        jButton1.setText("Generate List of All Items");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(160, 160, 160));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Customized Reports:");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jPanel2.setBackground(new java.awt.Color(25, 25, 25));

        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Items added between");

        jTextField1.setEditable(false);
        jTextField1.setText("jTextField1");

        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("and");

        jTextField2.setEditable(false);
        jTextField2.setText("jTextField1");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/office_calendar.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/office_calendar.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jLabel7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel7FocusGained(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(40, 40, 40));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(40, 40, 40));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jButton4.setBackground(new java.awt.Color(40, 40, 40));
        jButton4.setText("Generate Selected Report");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(40, 40, 40));
        jCheckBox1.setForeground(new java.awt.Color(204, 204, 204));
        jCheckBox1.setText("Lump Everything in one report");

        jButton2.setBackground(new java.awt.Color(40, 40, 40));
        jButton2.setText("Generate All Items Sold");
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton4)
                                            .addComponent(jCheckBox1))))
                                .addGap(29, 29, 29))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(25, 25, 25)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(23, 21, 21));

        jPanel5.setBackground(new java.awt.Color(23, 21, 21));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1125, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Reports Generated");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(1153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            Calendar cal = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String file_name = df.format(cal.getTime());

            CSVWriter writer = new CSVWriter(new FileWriter(installPath + "\\reports\\" + file_name + ".csv"), '\t');
            PreparedStatement pr3;
            pr3 = this.con.prepareStatement("Select * from shop_items, shop_item_type WHERE shop_items.type = shop_item_type.id");
            ResultSet rs1 = this.mysql.query(pr3);
            new UtilUse().generateCSVForInventory(rs1, writer, file_name).close();
            new UtilUse().logReport(file_name, "Inventory Reports", "CSV", this.con);

            Hashtable h1 = new Hashtable();
            h1.put("success", true);
            h1.put("message", "Report Created.\nFile(s) created:\n"
                    + installPath + "\\reports\\" + file_name + ".csv");
            this.hs.displayProcessMessage(h1);
            // feed in your array (or convert your data to an array)

        } catch (IOException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        dp.showPopup();
        dp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                    java.util.Date d = ((DatePicker) e.getSource()).getDate();
                    String dString = df.format(d);
                    InventoryReport.this.jTextField1.setText(dString);
                } catch (UnsupportedOperationException ex) {
                }
            }
        });
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        dp1.showPopup();
        dp1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                    java.util.Date d = ((DatePicker) e.getSource()).getDate();
                    String dString = df.format(d);
                    InventoryReport.this.jTextField2.setText(dString);
                } catch (UnsupportedOperationException ex) {
                }
            }
        });
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel7FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel7FocusGained

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            Calendar cal = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String file_name = df.format(cal.getTime());
            java.util.List selectedList = this.jList1.getSelectedValuesList();
            CSVWriter writer;
            String files = "";
            if (this.jCheckBox1.isSelected() == true) {
                writer = new CSVWriter(new FileWriter(installPath + "\\reports\\" + file_name + ".csv"), '\t');
                files = files + "\n" + installPath + "\\reports\\" + file_name + ".csv";
                PreparedStatement pr3;
                String strAttach = "";
                if (jTextField1.getText().length() > 0 && jTextField2.getText().length() > 0) {
                    strAttach = strAttach + " AND user_profile.dateUploaded BETWEEN ? AND ?";
                }
                for (int count = 0; count < selectedList.size(); count++) {
                    System.out.println("selected items = " + selectedList.get(count).toString());
                    try {
                        pr3 = this.con.prepareStatement("Select * from shop_items, shop_item_type WHERE shop_items.type = shop_item_type.id AND shop_item_type.name = ?" + strAttach);
                        pr3.setString(1, selectedList.get(count).toString());
                        if (jTextField1.getText().length() > 0 && jTextField2.getText().length() > 0) {
                            pr3.setString(2, jTextField1.getText());
                            pr3.setString(3, jTextField1.getText());
                        }
                        ResultSet rs1 = this.mysql.query(pr3);
                        System.out.println("QUERY STRING = " + rs1.getStatement().toString());
                        writer = new UtilUse().generateCSVForInventory(rs1, writer, file_name);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                new UtilUse().logReport(file_name, "Inventory Reports", "CSV", this.con);
                writer.close();

                Hashtable h1 = new Hashtable();
                h1.put("success", true);
                h1.put("message", "Report Created.\nFile(s) created:"
                        + files);
                this.hs.displayProcessMessage(h1);
            } else {

                PreparedStatement pr3;
                String strAttach = "";
                if (jTextField1.getText().length() > 0 && jTextField2.getText().length() > 0) {
                    strAttach = strAttach + " AND user_profile.date_created BETWEEN ? AND ?";
                }
                for (int count = 0; count < selectedList.size(); count++) {
                    file_name = df.format(cal.getTime()) + "_" + selectedList.get(count).toString();
                    files = files + "\n" + installPath + "\\reports\\" + file_name + ".csv";
                    writer = new CSVWriter(new FileWriter(installPath + "\\reports\\" + file_name + ".csv"), '\t');
                    System.out.println("selected items = " + selectedList.get(count).toString());
                    try {
                        pr3 = this.con.prepareStatement("Select * from shop_items, shop_item_type WHERE shop_items.type = shop_item_type.id AND shop_item_type.name = ?" + strAttach);
                        pr3.setString(1, selectedList.get(count).toString());
                        if (jTextField1.getText().length() > 0 && jTextField2.getText().length() > 0) {
                            pr3.setString(2, jTextField1.getText());
                            pr3.setString(3, jTextField1.getText());
                        }
                        ResultSet rs1 = this.mysql.query(pr3);
                        System.out.println("QUERY STRING = " + rs1.getStatement().toString());
                        writer = new UtilUse().generateCSVForInventory(rs1, writer, file_name);
                        new UtilUse().logReport(file_name, "User Reports", "CSV", this.con);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    writer.close();

                    Hashtable h1 = new Hashtable();
                    h1.put("success", true);
                    h1.put("message", "Report Created.\nFile(s) created:"
                            + files);
                    this.hs.displayProcessMessage(h1);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
            Hashtable h1 = new Hashtable();
            h1.put("success", true);
            h1.put("message", "Report was not created. Errors encountered. Try again!");
            this.hs.displayProcessMessage(h1);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Calendar cal = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String file_name = df.format(cal.getTime());

            CSVWriter writer = new CSVWriter(new FileWriter(installPath + "\\reports\\" + file_name + ".csv"), '\t');
            PreparedStatement pr3;
            pr3 = this.con.prepareStatement("Select * from shop_items_log, user_profile, shop_items, shop_item_type WHERE shop_items_log.shopItemId = shop_items.id AND shop_items.type = shop_item_type.id AND shop_items_log.userId = user_profile.id");
            ResultSet rs1 = this.mysql.query(pr3);
            new UtilUse().generateCSVForInventoryLog(rs1, writer, file_name).close();
            new UtilUse().logReport(file_name, "Inventory Reports", "CSV", this.con);

            Hashtable h1 = new Hashtable();
            h1.put("success", true);
            h1.put("message", "Report Created.\nFile(s) created:\n"
                    + installPath + "\\reports\\" + file_name + ".csv");
            this.hs.displayProcessMessage(h1);
            // feed in your array (or convert your data to an array)

        } catch (IOException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    class MouseListenClass implements MouseListener
    {
        String dataValue;
        public MouseListenClass(String dataValue)
        {
            this.dataValue=dataValue;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            try
            {
                int clickCount=e.getClickCount();
                System.out.println("clickCount=" + clickCount);
                if(clickCount==2)
                {
                    Desktop desktop = Desktop.getDesktop();
                    System.out.println("Open this file = " + dataValue);
                    desktop.edit(new File(dataValue));
                }
            } 
            catch(UnsupportedOperationException ex)
            {
            }
            catch (Exception ex) {
                Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            try
            {
                
            }
            catch(UnsupportedOperationException ex)
            {
                
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            try
            {
                
            }
            catch(UnsupportedOperationException ex)
            {
                
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            try
            {
                
            }
            catch(UnsupportedOperationException ex)
            {
                
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            try
            {
                
            }
            catch(UnsupportedOperationException ex)
            {
                
            }
        }
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
