package com.ycs.ws.authentication;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService
public class Authentication {
	
	private static Logger logger = Logger.getLogger(Authentication.class);
	
	/**
	 * @return "success" or "fail"
	 */
	@WebMethod
	public String authenticate(){
		logger.info("Authentication");
		
		return "success";
	}
}
