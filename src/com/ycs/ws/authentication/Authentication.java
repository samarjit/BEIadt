package com.ycs.ws.authentication;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ycs.ezlink.dao.AuthenticationDAO;
import com.ycs.fe.exception.BackendException;

@WebService
public class Authentication {
	
	private static Logger logger = Logger.getLogger(Authentication.class);
	
	/**
	 * @return "success" or "fail"
	 */
	@WebMethod
	public String authenticate(String user, String pass){
		logger.info("Authentication");
		boolean res = false;;
		try {
			res = AuthenticationDAO.authenticate(user, pass);
		} catch (BackendException e) {
			e.printStackTrace();
		}
		if(res) 
		return "success";
		else
			return "fail";
	}
}
