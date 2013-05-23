/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xkalibaer
 */
@XmlRootElement
public class User {
 
    private UserReportXML[] userReportXML;

    User()
    {
        
    }
    
    User(int a)
    {
        userReportXML=new UserReportXML[a];
    }
    /**
     * @return the userReportXML
     */
    public UserReportXML[] getUserReportXML() {
        return userReportXML;
    }

    /**
     * @param userReportXML the userReportXML to set
     */
    public void setUserReportXML(UserReportXML[] userReportXML) {
        this.userReportXML = userReportXML;
    }
    
    
    public void addToArray(UserReportXML userReportXML, int position)
    {
        this.userReportXML[position]=userReportXML;
    }
    
}
