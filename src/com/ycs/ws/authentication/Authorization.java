package com.ycs.ws.authentication;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService
public class Authorization {
	
private static Logger logger = Logger.getLogger(Authorization.class);
	
	/**
	 * @return TXNCODE or ScreenName to which the person has access
	 */
	@WebMethod
	public String authorize(){
		logger.info("Authorization");
		
		return "success";
	}
}
