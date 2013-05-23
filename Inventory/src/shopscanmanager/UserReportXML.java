/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author xkalibaer
 */

    
 
@XmlRootElement
public class UserReportXML {

    /**
     * @return the name
     */


    private String first_name;
    private String last_name;
    private String other_name;
    private String email_address;
    private String mobile_number;
    private String date_created;
    private String usertype;
    private int id;
    


    public int getId() {
            return id;
    }

    @XmlAttribute
    public void setId(int id) {
            this.id = id;
    }

    
    public String getFirst_name() {
        return first_name;
    }

    @XmlElement
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    @XmlElement
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getOther_name() {
        return other_name;
    }

    @XmlElement
    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    @XmlElement
    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    @XmlElement
    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDate_created() {
        return date_created;
    }

    @XmlElement
    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getUsertype() {
        return usertype;
    }

    @XmlElement
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
 
}