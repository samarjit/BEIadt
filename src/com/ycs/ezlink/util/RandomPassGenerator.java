package com.ycs.ezlink.util;

import java.util.Random;

import org.apache.log4j.Logger;

public class RandomPassGenerator {
	private static Logger logger = Logger.getLogger(RandomPassGenerator.class);

	/**
	 * @param range
	 * @param characters can be null then by default it will generate alpha numeric without special characters. If not null then, a set of characters 
	 * should be provided which be randomized to create password.
	 * @return
	 */
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
		if(characters != null)block = characters;
		//sb.append(block).append(block.toUpperCase()).append("0123456789");
		//block = sb.toString();
		//sb = new StringBuffer();
		Random random = new Random();
		try {
			for (int i = 0; i < range; i++) {
				sb.append(Character.toString(block.charAt(random.nextInt(block.length() - 1))));
			}
			randString = sb.toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Exception ramdom OTP generator",e);
		} catch (NumberFormatException e) {
		} catch (Exception e) {
		}
		return randString;
	}
	
	public static void main(String[] args) {
		System.out.println(RandomPassGenerator.randGenerator(8, null));
		
	} 
}