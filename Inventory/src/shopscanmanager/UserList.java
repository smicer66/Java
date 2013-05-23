/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.json.simple.JSONObject;

/**
 *
 * @author xkalibaer
 */
public class UserList extends javax.swing.JPanel {

    /**
     * Creates new form UserList
     */
    private ArrayList<JSONObject> jsonObjectArray;
    private final Connection con;
    private final Mysql mysql;
    private JPanel itemSelectedIndex;
    
    
    
    public UserList(ArrayList<JSONObject> jsonObjectArray, Connection con, Mysql mysql) {
        this.jsonObjectArray=jsonObjectArray;
        this.con=con;
        this.mysql=mysql;
        //initComponents();
    }
    
    

    

    public JPanel getAllComponents() {
        //JPanel panel=new JPanel();
        System.out.println("jsonObjectArray.size() = " + jsonObjectArray.size());
        HeaderPanel header=new HeaderPanel();
        
        BoxLayoutType bxt=new BoxLayoutType(BoxLayoutType.VERTICAL, 0, 20);
        this.setLayout(bxt);
        this.setBackground(new java.awt.Color(23, 21, 21));
        this.add(header.getAllComponents(this.con, this.mysql, "C:/wamp/www/scanshoplocal/"));
        
        GridBagConstraints gbc = new GridBagConstraints();
            
            
        
        
                
        //jPanel22.add(jLabelDate);
        //jPanel22.revalidate();
        //jLabel62.setText("18");
        //this.removeAll();
        //jPanel26.add(jLabelDate);
        //this.revalidate();
        //this.repaint();
        PreparedStatement pr;
        for(int count=0;count<jsonObjectArray.size();count++)
        {
            String dateString=jsonObjectArray.get(count).get("dateUploaded").toString();
            //String dateString="2012-02-02";
            System.out.println(dateString);
            JPanel jPanelLevel2=new JPanel();
            //jPanelLevel2.setPreferredSize(new Dimension((int)(super.getPreferredSize().getWidth()), 120));
            //jPanelLevel2.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
            //jPanelLevel2.setLayout(new BorderLayout());
            BoxLayoutType bx=new BoxLayoutType();
            bx.setOrientation(BoxLayoutType.HORIZONTAL);
            bx.setHgap(15);
            bx.setVgap(0);
            
            jPanelLevel2.setLayout(bx);
            jPanelLevel2.setBackground(new java.awt.Color(23, 21, 21));
            
            
            JPanel jPanelLevelDate=new JPanel();
            JPanel jPanelLevelDateHolder=new JPanel();
            jPanelLevelDate.setPreferredSize(new Dimension(100, 80));
            jPanelLevelDateHolder.setPreferredSize(new Dimension(120, 100));
            jPanelLevelDateHolder.setMaximumSize(new Dimension(120, 100));
            jPanelLevelDate.setLayout(new GridBagLayout());
            jPanelLevelDateHolder.setLayout(new BoxLayoutType(BoxLayoutType.HORIZONTAL, 0, 0));
            //jPanelLevelDateHolder.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jPanelLevelDate.setBackground(new java.awt.Color(23, 21, 21));
            jPanelLevelDateHolder.setBackground(new java.awt.Color(23, 21, 21));
            JLabel jLabelDateLabel = new JLabel(new UtilUse().getDateSuffix(dateString));
            jLabelDateLabel.setFont(new java.awt.Font("Arial", 1, 30));
            jLabelDateLabel.setForeground(new java.awt.Color(204, 204, 204));
            
            jPanelLevelDate.add(jLabelDateLabel, gbc);
            jPanelLevelDateHolder.add(jPanelLevelDate);
            
            JPanel jPanelLevelItemDetails=new JPanel();
            //jPanelLevelItemDetails.setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelLogo.getWidth() - 150), 100));
            //jPanelLevelItemDetails.setLayout(new WrapLayout(FlowLayout.LEFT));
            //jPanelLevelItemDetails.setLayout();
            GridBagLayout gridbag=new GridBagLayout();
            //jPanelLevelItemDetails.setLayout(gridbag);
            //BoxLayout boxlayout=new BoxLayout(jPanelLevelItemDetails, BoxLayout.PAGE_AXIS);
            BoxLayoutType boxlayout=new BoxLayoutType(BoxLayoutType.VERTICAL, 0, 0);
            boxlayout.setVgap(0);
            jPanelLevelItemDetails.setLayout(boxlayout);
            jPanelLevelItemDetails.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            jPanelLevelItemDetails.setLocation(0, 0);
            jPanelLevelItemDetails.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weighty=0;
            c.weightx=0;
            jPanelLevelItemDetails.setBackground(new java.awt.Color(23, 21, 21));
            
            JLabel jLabelDateOfTransaction=new JLabel(dateString);
            jLabelDateOfTransaction.setFont(new java.awt.Font("Arial", 1, 13));
            jLabelDateOfTransaction.setForeground(new java.awt.Color(204, 204, 204));
            
                
                
                try {
                    ArrayList<JSONObject> arrayItems=getAllItemsByDate(dateString);

                    JPanel[] jPanelItems=new JPanel[arrayItems.size()];
                    

                    //jPanelLevelItemDetails.add(jLabelDateOfTransaction);
                    
                    for(int count1=0;count1<arrayItems.size();count1++)
                    {
                        
                        JCheckBox checkBox=new JCheckBox();
                        checkBox.setPreferredSize(new Dimension(13, 10));
                        JPanel panelBox=new JPanel();
                        panelBox.setBackground(new Color(0x262624));
                        panelBox.setPreferredSize(new Dimension(30, 16));
                        panelBox.add(checkBox);
                        System.out.println(" & rs.getString(title)=" + arrayItems.get(count1).get("title").toString());
                            //jButtonKeepTrack.setIcon(new javax.swing.ImageIcon(getClass().getResource("favunselected.png")));
                            JLabel favPicLabel=new JLabel();
                            BufferedImage myPicture;
                                
                            try {
                                myPicture = ImageIO.read(new File(getClass().getResource("favunselected.png").getPath().substring(1)));
                                favPicLabel = new JLabel(new ImageIcon( myPicture ));
                                System.out.println("Image created");
                                //favPicLabel.setBackground(new java.awt.Color(23, 21, 21));
                            } catch (IOException ex) {
                                Logger.getLogger(ShopItemsList.class.getName()).log(Level.SEVERE, null, ex);
                            }//jButtonKeepTrack.setBackground(new java.awt.Color(23, 21, 21));
                            //jButtonKeepTrack.setFont(new java.awt.Font("Arial", 1, 9));
                            //JButton jButtonItemEdit=new JButton("E");
                            JLabel editPicLabel=new JLabel();

                            try {
                                myPicture = ImageIO.read(new File(getClass().getResource("edit.png").getPath().substring(1)));
                                editPicLabel = new JLabel(new ImageIcon( myPicture ));
                                //favPicLabel.setBackground(new java.awt.Color(23, 21, 21));
                            } catch (IOException ex) {
                                Logger.getLogger(ShopItemsList.class.getName()).log(Level.SEVERE, null, ex);
                            }


                            JLabel userRightPicLabel=new JLabel();

                                System.out.println();
                            try {
                                myPicture = ImageIO.read(new File(getClass().getResource("userright.png").getPath().substring(1)));
                                userRightPicLabel = new JLabel(new ImageIcon( myPicture ));
                                
                                //favPicLabel.setBackground(new java.awt.Color(23, 21, 21));
                            } catch (IOException ex) {
                                Logger.getLogger(ShopItemsList.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            //jButtonItemEdit.setFont(new java.awt.Font("Arial", 1, 9));rs.getString("title")

                            JLabel jLabelItemName=new JLabel(arrayItems.get(count1).get("title").toString());
                            jLabelItemName.setForeground(new java.awt.Color(204, 204, 204));
                            jLabelItemName.setPreferredSize(new Dimension(500, 16));
                            jLabelItemName.setFont(new java.awt.Font("Arial", 1, 10));
                            JLabel jLabelUpdatedTime=new JLabel("20:04");
                            jLabelUpdatedTime.setForeground(new java.awt.Color(204, 204, 204));
                            jLabelUpdatedTime.setFont(new java.awt.Font("Arial", 1, 9));
                            JLabel jLabelSalesCount=new JLabel(Integer.toString(arrayItems.size()));
                            jLabelSalesCount.setForeground(new java.awt.Color(204, 204, 204));
                            jLabelSalesCount.setFont(new java.awt.Font("Arial", 1, 9));
                            
                            //jPanelItems.add(jLabelDate);
                            jPanelItems[count1]=new JPanel();
                            jPanelItems[count1].add(panelBox);
                            jPanelItems[count1].add(favPicLabel);
                            jPanelItems[count1].add(editPicLabel);
                            jPanelItems[count1].add(jLabelItemName);
                            jPanelItems[count1].add(userRightPicLabel);
                            jPanelItems[count1].add(jLabelUpdatedTime);
                            jPanelItems[count1].add(jLabelSalesCount);
                            jPanelItems[count1].revalidate();
                            jPanelItems[count1].repaint();
                            jPanelItems[count1].setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelDateHolder.getWidth() - 150), 18));
                            jPanelItems[count1].setMaximumSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelDateHolder.getWidth() - 170), 18));
                            jPanelItems[count1].setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
                            jPanelItems[count1].setBackground(new Color(0x262624));//27, 23, 23
                            
