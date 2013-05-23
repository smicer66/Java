/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import java.io.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author xkalibaer
 */
public class TestApp {

    /**
     * @param args the command line arguments
     */
    
    
	  
	 
    
    
    public static void main(String[] args) {
        System.out.println(new UtilUse().GetMyDocuments());
        /*try  
        {
            FileWriter fstream = new FileWriter("c:\\logs\\out.txt", true); //true tells to append data.
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("\nsue");
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }*/
        UserReportXML userReportXML=new UserReportXML();
        userReportXML.setId(1);
        userReportXML.setFirst_name("John");
        userReportXML.setLast_name("Jim");
        userReportXML.setMobile_number("08030944719");
        userReportXML.setOther_name("Obi");
        userReportXML.setUsertype("Administrator");
        userReportXML.setEmail_address("jim@obi.com");
        userReportXML.setDate_created("2013-03-12");
        
        User user=new User(2);
        user.addToArray(userReportXML, 0);
        userReportXML=new UserReportXML();
        userReportXML.setId(2);
        userReportXML.setFirst_name("Peter");
        userReportXML.setLast_name("Jim");
        userReportXML.setMobile_number("08030944719");
        userReportXML.setOther_name("Obi");
        userReportXML.setUsertype("Administrator");
        userReportXML.setEmail_address("jim@obi.com");
        userReportXML.setDate_created("2013-03-12");
        user.addToArray(userReportXML, 1);
        //user.setUserReportXML(userReportXML);
        
        
        try {
 
		File file = new File("C:\\wamp\\www\\agn\\file_test.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
		jaxbMarshaller.marshal(user, file);
                jaxbMarshaller.marshal(user, System.out);
                
                
                
	      } catch (JAXBException e) {
		e.printStackTrace();
                System.out.println("We just caught this error");
	      }
                catch (Exception e) {
		e.printStackTrace();
                System.out.println("We just caught this error");
	      }
        
 
        
        // TODO code application logic here
        /*String[] a="seq3.png".split("\\.");
        System.out.println(a.length);
                            try {
                                FileInputStream fileInputStream=new FileInputStream(new File("C:/wamp/www/pillpod/images/1.gif"));
                                byte[] buffer = new byte[fileInputStream.available()];
                                System.out.println(buffer[5] + "" + buffer.toString());
                                int bytesRead = fileInputStream.read(buffer, 0, fileInputStream.available());
                                System.out.println(bytesRead);
                                int bytesAvailable;
                                int maxBufferSize=2*1048576;
                                FileOutputStream fileOutputsteam=new FileOutputStream(new File("C:/wamp/www/" + UUID.randomUUID() + ".gif"));

                                int count=0;
                                while (bytesRead > 0) {
                                    fileOutputsteam.write(buffer, count++, 1);
                                    
                                    /*bytesAvailable = fileInputStream.available();
                                    bytesAvailable = Math.min(bytesAvailable, maxBufferSize);*/
                                    /*bytesRead--;
                                }
                                fileInputStream.close();
                                fileOutputsteam.flush();
                                fileOutputsteam.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            catch (IOException ex)
                            {
                                Logger.getLogger(AddNewItem.class.getName()).log(Level.SEVERE, null, ex);
                            }*/
        
        
        
    }
}
