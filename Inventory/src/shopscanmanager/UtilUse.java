/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import au.com.bytecode.opencsv.CSVWriter;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author xkalibaer
 */
public class UtilUse {
    
    final static String DATABASE="scanshop1";
    final static int displayMax=28;
    public UtilUse()
    {
        
    }
    
    
    public BufferedImage resizeImage(BufferedImage originalImage, int width, int height, int type) {  
        
        BufferedImage resizedImage = new BufferedImage(width, height, type);  
        Graphics2D g = resizedImage.createGraphics();  
        g.drawImage(originalImage, 0, 0, width, height, null);  
        g.dispose();  
        return resizedImage;  
    }
    
    
    public CSVWriter generateCSVForUserReport(ResultSet rs1, CSVWriter writer, String file_name)
    {
        try {
            ArrayList ar=new ArrayList();
            String[] stringArr=new String[]{"First Name", "Last Name", "Other Name", "Email Address", "Mobile", 
                "Date account created", "User Level"};
            writer.writeNext(stringArr);
            
            
            
            while (rs1.next()) { 
                stringArr=new String[]{rs1.getString("first_name"), rs1.getString("last_name"),
                    rs1.getString("other_name"), rs1.getString("email_address"),rs1.getString("mobile_number"),
                    rs1.getString("date_created"),rs1.getString("usertype")};
            
                writer.writeNext(stringArr);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }
            return writer;
    }
    
    public CSVWriter generateCSVForInventoryLog(ResultSet rs1, CSVWriter writer, String file_name)
    {
        try {
            ArrayList ar=new ArrayList();
            String[] stringArr=new String[]{"Item Name", "Seller", "BarCode Number", "Item Type", "Date Added", 
                "Amount Sold"};
            writer.writeNext(stringArr);
            
            
            
            while (rs1.next()) { 
                String soldYes="Yes";
                if(rs1.getInt("status")==1)
                {
                    soldYes="No";
                }
                stringArr=new String[]{rs1.getString("title"), rs1.getString("first_name") + " " + rs1.getString("last_name")
                        + rs1.getString("other_name"),
                    rs1.getString("barCodeNumber"), rs1.getString("name"),rs1.getString("dateUploaded"),soldYes};
            
                writer.writeNext(stringArr);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }
            return writer;
    }
    
    
    
    public CSVWriter generateCSVForInventory(ResultSet rs1, CSVWriter writer, String file_name)
    {
        try {
            ArrayList ar=new ArrayList();
            String[] stringArr=new String[]{"Item Name", "Price", "BarCode Number", "Item Type", "Date Added", 
                "Sold?"};
            writer.writeNext(stringArr);
            
            
            
            while (rs1.next()) { 
                String soldYes="Yes";
                if(rs1.getInt("status")==1)
                {
                    soldYes="No";
                }
                stringArr=new String[]{rs1.getString("title"), rs1.getString("price"),
                    rs1.getString("barCodeNumber"), rs1.getString("name"),rs1.getString("dateUploaded"),soldYes};
            
                writer.writeNext(stringArr);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }
            return writer;
    }
    
    
    public void logReport(String file_name, String reportType, String fileType, Connection con)
    {
        int fileId=0;
        try {
            PreparedStatement pr=con.prepareStatement("INSERT INTO files (`unique_id`, `fileName`, `dateUploaded`, `status`) VALUES "
                                + "(?, ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS);

            pr.setString(1, file_name);
            pr.setString(2, file_name);
            pr.setInt(3, 1);
            int addCount = pr.executeUpdate();

            ResultSet res = pr.getGeneratedKeys();
            System.out.println(res.getStatement().toString());
            if (res.next()){
                fileId=(int)res.getInt(1);}
            
            pr=con.prepareStatement("INSERT INTO reports (`date_created`, `report_type`, `fileId`, `fileType`) VALUES "
                                + "(NOW(), ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pr.setString(1, reportType);
            pr.setInt(2, fileId);
            pr.setString(3, fileType);
            addCount = pr.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(UserReports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void Log(Exception e, String filepath)
    {
        FileWriter fstream = null;
        try {
            //File file=new File("c:\\logs\\log76.txt");
            StringWriter w = new StringWriter();
            e.printStackTrace(new java.io.PrintWriter(w));
            
            fstream = new FileWriter(filepath + "\\log77.txt", true); //true tells to append data.
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(w.toString());
            out.close();
            
            
            System.out.println("Done");
            
        } catch (IOException ex) {
            Logger.getLogger(UtilUse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilUse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void Log(Exception e)
    {
        FileWriter fstream = null;
        try {
            //File file=new File("c:\\logs\\log76.txt");
            StringWriter w = new StringWriter();
            e.printStackTrace(new java.io.PrintWriter(w));
            
            fstream = new FileWriter("c:\\logs\\log76.txt", true); //true tells to append data.
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(w.toString());
            out.close();
            
            
            System.out.println("Done");
            
        } catch (IOException ex) {
            Logger.getLogger(UtilUse.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilUse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean comparePasswords(char[] p1, char[] p2)
    {
        if(p1.length != p2.length)
        {
            return false;
        }
        else
        {
            return Arrays.equals(p1, p2);
        }
    }
    
    public String getDateSuffix(String dateStr)
    {
        dateStr=dateStr.substring(dateStr.length() - 2);
        if(dateStr.startsWith("0"))
        {
            dateStr=dateStr.substring(dateStr.length() - 1);
        }
        if(dateStr.endsWith("1"))
        {
            return dateStr + "st";
        }
        else if(dateStr.endsWith("2"))
        {
            return dateStr + "nd";
        }
        else if(dateStr.endsWith("3"))
        {
            return dateStr + "rd";
        }
        else
        {
            return dateStr + "th";
        }
    }
    
    public String GetMyDocuments() {
    
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        String dir=fw.getDefaultDirectory().toString();
        System.out.println(dir);
        return dir;
    
    }
    
    
    public static File getDirectoryChoice(Component owner, JFileChooser chooser, File defaultDir,
                                          String title) {
        //http://www.java2s.com/Code/Java/Swing-JFC/GetDirectoryChoice.htm
        SecurityManager sm = null;
        File         choice = null;

        sm = System.getSecurityManager();
        System.setSecurityManager(null);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if ((defaultDir != null) && defaultDir.exists()
                && defaultDir.isDirectory()) {
            chooser.setCurrentDirectory(defaultDir);
            chooser.setSelectedFile(defaultDir);
        }
        chooser.setDialogTitle(title);
        chooser.setApproveButtonText("OK");
        int v = chooser.showOpenDialog(owner);

        owner.requestFocus();
        switch (v) {
        case JFileChooser.APPROVE_OPTION:
            if (chooser.getSelectedFile() != null) {
                if (chooser.getSelectedFile().exists()) {
                    choice = chooser.getSelectedFile();
                } else {
                    File parentFile =
                        new File(chooser.getSelectedFile().getParent());

                    choice = parentFile;
                }
            }
            break;
        case JFileChooser.CANCEL_OPTION:
        case JFileChooser.ERROR_OPTION:
        }
        chooser.removeAll();
        chooser = null;
        System.setSecurityManager(sm);
        return choice;
    }
}
