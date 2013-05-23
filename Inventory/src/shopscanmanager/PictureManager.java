/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.codingcatholic.util.*;

/**
 *
 * @author xkalibaer
 */
public class PictureManager {
    
    public PictureManager()
    {
        
    }
    
    public BufferedImage getPicturePath(String path, String fileName, String originalFileName)
    {
        //System.out.println("originalFileName = " + originalFileName);
        String[] originalFileNameArray=originalFileName.split("\\.");
        //System.out.println("originalFileNameArray.length = " + originalFileNameArray.length);
        String pathway=path + fileName;
        //System.out.println(pathway);
        JLabel favPicLabel=new JLabel();
        BufferedImage myPicture;

        try {
            myPicture = ImageIO.read(new File(pathway));
            //System.out.println("Image created");
            return myPicture;
            //favPicLabel.setBackground(new java.awt.Color(23, 21, 21));getClass().getResource(pathway).getPath().substring(1)
        } catch (IOException ex) {
            //Logger.getLogger(ShopItemsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    
    public BufferedImage getPicturePath(String path, String fileName, String originalFileName, String ext)
    {
        //System.out.println("originalFileName = " + originalFileName);
        String[] originalFileNameArray=originalFileName.split("\\.");
        //System.out.println("originalFileNameArray.length = " + originalFileNameArray.length);
        String pathway=path + fileName + ext;
        //System.out.println(pathway);
        JLabel favPicLabel=new JLabel();
        BufferedImage myPicture;

        try {
            myPicture = ImageIO.read(new File(pathway));
            //System.out.println("Image created");
            return myPicture;
            //favPicLabel.setBackground(new java.awt.Color(23, 21, 21));getClass().getResource(pathway).getPath().substring(1)
        } catch (IOException ex) {
            //Logger.getLogger(ShopItemsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    
}
