/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RegistryValue;
import ca.beq.util.win32.registry.RootKey;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.json.simple.*;


/**
 *
 * @author xkalibaer
 */
public class HomeScreen extends javax.swing.JFrame {

    /**
     * Creates new form HomeScreen
     */
    
    private Connection con;
    private Mysql mysql;
    private AppAccess appAccess;
    private Hashtable selectedItemsList;
    Login login;
    private String dbHost="";
    private String dbPort="";
    private String dbPass="";
    private String dbType="";
    private String dbUser="";
    private String installPath="";
    public JPanel printJPanel;
    private QRCodesItems qrCodesItems;
    private Reports reportsScreen;
    
    public HomeScreen() {
        try {
            initComponents();
            setBeforeDisplay();
            appAccess=new AppAccess();
            mysql=new Mysql();
            
            selectedItemsList=new Hashtable();
            this.jMenuItem2.setEnabled(false);
            this.jMenuItem3.setEnabled(false);
            this.jMenuItem4.setEnabled(false);
            this.jMenuItem5.setEnabled(false);
            this.jMenuItem8.setEnabled(false);
            this.jMenuItem19.setEnabled(false);
            this.jMenuItem7.setEnabled(false);
            this.jMenuItem8.setEnabled(false);
            this.jMenuItem9.setEnabled(false);
            this.jMenuItem10.setEnabled(false);
            this.jMenuItem11.setEnabled(false);
            //this.jMenuItem12.setEnabled(false);
            this.jMenuItem13.setEnabled(false);
            this.jMenuItem16.setEnabled(false);
            this.jMenuItem20.setEnabled(false);
            this.jMenuItem21.setEnabled(false);
            this.jMenuItem22.setEnabled(false);
            //this.jMenu3.setEnabled(false);
            this.jPanel5.setBackground(new Color(23, 21, 21));
            this.jPanel3.setBackground(new Color(23, 21, 21));
            this.jPanel4.setBackground(new Color(23, 21, 21));
            this.jLabel1.setForeground(new Color(23, 21, 21));
            jScrollPane1.getViewport().setBackground(new Color(23, 21, 21)); 
            this.setBackground(new Color(23, 21, 21));
            int width=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (int)jPanel3.getPreferredSize().getWidth()
                    - (int)jPanel5.getPreferredSize().getWidth() - 180;
            int height=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (int)jPanel3.getPreferredSize().getHeight()
                    - 360;
            
            jLabel2.setPreferredSize(new Dimension(jPanel5.getPreferredSize().width-10, (int)jPanel5.getPreferredSize().getHeight()));
            JPanel jpanelHome3=new JPanel();
            jpanelHome3.setBorder(new EmptyBorder(0, 0, 0, 0));
            
            
            jPanel6.setLayout(new GridBagLayout());
            GridBagConstraints gbc=new GridBagConstraints();
            
            JLabel jlabel_=new JLabel();
            jlabel_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/body.png"))); // NOI18N
            
            //JLabel jlabel_1=new JLabel();
            //jlabel_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/body1.png")));
            
            jpanelHome3.add(jlabel_);
            //jpanelHome3.add(jlabel_1);
            jPanel6.add(jpanelHome3, gbc);
            jPanel6.setPreferredSize(new Dimension(width, height));
            jScrollPane1.getViewport().setPreferredSize(new Dimension(width, height));
            this.setBackground(new Color(0x262624));
            
            dbHost=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbHost");
            dbPass=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbPass");
            dbPort=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbPort");
            dbType=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbType");
            dbUser=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbUser");
            installPath=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "installPath");
            System.out.println("WinRegistry stuff =" + installPath);
            //RegistryKey r = new RegistryKey(RootKey.HKEY_CURRENT_USER, "Software\\ShopScan Manager");
            boolean startApp=false;
            if(installPath!=null && 
                    dbHost!=null && 
                    dbType!=null && 
                    dbUser!=null && installPath.length()>0 && 
                    dbHost.length()>0 && 
                    dbType.length()>0 && 
                    dbUser.length()>0) {
                
                con=mysql.mysqlConnectToDb(dbHost, dbUser, dbPass, UtilUse.DATABASE);
                if(con!=null)
                {
                    ArrayList<String> list=new ArrayList();
                    Statement st = this.con.createStatement();
                    DatabaseMetaData meta = con.getMetaData();
                    ResultSet rs = meta.getCatalogs();
                    while (rs.next()) {
                        String listofDatabases=rs.getString("TABLE_CAT");
                        System.out.println(listofDatabases);
                        list.add(listofDatabases);
                    }
                    if(list.contains(UtilUse.DATABASE)){
                        startApp=true;
                    }
                }
            } 
            
            
            if(startApp==true)
            {
                PreparedStatement pr = this.con.prepareStatement("SELECT * from settings");
                ResultSet rs=mysql.query(pr);
                int count=0;
                while (rs.next()) { 
                    count++;
                }
                if(count==0)
                {
                    displaySettings(this.mysql, UtilUse.DATABASE);
                }
                else
                {
                    pr = this.con.prepareStatement("SELECT * from shop_details");
                    rs=mysql.query(pr);
                    count=0;
                    while (rs.next()) { 
                        count++;
                    }
                    if(count==0)
                    {
                        displaySetUpShop(this.con, this.mysql, installPath);
                    }
                    else
                    {
                        qrCodesItems=new QRCodesItems(this.mysql, this.con, this, this.installPath);
                    }
                }
            }
            else
            {
                displaySettings(this.mysql, UtilUse.DATABASE);
            }
            
                   
            
            //initialize();
        } catch (Exception ex) {
            new UtilUse().Log(ex, HomeScreen.this.installPath);
            Hashtable h1=new Hashtable();
            h1.put("success", false);
            h1.put("message", "Error setting up application. Try again");
            h1.put("operation", "Setting up application...");
            displayError(h1);
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void displayError(Hashtable h1)
    {
        /*JDialog jd=new JDialog();
        jd.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jd.setTitle("ScanShop");
        JLabel l=new JLabel("Error while setting up ScanShop Application. Try again");
        l.setBackground(new Color(0xcccccc));
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setHorizontalTextPosition(SwingConstants.CENTER);
        jd.getContentPane().setBackground(new Color(0x262424));

        jd.setPreferredSize(new Dimension(300, 200));
        jd.addWindowListener(new WindowListener()
        {

            @Override
            public void windowOpened(WindowEvent e) {
                try
                {

                }
                catch(UnsupportedOperationException ex)
                {
                    new Util().Log(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try
                {
                    System.exit(0);
                }catch(UnsupportedOperationException ex)
                {
                    new Util().Log(ex);
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void windowIconified(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void windowActivated(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
        JButton b=new JButton("Exit");
        b.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    System.exit(0);
                }
                catch(UnsupportedOperationException ex)
                {
                    new Util().Log(ex);
                }
            }

        });

        JPanel jp=new JPanel();
        jp.setBackground(new Color(0x262424));

        JPanel jp2=new JPanel();
        jp2.setBackground(new Color(0x262424));

        GridBagConstraints gbc1=new GridBagConstraints();
        gbc1.gridwidth=GridBagConstraints.REMAINDER;
        jp2.setPreferredSize(new Dimension((int)jd.getPreferredSize().getWidth(), (int)jd.getPreferredSize().getHeight()));
        jp2.setLayout(new GridBagLayout());
        l.setBackground(new Color(0x262424));
        l.setForeground(new Color(204, 204, 204));
        jp2.add(l, gbc1);
        jp.setLayout(new GridBagLayout());

        jp.add(jp2, gbc1);
        jp.add(b, gbc1);
        jd.getContentPane().add(jp);
        int x=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - (int)jd.getPreferredSize().getWidth();
        int y=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (int)jd.getPreferredSize().getHeight();
        jd.setLocation(x/2, y/2);
        jd.pack();
        jd.setVisible(true);*/
    }
    
    public void initialize()
    {
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WrapLayout wrapLayout=new WrapLayout(FlowLayout.TRAILING, 0, 0);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
        
        jMenuBar1=new JMenuBar();
        jMenu1=new JMenu();
        jMenu1.setText("File");
        jMenu2=new JMenu();
        jMenu2.setText("Shop Items");
        
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);
        this.setJMenuBar(jMenuBar1);
        
        
    }

    
    public void showShopItem(JPanel shopItem)
    {
        GridBagConstraints gbc = new GridBagConstraints();
        shopItem.setBackground(new Color(0x262624));
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(shopItem);
        jScrollPane1.revalidate();
        System.out.println("We are her1");
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setAlignmentX(0.0F);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Regulars");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/log_in.png"))); // NOI18N
        jLabel4.setText("Login");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/add.png"))); // NOI18N
        jLabel5.setText("Add New Shop Item");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/view_items.png"))); // NOI18N
        jLabel6.setText("View My Shop Items");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/sync.png"))); // NOI18N
        jLabel7.setText("Synchronize");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Specialized Functions");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/arrow_double_up.png"))); // NOI18N
        jLabel9.setText("Export Shop Data");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/arrow_down.png"))); // NOI18N
        jLabel10.setText("Import Data");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/report.png"))); // NOI18N
        jLabel11.setText("Generate Reports");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/user_red.png"))); // NOI18N
        jLabel12.setText("User Management");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/priviledge.png"))); // NOI18N
        jLabel13.setText("User Priviledges");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/view_items.png"))); // NOI18N
        jLabel14.setText("View QR Codes");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/view_items.png"))); // NOI18N
        jLabel15.setText("View Barcodes");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Help");

        jLabel17.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 204, 204));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/support_ic.png"))); // NOI18N
        jLabel17.setText("Support Online");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/hel2.png"))); // NOI18N
        jLabel18.setText("Help Docs");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 204, 204));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/support_ic.png"))); // NOI18N
        jLabel19.setText("Report Issue");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel19))
                                .addComponent(jLabel15)))
                        .addComponent(jLabel8))
                    .addComponent(jLabel3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(32, 32, 32)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(30, 30, 30)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/advert1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 1076, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2004, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 969, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel6);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Operation:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1419, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/log_in.png"))); // NOI18N
        jMenuItem1.setText("Login");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/arrow_down.png"))); // NOI18N
        jMenuItem2.setText("Import");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/arrow_double_up.png"))); // NOI18N
        jMenuItem3.setText("Export");
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator1);

        jMenuItem4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/report.png"))); // NOI18N
        jMenuItem4.setText("Print2Pdf");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/report.png"))); // NOI18N
        jMenuItem5.setText("Print");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator2);

        jMenuItem6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/log_in.png"))); // NOI18N
        jMenuItem6.setText("Exit");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Shop Items");
        jMenu2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem19.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/view_items.png"))); // NOI18N
        jMenuItem19.setText("My Store Items");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem19);

        jMenuItem7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/add.png"))); // NOI18N
        jMenuItem7.setText("Add Shop Item");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem22.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/view_items.png"))); // NOI18N
        jMenuItem22.setText("View QR Codes");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem22);

        jMenuItem21.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/add.png"))); // NOI18N
        jMenuItem21.setText("Refill Shop Item Quantity");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem21);

        jMenuItem8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/erase.png"))); // NOI18N
        jMenuItem8.setText("Delete Shop Item(s)");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);
        jMenu2.add(jSeparator3);

        jMenuItem9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/user_red.png"))); // NOI18N
        jMenuItem9.setText("All Users");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuItem10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/priviledge.png"))); // NOI18N
        jMenuItem10.setText("Rights & priviledges");
        jMenu2.add(jMenuItem10);

        jMenuItem20.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/user_red.png"))); // NOI18N
        jMenuItem20.setText("Create A New User");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem20);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Reports");
        jMenu3.setToolTipText("");
        jMenu3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jMenuItem13.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/report.png"))); // NOI18N
        jMenuItem13.setText("Generate Reports");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem13);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Tools");
        jMenu4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jMenuItem11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/sync.png"))); // NOI18N
        jMenuItem11.setText("Synchronize to Live Server");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Help");
        jMenu5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        jMenuItem14.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/hel2.png"))); // NOI18N
        jMenuItem14.setText("Help Contents");
        jMenu5.add(jMenuItem14);

        jMenuItem15.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/support_ic.png"))); // NOI18N
        jMenuItem15.setText("Report Issue");
        jMenu5.add(jMenuItem15);
        jMenu5.add(jSeparator4);

        jMenuItem16.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/arrow_down.png"))); // NOI18N
        jMenuItem16.setText("Check For Updates");
        jMenu5.add(jMenuItem16);

        jMenuItem17.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/edit.png"))); // NOI18N
        jMenuItem17.setText("About");
        jMenu5.add(jMenuItem17);

        jMenuItem18.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/shopscanmanager/log_in.png"))); // NOI18N
        jMenuItem18.setText("Home Screen");
        jMenu5.add(jMenuItem18);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void displayLogin()
    {
        try {
            String dbHost=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbHost");
            String dbPass=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbPass");
            String dbPort=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbPort");
            String dbType=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbType");
            String dbUser=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "dbUser");
            String installPath=WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "Software\\ShopScan Manager", "installPath");
            System.out.println("WinRegistry stuff");
            //RegistryKey r = new RegistryKey(RootKey.HKEY_CURRENT_USER, "Software\\ShopScan Manager");
            
            if(installPath!=null && 
                    dbHost!=null && 
                    dbType!=null && 
                    dbUser!=null && installPath.length()>0 && 
                    dbHost.length()>0 && 
                    dbType.length()>0 && 
                    dbUser.length()>0) {
                System.out.println("Yes we have entries in the registry = " + 3);
                this.con=mysql.mysqlConnectToDb(dbHost, dbUser, dbPass, UtilUse.DATABASE);
                login=new Login(mysql, this.con, this, installPath);
                login.getJButton1_().addActionListener(new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                            HomeScreen.this.appAccess.setAccessKey(login.doLogin(con, mysql));
                            System.out.println("Access key=" + HomeScreen.this.appAccess.getAccessKey());

                            if(HomeScreen.this.appAccess.getAccessKey().length()>0)
                            {
                                ShopItemsList shopItemsList=new ShopItemsList(con, mysql, appAccess, HomeScreen.this);



                                HomeScreen.this.jMenuItem2.setEnabled(true);
                                HomeScreen.this.jMenuItem3.setEnabled(true);
                                HomeScreen.this.jMenuItem4.setEnabled(true);
                                HomeScreen.this.jMenuItem5.setEnabled(true);
                                HomeScreen.this.jMenuItem8.setEnabled(true);
                                HomeScreen.this.jMenuItem19.setEnabled(true);
                                HomeScreen.this.jMenuItem7.setEnabled(true);
                                HomeScreen.this.jMenuItem8.setEnabled(true);
                                HomeScreen.this.jMenuItem9.setEnabled(true);
                                HomeScreen.this.jMenuItem10.setEnabled(true);
                                HomeScreen.this.jMenuItem11.setEnabled(true);
                                //HomeScreen.this.jMenuItem12.setEnabled(true);
                                HomeScreen.this.jMenuItem13.setEnabled(true);
                                HomeScreen.this.jMenuItem16.setEnabled(true);
                                HomeScreen.this.jMenuItem20.setEnabled(true);
                                HomeScreen.this.jMenuItem21.setEnabled(true);
                                HomeScreen.this.jMenuItem22.setEnabled(true);
                                //login.setVisible(true);
                                //jPanel4.setLayout(new WrapLayout(FlowLayout.TRAILING, 10, 0));
                                //jScrollPane1.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 0));
                                GridBagConstraints gbc = new GridBagConstraints();
                                Dimension dimension3 = new Dimension();
                                dimension3=jScrollPane1.getPreferredSize();
                                dimension3.width=dimension3.width - 10;
                                dimension3.height=dimension3.height - 10;
                                dimension3.height=2000;
                                shopItemsList.setPreferredSize(dimension3);
                                JPanel jp=shopItemsList.displayAllComponents(shopItemsList.getShopItemsGroupedByDate(), HomeScreen.this.installPath);
                                jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
                                jScrollPane1.setViewportView(jp);
                                jScrollPane1.revalidate();
                            }
                            else
                            {
                                login.getJLabel3_().setForeground(Color.red);
                            }
                        }
                        catch(UnsupportedOperationException exc)
                        {
                            new UtilUse().Log(exc, HomeScreen.this.installPath);
                        }
                    }

                });
                login.getJLabel4_().addMouseListener(new MouseListener()
                {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try
                        {
                            NewUser newUser=new NewUser(con, mysql, appAccess, HomeScreen.this, HomeScreen.this.installPath);
                            GridBagConstraints gbc = new GridBagConstraints();
                            Dimension dimension3 = new Dimension();
                            dimension3=jScrollPane1.getPreferredSize();
                            dimension3.width=dimension3.width - 10;
                            dimension3.height=dimension3.height - 10;
                            dimension3.height=Toolkit.getDefaultToolkit().getScreenSize().height;
                            newUser.setPreferredSize(dimension3);
                            JPanel jp=newUser.getAllComponents();
                            jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
                            jScrollPane1.setViewportView(jp);
                            jScrollPane1.revalidate();
                        }
                        catch(UnsupportedOperationException ex)
                        {
                            new UtilUse().Log(ex, HomeScreen.this.installPath);


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

                });


                login.getJLabel5_().addMouseListener(new MouseListener()
                {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try
                        {
                            RecoverLogin recoverLogin=new RecoverLogin();
                            GridBagConstraints gbc = new GridBagConstraints();
                            Dimension dimension3 = new Dimension();
                            dimension3=jScrollPane1.getPreferredSize();
                            dimension3.width=dimension3.width - 10;
                            dimension3.height=dimension3.height - 10;
                            dimension3.height=Toolkit.getDefaultToolkit().getScreenSize().height;
                            JPanel jp=recoverLogin.getAllComponents();
                            JPanel panel=new JPanel();

                            panel.setLayout(new GridBagLayout());
                            panel.setPreferredSize(dimension3);
                            panel.setBackground(new Color(0x262624));

                            panel.add(jp, gbc);

                            jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
                            jScrollPane1.setViewportView(panel);
                            jScrollPane1.revalidate();
                        }
                        catch(UnsupportedOperationException ex)
                        {
                            new UtilUse().Log(ex, HomeScreen.this.installPath);
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

                });



                login.setVisible(true);
                //jPanel4.setLayout(new WrapLayout(FlowLayout.TRAILING, 10, 0));
                JPanel panel=new JPanel();
                panel.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();

                Dimension dimension3 = new Dimension();
                dimension3=Toolkit.getDefaultToolkit().getScreenSize();
                jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                dimension3.width=dimension3.width - (int)jPanel3.getPreferredSize().getWidth() - 180 - (int)jPanel5.getPreferredSize().getWidth();
                dimension3.height=dimension3.height - 180 - (int)jPanel4.getPreferredSize().getHeight();
                //dimension3.height=Toolkit.getDefaultToolkit().getScreenSize().height;
                JPanel jp=login.getAllComponents();
                panel.setPreferredSize(dimension3);
                panel.setBackground(new Color(23, 21, 21));

                panel.add(jp, gbc);

                jScrollPane1.getViewport().setBackground(new Color(23, 21, 21)); 
                jScrollPane1.setViewportView(panel);
                jScrollPane1.revalidate();
            }
            
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.out.println("We are in problem");
        displayLogin();
        //jPanel4.add(login);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    
    public void displayCategoryList(int id)
    {
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        CategoryList categoryList=new CategoryList(mysql, con, dimension3, installPath, this);
        categoryList.setParentId(id);
        if(appAccess.getAccessKey().length()>0)
        {
            //login.setVisible(true);
            //jPanel4.setLayout(new WrapLayout(FlowLayout.TRAILING, 10, 0));
            //jScrollPane1.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 0));
            GridBagConstraints gbc = new GridBagConstraints();
            dimension3 = new Dimension();
            dimension3=jScrollPane1.getPreferredSize();
            dimension3.width=dimension3.width - 10;
            dimension3.height=dimension3.height - 10;
            dimension3.height=2000;
            categoryList.setPreferredSize(dimension3);
            JPanel jp=categoryList.getAllComponents();
            jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
            jScrollPane1.setViewportView(jp);
            jScrollPane1.revalidate();
        }
        else
        {
            displayLogin();
        }
    }
    
    public void displayViewShopItems()
    {
        ShopItemsList shopItemsList=new ShopItemsList(con, mysql, appAccess, this);
        if(appAccess.getAccessKey().length()>0)
        {
            //login.setVisible(true);
            //jPanel4.setLayout(new WrapLayout(FlowLayout.TRAILING, 10, 0));
            //jScrollPane1.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 0));
            GridBagConstraints gbc = new GridBagConstraints();
            Dimension dimension3 = new Dimension();
            dimension3=jScrollPane1.getPreferredSize();
            dimension3.width=dimension3.width - 10;
            dimension3.height=dimension3.height - 10;
            dimension3.height=2000;
            shopItemsList.setPreferredSize(dimension3);
            JPanel jp=shopItemsList.displayAllComponents(shopItemsList.getShopItemsGroupedByDate(), this.installPath);
            jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
            jScrollPane1.setViewportView(jp);
            jScrollPane1.revalidate();
        }
        else
        {
            displayLogin();
        }
    }
    
    
    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayViewShopItems();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    
    void displayNotification(String StringNotification)
    {
        jLabel1.setText(StringNotification);
        jLabel1.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        Thread mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        System.out.println("start");
                        wait(10000);
                        System.out.println("end");
                        jLabel1.setText("");
                        //jLabel1.setVisible(false);
                    }
                }
                catch(InterruptedException ex){
                    new UtilUse().Log(ex, HomeScreen.this.installPath);
                    System.out.println("fool");
                }

            }
        };
        mSplashThread.start();
    }
    
    //add new shop item
    public void displayProcessMessage(Hashtable resultValue)
    {
        JOptionPane.showMessageDialog(HomeScreen.this,
            resultValue.get("message"));
        System.out.println(resultValue.toString()); 
        System.out.println("Display Login now = " + 1);
        if(resultValue.get("success") ==true)
        {
            System.out.println("Display Login now = " + 2);
            if(this.appAccess.getAccessKey().length()==0)
            {
                System.out.println("Display Login now = " + 3);
                displayLogin();
            }
            else
            {
                displayShopItems();
            }
        }
        displayNotification("Operation: " + resultValue.get("operation"));
    }
    
    
    
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayAddShopItem();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    public void displayAddShopItem()
    {
        System.out.println("HomeScreen.this.installPath=" + HomeScreen.this.installPath);
        final AddNewItem addNewItem=new AddNewItem(this.con, this.mysql, this, HomeScreen.this.installPath);
        addNewItem.getJButton1_().addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Hashtable resultValue=addNewItem.addNewItemProcess(HomeScreen.this.con, HomeScreen.this.mysql, HomeScreen.this.appAccess, HomeScreen.this.installPath);
                    displayProcessMessage(resultValue);
                    
                }catch(UnsupportedOperationException ex)
                {
                    new UtilUse().Log(ex, HomeScreen.this.installPath);
                    
                }
            }
            
        });
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        dimension3.height=2000;
        addNewItem.setPreferredSize(dimension3);
        JPanel jp=addNewItem.getAllComponents();
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
    }
    
    public void displayShopItems()
    {
        PreparedStatement pr;
        ArrayList<JSONObject> jsonObjArr=new ArrayList();
        
        try {
            pr = this.con.prepareStatement("SELECT * from user_profile");
            ResultSet rs=mysql.query(pr);
            
            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
		  
                    jsonObject.put("first_name", rs.getString("first_name"));
                    jsonObject.put("last_name", rs.getString("last_name"));
                    jsonObject.put("other_name", rs.getString("other_name"));
                    jsonObject.put("nhsNumber", rs.getString("nhsNumber"));
                    //jsonObject.put("time", "02:00");
                    jsonObjArr.add(jsonObject);
                    
            }
        } catch (SQLException ex) {
            new UtilUse().Log(ex, HomeScreen.this.installPath);
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ShopItemsList shopItemsList=new ShopItemsList(con, mysql, appAccess, this);
        //login.setVisible(true);
        //jPanel4.setLayout(new WrapLayout(FlowLayout.TRAILING, 10, 0));
        //jScrollPane1.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        dimension3.height=2000;
        shopItemsList.setPreferredSize(dimension3);
        JPanel jp=shopItemsList.displayAllComponents(shopItemsList.getShopItemsGroupedByDate(), this.installPath);
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
    }
    
    
    public void displayNewUser()
    {
        PreparedStatement pr;
        NewUser newUser=new NewUser(con, mysql, appAccess, this, HomeScreen.this.installPath);
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        dimension3.height=2000;
        newUser.setPreferredSize(dimension3);
        JPanel jp=newUser.getAllComponents();
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
    }
    
    
    public void displayItemRefill()
    {
        PreparedStatement pr;
        RefillQuantity refillQuantity=new RefillQuantity(con, mysql, appAccess, this);
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        dimension3.height=2000;
        //newUser.setPreferredSize(dimension3);
        JPanel jp=refillQuantity.getAllComponents();
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
    }
    
    
    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        displayShopItems();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        System.out.println("hmm");
        System.out.println(this.selectedItemsList.size());
        Enumeration e=this.selectedItemsList.keys();
        Object key;
        boolean quit=false;
        while(e.hasMoreElements() && quit==false)
        {
            key =e.nextElement();
            
            if(deleteShopItems(Integer.parseInt(this.selectedItemsList.get(key).toString()))==false)
            {
                quit=true;
            }
        }
        
        if(quit==true)
        {
            Hashtable h=new Hashtable();
            h.put("success", true);
            h.put("message", "Some Shop items selected have been deleted successfully!");
            h.put("operation", "Shop Items Deletion...");
            displayProcessMessage(h);
        }
        else
        {
            Hashtable h=new Hashtable();
            h.put("success", true);
            h.put("message", "All Shop items selected have been deleted successfully!");
            h.put("operation", "Shop Items Deletion...");
            displayProcessMessage(h);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
        if(this.selectedItemsList.isEmpty())
        {
            System.out.println("yes no keys");
            this.jMenuItem8.setEnabled(false);
        }
        else
        {
            this.jMenuItem8.setEnabled(true);
        }
        
    }//GEN-LAST:event_jMenu2ActionPerformed

    public void synchronizeToLive()
    {
        try {
            JSONObject jsonObjArrSuper=new JSONObject();
            PreparedStatement pr;
            pr = this.con.prepareStatement("SELECT * from shop_items");
            ResultSet rs=mysql.query(pr);
            //ArrayList<JSONObject> jsonObjArr=new ArrayList();
            JSONObject jsonObjectArray = new JSONObject();
            int i=0;
            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", rs.getString("title"));
                jsonObject.put("price", rs.getString("price"));
                jsonObject.put("type", rs.getString("type"));
                jsonObject.put("dateUploaded", rs.getString("dateUploaded"));
                jsonObject.put("qr_code", rs.getString("qr_code"));
                jsonObject.put("details", rs.getString("details"));
                jsonObject.put("uniqueId", rs.getString("uniqueId"));
                jsonObject.put("status", rs.getString("status"));
                jsonObject.put("fileId", rs.getString("fileId"));
                jsonObject.put("id", rs.getString("id"));
                //jsonObject.put("time", "02:00");
                //jsonObjArr.add(jsonObject);
                
                jsonObjectArray.put(Integer.toString(i++), jsonObject);
            }
            jsonObjArrSuper.put("shop_items", jsonObjectArray);
            
            
            pr = this.con.prepareStatement("SELECT * from user_profile");
            rs=mysql.query(pr);
            //ArrayList<JSONObject> jsonObjArr=new ArrayList();
            jsonObjectArray = new JSONObject();
            i=0;
            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("first_name", rs.getString("first_name"));
                jsonObject.put("last_name", rs.getString("last_name"));
                jsonObject.put("other_name", rs.getString("other_name"));
                jsonObject.put("email_address", rs.getString("email_address"));
                jsonObject.put("contact_address_id", rs.getString("contact_address_id"));
                jsonObject.put("mobile_number", rs.getString("mobile_number"));
                jsonObject.put("sex", rs.getString("sex"));
                jsonObject.put("date_created", rs.getString("date_created"));
                jsonObject.put("password", rs.getString("password"));
                jsonObject.put("key", rs.getString("key"));
                jsonObject.put("nhsNumber", rs.getString("nhsNumber"));
                jsonObject.put("securityQuestionId", rs.getString("securityQuestionId"));
                jsonObject.put("securityQuestionAnswer", rs.getString("securityQuestionAnswer"));
                jsonObject.put("dateUpdated", rs.getString("dateUpdated"));
                jsonObject.put("id", rs.getString("id"));
                jsonObject.put("confirmationCode", rs.getString("confirmationCode"));
                //jsonObject.put("time", "02:00");
                //jsonObjArr.add(jsonObject);
                jsonObjectArray.put(Integer.toString(i++), jsonObject);
            }
            jsonObjArrSuper.put("user_profile", jsonObjectArray);
            
            
            
            pr = this.con.prepareStatement("SELECT * from shop_details");
            rs=mysql.query(pr);
            //ArrayList<JSONObject> jsonObjArr=new ArrayList();
            jsonObjectArray = new JSONObject();
            i=0;
            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("shopName", rs.getString("shopName"));
                jsonObject.put("emailAddress", rs.getString("emailAddress"));
                jsonObject.put("mobileNumber", rs.getString("mobileNumber"));
                jsonObject.put("addressLine1", rs.getString("addressLine1"));
                jsonObject.put("addressLine2", rs.getString("addressLine2"));
                jsonObject.put("details", rs.getString("details"));
                jsonObject.put("logoId", rs.getString("logoId"));
                jsonObject.put("uniqueId", rs.getString("uniqueId"));
                jsonObject.put("dateCreated", rs.getString("dateCreated"));
                jsonObject.put("id", rs.getString("id"));
                //jsonObject.put("time", "02:00");
                //jsonObjArr.add(jsonObject);
                jsonObjectArray.put(Integer.toString(i++), jsonObject);
            }
            jsonObjArrSuper.put("shop_details", jsonObjectArray);
            
            
            
            
            pr = this.con.prepareStatement("SELECT * from country");
            rs=mysql.query(pr);
            //ArrayList<JSONObject> jsonObjArr=new ArrayList();
            jsonObjectArray = new JSONObject();
            i=0;
            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", rs.getString("name"));
                jsonObject.put("iso_code", rs.getString("iso_code"));
                jsonObject.put("id", rs.getString("id"));
                //jsonObject.put("time", "02:00");
                //jsonObjArr.add(jsonObject);
                jsonObjectArray.put(Integer.toString(i++), jsonObject);
            }
            jsonObjArrSuper.put("country", jsonObjectArray);
            
            
            
            pr = this.con.prepareStatement("SELECT * from states");
            rs=mysql.query(pr);
            //ArrayList<JSONObject> jsonObjArr=new ArrayList();
            jsonObjectArray = new JSONObject();
            i=0;
            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", rs.getString("name"));
                jsonObject.put("countryId", rs.getString("countryId"));
                jsonObject.put("status", rs.getString("status"));
                jsonObject.put("id", rs.getString("id"));
                //jsonObject.put("time", "02:00");
                //jsonObjArr.add(jsonObject);
                jsonObjectArray.put(Integer.toString(i++), jsonObject);
            }
            jsonObjArrSuper.put("states", jsonObjectArray);
            // TODO add your handling code here:
            
            String message=jsonObjArrSuper.toJSONString();
            
            System.out.println("message " + message);
            Informable informable= new Informable(){
                @Override
                public void messageChanged(String message){
                    System.out.println("message changed");
                }
            };
            Processor worker =
                new Processor("FP", "synchronization", this.appAccess.getAccessKey(),
                                message,
                                informable, this.mysql, this.con){
            // This method is invoked when the worker is finished
            // its task
            @Override
            protected void done(){
                    try {
                        System.out.println("done");
                        
                        
                        if(this.isDone())
                        {
                            
                            Hashtable h=new Hashtable();
                            h.put("success", this.get().get("status"));
                            h.put("message", this.get().get("message"));
                            h.put("operation", "Synchronization");
                            displayProcessMessage(h);
                        }
                        else
                        {
                            displayNotification("Operation: Synchronizing to server...");
                        }
                        //jLabel1.setText("");
                        //jLabel1.setForeground(new java.awt.Color(0x262424));
                    } catch (InterruptedException ex) {
                        new UtilUse().Log(ex, HomeScreen.this.installPath);
                        Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ExecutionException ex) {
                        new UtilUse().Log(ex, HomeScreen.this.installPath);
                        Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            }
            };
            worker.execute();
            
            
        } catch (SQLException ex) {
            new UtilUse().Log(ex, HomeScreen.this.installPath);
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        if(this.appAccess.getAccessKey().length()>0)
        {
            synchronizeToLive();
        }
        else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayNewUser();
        }else
        {
            displayLogin();
        }
        
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xCCCCCC));
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        displayLogin();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayAddShopItem();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xCCCCCC));
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayViewShopItems();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel7MouseExited

    
    public void displaySettings(Mysql mysql, String db)
    {
        //this.con=mysql.mysqlConnectToDb("localhost", "root", "", db);
        Settings settings=new Settings(this.con, this.mysql, this);
        JPanel jp=settings.showScreen();
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        settings.getJPanel1().setPreferredSize(dimension3);
        settings.getJPanel2().setPreferredSize(dimension3);
        jp.setPreferredSize(dimension3);
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
        
    }
    
    
    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            synchronizeToLive();
        }
        else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayItemRefill();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayReports();
        }else
        {
            displayLogin();
        }
        
    }//GEN-LAST:event_jLabel11MouseClicked

    public void displayReports()
    {
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        System.out.println("INstall path " + this.installPath);
        reportsScreen=new Reports(dimension3, this.mysql, this.con, this.installPath, this);
        
        JPanel jp=reportsScreen.getAllComponents();
        
        jScrollPane1.getViewport().setBackground(new Color(23, 21, 21)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
    }
    
    
    public void displayUserManage()
    {
        
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        UserManage userManage= new UserManage(mysql, con, dimension3, installPath, this);
        JPanel jp=userManage.getAllComponents();
        
        
        jp.setPreferredSize(dimension3);
        jScrollPane1.getViewport().removeAll();
//        System.out.println("Remove All Components" + (((JPanel)((JPanel)jp.getComponent(2)).getComponent(0)).getComponentCount() + ((JPanel)((JPanel)jp.getComponent(2)).getComponent(1)).getComponentCount()));
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
        
        jMenuItem5.setEnabled(true);
    }
    
    public void displayQRCodes()
    {
        
        Dimension dimension3 = new Dimension();
        dimension3=jScrollPane1.getPreferredSize();
        dimension3.width=dimension3.width - 10;
        dimension3.height=dimension3.height - 10;
        JPanel jp=qrCodesItems.getAllComponents(dimension3);
        
        JButton back=qrCodesItems.getBackButton();
        JButton next=qrCodesItems.getNextButton();
        next.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {

                    System.out.println("\n\n\n\n\n\n\n\n -------------------------\n"
                            + "current page=" + HomeScreen.this.qrCodesItems.getCurrentPage());
                    if(HomeScreen.this.qrCodesItems.getTotalItemsSize()>new UtilUse().displayMax)
                    {
                        HomeScreen.this.qrCodesItems.setCurrentPage(HomeScreen.this.qrCodesItems.getCurrentPage() + new UtilUse().displayMax);
                        HomeScreen.this.displayQRCodes();
                    }

                }
                catch(UnsupportedOperationException ex)
                {

                }
            }
        });
        back.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(HomeScreen.this.qrCodesItems.getCurrentPage()>=new UtilUse().displayMax)
                    {
                        HomeScreen.this.qrCodesItems.setCurrentPage(HomeScreen.this.qrCodesItems.getCurrentPage() - new UtilUse().displayMax);
                        HomeScreen.this.displayQRCodes();
                    }
                }
                catch(UnsupportedOperationException ex)
                {

                }
            }
        });
        jp.setPreferredSize(dimension3);
        jScrollPane1.getViewport().removeAll();
        System.out.println("Remove All Components" + (((JPanel)((JPanel)jp.getComponent(2)).getComponent(0)).getComponentCount() + ((JPanel)((JPanel)jp.getComponent(2)).getComponent(1)).getComponentCount()));
        jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
        jScrollPane1.setViewportView(jp);
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
        
        jMenuItem5.setEnabled(true);
    }
    
    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayQRCodes();
        }else
        {
            displayLogin();
        }
        
        //this.printJPanel=jp;
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        System.out.println(this.printJPanel.getComponent(0).toString());
        Printer.printComponent(this.printJPanel);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayReports();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayQRCodes();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayQRCodes();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel14MouseExited

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayUserManage();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel12MouseExited

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        if(this.appAccess.getAccessKey().length()>0)
        {
            displayUserManage();
        }else
        {
            displayLogin();
        }
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xffffff));
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        // TODO add your handling code here:
        ((JLabel)evt.getSource()).setForeground(new Color(0xcccccc));
    }//GEN-LAST:event_jLabel13MouseExited

    
    public boolean deleteShopItems(int id)
    {
        System.out.println("Delete id " + id);
        PreparedStatement pr;
        int rs=0;
        try {
            pr = this.con.prepareStatement("DELETE from shop_items WHERE id = ?");
            pr.setInt(1, id);
            rs=pr.executeUpdate();
        } catch (SQLException ex) {
            new UtilUse().Log(ex, HomeScreen.this.installPath);
            Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(rs>0)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    public void setSelectedItems(Hashtable h)
    {
        this.selectedItemsList=h;
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            //new UtilUse().Log(ex, installPath);
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            //new UtilUse().Log(ex, HomeScreen.this.installPath);
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //new UtilUse().Log(ex, HomeScreen.this.installPath);
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //new UtilUse().Log(ex, HomeScreen.this.installPath);
            java.util.logging.Logger.getLogger(HomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                if(AppLock.setLock("Trest"))
                {
                    HomeScreen homeScreen=new HomeScreen();
                    homeScreen.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    homeScreen.setBounds(0,0,screenSize.width, screenSize.height);
                    WrapLayout wrapLayout=new WrapLayout(FlowLayout.TRAILING, 0, 0);

                    homeScreen.setLayout(wrapLayout);



                    homeScreen.setVisible(true);
                }
                
                
                
                
                
            }
        });
    }
    
    
    public void setBeforeDisplay()
    {
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        super.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension = new Dimension();
        dimension.setSize(screenSize.getWidth()-20, 35);
        //jPanel1.setPreferredSize(dimension);
        //jPanel1.setAlignmentX(0);
        
        Dimension dimension1 = new Dimension();
        
        dimension1.setSize(screenSize.getWidth()-20, (screenSize.getHeight() - jPanel2.getY() - 5));
        jPanel2.setPreferredSize(dimension1);
        jPanel2.setLayout(new WrapLayout(FlowLayout.LEFT, 3, 3));
        //jPanel2
        
        jPanel4.setPreferredSize(new Dimension((int)screenSize.getWidth(), 20));
        
        Dimension dimension2 = new Dimension();
        dimension2.setSize(180, (screenSize.getHeight() - jPanel2.getY() - jPanel4.getPreferredSize().getHeight()- 10));
        jPanel3.setPreferredSize(dimension2);
        
        
        Dimension dimension3 = new Dimension();
        dimension3.setSize((screenSize.getWidth()- 180 -40 - jPanel5.getWidth()), (screenSize.getHeight() - jPanel2.getY() - jPanel4.getPreferredSize().getHeight()- 10));
        jScrollPane1.setPreferredSize(dimension3);
        
        jScrollPane1.setLayout(new ScrollPaneLayout());
        
        
        Dimension dimension4 = new Dimension();
        dimension4.setSize((jPanel5.getWidth()), (screenSize.getHeight() - jPanel2.getY() - jPanel4.getPreferredSize().getHeight()- 10));
        jPanel5.setPreferredSize(dimension4);
        
        jPanel5.setLayout(new WrapLayout(FlowLayout.LEFT, 3, 3));
        //JButton jButton2=new JButton("Test");
        //jPanel4.add(jButton2);
        //jPanel3.getPreferredSize().setSize(300, 500);
        // wrapLayout=new WrapLayout(FlowLayout.TRAILING, 10, 0);
        //jToolBar1.setLayout(wrapLayout);
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    // End of variables declaration//GEN-END:variables

    public void displaySetUpShop(Connection con, Mysql mysql, String installFolder) {
        try
        {
            this.installPath=installFolder;
            SetUpShop setUpShop=new SetUpShop(con, mysql, this);

            GridBagConstraints gbc = new GridBagConstraints();
            Dimension dimension3 = new Dimension();
            dimension3=jScrollPane1.getPreferredSize();
            dimension3.width=dimension3.width - 10;
            dimension3.height=dimension3.height - 10;
            dimension3.height=2000;
            JPanel jp=setUpShop.getAllComponents(this.installPath);
            jp.setBackground(new Color(0x262624));
            jScrollPane1.getViewport().setBackground(new Color(0x262624)); 
            jScrollPane1.setViewportView(jp);
            jScrollPane1.revalidate();
        }catch(UnsupportedOperationException ex)
        {
            new UtilUse().Log(ex, HomeScreen.this.installPath);
        }
    }
}
