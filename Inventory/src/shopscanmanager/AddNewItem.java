/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shopscanmanager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.json.simple.JSONObject;



/**
 *
 * @author xkalibaer
 */
public class AddNewItem extends javax.swing.JPanel {
    private File fileSelected;
    private String fileSelectedPath;
    private int editId;
    private JSONObject editDetails;
    private Mysql mysql;
    private Connection con;
    private AppAccess appAccess;
    private HomeScreen hs;
    private String savePath="";

    /**
     * Creates new form AddNewItem
     */
    public AddNewItem(Connection con, Mysql mysql, HomeScreen hs, String savePath) {
        this.hs=hs;
        initComponents();
        this.con=con;
        this.mysql=mysql;
        this.savePath=savePath;
    }
    
    
    public JPanel showEditScreen(int id, Connection con, Mysql mysql, AppAccess appAccess)
    {
        System.out.println("Show Edit Screen");
        this.removeAll();
        initComponents();
        this.editId=id;
        this.con=con;
        this.mysql=mysql;
        this.appAccess=appAccess;
        PreparedStatement pr;
        try {
            pr = con.prepareStatement("SELECT * from shop_items WHERE id = ?");
            pr.setInt(1, id);
            ResultSet rs=mysql.query(pr);
            System.out.println("id = "+ id);


            while (rs.next()) { 
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", rs.getString("title"));
                jsonObject.put("price", rs.getString("price"));
                jsonObject.put("type", rs.getString("type"));
                jsonObject.put("fileId", rs.getString("fileId"));
                jsonObject.put("details", rs.getString("details"));
                jsonObject.put("barCodeNumber", rs.getString("barCodeNumber"));
                jsonObject.put("dateUploaded", rs.getString("dateUploaded"));
                jsonObject.put("uniqueId", rs.getString("uniqueId"));
                this.editDetails=jsonObject;
                System.out.println("editDetails = " + this.editDetails.toJSONString());
            }
            if(editDetails.get("barCodeNumber").toString().length()>0)
            {
                System.out.println("Set combo to Bar Code");
                this.jComboBox2.setSelectedIndex(1);
                this.jLabel5.setVisible(true);
                this.jTextField5.setVisible(true);
                this.jTextField5.setText(editDetails.get("barCodeNumber").toString());
            }
            else
            {
                System.out.println("Set combo to QR Code");
                this.jComboBox2.setSelectedIndex(2);
                this.jLabel5.setVisible(false);
                this.jTextField5.setVisible(false);
            }
            
            this.jComboBox1.setSelectedIndex(Integer.parseInt(editDetails.get("type").toString()));
            System.out.println("combo 1 = " + editDetails.get("type").toString());
            this.jTextArea1.setText(editDetails.get("details").toString());
            this.jButton1.setText("Save Items Details");
            this.jButton1.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    try
                    {
                        //AddNewItem addNewItem=new AddNewItem();
                        Hashtable h1=AddNewItem.this.addNewItemProcess(AddNewItem.this.con, AddNewItem.this.mysql, AddNewItem.this.appAccess, "C:/wamp/www/scanshoplocal/");
                        /*Frame[] f=HomeScreen.getFrames();
                        AddNewItem addNewItem=new AddNewItem(AddNewItem.this.con, AddNewItem.this.mysql);

                        for(int count5=0; count5<f.length; count5++)
                        {
                            System.out.println(f[count5].toString());
                            HomeScreen h=((HomeScreen)f[count5]);*/

                            AddNewItem.this.hs.displayProcessMessage(h1);
                        //}
                    }
                    catch(UnsupportedOperationException ex)
                    {
                        
                    }
                }
            
            });
        } catch (SQLException ex) {
            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        //initComponents();
        return getAllComponents();
    }
    
    public JPanel getAllComponents()
    {
        HeaderPanel header=new HeaderPanel();
        if(this.editDetails!=null)
        {
            System.out.println("editDetails = " + this.editDetails.toJSONString());
        }
        BoxLayoutType bxt=new BoxLayoutType(BoxLayoutType.VERTICAL, 0, 0);
        this.setLayout(bxt);
        this.setBackground(new java.awt.Color(23, 21, 21));
        /*this.jPanel2.setBackground(new java.awt.Color(23, 21, 21));*/
        this.jPanel3.setBackground(new java.awt.Color(23, 21, 21));
        this.jComboBox1.removeAllItems();
        this.jComboBox1.addItem("-Select One-");
        this.jLabel5.setVisible(false);
            this.jTextField5.setVisible(false);
        
        PreparedStatement pr3;
        try {
            pr3 = this.con.prepareStatement("Select * from shop_item_type WHERE `status` = 1");
            ResultSet rs1=mysql.query(pr3);


            while (rs1.next()) { 
                this.jComboBox1.addItem(rs1.getString("name"));
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jComboBox2.removeAllItems();
        this.jComboBox2.addItem("-Select One-");
        this.jComboBox2.addItem("Bar Code");
        this.jComboBox2.addItem("QR Code");
        
        this.jButton2.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    JFileChooser chooser = new JFileChooser();
                    
                    FileFilter filter1 = new ExtensionFileFilter("JPG and JPEG and PNG", new String[] { "JPG", "JPEG", "PNG" });
                    chooser.setFileFilter(filter1);
                    int returnVal = chooser.showOpenDialog(AddNewItem.this.jButton2);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        try {
                            System.out.println("You chose to open this file: " +
                                chooser.getSelectedFile().getName() + "" + chooser.getSelectedFile().getCanonicalPath()
                                 + "" + chooser.getSelectedFile().getPath());
                            AddNewItem.this.fileSelectedPath=chooser.getSelectedFile().getCanonicalPath();
                            AddNewItem.this.fileSelected=chooser.getSelectedFile();
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
            this.jTextField5.setText(this.editDetails.get("barCodeNumber").toString());
            this.jTextField6.setText(this.editDetails.get("price").toString());
            
            if(this.editDetails.get("barCodeNumber").toString().length()>0)
            {
                System.out.println("Set to bar code");
                this.jComboBox2.setSelectedIndex(1);
            }
            else
            {
                System.out.println("Set to qr code");
                this.jComboBox2.setSelectedIndex(2);
            }
            //this.jComboBox1.g
            PreparedStatement pr1;
            try {
                pr1 = this.con.prepareStatement("SELECT * from shop_item_type");
                
                ResultSet rs1=mysql.query(pr1);
                System.out.println(rs1.getStatement().toString());
                ArrayList a1=new ArrayList();
                while (rs1.next()) { 
                    a1.add(rs1.getString("id"));
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nShop Item Type = " + rs1.getString("id") + " & =" + rs1.getString("name"));
                }
                if(a1.size()>0)
                {
                    System.out.println("Shop Item Type = " + a1.size() + " & as= "  + this.editDetails.get("type").toString() + " &w= " + a1.indexOf(this.editDetails.get("type").toString()));
                    this.jComboBox1.setSelectedIndex(a1.indexOf(this.editDetails.get("type").toString())+ 1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        
        this.add(header.getAllComponents(this.con, this.mysql, this.savePath), 0);
        return this;
    }
    
    
    public Hashtable addNewItemProcess(Connection con, Mysql mysql, AppAccess appAccess, String savePath)
    {
        try {
            Component[] comp=this.jPanel3.getComponents();
            this.savePath=savePath;
            /*for(int c9=0;c9<comp.length; c9++)
            {
                //System.out.println("c9 = " + comp[c9].getName() + " " + comp[c9].toString());
                if(c9==6)
                {
                    JTextField jtf=((JTextField)comp[c9]);
                   // System.out.println("c9 = " + comp[c9].getName() +  " " + jtf.getText().toString());
                }
            }*/
            if(this.jTextField1!=null)
            {
                System.out.println("Is Null");
            }
            String itemName=this.jTextField1.getText().toString();
            int itemType=this.jComboBox1.getSelectedIndex();
            PreparedStatement pr1 = this.con.prepareStatement("SELECT * from shop_item_type LIMIT ?, 1");
            pr1.setInt(1, (itemType - 1));
            ResultSet rs1=mysql.query(pr1);
            System.out.println("SQL =" + rs1.getStatement().toString());
            System.out.println(rs1.getStatement().toString());
            while (rs1.next()) { 
                itemType=rs1.getInt("id");
                System.out.println("itemType =" + itemType);
            }
            //System.out.println(this.jTextField3.getText().toString());
            
            int quantity=0;
            try
            {
                quantity=Integer.parseInt(this.jTextField3.getText().toString());
            }
            catch(java.lang.NumberFormatException ex)
            {
                /*Frame[] f=HomeScreen.getFrames();
                AddNewItem addNewItem=new AddNewItem(AddNewItem.this.con, AddNewItem.this.mysql);*/
                Hashtable h1=new Hashtable();
                h1.put("success", false);
                h1.put("message", "Numeric value needed for quantity. Will default to 0 if not numeric");
                h1.put("operation", "Add New Shop Item...");
                
                /*for(int count5=0; count5<f.length; count5++)
                {
                    System.out.println(f[count5].toString());
                    HomeScreen h=((HomeScreen)f[count5]);*/

                    this.hs.displayProcessMessage(h1);
                //}
            }
            int identificationType=this.jComboBox2.getSelectedIndex();
            String barCodeNumber="";
            if(this.jTextField5.isVisible())
            {
                barCodeNumber=this.jTextField5.getText().toString();
            }
            String price=this.jTextField6.getText().toString();
            String description=this.jTextArea1.getText().toString();
            String uniqIdImage;
            
            Hashtable processResult=new Hashtable();
            System.out.println("Add/Edit new item1");
            
            if(quantity>0)
            {
                System.out.println("Add/Edit new item2");
                if(itemName.length()>0)
                {
                    if(itemType>0)
                    {
                        System.out.println("Add/Edit new item3");
                        if(identificationType>0)
                        {
                            int rt=0;
                            if(this.jTextField5.isVisible()==false)
                            {
                                rt=1;
                            }
                            else if(this.jTextField5.isVisible() && barCodeNumber.length()>0 )
                            {
                                rt=1;
                            }
                            if(rt==1)
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
                                                FileOutputStream fileOutputsteam=new FileOutputStream(new File(savePath + "\\images\\" + uniqIdImage));

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
                                                processResult.put("operation", "Add New Shop Item...");
                                            }
                                            catch (IOException ex)
                                            {
                                                Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                                processResult.put("success", false);
                                                processResult.put("message", "Process failed. Try again");
                                                processResult.put("operation", "Add New Shop Item...");
                                            }

                                            System.out.println("appAccess.getAccessKey()=" + appAccess.getAccessKey());
                                            pr=con.prepareStatement("INSERT INTO files (`unique_id`, `fileName`, `source_id`, `dateUploaded`, `status`, `receipient_id`) VALUES "
                                                    + "(?, ?, (SELECT `id` FROM user_profile where `key` = ?), NOW(), ?, "
                                                    + "(SELECT `id` FROM user_profile where `key` = ?))", Statement.RETURN_GENERATED_KEYS);

                                            pr.setString(1, uniqIdImage);
                                            pr.setString(2, fileSelected.getName());
                                            pr.setString(3, appAccess.getAccessKey());
                                            pr.setInt(4, 1);
                                            pr.setString(5, appAccess.getAccessKey());
                                            int addCount = pr.executeUpdate();

                                            ResultSet res = pr.getGeneratedKeys();
                                            while (res.next()){
                                                fileId=res.getInt(1);}
                                        }

                                        String uniqId=UUID.randomUUID().toString();
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("title", itemName);
                                        jsonObject.put("price", price);
                                        jsonObject.put("type", itemType);
                                        if(fileId>-1)
                                        {
                                            jsonObject.put("fileId", fileId);
                                        }
                                        jsonObject.put("uniqueId", uniqId);
                                        jsonObject.put("barCodeNumber", barCodeNumber);
                                        jsonObject.put("details", description);
                                        String jsonObjectStr=jsonObject.toJSONString();

                                        ByteArrayOutputStream out = QRCode.from(jsonObjectStr).to(ImageType.PNG).stream();

                                        try {
                                            FileOutputStream fout = new FileOutputStream(new File(savePath + "\\qr_codes\\" + uniqId + ".png"));

                                            fout.write(out.toByteArray());

                                            fout.flush();
                                            fout.close();

                                        } catch (FileNotFoundException e) {
                                            // Do Logging
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                            processResult.put("success", false);
                                            processResult.put("message", "Process failed. Try again");
                                            processResult.put("operation", "Add New Shop Item...");
                                        } catch (IOException e) {
                                            // Do Logging
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                            processResult.put("success", false);
                                            processResult.put("message", "Process failed. Try again");
                                            processResult.put("operation", "Add New Shop Item...");
                                        } 

                                        try
                                        {
                                            BitMatrix bitMat=new Code128Writer().encode(uniqId, 
                                                BarcodeFormat.CODE_128, 100, 100);
                                            
                                            MatrixToImageWriter.writeToStream(bitMat, "png", 
                                                    new FileOutputStream(new File(savePath + "\\bar_codes\\" + uniqId + ".png")));
                                        }
                                        catch(WriterException  e)
                                        {
                                            new UtilUse().Log(e, this.savePath);
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                        catch(Exception e)
                                        {
                                            new UtilUse().Log(e, this.savePath);
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                        }
                                        
                                        
                                        //jsonObject.put("time", "02:00");

                                        
                                        if(fileId>-1)
                                        {
                                            pr=con.prepareStatement("INSERT INTO shop_items (`qr_code`, `title`, `price`, `details`, `uniqueId`, `type`, `status`, "
                                                    + "`dateUploaded`, `barCodeNumber`, `fileId`) VALUES "
                                                + "(?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)");
                                            System.out.println("\n\n\n\n\n\n\n\nyes file");
                                        }
                                        else
                                        {
                                            pr=con.prepareStatement("INSERT INTO shop_items (`id`,`qr_code`, `title`, `price`, `details`, `uniqueId`, `type`, "
                                                + "`status`, `dateUploaded`, `barCodeNumber`, `fileId`) VALUES "
                                                + "(null, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NULL)");
                                            System.out.println("\n\n\n\n\n\n\n\nno file");
                                        }
                                        qr_code=jsonObjectStr;
                                        pr.setString(1, qr_code);
                                        pr.setString(2, itemName);
                                        pr.setString(3, price);
                                        pr.setString(4, description);
                                        pr.setString(5, uniqId);
                                        pr.setInt(6, itemType);
                                        pr.setInt(7, 1);
                                        pr.setString(8, barCodeNumber);
                                        if(fileId>-1)
                                        {
                                            pr.setInt(9, fileId);
                                        }

                                        int shopItemCount = pr.executeUpdate();
                                        if(shopItemCount>0)
                                        {
                                            processResult.put("success", true);
                                            processResult.put("message", "Upload successful!");
                                            processResult.put("operation", "Add New Shop Item...");
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
                                            sql="UPDATE shop_items SET `qr_code` = ?, `title` = ?, `price` = ?, `details` = ?, `type` = ?, `status` = ?, `barCodeNumber`= ?, `dateUploaded` = NOW() WHERE `id` = " + editId;
                                            
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
                                                FileOutputStream fileOutputsteam=new FileOutputStream(new File(savePath + "\\images\\" + uniqIdImage));

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
                                                processResult.put("operation", "Updating Shop Item...");

                                            }
                                            catch (IOException ex)
                                            {
                                                Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                                processResult.put("success", false);
                                                processResult.put("message", "Process failed. Try again");
                                                processResult.put("operation", "Updating Shop Item...");
                                            }

                                            System.out.println("appAccess.getAccessKey()=" + appAccess.getAccessKey());
                                            pr=con.prepareStatement("INSERT INTO files (`unique_id`, `fileName`, `source_id`, `dateUploaded`, `status`, `receipient_id`) VALUES "
                                                    + "(?, ?, (SELECT `id` FROM user_profile where `key` = ?), NOW(), ?, "
                                                    + "(SELECT `id` FROM user_profile where `key` = ?))", Statement.RETURN_GENERATED_KEYS);

                                            pr.setString(1, uniqIdImage);
                                            pr.setString(2, fileSelected.getName());
                                            pr.setString(3, appAccess.getAccessKey());
                                            pr.setInt(4, 1);
                                            pr.setString(5, appAccess.getAccessKey());
                                            int addCount = pr.executeUpdate();

                                            ResultSet res = pr.getGeneratedKeys();
                                            while (res.next()){
                                                fileId=res.getInt(1);}

                                            if(fileId>-1)
                                            {
                                                sql="UPDATE shop_items SET `qr_code` = ?, `title` = ?, `price` = ?, `details` = ?, `type` = ?, `status` = ?, `dateUploaded` = NOW(), `barCodeNumber`= ?, `fileId` = ? WHERE `id` = "  + editId;
                                                System.out.println("fileId>-1");
                                            }
                                            else
                                            {
                                                sql="UPDATE shop_items SET `qr_code` = ?, `title` = ?, `price` = ?, `details` = ?, `type` = ?, `status` = ?, `dateUploaded` = NOW(), `barCodeNumber` = ? WHERE `id` = " + editId;
                                                System.out.println("fileId==-1");
                                            }

                                        }

                                        String uniqId=this.editDetails.get("uniqueId").toString();
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("title", itemName);
                                        jsonObject.put("price", price);
                                        jsonObject.put("type", itemType);
                                        if(this.editDetails.containsKey("fileId"))
                                        {
                                            jsonObject.put("fileId", this.editDetails.containsKey("fileId"));
                                        }
                                        jsonObject.put("uniqueId", uniqId);
                                        jsonObject.put("details", description);
                                        jsonObject.put("barCodeNumber", barCodeNumber);
                                        String jsonObjectStr=jsonObject.toJSONString();

                                        qr_code=jsonObjectStr;
                                        pr=con.prepareStatement(sql);
                                        pr.setString(1, qr_code);
                                        pr.setString(2, itemName);
                                        pr.setString(3, price);
                                        pr.setString(4, description);
                                        pr.setInt(5, itemType);
                                        pr.setInt(6, 1);
                                        pr.setString(7, barCodeNumber);
                                        if(fileId>-1)
                                        {
                                            pr.setInt(8, fileId);
                                        }
                                        
                                        /*sql="UPDATE shop_items SET `qr_code` = ?, `title` = ?, `price` = ?, `details` = ?, "
                                                + "`type` = ?, `status` = ?, `dateUploaded` = NOW(), `barCodeNumber` = ? WHERE `id` = " + editId;*/
                                        int shopItemCount = pr.executeUpdate();
                                    //    System.out.println("\n\n\n\n\n\n\n\n" + pr.getResultSet().getStatement().toString());





                                        ByteArrayOutputStream out = QRCode.from (jsonObjectStr).to(ImageType.PNG).stream();

                                        try {
                                            File file_=new File(savePath + "\\qr_codes\\" + uniqId);
                                            if(file_.exists())
                                            {
                                                file_.delete();
                                            }
                                            FileOutputStream fout = new FileOutputStream(new File(savePath + "\\qr_codes\\" + uniqId + ".png"));

                                            fout.write(out.toByteArray());

                                            fout.flush();
                                            fout.close();

                                        } catch (FileNotFoundException e) {
                                            // Do Logging
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                            processResult.put("success", false);
                                            processResult.put("message", "Process failed. Try again");
                                            processResult.put("operation", "Updating Shop Item...");
                                        }  catch (FileAlreadyExistsException e)
                                        {
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                            processResult.put("success", false);
                                            processResult.put("message", "Process failed. Try again");
                                            processResult.put("operation", "Updating Shop Item...");
                                        }catch (IOException e) {
                                            // Do Logging
                                            Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, e);
                                            processResult.put("success", false);
                                            processResult.put("message", "Process failed. Try again");
                                            processResult.put("operation", "Updating Shop Item...");
                                        }

                                        //jsonObject.put("time", "02:00");


                                        if(shopItemCount>0)
                                        {
                                            System.out.println("Successful");
                                            processResult.put("success", true);
                                            if(this.editDetails!=null)
                                            {
                                                processResult.put("message", "Upload successful!");
                                            }
                                            else
                                            {
                                                processResult.put("message", "Update successful!");
                                            }
                                            processResult.put("operation", "Updating Shop Item...");
                                        }
                                        else
                                        {
                                            System.out.println("Failed");
                                            processResult.put("success", false);
                                            processResult.put("message", "Process failed. Try again");
                                            processResult.put("operation", "Updating Shop Item...");
                                        }
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                                    processResult.put("success", false);
                                    processResult.put("message", "Process failed. Try again");
                                    processResult.put("operation", "Add/Update Shop Item...");
                                }
                            }
                            else
                            {
                                processResult.put("success", false);
                                processResult.put("message", "No barcode provided. Please provide one.");
                                processResult.put("operation", "Add/Update Shop Item...");
                            }
                            
                        }
                        else
                        {
                            processResult.put("success", false);
                            processResult.put("message", "Select An Identification Type");
                            processResult.put("operation", "Add/Update Shop Item...");
                        }
                    }
                    else
                    {
                        processResult.put("success", false);
                        processResult.put("message", "Select An Item Type");
                        processResult.put("operation", "Add/Update Shop Item...");
                    }
                }
                else
                {
                    processResult.put("success", false);
                    processResult.put("message", "Provide name of the item");
                    processResult.put("operation", "Add/Update Shop Item...");
                }
            }
            else
            {
                processResult.put("success", false);
                processResult.put("message", "Quantity provided not greater than 0");
                processResult.put("operation", "Add/Update Shop Item...");
            }
            return processResult;
        } catch (SQLException ex) {
            Hashtable processResult=new Hashtable();
            processResult.put("success", false);
            processResult.put("message", "Quantity provided not greater than 0");
            processResult.put("operation", "Add/Update Shop Item...");
            return processResult;
        }
    }
    
    public JButton getJButton1_()
    {
        return this.jButton1;
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
        jLabel55 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(42, 42, 42));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel55.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(204, 204, 204));
        jLabel55.setText("Add A New Shop Item");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
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
        jLabel1.setText("Name of Item:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Item Type:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Add/Edit Item Types");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Quantity:");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Identification Type:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setText("Bar Code Number:");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Item SnapShot:");

        jButton2.setText("Select Snap Shot Image");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Description:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Add Item");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Price:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel9)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(610, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
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

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        // TODO add your handling code here:
        if(this.jComboBox2.getSelectedIndex()!=-1)
        {
            String str = (String)this.jComboBox2.getSelectedItem();
            System.out.println("tttt=" + this.jComboBox2.getSelectedIndex());
            System.out.println(str);
            if(str.equals("Bar Code"))
            {
                this.jLabel5.setVisible(true);
                this.jTextField5.setVisible(true);
            }
            else
            {
                this.jLabel5.setVisible(false);
                this.jTextField5.setVisible(false);
            }
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
