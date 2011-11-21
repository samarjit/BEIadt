package com.ycs.ezlink.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import com.ycs.crypto.CmdEncrypter;
import com.ycs.crypto.exception.YCSCryptoException;
import com.ycs.fe.util.CompoundResource;


/**
 * java 
 * @author Samarjit
 *
 */
public class GenerateRadomValForMobileDataLoad {
	
	public String DBURL;
	public String DBUSER;
	public String DBPASSWORD;
	public String DRIVERNAME;
	public String contextPath;
	Properties prop = new Properties();
	private String str;
	private String filename;
	private String sql;

	public GenerateRadomValForMobileDataLoad(String args) {
		ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
		
//			File file = new File(CompoundResource.getString(rb, "ezlinkupload.properties"));// ."/App1/ezltrp/sg_src/EzLinkUpload/config/config.properties");
//			FileInputStream fos = new FileInputStream(file);
//			Properties prop = new Properties();
//			prop.load(fos);
//	InputStream is = this.getClass().getResourceAsStream("$EZ_SRC/config.properties");  
		 //	Properties prop = new Properties();  
		DBURL = rb.getString("DBURL");
		DBUSER = rb.getString("DBUSER");
		DBPASSWORD = rb.getString("DBPASSWORD");
		DRIVERNAME = rb.getString("DRIVERNAME");
		contextPath = rb.getString("dataload.context");
		filename = CompoundResource.getString(rb,"dataload.filename");
		
		if(args.equals("RELOAD"))
		    sql = rb.getString("dataload.reloadsql");
		else
			sql = rb.getString("dataload.mobileloadsql");
		
	}

	public Connection getConnection(){
		Connection con = null;
		try {
			Class.forName(DRIVERNAME);
			 con = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void generateFile() throws InvalidKeyException, YCSCryptoException {
		Connection con = null;
		String nric;
		try {
			File file = new File(filename);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream  dos = new DataOutputStream(fos);
			con = getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				nric = rs.getString("nric");
				String cin = "trsCin" + randGenerator(5, null);
				String systemUserId = "trsUsr" + randGenerator(8, null);
				String pass = randGenerator(8, null);
				String passHashed = passwordHashing(pass);
//				System.out.println(nric + "~~~~" + cin + "~~~~" + systemUserId + "~~~~" + pass + "~~~~" + passHashed);
				CmdEncrypter cmdEncr = new CmdEncrypter();
				String encr = cmdEncr.encrypt(nric,pass);
				String url = contextPath + "param="+encr;
				str = nric+","+cin + "," + systemUserId + "," + pass + "," + passHashed+","+url+"\n";
			    dos.write(str.getBytes());		
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null && !con.isClosed()) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	

	public static String randGenerator(int range, String characters) {
		// you can simply pass the range bellow also, range is how many
		// charecter you want. If only uppercase letter are required you may
		// just delete the lowercase letters.

		String randString = "";
		if (range <= 0) {
			return randString;
		}
		StringBuffer sb = new StringBuffer();
		String block = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFIJKLMNOPQRSTUVWXYZ";
		if (characters != null)
			block = characters;
		// sb.append(block).append(block.toUpperCase()).append("0123456789");
		// block = sb.toString();
		// sb = new StringBuffer();
		Random random = new Random();
		try {
			for (int i = 0; i < range; i++) {
				sb.append(Character.toString(block.charAt(random.nextInt(block.length() - 1))));
			}
			randString = sb.toString();
		} catch (ArrayIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		} catch (Exception e) {
		}
		return randString;
	}

	public static String passwordHashing(String passClear) {
		byte[] hash = null;
		MessageDigest digest;
		StringBuffer hexString = new StringBuffer();
		try {
			digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(passClear.getBytes());
			hash = digest.digest();
			//
			// Create Hex String

			for (int i = 0; i < hash.length; i++)
				hexString.append(Integer.toHexString(0xFF & hash[i]));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
		// return new String(hash);
	}

	public static void main(String[] args) throws InvalidKeyException, YCSCryptoException {
		System.out.println("******************Started***************");
		GenerateRadomValForMobileDataLoad gg = new GenerateRadomValForMobileDataLoad(args[0]);
		gg.generateFile();
		System.out.println("******************completed Suceessfully ***************");
		
//		CmdEncrypter cmdEncr = new CmdEncrypter();
//		try {
//			String encr = cmdEncr.encrypt("F1234567Q","123");
//			System.out.println("ENcry :"+encr);
//			String decr = cmdEncr.decrypt(encr);
//			System.out.println("Decry :"+decr);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}
}