                            jPanelItems[count1].addMouseListener(new MouseListener()
                            {
                                
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    try
                                    {
                                        System.out.println("this has been Clicked");
                                        
                                        //System.out.println(eComponentParent.toString() + eComponentParent.getComponentCount());
                                        int clickCount=e.getClickCount();
                                        System.out.println("clickCount=" + clickCount);
                                        if(clickCount==1)
                                        {
                                            if(((JPanel)e.getComponent()).getBackground().equals(new Color(0x262624)))
                                            {
                                                JPanel jpanel=((JPanel)e.getComponent());
                                                jpanel.setBackground(new Color(0x585858));
                                                JPanel chkboxPanel=(JPanel)jpanel.getComponent(0);
                                                chkboxPanel.setBackground(new Color(0x585858));
                                            }
                                            else if(((JPanel)e.getComponent()).getBackground().equals(new Color(0x585858)))
                                            {
                                                /*JPanel jpanel=((JPanel)e.getComponent());
                                                jpanel.setBackground(new Color(0x262624));
                                                JPanel chkboxPanel=(JPanel)jpanel.getComponent(0);
                                                chkboxPanel.setBackground(new Color(0x262624));//262624  B2E0F9*/
                                            }
                                            if(UserList.this.itemSelectedIndex!=null)
                                            {
                                                System.out.println("ok we are in");
                                                UserList.this.itemSelectedIndex.setBackground(new Color(0x262624));
                                                JPanel chkboxPanel=(JPanel)((UserList.this.itemSelectedIndex).getComponent(0));
                                                chkboxPanel.setBackground(new Color(0x262624));
                                            }
                                            UserList.this.itemSelectedIndex=((JPanel)e.getComponent());
                                        }
                                        else
                                        {
                                            //go show item details
                                        }
                                    }                                              

                                    catch(UnsupportedOperationException ex)
                                    {
                                        
                                    }
                                    
                                    
                                }
                                

                                @Override
                                public void mousePressed(MouseEvent e) {
                                    try
                                    {}catch(UnsupportedOperationException ex)
                                    {}
                                }

                                @Override
                                public void mouseReleased(MouseEvent e) {
                                    try
                                    {}catch(UnsupportedOperationException ex)
                                    {}
                                }

                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    try
                                    {}catch(UnsupportedOperationException ex)
                                    {}
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    try
                                    {}catch(UnsupportedOperationException ex)
                                    {}
                                }
                            });
                            
                            
                            System.out.println("get count of = " + jPanelLevelItemDetails.getComponents().length);

                    }
                    int count2=0;
                    
                    
                    JLabel jLabelDate=new JLabel(dateString);
                    jLabelDate.setForeground(new java.awt.Color(204, 204, 204));
                    jLabelDate.setFont(new java.awt.Font("Arial", 1, 12));
                    jPanelLevelItemDetails.add(jLabelDate);
                    //jLabelDate.setPreferredSize(new Dimension((int)(jPanelRight.getPreferredSize().getWidth() - 40), 3));
                    //jLabelDate.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth() - 40), 3));
                    for(int count1=0;count1<arrayItems.size();count1++)
                    {
                        c.weightx = 0.1;
                        c.weighty = 1;
                        c.gridwidth = GridBagConstraints.REMAINDER;
                        c.gridheight = 1;
                        c.gridx=0;
                        c.gridy = count2;
                        c.anchor = GridBagConstraints.NORTHWEST;
                        
                        
                        //gridbag.setConstraints(jPanelItems[count1], c);
                        //
                        
                        jPanelLevelItemDetails.add(jPanelItems[count1]);  
                        //jPanelLevelItemDetails.add(Box.createVerticalGlue());
                        JPanel panelspace=new JPanel();
                        panelspace.setPreferredSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelDateHolder.getWidth() - 170), 2));
                        panelspace.setMaximumSize(new Dimension(((int)(super.getPreferredSize().getWidth()) - jPanelLevelDateHolder.getWidth() - 170), 2));
                        c.gridy = count2++;
                        //gridbag.setConstraints(panelspace, c);
                        panelspace.setBackground(new java.awt.Color(23, 21, 21));
                        jPanelLevelItemDetails.add(panelspace);  
                        
                        //.add(Box.createVerticalGlue());
                        jPanelLevelItemDetails.revalidate();
                        jPanelLevelItemDetails.repaint();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ShopItemsList.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                    
                    
                    

                      
                        
            jPanelLevel2.add(jPanelLevelDateHolder);
            jPanelLevel2.add(jPanelLevelItemDetails);
            jPanelLevel2.revalidate();
            jPanelLevel2.repaint();
            
            this.add(jPanelLevel2);
            this.revalidate();
            this.repaint();
            /*String itemName="";
            String updatedTime="";
            String itemSaleCount="";
            JPanel jpanel=new JPanel();
            Dimension dimension = new Dimension();
            dimension.setSize(100, super.getHeight()-10);
            jpanel.setPreferredSize(dimension);
            jpanel.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
            
                JPanel jpanelDateExt=new JPanel();
                jpanelDateExt.setPreferredSize(new Dimension(113, 115));
                
                JPanel jpanelDateInt=new JPanel();
                jpanelDateInt.setPreferredSize(new Dimension(104, 93));
                    jpanelDateExt.add(jpanelDateInt);
                
                JPanel jPanelRight=new JPanel();
                jPanelRight.setPreferredSize(new Dimension((int)(super.getPreferredSize().getWidth() - jpanelDateExt.getWidth()), 3));
                jPanelRight.setLayout(new WrapLayout(FlowLayout.TRAILING, 10, 10));
                
                    JPanel jPanelItemDate=new JPanel();
                    jPanelItemDate.setPreferredSize(new Dimension((int)(jPanelRight.getPreferredSize().getWidth() - 40), 3));
                    jPanelItemDate.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));*/
                
                    //JLabel jLabelDate=new JLabel(dateString);
                    //jLabelDate.setPreferredSize(new Dimension((int)(jPanelRight.getPreferredSize().getWidth() - 40), 3));
                    //jLabelDate.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth() - 40), 3));
                    
                    /*JPanel jPanelItems=new JPanel();
                    jPanelItems.setPreferredSize(new Dimension((int)(jPanelRight.getPreferredSize().getWidth() - 40), 3));
                    jPanelItems.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
                    
                        JButton jButtonKeepTrack=new JButton("K");
                        JButton jButtonItemEdit=new JButton("E");
                        JLabel jLabelItemName=new JLabel(itemName);
                        jLabelItemName.setPreferredSize(new Dimension(200, 3));
                        JButton jButtonItemBI=new JButton("B.I");
                        JButton jButtonUpdatedTime=new JButton(updatedTime);
                        JLabel jLabelSalesCount=new JLabel(itemSaleCount);
                        
                        jPanelItems.add(jLabelDate);
                        jPanelItems.add(jButtonKeepTrack);
                        jPanelItems.add(jButtonItemEdit);
                        jPanelItems.add(jLabelItemName);
                        jPanelItems.add(jButtonItemBI);
                        jPanelItems.add(jButtonUpdatedTime);
                        jPanelItems.add(jLabelSalesCount);
                        
                            jPanelRight.add(jPanelItems);
                                jpanel.add(jpanelDateExt);
                                jpanel.add(jPanelRight);
                                    this.add(jpanel);*/
                                    //jPanel22.add(jLabelDate);
                                    //jPanel22.revalidate();
                        
        }
        //panel.add(this);
        return this;
    }
    
    
    public ArrayList getAllItemsByDate(String date)
    {
        ArrayList<JSONObject> jsonObjArr=new ArrayList();
        PreparedStatement pr;
        try {
            pr = this.con.prepareStatement("SELECT * from shop_items WHERE dateUploaded = ?");
            pr.setString(1, date);
            ResultSet rs=mysql.query(pr);
            System.out.println("dateString = "+ date);


            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", rs.getString("title"));
                jsonObject.put("price", rs.getString("price"));
                jsonObject.put("type", rs.getString("type"));
                jsonObject.put("dateUploaded", rs.getString("dateUploaded"));
                //jsonObject.put("time", "02:00");
                jsonObjArr.add(jsonObject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonObjArr;
                    
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
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel55.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(204, 204, 204));
        jLabel55.setText("Items In Shop");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel55)
                .addContainerGap(782, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel55)
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel41.setBackground(new java.awt.Color(0, 0, 0));

        jPanel42.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 93, Short.MAX_VALUE)
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 88, Short.MAX_VALUE)
        );

        jPanel43.setBackground(new java.awt.Color(0, 0, 0));
        jPanel43.setForeground(new java.awt.Color(255, 255, 255));

        jLabel56.setForeground(new java.awt.Color(204, 204, 204));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Change Picture");

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addComponent(jLabel56)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel56))
        );

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel41Layout.createSequentialGroup()
                        .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel44.setBackground(new java.awt.Color(51, 51, 51));

        jPanel45.setBackground(new java.awt.Color(0, 0, 0));

        jLabel58.setForeground(new java.awt.Color(204, 204, 204));
        jLabel58.setText("Export Everything");

        jLabel59.setForeground(new java.awt.Color(204, 204, 204));
        jLabel59.setText("Edit My Store Details");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel58)
                .addGap(18, 18, 18)
                .addComponent(jLabel59)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(jLabel59))
        );

        jPanel46.setBackground(new java.awt.Color(51, 51, 51));

        jLabel60.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(204, 204, 204));
        jLabel60.setText("Swip Span");

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel47.setBackground(new java.awt.Color(0, 0, 0));

        jLabel57.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(204, 204, 204));
        jLabel57.setText("Swip Span is a shop dedicated to selling Aloe Vera products. Since 2000 we have been involved in distributing Aloe Vera products to our customers");

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel22.setBackground(new java.awt.Color(23, 21, 21));

        jPanel24.setBackground(new java.awt.Color(51, 51, 51));

        jPanel25.setBackground(new java.awt.Color(0, 0, 0));

        jPanel26.setBackground(new java.awt.Color(51, 51, 51));

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(204, 204, 204));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("11th");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addGap(22, 22, 22))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel62)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel30.setBackground(new java.awt.Color(51, 51, 51));

        jPanel31.setBackground(new java.awt.Color(62, 62, 62));

        jLabel30.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 204, 204));
        jLabel30.setText("jLabel2");

        jLabel31.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(204, 204, 204));
        jLabel31.setText("jLabel2");

        jPanel32.setBackground(new java.awt.Color(62, 62, 62));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel32.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(204, 204, 204));
        jLabel32.setText("jLabel4");

        jLabel33.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(204, 204, 204));
        jLabel33.setText("jLabel5");

        jLabel34.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(204, 204, 204));
        jLabel34.setText("jLabel6");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addGap(31, 31, 31)
                .addComponent(jLabel34)
                .addContainerGap(274, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel30)
                .addComponent(jLabel31))
            .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel32)
                .addComponent(jLabel33)
                .addComponent(jLabel34))
        );

        jPanel33.setBackground(new java.awt.Color(51, 51, 51));

        jLabel35.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(204, 204, 204));
        jLabel35.setText("jLabel2");

        jLabel36.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(204, 204, 204));
        jLabel36.setText("jLabel2");

        jPanel34.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel37.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(204, 204, 204));
        jLabel37.setText("jLabel4");

        jLabel38.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(204, 204, 204));
        jLabel38.setText("jLabel5");

        jLabel39.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(204, 204, 204));
        jLabel39.setText("jLabel6");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addGap(31, 31, 31)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel35)
                .addComponent(jLabel36))
            .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel37)
                .addComponent(jLabel38)
                .addComponent(jLabel39))
        );

        jPanel35.setBackground(new java.awt.Color(62, 62, 62));

        jLabel40.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(204, 204, 204));
        jLabel40.setText("jLabel2");

        jLabel41.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(204, 204, 204));
        jLabel41.setText("jLabel2");

        jPanel36.setBackground(new java.awt.Color(62, 62, 62));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel42.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 204, 204));
        jLabel42.setText("jLabel4");

        jLabel43.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(204, 204, 204));
        jLabel43.setText("jLabel5");

        jLabel44.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(204, 204, 204));
        jLabel44.setText("jLabel6");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel41)
                .addGap(18, 18, 18)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addGap(31, 31, 31)
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel40)
                .addComponent(jLabel41))
            .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel42)
                .addComponent(jLabel43)
                .addComponent(jLabel44))
        );

        jPanel37.setBackground(new java.awt.Color(51, 51, 51));

        jLabel45.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(204, 204, 204));
        jLabel45.setText("jLabel2");

        jLabel46.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(204, 204, 204));
        jLabel46.setText("jLabel2");

        jPanel38.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel47.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(204, 204, 204));
        jLabel47.setText("jLabel4");

        jLabel48.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(204, 204, 204));
        jLabel48.setText("jLabel5");

        jLabel49.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(204, 204, 204));
        jLabel49.setText("jLabel6");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addGap(18, 18, 18)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addGap(31, 31, 31)
                .addComponent(jLabel49)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel45)
                .addComponent(jLabel46))
            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel47)
                .addComponent(jLabel48)
                .addComponent(jLabel49))
        );

        jPanel39.setBackground(new java.awt.Color(62, 62, 62));

        jLabel50.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(204, 204, 204));
        jLabel50.setText("jLabel2");

        jLabel51.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(204, 204, 204));
        jLabel51.setText("jLabel2");

        jPanel40.setBackground(new java.awt.Color(62, 62, 62));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel52.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(204, 204, 204));
        jLabel52.setText("jLabel4");

        jLabel53.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(204, 204, 204));
        jLabel53.setText("jLabel5");

        jLabel54.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(204, 204, 204));
        jLabel54.setText("jLabel6");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addGap(18, 18, 18)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addGap(31, 31, 31)
                .addComponent(jLabel54)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel50)
                .addComponent(jLabel51))
            .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel52)
                .addComponent(jLabel53)
                .addComponent(jLabel54))
        );

        jLabel29.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 204, 204));
        jLabel29.setText("11th March (2013)");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel29)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 924, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 907, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(402, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(184, 184, 184)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(243, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 937, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    // End of variables declaration//GEN-END:variables
}
