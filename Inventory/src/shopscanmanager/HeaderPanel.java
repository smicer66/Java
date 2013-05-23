/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author xkalibaer
 */
public class HeaderPanel extends javax.swing.JPanel {
    private Mysql mysql;
    private Connection con;
    private ImageIcon imgLogoIcon;

    /**
     * Creates new form HeaderPanel
     */
    public HeaderPanel() {
        initComponents();
    }
    
    
    public JPanel getAllComponents(Connection con, Mysql mysql, String path)
    {
        this.con=con;
        this.mysql=mysql;
        JPanel jPanelLevel1=new JPanel();
        jPanelLevel1.setPreferredSize(new Dimension((int)(super.getPreferredSize().getWidth())- 150, 120));
        jPanelLevel1.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
        jPanelLevel1.setBackground(new java.awt.Color(23, 21, 21));
        jPanelLevel1.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        
        
            final JPanel jPanelLevelLogo=new JPanel();
            jPanelLevelLogo.setPreferredSize(new Dimension(120, 100));
            jPanelLevelLogo.setLayout(new GridBagLayout());
            jPanelLevelLogo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jPanelLevelLogo.setBackground(new java.awt.Color(23, 21, 21));
            
            
            JPanel jPanelLevelDetails=new JPanel();
            jPanelLevelDetails.setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelLogo.getWidth() - 150), 100));
            jPanelLevelDetails.setLayout(new WrapLayout(FlowLayout.LEFT));
            //jPanelLevelDetails.setPreferredSize(new Dimension(((int)(jPanelLevelDetails.getPreferredSize().getWidth())), 100));
            jPanelLevelDetails.setBackground(new java.awt.Color(23, 21, 21));
            
                JPanel jPanelStoreName=new JPanel();
                jPanelStoreName.setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelLogo.getWidth() - 180), 24));
                jPanelStoreName.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                jPanelStoreName.setBorder(new EmptyBorder(0, 0, 0,0));
                jPanelStoreName.setBackground(new java.awt.Color(23, 21, 21));
                
            ////System.out.println(((int)(super.getPreferredSize().getWidth()) - jPanelLevelLogo.getWidth() - 100));
            
                JLabel jLabelStoreName = new JLabel("Shop Name");
                PreparedStatement pr;
                try {
                    pr = this.con.prepareStatement("SELECT * FROM shop_details");
                    ResultSet rs1=mysql.query(pr);
                    while (rs1.next()) { 
                        //System.out.println("rs1.getString(title)=" + rs1.getString("shopName"));
                        jLabelStoreName.setText(rs1.getString("shopName"));
                    }
                } catch (SQLException ex) {
                    //Logger.getLogger(HeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                jLabelStoreName.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
                jLabelStoreName.setForeground(new java.awt.Color(204, 204, 204));
                    
                    jPanelStoreName.add(jLabelStoreName);
                
                JPanel jPanelLevelDetailsExport=new JPanel();
                jPanelLevelDetailsExport.setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelLogo.getWidth() - 180), 24));
                jPanelLevelDetailsExport.setLayout(new WrapLayout(FlowLayout.LEFT, 18, 8));
                jPanelLevelDetailsExport.setBackground(new java.awt.Color(23, 21, 21));
                    
                    JLabel jLabelExport = new JLabel("Export My Store");
                    jLabelExport.setFont(new java.awt.Font("Arial", 1, 9)); // NOI18N
                    jLabelExport.setForeground(new java.awt.Color(204, 204, 204));
                    jLabelExport.setBackground(new java.awt.Color(0, 0, 0));
                    
                    JLabel jLabelEditDetails = new JLabel("Edit Details");
                    jLabelEditDetails.setFont(new java.awt.Font("Arial", 1, 9)); // NOI18N
                    jLabelEditDetails.setForeground(new java.awt.Color(204, 204, 204));
                    jLabelEditDetails.setBackground(new java.awt.Color(0, 0, 0));
                    
                    //add
                jPanelLevelDetailsExport.add(jLabelExport);
                jPanelLevelDetailsExport.add(jLabelEditDetails);

                JPanel jPanelLevelDetailsAbout=new JPanel();
                jPanelLevelDetailsAbout.setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelLogo.getWidth() - 180), 24));
                jPanelLevelDetailsAbout.setLayout(new WrapLayout(FlowLayout.LEFT));
                    
                    JLabel jLabelDetailsAbout = new JLabel("Information about this shop is placed here");
                    try {
                        pr = this.con.prepareStatement("SELECT * FROM shop_details");
                        ResultSet rs1=mysql.query(pr);
                        while (rs1.next()) { 
                            //System.out.println("rs1.getString(details)=" + rs1.getString("details"));
                            jLabelDetailsAbout.setText(rs1.getString("details"));
                            
                            PreparedStatement pr1 = this.con.prepareStatement("SELECT * from files WHERE id = ?");
                            //System.out.println("fileId = " + rs1.getInt("logoId"));
                            pr1.setInt(1, rs1.getInt("logoId"));
                            ResultSet rs2=mysql.query(pr1);
                            BufferedImage bufferedImage;
                            while (rs2.next()) { 
                                //System.out.println("rs1.getString(title)=" + rs2.getString("fileName"));
                                
                                File file=null;
                                try {
                                    String[] suffix=rs2.getString("fileName").split("\\.");
                                    file = File.createTempFile(rs2.getString("unique_id"), "." + suffix[(suffix.length - 1)]);
                                    //System.out.println("temp file path =  " + file.getCanonicalPath());
                                    try {
                                            FileInputStream fileInputStream=new FileInputStream(new File(path + "/shop/" + rs2.getString("unique_id")));
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
                                            
                                            bufferedImage = ImageIO.read(new File(file.getCanonicalPath()));
                                            UtilUse util=new UtilUse();
                                            bufferedImage=util.resizeImage(bufferedImage, 120, 100, BufferedImage.TYPE_INT_RGB);
                                            this.imgLogoIcon=new ImageIcon( bufferedImage );
                                            //Image img=imgIcon.getImage();
                                            //Image imgnew=img.getScaledInstance( jLabel14.getPreferredSize().width, jLabel14.getPreferredSize().height,  java.awt.Image.SCALE_AREA_AVERAGING ) ; 
                                            JLabel logoLabel=new JLabel(this.imgLogoIcon);
                                            /*{
                                                
                                                @Override
                                                public void paintComponent (Graphics g) {
                                                    super.paintComponent (g);

                                                
                                                    if (imgLogoIcon != null) {
                                                        //System.out.println("durty");
                                                        g.drawImage (imgLogoIcon.getImage(), 0, 0, 500, 500, null);
                                                    }
                                                }
                                            };*/
                                            //BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
                                            //JLabel picLabel = new JLabel(new ImageIcon( myPicture ))
                                            logoLabel.setPreferredSize(new Dimension(120, 100));
                                            logoLabel.setBackground(new Color(0x000000));
                                            //logoLabel.setIcon(new ImageIcon(img));
                                            logoLabel.setForeground(Color.red);
                                            logoLabel.setText("yes");
                                            jPanelLevelLogo.add(logoLabel);
                                            
                                        } catch (FileNotFoundException ex) {
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        catch (IOException ex)
                                        {
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                } catch (IOException ex) {
                                    Logger.getLogger(PictureManager.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                

                            }
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(HeaderPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jLabelDetailsAbout.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
                    jLabelDetailsAbout.setForeground(new java.awt.Color(204, 204, 204));
                    
                jPanelLevelDetailsAbout.add(jLabelDetailsAbout);
            
            jPanelLevelDetails.add(jPanelStoreName);
            jPanelLevelDetails.add(jPanelLevelDetailsExport);
            jPanelLevelDetails.add(jLabelDetailsAbout);
            
        jPanelLevel1.add(jPanelLevelLogo);
        jPanelLevel1.add(jPanelLevelDetails);
        jPanelLevel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        return jPanelLevel1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
