package com.opensymphony.xwork2.util;

import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

public class ValueStack {
	Map<String, Object> root = new HashMap<String, Object>();
	Map<String, Object> context = new HashMap<String, Object>();
	public Map<String, Object> getContext() {
		return context;
	}

	public void set(String key, Object value) {
		context.put(key, value);
	}

	public String findString(String string) throws OgnlException   {
		try {
			return (String) Ognl.getValue(string, context, root);
		} catch (OgnlException e) {
			throw new  OgnlException("expression ["+string+"]");
		}

	}
	
	public static void main(String []args){
		Map<String, Object> context = new HashMap<String, Object>();
		Map<String, Object> root = new HashMap<String, Object>();
//		String expression = "#inputDTO.data";
		String expression = "#r1oot1";
		HashMap<String,String> temphm = new HashMap<String, String>();
		temphm.put("data", "data1");
		context.put("r1oot1", temphm);
		
//		root.put("inputDTO", "rootvalue");
		try {
//			String val =   (String) Ognl.getValue(expression, context, root);
			System.out.println("value = "+Ognl.getValue(expression, context, root));
		} catch (OgnlException e) {
			e.printStackTrace();
		}
	}
}
