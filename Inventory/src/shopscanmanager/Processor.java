/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

//import com.sun.xml.internal.ws.wsdl.writer.document.Port;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import org.apache.http.client.methods.HttpPost;
import org.apache.*;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import sun.net.www.http.HttpClient;
//import sun.net.www.http.HttpClient;

/**
 *
 * @author xkalibaer
 */
public class Processor 
             extends SwingWorker<JSONObject, String> {
 
  private final String feature;
  private final String action;
  private final String key;
  private final String message;
  private final Informable informable;
  private Connection con;
  private Mysql mysql;
 
  public Processor(String feature, String action, String key,
                                String message,
                                Informable informable, Mysql mysql, Connection con){
      System.out.println("We in processor");
    this.feature = feature;
    this.action = action;
    this.key = key;
    this.message = message;
    this.informable = informable;
    this.mysql=mysql;
    this.con=con;
  }
 
  @Override
  protected JSONObject doInBackground() throws Exception {
      //System.out.println("We in processor bg");
    JSONObject matches = new JSONObject();
    //Thread t=new Thread();
    long timenow=System.currentTimeMillis();
    long end=timenow + (2*60*1000);
    /*while(timenow<end)
    {
        publish("Uploading data to server: ...");
        setProgress((int) (timenow * 100 / (2*60*1000)));
        timenow=timenow + 1000;
        System.out.println("We in processor bg " + timenow);
    }*/
    //System.out.println("still in bg ");
    
    URL url=new URL("http://www.imobilliare.com/scanshop/index.php");
    System.out.println("Output from Server .... \n");
    JSONObject formDet=new JSONObject();
    formDet.put("feature", feature);
    formDet.put("action", action);
    formDet.put("key", key);
    formDet.put("message", message);
    String urlParameters="feature=" + feature + "&action=" + action + 
            "&key=" + key + "&msg=" + URLEncoder.encode(message, "UTF-8");
    /*String urlParameters =
        "fName=" + URLEncoder.encode("asdasds", "UTF-8") +
        "&lName=" + URLEncoder.encode("saffsaf", "UTF-8");*/
    HttpURLConnection connection = null;  
    try {
      //Create connection
      
      connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", 
           "application/x-www-form-urlencoded");
			
      connection.setRequestProperty("Content-Length", "" + 
               Integer.toString(urlParameters.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");  
			
      connection.setUseCaches (false);
      connection.setDoInput(true);
      connection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream (
                  connection.getOutputStream ());
      wr.writeBytes (urlParameters);
      wr.flush ();
      wr.close ();

      //Get Response	
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      String line;
      StringBuffer response = new StringBuffer(); 
      while((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      
      int c;
      while((c = rd.read()) !=-1)
      {
          response.append((char)c);
      }
      String str=response.toString().replaceAll("_white_", "_");
      System.out.println(str);
      
      Object obj = JSONValue.parse(str);
      JSONObject jObj = (JSONObject)obj;
      System.out.println("-----" + jObj);
      
      
      JSONObject resp=new JSONObject();
      
      if(jObj!=null && (Boolean)jObj.get("status")==true)
      {
            
            JSONParser parser=new JSONParser();
            System.out.println("logs" + jObj.get("shop_logs").toString());
            JSONArray jObj1= (JSONArray)parser.parse(jObj.get("shop_logs").toString());
            //Object obj1 = JSONValue.parseWithException(jObj.get("logs").toString());
            //JSONObject jObj1 = (JSONObject)obj1;
            System.out.println("-----" + ((JSONObject)jObj1.get(0)).get("id"));
            
            if(jObj1!=null)
            {
                for(int count=0;count<jObj1.size();count++)
                {
                    PreparedStatement pr=this.con.prepareStatement("TRUNCATE TABLE shop_items_log");
                    pr.executeUpdate();

                    PreparedStatement query=con.prepareStatement("INSERT INTO `shop_items_log` (`id`, "
                            + "`shopItemId`, `timeLogged`, `amountSoldFor`, `uniqueId`, `userId`, `status`) VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                            query.setString(1, ((JSONObject)(jObj1.get(count))).get("id").toString());
                            query.setString(2, ((JSONObject)(jObj1.get(count))).get("shopItemId").toString());
                            query.setString(3, ((JSONObject)(jObj1.get(count))).get("timeLogged").toString());
                            query.setString(4, ((JSONObject)(jObj1.get(count))).get("amountSoldFor").toString());
                            query.setString(5, ((JSONObject)(jObj1.get(count))).get("uniqueId").toString());
                            query.setString(6, ((JSONObject)(jObj1.get(count))).get("userId").toString());
                            query.setString(7, ((JSONObject)(jObj1.get(count))).get("status").toString());
                            int addCount = query.executeUpdate();
                }
            }
            
            
            JSONArray jObj2= (JSONArray)parser.parse(jObj.get("activity_logs").toString());
            //Object obj1 = JSONValue.parseWithException(jObj.get("logs").toString());
            //JSONObject jObj1 = (JSONObject)obj1;
            System.out.println("-----" + ((JSONObject)jObj1.get(0)).get("id"));
            
            if(jObj2!=null)
            {
                for(int count=0;count<jObj2.size();count++)
                {
                    PreparedStatement pr=this.con.prepareStatement("TRUNCATE TABLE activity_log");
                    pr.executeUpdate();

                    PreparedStatement query=con.prepareStatement("INSERT INTO `activity_log` (`id`, "
                            + "`user_profile_id`, `activity_type_id`, `activity_date`) VALUES "
                            + "(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                            query.setString(1, ((JSONObject)(jObj2.get(count))).get("id").toString());
                            query.setString(2, ((JSONObject)(jObj2.get(count))).get("user_profile_id").toString());
                            query.setString(3, ((JSONObject)(jObj2.get(count))).get("activity_type_id").toString());
                            query.setString(4, ((JSONObject)(jObj2.get(count))).get("activity_date").toString());
                            int addCount = query.executeUpdate();
                }
            }
      }
      resp.put("message", jObj.get("message").toString());
      resp.put("status", (boolean)jObj.get("status"));
      
      rd.close();
      connection.disconnect(); 
      System.out.println("resp value=" + resp.toJSONString());
      return resp;
    } catch (Exception e) {
        new UtilUse().Log(e);
      e.printStackTrace();
      return null;

    } 
  
                    /*System.out.println("12Output from Server .... \n" + conn.getResponseCode());
                    if (conn.getResponseCode() != 200) {
                            /*throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());*/
                        /*JSONObject resp=new JSONObject();
                        resp.put("message", "Connection Failed");
                        resp.put("status", false);
                        return resp;
                    }
                    else
                    {
                        System.out.println("Test12");

                        InputStream is = conn.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        String line;
                        StringBuffer response = new StringBuffer(); 
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();
                        //return response.toString();

                        System.out.println("output=" + response.toString());
                        conn.disconnect();


                        JSONObject resp=new JSONObject();
                        resp.put("message", response.toString());
                        resp.put("status", true);
                        return resp;
                    }*/

            

  }
 
  @Override
  protected void process(List<String> chunks){
    for(String message : chunks){
      informable.messageChanged(message);
    }
  }
}


interface Informable {
    void messageChanged(String message);
}