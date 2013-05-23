/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import com.michaelbaranov.microba.calendar.DatePicker;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author xkalibaer
 */
public class UserManage extends javax.swing.JPanel {

    private Connection con;
    private DatePicker dp;
    private DatePicker dp1;
    private Mysql mysql;
    private Dimension dimension;
    private String installPath;
    private HomeScreen hs;
    /**
     * Creates new form UserManage
     */
    public UserManage(Mysql mysql, Connection con, Dimension dimension, String installPath, HomeScreen hs) {
        this.con=con;
        this.mysql=mysql;
        this.dimension=dimension;
        this.installPath=installPath;
        this.hs=hs;
        initComponents();
        System.out.println("New User Reports");
    }
    
    
    public JPanel getAllComponents()
    {
        JPanel jPanel5_1=new JPanel();
        jPanel5_1.setBackground(new Color(23, 23, 23));
        FlowLayout layout=new FlowLayout(FlowLayout.LEADING);
        jPanel5_1.setLayout(layout);
        ArrayList ar=new ArrayList();
        try {
            PreparedStatement pr3 = this.con.prepareStatement("Select * from user_profile");
            System.out.println("----" +  + (dimension.width - 50));
            ResultSet rs1=this.mysql.query(pr3);

            jPanel2.setPreferredSize(new Dimension((dimension.width), dimension.height));
            jPanel5.setPreferredSize(new Dimension((dimension.width - 50), dimension.height - 100));
            jPanel8.setPreferredSize(new Dimension((dimension.width  - 60), (dimension.height - 100)));
            jPanel5_1.setPreferredSize(new Dimension((dimension.width  - 70), (dimension.height - 125)));
            jPanel8.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            FlowLayout fl=new FlowLayout(FlowLayout.LEADING, 5, 5);
            jPanel8.setLayout(fl);
            BufferedImage bufferedImageQR;
            ImageIcon iicon;

            ToolTipManager.sharedInstance().setInitialDelay(0);
            ToolTipManager.sharedInstance().setDismissDelay(500);

            while (rs1.next()) { 
               // System.out.println(" File type = " + rs1.getString("fileType"));
                JPanel jPaneltmp=new JPanel();
                jPaneltmp.setBackground(new Color(23, 23, 23));
                jPaneltmp.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jPaneltmp.setPreferredSize(new Dimension(100, 140));
                JLabel jLabelTmp=new JLabel();
                if(rs1.getString("fileId")==null)
                {
                    jLabelTmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/user.png")));
                }
                else 
                {
                    PreparedStatement pr31 = this.con.prepareStatement("Select * from files where id = ?");
                    pr31.setInt(1, rs1.getInt("fileId"));
                    System.out.println("----" +  + (dimension.width - 50));
                    ResultSet rs31=this.mysql.query(pr31);
                    rs31.next();
                    String uniq=rs31.getString("unique_id");
                    String[] orig=rs31.getString("fileName").split(".");
                    
                    bufferedImageQR=new PictureManager().getPicturePath(installPath + "\\user\\", 
                            uniq, "." + uniq + orig[orig.length - 1], "." + orig[orig.length - 1]);
                    
                    if(bufferedImageQR!=null)
                    {
                        ImageIcon imgIcon=new ImageIcon( bufferedImageQR );
                        Image img=imgIcon.getImage();
                        Image imgnew=img.getScaledInstance( jPaneltmp.getPreferredSize().width, jPaneltmp.getPreferredSize().height,  java.awt.Image.SCALE_AREA_AVERAGING ) ; 
                        jLabelTmp.setIcon(new ImageIcon(imgnew));
                    }
                }

                jLabelTmp.setToolTipText(rs1.getString("first_name"));
                //MouseListenClass mouseListenClass=new MouseListenClass(installPath + "\\reports\\" + rs31.getString("fileName") + ".csv");
                //jLabelTmp.addMouseListener(mouseListenClass);
                jPaneltmp.add(jLabelTmp);            
                JPanel m=new JPanel();
                m.setLayout(new FlowLayout(FlowLayout.LEADING));
                JCheckBox check=new JCheckBox();
                check.setBackground(new Color(23,21, 21));
                m.setBackground(new Color(23,21, 21));
                m.add(check);
                JLabel lbl=new JLabel(rs1.getString("first_name"));
                lbl.setForeground(new Color(204,204, 204));
                lbl.setBackground(new Color(23,21, 21));
                m.add(lbl);
                jPaneltmp.add(m);
                ar.add(jPaneltmp);
            }


            for(int count=0; count<ar.size(); count++)
            {
                System.out.println(" Add number = " + count);
                jPanel5_1.add((JPanel)ar.get(count));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jPanel8.add(jPanel5_1);
        return this;
    }
    
    
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
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(23, 21, 21));

        jPanel1.setBackground(new java.awt.Color(23, 21, 21));

        jPanel2.setBackground(new java.awt.Color(23, 21, 21));

        jPanel5.setBackground(new java.awt.Color(23, 21, 21));

        jPanel8.setBackground(new java.awt.Color(23, 23, 23));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1125, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Users");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(492, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1161, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1665, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1693, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    // End of variables declaration//GEN-END:variables
}
