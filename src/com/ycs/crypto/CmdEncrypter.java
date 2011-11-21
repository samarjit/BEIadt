package com.ycs.crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.util.ResourceBundle;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ycs.crypto.exception.YCSCryptoException;
import com.ycs.crypto.utils.CryptoTool;
import com.ycs.ezlink.util.EzLinkConstant;
import com.ycs.fe.util.CompoundResource;

/**

unix:
set EzBE=/App1/ezltrp/sg_src/EzLinkBE
set JAVA_JAR_PATH=$EzBE/target/dependency


java -classpath ${JAVA_JAR_PATH}/ojdbc-6.jar:${JAVA_JAR_PATH}/commons-logging-1.1.
1.jar:${JAVA_JAR_PATH}/freemarker-2.3.16.jar:${JAVA_JAR_PATH}/log4j-1.2.15.jar:$Ez
BE/bin: com.ycs.ezlink.util.$1

windows:
set EzBE=C:/Eclipse/workspace2/EzLinkBE
set JAVA_JAR_PATH=%EzBE%/target/dependency

oktech-profiler
---------------
java -classpath %JAVA_JAR_PATH%/ojdbc-6.jar;%JAVA_JAR_PATH%/commons-logging-1.1.1.jar;%JAVA_JAR_PATH%/freemarker-2.3.16.jar;%JAVA_JAR_PATH%/log4j-1.2.15.jar;%EzBE%/bin;  -javaagent:C:\Users\Samarjit\Downloads\java_profiler\performance_meas\oktech-profiler-1.1\hu.oktech.profiler-runtime.jar=instr.class=+com.ycs.crypto.CmdEncrypter com.ycs.crypto.CmdEncrypter

jip-profiler
------------
java -classpath %JAVA_JAR_PATH%/ojdbc-6.jar;%JAVA_JAR_PATH%/commons-logging-1.1.1.jar;%JAVA_JAR_PATH%/freemarker-2.3.16.jar;%JAVA_JAR_PATH%/log4j-1.2.15.jar;%EzBE%/bin; -javaagent:C:\Users\Samarjit\Downloads\java_profiler\performance_meas\jip-src-1.2\jip-src-1.2\profile\profile.jar    com.ycs.crypto.CmdEncrypter


 * @author Samarjit
 *
 */
public class CmdEncrypter {
	private static final Log logger = LogFactory.getLog(CmdEncrypter.class);

	

	public CmdEncrypter() {
		super();
		ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
		String KEY_STORE_PATH = CompoundResource.getString(rb, "keystore.path");
		CryptoTool.setKeystorePath(KEY_STORE_PATH+"/Test.jks");			
		CryptoTool.setKeyAlias("keyAlias");
		CryptoTool.setKeyPassword("keyPassword");
		CryptoTool.setKeystorePassword("keyStorePassword");
		CryptoTool.setAlgorithm("AES");
		CryptoTool.cryptoTool();
		String cipherText=null;
		String decrypted=null;
	}

	public static void createKeyStore(String location, String keyStoreName,
			String keyStorePassword, String keyAlias, String keyPassword) {
		try {
			String keystorePassword = keyStorePassword;
			KeyStore keystore = KeyStore.getInstance("JCEKS");
			keystore.load(null, keystorePassword.toCharArray());
			logger.debug("The default KS loaded.. " + keystore);

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKey skey = kgen.generateKey();

			keystore.setKeyEntry(keyAlias, skey, keyPassword.toCharArray(),
					null);

			FileOutputStream out = new FileOutputStream(location + "/"
					+ keyStoreName);
			keystore.store(out, keystorePassword.toCharArray());
			out.close();
			logger.debug("Key stored successfully in "+location+"/"+keyStoreName);
		} catch (Exception e) {
			logger.debug("Exception " + e);
		}
	}

