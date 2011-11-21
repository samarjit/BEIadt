package com.ycs.ezlink.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.CharSet;

public class PasswordHashing {
	public static String passwordHashing(String passClear) {
		byte[] hash = null;
		MessageDigest digest;
		StringBuffer hexString = new StringBuffer();
		try {
//			digest = java.security.MessageDigest.getInstance("MD5");
//			digest.update(passClear.getBytes("UTF-8"));
//			hash = digest.digest();
//			System.out.println("hash md5" + new String(hash ,"UTF-8")+ "   "+hash.length);
//
//			// Create Hex String
//		
//			for (int i = 0; i < hash.length; i++)
//				hexString.append(String.format("%02x", hash[i]));
//			
//			 
			
			
			hexString.append(DigestUtils.md5Hex(passClear.getBytes("UTF-8")));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
		//return new String(hash);
	}

	public static void main(String[] args) {

		System.out.println("Password : " + passwordHashing("samarjit"));
	}
}
