package com.ycs.ezlink.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
	public static String getMD5(String strData){
		byte[] hash = null;
		MessageDigest digest;
		String hexString = null;
		try {
			hexString = 	DigestUtils.md5Hex(strData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString;
	}
}