	/*
	public static void createKeyStoreWithKeyPair() {
		try {
			String keystorePassword = "issuer1789&*(";
			KeyStore keystore = KeyStore.getInstance("JCEKS");
			keystore.load(null, keystorePassword.toCharArray());
			logger.debug("The default KS loaded.. " + keystore);

			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(512); // 512 is the keysize.

			// keystore.setKeyEntry("EVDKEY", kp,
			// keystorePassword.toCharArray(), null);

			FileOutputStream out = new FileOutputStream(
					"c:\\jksdir\\generated\\Anvar.jks");
			keystore.store(out, keystorePassword.toCharArray());
			out.close();
			logger.debug("Key stored successfully");
		} catch (Exception e) {
			logger.debug("Exception " + e);
		}
	}
	*/
	
	
	
	public static void main1(String x[]) {
		ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
		String KEY_STORE_PATH = CompoundResource.getString(rb, "keystore.path");
		
		CmdEncrypter.createKeyStore(KEY_STORE_PATH ,"Test.jks","keyStorePassword" , "keyAlias" ,"keyPassword"); 
	}
	
	public static String encrypt(String nric, String password) throws InvalidKeyException, UnsupportedEncodingException, YCSCryptoException{
	  	String param = "{'nric':'"+nric+"','password':'"+password+"'}";
	  	return URLEncoder.encode(CryptoTool.encrypt(param),"UTF-8");
	}
	
	public static String decrypt(String encryptedText) throws InvalidKeyException, UnsupportedEncodingException, YCSCryptoException{
		//return CryptoTool.decrypt(URLDecoder.decode(encryptedText,"UTF-8"));
		System.out.println(URLDecoder.decode(encryptedText,"UTF-8"));
		System.out.println(encryptedText);
		return CryptoTool.decrypt(encryptedText);
	}
	
	public static void main(String[] args)
	{
		try
		{
			ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
			String KEY_STORE_PATH = CompoundResource.getString(rb, "keystore.path");
			String clearText="Hello World! My name is Anthony Gonsalves";
			String encrypted=null;
			File f = new File(KEY_STORE_PATH);
			if(!f.exists()){
				System.err.println("Error: Crypto Key path not found in "+f.getAbsolutePath());
			}else{
				System.out.println("Note: using keystore:"+f.getAbsolutePath());
			}
			f = new File (KEY_STORE_PATH+"/Test.jks");
			if(!f.exists())System.err.println("Error: Crypto Key not found while searching for "+f.getAbsolutePath());
			
			if(args.length == 0){
				System.out.println("Usage:com.ycs.crypto.CmdEncrypter createkey \r\n encrypt nric password \r\n decrypt encryptedText");
				return;
			}else if(args[0].contains("createkey")){
				CmdEncrypter.createKeyStore(KEY_STORE_PATH ,"Test.jks","keyStorePassword" , "keyAlias" ,"keyPassword"); 
				return;
			}
			
			CryptoTool.setKeystorePath(KEY_STORE_PATH+"/Test.jks");			
			CryptoTool.setKeyAlias("keyAlias");
			CryptoTool.setKeyPassword("keyPassword");
			CryptoTool.setKeystorePassword("keyStorePassword");
			CryptoTool.setAlgorithm("AES");
			CryptoTool.cryptoTool();
			String cipherText=null;
			String decrypted=null;
			encrypted=CryptoTool.encrypt(clearText);
			cipherText=encrypted;
			System.out.println("Encrypted "+clearText+" To "+encrypted);			
			decrypted=CryptoTool.decrypt(cipherText);
			System.out.println("Decrypted "+cipherText+" To "+decrypted);
			if(args.length >0){
				
				if(args[0].contains("encrypt") ){
					if(args.length != 3){System.out.println("Usage: encrypt nric password"); return ;}
					FileWriter fos = new FileWriter("tempEncrText.txt");
					fos.write( CmdEncrypter.encrypt(args[1],args[2]));
					fos.close();
				}
				if(args[0].contains("decrypt") ){
					if(args.length != 2){System.out.println("Usage: decrypt encryptedText"); return;}
					FileWriter fos = new FileWriter("tempEncrText.txt");
					fos.write( CmdEncrypter.decrypt(args[1]));
					fos.close();
				}
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Exception "+e);
			e.printStackTrace();
		}
	}
}
