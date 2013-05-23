/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

/**
 *
 * @author xkalibaer
 */
import javax.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.mysql.*;

public final class Mysql {
	
	
	Connection con = null;  
	Statement stmt = null;
	ResultSet rs = null;
	int num_rows;
	
	public Connection mysql(String host, String user, String pass, String db, int port) throws ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://localhost/" + db,user,pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.con;
	}
	
	public String test()
	{
		return "Lets test";
	}
	public Connection mysqlConnectToDb(String host, String user, String pass, String db, int port)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://"+ host + "/" + db,user,pass);
			//System.out.println("Confirm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.con;
	}
	
	public Connection mysqlConnectToDb(String host, String user, String pass, String db)
	{
		//System.out.println("jdbc:mysql://"+ host + "/" + db);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://"+ host + "/" + db,user,pass);
			//System.out.println("Confirm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.con;
	}
        
        
        public Connection mysqlConnectToDb(String host, String user, String pass)
	{
		//System.out.println("jdbc:mysql://"+ host);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
                        new UtilUse().Log(e);
			e.printStackTrace();
		}
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://"+ host,user,pass);
			//System.out.println("Confirm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
                        new UtilUse().Log(e);
			e.printStackTrace();
		}
		return this.con;
	}
	
	public void createDB(String dbName) throws SQLException
	{
		//create database. If it already exists it should throw an error
		String str="CREATE DATABASE `" + dbName + "`";
		stmt = this.con.createStatement();
		rs = stmt.executeQuery(str);

	}

	public void createDBUser(String username, String host, String password, String dbName) throws SQLException
	{
		String str="GRANT ALL PRIVILEGES ON " + dbName + " . * TO '"+ username + "'@'" + host + "' IDENTIFIED BY '" + password + "' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0";
		stmt = con.createStatement();
		rs = stmt.executeQuery(str);

	}
	
	
	
	
	public void close() throws SQLException
	{
		if(rs != null) {
			rs.close();
			rs = null;
		}
		if(stmt != null) {
			stmt.close();
			stmt = null;
		}
		if(con != null) {
			con.close();
			con = null;
		}
	
	}
	
	public ResultSet query(PreparedStatement pr) throws SQLException
	{
		//echo str;
		//System.out.println("PreparedStatement test");
		ParameterMetaData md=pr.getParameterMetaData();
		//System.out.println(md.getParameterCount());
		ResultSet resultSet = pr.executeQuery();
                
		return resultSet;
		//for(int count=0; count<array)
		/*sql = mysql_query(str,this.conn);
		
		if(rs.rowUpdated())
		{
			this.num_rows = rs.rowUpdated();
		}
		
		
		this.affected_rows = mysql_affected_rows(this.conn);
		this.error = mysql_error(this.conn);
		if(mysql_insert_id(this.conn))
			this.insert_id = mysql_insert_id(this.conn);
		if(!sql)
			return false;
		//return new mysql_result(sql,this.conn);
		return sql;*/
	}
	
	/*public fetch_assoc_mine(sql)
	{
		return mysql_fetch_assoc(sql);
	}

	
	
	public get_mysql_result(sql)
	{
		return new mysql_result(sql,this.conn);
	}
	
	public fetch_assoc()
	{
		return mysql_fetch_assoc(this.conn);
	}
	
	public escape_string(str)
	{
		return mysql_escape_string(str);
	}
	
	public real_escape_string(str)
	{
		return mysql_real_escape_string(str);
	}
	
	
	public mysql_table_exists(db, tableName)
	{
	  tables = array();
	  tablesResult = mysql_query("SHOW TABLES FROM db;", this.conn);
	  while (row = mysql_fetch_row(tablesResult)) tables[] = row[0];
	   if (!tablesResult) {
	   }
	  return(in_array(tableName, tables));
	}
	
	public db_name_exists(db)
	{
		db_list = mysql_list_dbs(this.conn);
		i = 0;
		check=0;
		cnt = mysql_num_rows(db_list);
		while (i < cnt) {
		   name=mysql_db_name(db_list, i);
		   if(name==db)
		   {
		   		check=1;
		   }
		   i++;
		}
		
		if(check==0)
		{
			return FALSE;
		}
		else
		{
			return TRUE;
		}


	}*/
}
