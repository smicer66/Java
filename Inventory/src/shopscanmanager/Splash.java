/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author xkalibaer
 */
public class Splash extends JWindow{
    double windowWidth, windowHeight;
    double width=630;
    double height=330;
    double x, y;
    private Image img;
    JLabel label1;
    JLabel label2;
    
    public Splash()
    {
        super.setBackground(Color.WHITE);
        initialize();
        
    }
    
    public void initialize()
    {
        windowHeight=Toolkit.getDefaultToolkit().getScreenSize().getSize().height;
        windowWidth=Toolkit.getDefaultToolkit().getScreenSize().getSize().width;
        
        x=(windowWidth - width)/2;
        y=(windowHeight - height)/2;
        System.out.println(windowWidth + " | " + x + " | " + (windowWidth - width) + " | " + width);
        setBounds((int)x, (int)y, (int)width, (int)height);
        //this.setLayout(new GridLayout(2, 1));
        WrapLayout wrapLayout=new WrapLayout(FlowLayout.TRAILING, 0, 0);
        
        this.setLayout(wrapLayout);
        this.setBackground(Color.WHITE);
        JPanel panel0=new JPanel();
        JPanel panel3=new JPanel();
        label1=new JLabel();
        try
        {
            label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("logo.png"))); 
            
        }
        catch(NullPointerException e)
        {
            System.out.println("NullPointer exception");
        }
        panel0.setBackground(Color.WHITE);
        panel0.setLayout(new BorderLayout());
        panel0.add(label1, BorderLayout.NORTH);
        
        JLabel label7=new JLabel();
        label7.setIcon(new javax.swing.ImageIcon(getClass().getResource("splash_icon4.png")));
        
        BorderLayout borderLayout=new BorderLayout();
        
        panel3.setLayout(borderLayout);
        panel3.setBackground(Color.WHITE);
        panel3.add(label7, BorderLayout.NORTH);
        
        
        
        
        this.add(panel0);
        this.add(panel3);
        
        setVisible(true);
        
    }
    
    
    /*public static void main(String[] s)
    {
        Splash splash= new Splash();
        System.out.println(splash.getClass().getResource("logo.png"));

        
    }*/
}
