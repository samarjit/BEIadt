package com.ycs.fe.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

import com.ycs.fe.dto.PrepstmtDTO;
import com.ycs.fe.dto.PrepstmtDTOArray;


 
/** 
 * This class is used to connect to the database and execute queries. 
 * 
 */ 
public class DBConnector {  
private static String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
private static String DBURL = "jdbc:oracle:thin:@localhost:1521:XE";
private static String DBUSER = "test";
private static String DBPASSWORD = "test";
private boolean isRuninServerContext;





public DBConnector() {
	Properties prop = new Properties();
	try {
		 ClassLoader loader = this.getClass().getClassLoader();
		 prop.load(loader.getResourceAsStream("path_config.properties"));
		 
		 DBURL = prop.getProperty("DBURL");
		 DBUSER = prop.getProperty("DBUSER");
		 DBPASSWORD = prop.getProperty("DBPASSWORD");
		 DRIVERNAME = prop.getProperty("DRIVERNAME");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
private void debug(int priority, String s){ 
        if(priority>0){ 
                System.out.println("DBConnecctor:"+s); 
        } 
} 
private void debug(int priority, String s, Throwable e){ 
    if(priority>0){ 
            System.out.println("DBConnecctor:"+s); 
    } 
}
/** 
 * This function is used to return a connection with the database. 
 *  
 */ 
public Connection getConnection() 
{ 
          
         
        Connection conn = null; 
        try 
    { 
        String driverName = DRIVERNAME;// "oracle.jdbc.driver.OracleDriver"; 
       // String url = "jdbc:mysql://localhost:3306/ams"; 
		String url = DBURL;// "jdbc:oracle:thin:@127.0.0.1:1521:XE";
		String userName = DBUSER;
		String password = DBPASSWORD;// "test";

		Context initContext = null;
	 
		initContext = new InitialContext();
		 
			 
		Context envContext = null;
		try {
			envContext = (Context) initContext.lookup("java:/comp/env");
		} catch (Exception e) {
			debug(3,"Initial context Exception",e);
		}
			 
		boolean fallaback = false;
		if (envContext != null) {
			try{
				DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
				conn = ds.getConnection();
				
				if (conn == null || conn.isClosed()) {
					System.err.print("Some thing wrong with connecting with database!");
					throw new Exception("Some thing wrong with connecting with database using connection pool!");
				}
				debug(3, "Database connection Running in tomcat datasource pooled mode");
				isRuninServerContext = true;
			}catch(Exception e){
				fallaback = true;
				debug(3,"Datasource is not configured, Using fallback method of direct connection",e);
			}
		}
		
		if(envContext == null || fallaback ){
		///Running in standalone mode
		 debug(3, "Database connection Running in standalone mode# "+DBURL);
//		 conn = DriverManager.getConnection (url, userName, password);
		 Class.forName (driverName); 
		  
	        conn = DriverManager.getConnection (url, userName, password); 
	        if(conn == null){ 
	                System.err.print("Some thing wrong with connecting with database!"); 
	        } 
	         
		}

		
        
        debug (0, "Database connection established"); 
        //CachedRowSet crs; 
         
    } 
    catch (Exception e) 
    { 
        System.err.println ("Cannot connect to database server:"+e);
        e.printStackTrace();
    } 
     
    return conn; 
} 
 
/** 
 * This functions executes a query 
 * @param qry 
 * @return result 
 * @throws SQLException 
 */ 
public CachedRowSet executeQuery(String qry) throws SQLException{ 
        CachedRowSet crs; 
        Connection conn =null; 
        try { 
                 
                conn = getConnection(); 
                Statement stmt = conn.createStatement();  
                debug(0, qry); 
        ResultSet rs =  stmt.executeQuery(qry); 
         
          
        crs = new OracleCachedRowSet(); 
        crs.populate(rs);  
        rs.close();  
        stmt.close(); 
         
          
        } catch (SQLException e) { 
                debug(5,"Exception:"+qry); 
                e.printStackTrace(); 
                throw e; 
        } 
        finally 
    { 
        if (conn != null) 
        { 
            try 
            { 
                conn.close (); 
                conn =null; 
               debug (0, "Database connection terminated"); 
            } 
            catch (Exception e) { 
            e.printStackTrace();         
            /* ignore close errors */ } 
        } 
    } 
         
        return crs; 
} 
 
/** 
 * This function is used to execute update query. 
 * @param qry 
 * @return 
 * @throws SQLException 
 */ 
public int executeUpdate(String qry) throws SQLException{ 
        Connection conn =null; 
        int retval =0; 
        try { 
                 
                conn = getConnection(); 
                Statement stmt = conn.createStatement();  
                retval  = stmt.executeUpdate(qry); 
        }catch(SQLException  e){ 
                debug(5,"Exception:"+qry); 
                e.printStackTrace();  
                throw e; 
        } 
        finally 
    { 
        if (conn != null) 
        { 
            try 
            { 
                conn.close (); 
                //log ("Database connection terminated"); 
            } 
            catch (Exception e) { /* ignore close errors */ } 
        } 
    } 
        return retval; 
} 
 
/** 
 * Executes prepared statements 
 * @param qry 
 * @param arPrepstmt 
 * @return result 
 * @throws SQLException 
 */ 
public CachedRowSet executePreparedQuery(String qry,PrepstmtDTOArray arPrepstmt) throws SQLException{ 
        CachedRowSet crs = null; 
        Connection conn =null; 
        try { 
                 
                conn = getConnection(); 
                PreparedStatement  stmt = conn.prepareStatement(qry);  
        Iterator itr = arPrepstmt.getArdto().iterator(); 
        int count = 1; 
        while(itr.hasNext()){ 
                PrepstmtDTO pd = (PrepstmtDTO)itr.next(); 
                if(pd.getType() == PrepstmtDTO.DataType.DATE){ 
                        Date newDate = new Date( ( new SimpleDateFormat("DD/MM/yyyy")).parse(pd.getData()).getTime()); 
                        stmt.setDate(count,  newDate); 
                }else if(pd.getType() == PrepstmtDTO.DataType.DOUBLE){ 
                        String in = pd.getData(); 
                        if(in == null || "".equals(in))in = "0.0D"; 
                        stmt.setDouble(count, Double.parseDouble(in));                   
                        }else if(pd.getType() == PrepstmtDTO.DataType.FLOAT){ 
                                String in = pd.getData(); 
                                if(in == null || "".equals(in))in = "0.0f"; 
                                stmt.setFloat(count, Float.parseFloat(in)); 
                        }else if(pd.getType() == PrepstmtDTO.DataType.INT){ 
                                String in = pd.getData(); 
                                if(in == null || "".equals(in))in = "0"; 
                                stmt.setInt(count, Integer.parseInt(in)); 
                        }else if(pd.getType() == PrepstmtDTO.DataType.STRING){ 
                                stmt.setString(count, pd.getData()); 
                        } 
                count ++; 
        } 
                debug(0, qry); 
        ResultSet rs =  stmt.executeQuery(); 
         
          
        crs = new OracleCachedRowSet(); 
        crs.populate(rs);  
        rs.close();  
        stmt.close(); 
         
          
        } catch (SQLException e) { 
                debug(5,"Exception:"+qry); 
                e.printStackTrace(); 
                throw e; 
        } catch (ParseException e) { 
                debug(5,"Exception:"+qry); 
                e.printStackTrace(); 
} 
        finally 
    { 
        if (conn != null) 
        { 
            try 
            { 
                conn.close (); 
                conn =null; 
               debug (0, "Database connection terminated"); 
            } 
            catch (Exception e) { 
            e.printStackTrace();         
            /* ignore close errors */ } 
        } 
    } 
         
        return crs; 
} 
 
/** 
 * Executes prepared update statements. 
 * @param qry 
 * @param arPrepstmt 
 * @return result 
 * @throws SQLException 
 */ 
public int executePreparedUpdate(String qry,PrepstmtDTOArray arPrepstmt) throws SQLException{ 
        Connection conn =null; 
        int retval =0; 
        try { 
                 
                conn = getConnection(); 
                PreparedStatement  stmt = conn.prepareStatement(qry);  
        Iterator itr = arPrepstmt.getArdto().iterator(); 
        int count = 1; 
        while(itr.hasNext()){ 
                PrepstmtDTO pd = (PrepstmtDTO)itr.next(); 
                if(pd.getType() == PrepstmtDTO.DataType.DATE){ 
                        Date newDate = new Date( ( new SimpleDateFormat("DD/MM/yyyy")).parse(pd.getData()).getTime()); 
                        stmt.setDate(count,  newDate); 
                }else if(pd.getType() == PrepstmtDTO.DataType.DOUBLE){ 
                        String in = pd.getData(); 
                        if(in == null || "".equals(in))in = "0.0D"; 
                        stmt.setDouble(count, Double.parseDouble(in));                   
                        }else if(pd.getType() == PrepstmtDTO.DataType.FLOAT){ 
                                String in = pd.getData(); 
                                if(in == null || "".equals(in))in = "0.0f"; 
                                stmt.setFloat(count, Float.parseFloat(in)); 
                        }else if(pd.getType() == PrepstmtDTO.DataType.INT){ 
                                String in = pd.getData(); 
                                if(in == null || "".equals(in))in = "0"; 
                                stmt.setInt(count, Integer.parseInt(in)); 
                        }else if(pd.getType() == PrepstmtDTO.DataType.STRING){ 
                                stmt.setString(count, pd.getData()); 
                        } 
                count ++; 
        } 
          
                retval  = stmt.executeUpdate(); 
        }catch(SQLException  e){ 
                debug(5,"Exception:"+qry); 
                e.printStackTrace();  
                throw e; 
        } catch (ParseException e) { 
                debug(5,"Exception:"+qry); 
                e.printStackTrace(); 
        } 
        finally 
    { 
        if (conn != null) 
        { 
            try 
            { 
                conn.close (); 
                //log ("Database connection terminated"); 
            } 
            catch (Exception e) { /* ignore close errors */ } 
        } 
    } 
        return retval; 
} 




 
        /* 
         * testing  
         */ 
 
        public static void main(String[] args) { 
                try{ 
           
                CachedRowSet crs = new DBConnector().executeQuery("select * from  test2"); 
                while(crs.next()){ 
                System.out.println("ARGUMENT_NAME:"+crs.getString(1)); 
               // System.out.println(",DATA_TYPE:"+crs.getString("DATA_TYPE"));       
                } 
                crs.close(); 
                }catch(SQLException e){ 
                        e.printStackTrace(); 
                } 
        }  
 
} 
