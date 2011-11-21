
The following program is used to generate a key. Use this program to generate a key.

1. Copy this program
2. Change the paths which you find in the program
2. Run the program

/**
 * Project Name 	    :  YCSUtil
 * Program Name			:  KeyGen.java
 * Author Name			:  Anvar
 * Program Description	:  
 */

package com.ycs.crypto.utils;

import java.io.FileOutputStream;
import java.security.KeyStore;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * @author Anvar
 *
 */
public class KeyGen {

	private static final Log logger = LogFactory.getLog(KeyGen.class);

	/*
	 * public static void main(String x[]) { createKeyStore("C:\\Documents and
	 * Settings\\ycs\\Desktop","Test.jks" ,"keyStorePassword" , "keyAlias"
	 * ,"keyPassword"); }
	 */

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

			FileOutputStream out = new FileOutputStream(location + "\\"
					+ keyStoreName);
			keystore.store(out, keystorePassword.toCharArray());
			out.close();
			logger.debug("Key stored successfully");
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
}
==============================================================================================================
The below programis used to test the encryption and decryption.
Replace "someXXXX" with the required values

package com.ycs.crypto.utils;

public class TestCrypto
{
	public static void main(String[] args)
	{
		try
		{
			String clearText="Hello World! My name is Anthony Gonsalves";
			String encrypted=null;
			CryptoTool.setKeystorePath("somepath");			
			CryptoTool.setKeyAllias("somealias");
			CryptoTool.setKeyPassword("somepassword");
			CryptoTool.setKeystorePassword("somekeystorepassword");
			CryptoTool.setAlgorithm("somealogrithm");
			CryptoTool.cryptoTool();
			String cipherText=null;
			String decrypted=null;
			encrypted=CryptoTool.encrypt(clearText);
			cipherText=encrypted;
			System.out.println("Encrypted "+clearText+" To "+encrypted);			
			decrypted=CryptoTool.decrypt(cipherText);
			System.out.println("Decrypted "+cipherText+" To "+decrypted);
		}
		catch(Exception e)
		{
			System.out.println("Exception "+e);
			e.printStackTrace();
		}
	}
}
