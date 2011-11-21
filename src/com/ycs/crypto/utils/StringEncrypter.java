package com.ycs.crypto.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

class StringEncrypter {
	private static final Log LOG = LogFactory.getLog(StringEncrypter.class);
	private Cipher ecipher;
	private Cipher dcipher;

	StringEncrypter(SecretKey key, String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
		try {
			this.ecipher = Cipher.getInstance(algorithm);
			this.dcipher = Cipher.getInstance(algorithm);
			this.ecipher.init(1, key);
			this.dcipher.init(2, key);
		} catch (NoSuchPaddingException e) {
			LOG.error("NoSuchPaddingException", e);
			throw e;
		} catch (NoSuchAlgorithmException e) {
			LOG.error("NoSuchAlgorithmException", e);
			throw e;
		} catch (InvalidKeyException e) {
			LOG.error("InvalidKeyException", e);
			throw e;
		}
	}

	StringEncrypter(String passPhrase) {
		byte[] salt = { -87, -101, -56, 50, 86, 52, -29, 3 };

		int iterationCount = 19;
		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			this.ecipher = Cipher.getInstance(key.getAlgorithm());
			this.dcipher = Cipher.getInstance(key.getAlgorithm());

			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			this.ecipher.init(1, key, paramSpec);
			this.dcipher.init(2, key, paramSpec);
		} catch (InvalidAlgorithmParameterException e) {
			LOG.error("InvalidAlgorithmParameterException", e);
		} catch (InvalidKeySpecException e) {
			LOG.error("InvalidKeySpecException", e);
		} catch (NoSuchPaddingException e) {
			LOG.error("NoSuchPaddingException", e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("NoSuchAlgorithmException", e);
		} catch (InvalidKeyException e) {
			LOG.error("InvalidKeyException", e);
		}
	}

	public synchronized String encrypt(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
		try {
			byte[] utf8 = str.getBytes("UTF8");

			byte[] enc = this.ecipher.doFinal(utf8);

			return new BASE64Encoder().encode(enc);
		} catch (BadPaddingException e) {
			throw e;
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public synchronized String decrypt(String str) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, IOException {
		try {
			byte[] dec = new BASE64Decoder().decodeBuffer(str);

			byte[] utf8 = this.dcipher.doFinal(dec);

			return new String(utf8, "UTF8");
		} catch (BadPaddingException e) {
			throw e;
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (UnsupportedEncodingException e) {
			throw e;
		} catch (IOException e) {
		}
		return null;
	}

	public static byte[] HexStringToByteArray(String strHex) {
		System.out.println("[JKSUtils] [encryptPassword] Inside HexStringToByteArray");

		byte[] bytKey = new byte[strHex.length() / 2];
		int y = 0;

		for (int x = 0; x < bytKey.length; x++) {
			String strbyte = strHex.substring(y, y + 2);
			if (strbyte.equals("FF"))
				bytKey[x] = -1;
			else {
				bytKey[x] = (byte) Integer.parseInt(strbyte, 16);
			}
			y += 2;
		}
		return bytKey;
	}

	public static String ByteArrayToHexString(byte[] byteArray) {
		StringBuffer strArray = new StringBuffer();
		strArray.append("");
		for (int x = 0; x < byteArray.length; x++) {
			int b = byteArray[x] & 0xFF;
			if (b < 16) {
				strArray.append("0");
				strArray.append(Integer.toHexString(b).toUpperCase());
			} else {
				strArray.append(Integer.toHexString(b).toUpperCase());
			}
		}
		return strArray.toString();
	}
}