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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author xkalibaer
 */
public class QRCodesItems extends javax.swing.JPanel {
    private final Mysql mysql;
    private final String savePath;
    private final Connection con;
    private JPanel superJP;
    private HomeScreen hs;
    private int current_page=0;
    private Dimension dimension;
    private ArrayList allItems;
    private UtilUse util;
    private JButton next;
    private JButton back;
    /**
     * Creates new form QRCodesItems
     */
    public QRCodesItems(Mysql mysql, Connection con, HomeScreen hs, String savePath) {
        initComponents();
        this.mysql=mysql;
        this.con=con;
        this.savePath=savePath;
        this.hs=hs;
        
    }
    
    public JButton getBackButton()
    {
        return this.back;
    }
    
    public JButton getNextButton()
    {
        return this.next;
    }

    public JPanel getAllComponents(Dimension dimension3)
    {
        try {
            this.util=new UtilUse();
            this.superJP=new JPanel();
            this.dimension=dimension3;
            this.superJP.setPreferredSize(dimension3);
            this.setBackground(new Color(0x262624));
            
            PreparedStatement pr3 = this.con.prepareStatement("SELECT * from shop_items");
            ResultSet rs3=mysql.query(pr3);
            ArrayList ay=new ArrayList();
            while (rs3.next()) { 
                ay.add(rs3.getInt("id"));
            }
            this.allItems=ay;
            
            PreparedStatement pr = this.con.prepareStatement("SELECT * from shop_items LIMIT ?, ?");
            pr.setInt(1, current_page);
            pr.setInt(2, util.displayMax);
            ResultSet rs=mysql.query(pr);
            System.out.println(rs.getStatement().toString());
            
            GridBagLayout gbLayout=new GridBagLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            //ArrayList arrayListAll=new ArrayList();
            ArrayList arrayListAllQR=new ArrayList();
            ArrayList arrayListAllBR=new ArrayList();
            ArrayList arrayListQR=new ArrayList();
            ArrayList arrayListBar=new ArrayList();
            while (rs.next()) { 
                if(rs.getString("barCodeNumber").length()>0)
                {
                    arrayListBar.add(rs.getString("uniqueId"));
                }
                else
                {
                    arrayListQR.add(rs.getString("uniqueId"));
                }
            }
            
            for(int count=0; count<arrayListQR.size(); count++)
            {
                JPanel jPanel_holderQR=new JPanel();
                jPanel_holderQR.setBackground(Color.WHITE);
                this.setLayout(gbLayout);
                jPanel_holderQR.setPreferredSize(new Dimension(120, 100));
                
                BufferedImage bufferedImageQR;
                
                    bufferedImageQR=new PictureManager().getPicturePath(savePath + "\\qr_codes\\", 
                            arrayListQR.get(count).toString(), arrayListQR.get(count).toString() + ".png", ".png");
                    System.out.println("File name: " + arrayListQR + ".png");
                    JLabel jLabel15=new JLabel();
                    jLabel15.setText("");
                    jLabel15.setForeground(new Color(204, 204, 204));
                    ImageIcon imgIconQR=new ImageIcon( bufferedImageQR );
                    Image imgqr=imgIconQR.getImage();
                    //Image imgqrnew=imgqr.getScaledInstance( jLabel15.getPreferredSize().width, jLabel15.getPreferredSize().height, java.awt.Image.SCALE_DEFAULT ) ; 
                    jLabel15.setIcon(new ImageIcon(imgqr));
                    
                    //jLabel15.setText(rs.getString("id"));
                    jPanel_holderQR.setBackground(Color.WHITE);
                    jPanel_holderQR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    jPanel_holderQR.setLayout(gbLayout);
                    jPanel_holderQR.add(jLabel15);
                    arrayListAllQR.add(jPanel_holderQR);
                
                    
                //this.jLabel14.setText(rs.getString("type"));//barcode
            }
             
            
            for(int count=0; count<arrayListBar.size(); count++)
            {
                JPanel jPanel_holderBR=new JPanel();
                jPanel_holderBR.setBackground(Color.WHITE);
                this.setLayout(gbLayout);
                jPanel_holderBR.setPreferredSize(new Dimension(250, 70));
                
                BufferedImage bufferedImageQR;
                
                    bufferedImageQR=new PictureManager().getPicturePath(savePath + "\\bar_codes\\", 
                            arrayListBar.get(count).toString(), arrayListBar.get(count).toString() + ".png", ".png");
                    System.out.println("File name: " + arrayListBar + ".png");
                    JLabel jLabel15=new JLabel();
                    jLabel15.setText("");
                    jLabel15.setForeground(new Color(204, 204, 204));
                    ImageIcon imgIconQR=new ImageIcon( bufferedImageQR );
                    Image imgqr=imgIconQR.getImage();
                    
                    Image imgqrnew=imgqr.getScaledInstance( 250, 70, java.awt.Image.SCALE_DEFAULT ) ; 
                    jLabel15.setIcon(new ImageIcon(imgqrnew));
                    
                    //jLabel15.setText(rs.getString("id"));
                    jPanel_holderBR.setBackground(Color.WHITE);
                    //jPanel_holderBR.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    jPanel_holderBR.setLayout(gbLayout);
                    jPanel_holderBR.add(jLabel15);
                    arrayListAllBR.add(jPanel_holderBR);
                
                    
                //this.jLabel14.setText(rs.getString("type"));//barcode
            }
             
                
            int rows1=Math.round(arrayListQR.size() % 7);
            int rows2=Math.round(arrayListBar.size() % 3);
            rows1=arrayListQR.size();
            rows2=arrayListBar.size();
            
            int counter=0;
            System.out.println("arrayListAll.size()=" + arrayListQR.size() + " & r=" + rows1);
            System.out.println("arrayListAll.size()=" + arrayListBar.size() + " & r=" + rows2);
            BoxLayoutType bxt=new BoxLayoutType(BoxLayoutType.HORIZONTAL, 10, 20);
            ArrayList arr=new ArrayList();
            JPanel jpanelRow=new JPanel();
            for(int count=0; count<rows1; count++)
            {
                if(count%7==0)
                {
                    jpanelRow=new JPanel();
                    jpanelRow.setLayout(bxt);
                    jpanelRow.setBackground(new Color(0xffffff));
                    jpanelRow.setPreferredSize(new Dimension(dimension3.width, 120));
                    //jpanelRow.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                }
                jpanelRow.add((JPanel)arrayListAllQR.get(count));
                        
                arr.add(jpanelRow);
            }
            
            for(int count=0; count<rows2; count++)
            {
                if(count%3==0)
                {
                    jpanelRow=new JPanel();
                    jpanelRow.setLayout(bxt);
                    jpanelRow.setBackground(new Color(0xffffff));
                    jpanelRow.setPreferredSize(new Dimension(dimension3.width, 120));
                    //jpanelRow.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                }
                jpanelRow.add((JPanel)arrayListAllBR.get(count));
                        
                arr.add(jpanelRow);
            }

            BoxLayoutType bxt1=new BoxLayoutType(BoxLayoutType.VERTICAL, 35, 0);

            this.superJP.setLayout(bxt1);
            this.superJP.setBackground(new Color(0xffffff));
            for(int count=0; count<arr.size(); count++)
            {
                //System.out.println("ugly=" + count);
                this.superJP.add((JPanel)arr.get(count));
            }
            this.setPreferredSize(dimension3);
            

            BoxLayoutType bxt3=new BoxLayoutType(BoxLayoutType.VERTICAL, 0, 0);
            this.setLayout(bxt3);
            JPanel jPanel2=new JPanel();
            jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
            jPanel2.setBackground(new java.awt.Color(42, 42, 42));
            jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

            JLabel jLabel55=new JLabel();
            jLabel55.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
            jLabel55.setForeground(new java.awt.Color(204, 204, 204));
            jLabel55.setText("All Shop Items - QR and Bar codes!");
            jPanel2.add(jLabel55);
        
            superJP.setBounds(20, 20, dimension3.width - 20, dimension3.height - 20);
            //System.out.println("Components count=" + this.getComponentCount());
            this.removeAll();
            this.add(new HeaderPanel().getAllComponents(this.con, this.mysql,this.savePath));
            this.add(jPanel2);
            this.next=new JButton("Next");
            this.back=new JButton("Back");
            
            JPanel buttonHolder=new JPanel();
            buttonHolder.setPreferredSize(new Dimension(dimension3.width, 40));
            FlowLayout fl=new FlowLayout(FlowLayout.RIGHT);
            buttonHolder.setLayout(fl);
            System.out.println("QRCodesItems.this.current_page ....=" + QRCodesItems.this.current_page);
            if(QRCodesItems.this.current_page>=util.displayMax)
            {
                buttonHolder.add(back);
            }
            if((QRCodesItems.this.current_page + util.displayMax) < QRCodesItems.this.allItems.size())
            {
                buttonHolder.add(next);
            }
            buttonHolder.setBackground(new Color(0xffffff));
            this.superJP.add(buttonHolder);
            this.add(superJP);
            
            this.hs.printJPanel=superJP;
            //(this.jTextPane1).setText(rs.getString("details"));
                
            
        } catch (SQLException ex) {
            Logger.getLogger(QRCodesItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    public void setCurrentPage(int current_page)
    {
        this.current_page=current_page;
    }
    
    public int getCurrentPage()
    {
        return this.current_page;
    }
    
    
    public int getTotalItemsSize()
    {
        return QRCodesItems.this.allItems.size();
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
