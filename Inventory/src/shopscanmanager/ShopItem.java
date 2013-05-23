/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author xkalibaer
 */
public class ShopItem extends javax.swing.JPanel {
    private Connection con;
    private final Mysql mysql;
    private int itemId;
    private AppAccess appAccess;
    private  HomeScreen hs;
    private String savePath="";

    /**
     * Creates new form ShopItem
     */
    public ShopItem(Connection con, Mysql mysql, AppAccess appAccess, HomeScreen hs, String savePath) {
        this.hs=hs;
        this.con=con;
        this.mysql=mysql;
        this.appAccess=appAccess;
        this.savePath=savePath;
        initComponents();
    }

    public JPanel getAllComponents(int id, String path)
    {
        HeaderPanel header=new HeaderPanel();
        this.jPanel1.setBackground(new Color(23, 21, 21));
        this.jPanel3.setBackground(new Color(23, 21, 21));
                
        System.out.println("We are In GetAllCOmpoentns ShopItem" + id);
        PreparedStatement pr;
        try {
            pr = this.con.prepareStatement("SELECT * from shop_items LIMIT ?, 1");
            pr.setInt(1, id);
            ResultSet rs=mysql.query(pr);


            while (rs.next()) { 
                System.out.println("rs.getString(title)=" + rs.getString("title"));
                this.itemId=rs.getInt("id");
                this.jLabel10.setText(rs.getString("title"));
                this.jLabel10.setForeground(new Color(204, 204, 204));
                
                PreparedStatement pr1 = this.con.prepareStatement("SELECT * from shop_item_type WHERE id = ?");
                pr1.setString(1, rs.getString("type"));
                ResultSet rs1=mysql.query(pr1);
                System.out.println(rs1.getStatement().toString());
                while (rs1.next()) { 
                    this.jLabel7.setText(rs1.getString("name"));
                }
                
                this.jLabel7.setForeground(new Color(204, 204, 204));
                
                if(rs.getString("barCodeNumber").length()>0)
                {
                    this.jLabel12.setText("Bar code");//identification type
                    this.jLabel12.setForeground(new Color(204, 204, 204));
                    this.jLabel13.setText(rs.getString("barCodeNumber"));//barcode
                    this.jLabel13.setForeground(new Color(204, 204, 204));
                }
                else
                {
                    jPanel5.remove(jLabel13);
                    jPanel5.remove(jLabel5);
                    this.jLabel12.setText("QR Code");//barcode
                }
                
                this.jLabel8.setText(rs.getString("price") + " Naira");
                this.jLabel8.setForeground(new Color(204, 204, 204));
                
                pr = this.con.prepareStatement("SELECT * from files WHERE id = ?");
                System.out.println("fileId = " + rs.getInt("fileId"));
                pr.setInt(1, rs.getInt("fileId"));
                rs1=mysql.query(pr);
                BufferedImage bufferedImage;
                BufferedImage bufferedImageQR;
                while (rs1.next()) { 
                    System.out.println("rs1.getString(title)=" + rs1.getString("fileName"));
                    
                     bufferedImage=new PictureManager().getPicturePath(path + "\\images\\", rs1.getString("unique_id"), rs1.getString("fileName"));
                     bufferedImage=new UtilUse().resizeImage(bufferedImage, 120, 100, BufferedImage.TYPE_INT_RGB);
                     File file=null;
                     if(bufferedImage!=null)
                     {
                        try    {
                            String[] suffix=rs1.getString("fileName").split("\\.");
                            file = File.createTempFile(rs1.getString("unique_id"), "." + suffix[(suffix.length - 1)]);
                            System.out.println("temp file path =  " + file.getCanonicalPath());
                            try {
                                    FileInputStream fileInputStream=new FileInputStream(new File(path + "\\images\\" + rs1.getString("unique_id")));
                                    byte[] buffer = new byte[fileInputStream.available()];
                                    int bytesRead = fileInputStream.read(buffer, 0, fileInputStream.available());
                                    FileOutputStream fileOutputsteam=new FileOutputStream(file);

                                    int count=0;
                                    while (bytesRead > 0) {
                                        fileOutputsteam.write(buffer, count++, 1);
                                        bytesRead--;
                                    }
                                    fileInputStream.close();
                                    fileOutputsteam.flush();
                                    fileOutputsteam.close();
                                } catch (FileNotFoundException ex) {
                                    //Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                catch (IOException ex)
                                {
                                    //Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        } catch (IOException ex) {
                            //Logger.getLogger(PictureManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     }
                     bufferedImageQR=new PictureManager().getPicturePath(path + "\\qr_codes\\", rs.getString("uniqueId"), rs.getString("uniqueId") + ".png", ".png");
                     this.jLabel14.setText("");
                    this.jLabel14.setForeground(new Color(204, 204, 204));
                    if(bufferedImageQR!=null)
                    {
                        ImageIcon imgIcon=new ImageIcon( bufferedImage );
                    
                        Image img=imgIcon.getImage();
                        //Image imgnew=img.getScaledInstance( jLabel14.getPreferredSize().width, jLabel14.getPreferredSize().height,  java.awt.Image.SCALE_AREA_AVERAGING ) ; 
                        this.jLabel14.setIcon(new ImageIcon(img));
                    }
                    this.jLabel15.setText("");
                    this.jLabel15.setForeground(new Color(204, 204, 204));
                    ImageIcon imgIconQR=new ImageIcon( bufferedImageQR );
                    Image imgqr=imgIconQR.getImage();
                    //Image imgqrnew=imgqr.getScaledInstance( jLabel15.getPreferredSize().width, jLabel15.getPreferredSize().height, java.awt.Image.SCALE_DEFAULT ) ; 
                    this.jLabel15.setIcon(new ImageIcon(imgqr));
                    
                    this.jPanel5.setBackground(new Color(0x262624));
                    this.jPanel4.setBackground(new Color(0x262624));
                    
                    GridBagLayout gbLayout=new GridBagLayout();
                    GridBagConstraints gbc = new GridBagConstraints();
                    this.jPanel5.setLayout(gbLayout);
                    this.jPanel4.setLayout(gbLayout);
                    
                    
                }
                //this.jLabel14.setText(rs.getString("type"));//barcode
                
                
                
                //(this.jTextPane1).setText(rs.getString("details"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShopItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BoxLayoutType bxt=new BoxLayoutType(BoxLayoutType.VERTICAL, 0, 0);
        this.setLayout(bxt);
        this.setBackground(new java.awt.Color(23, 21, 21));
        
        this.add(header.getAllComponents(this.con, this.mysql, this.savePath), 0);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(42, 42, 42));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel55.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(204, 204, 204));
        jLabel55.setText("My Shop Item Details");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel55)
                .addContainerGap(875, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel55))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Name of Item:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Item Type:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Identification Type:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Bar Code Number:");

        jButton1.setText("Edit this item");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Price:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("jLabel10");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("jLabel7");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("jLabel12");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("jLabel13");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("jLabel8");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jButton1)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel8))
                .addContainerGap(276, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(32, 32, 32)
                .addComponent(jButton1)
                .addContainerGap(244, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setText("jLabel14");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("jLabel14");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(963, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1037, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1590, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    //edit button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Frame[] f=HomeScreen.getFrames();
        AddNewItem addNewItem=new AddNewItem(this.con, this.mysql, this.hs, this.savePath);
        
        for(int count5=0; count5<f.length; count5++)
        {
            System.out.println(f[count5].toString());
            HomeScreen h=((HomeScreen)f[count5]);
            
            h.showShopItem(addNewItem.showEditScreen(itemId, this.con, this.mysql, this.appAccess));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
