package com.ycs.ezlink.util;

import java.util.Calendar;
import java.util.Date;

public class CardNoFormatter {
	public static String format(String  cardNo) {
		if(cardNo == null || cardNo.length() < 16)return cardNo; 
		String part1 = cardNo.substring(0, 4); 
		String part2 = cardNo.substring(4, 8); 
		String part3 = cardNo.substring(8, 12); 
		String part4 = cardNo.substring(12, 16);
		return String.format("%s %s %s %s", part1, part2, part3, part4);
	}
}
