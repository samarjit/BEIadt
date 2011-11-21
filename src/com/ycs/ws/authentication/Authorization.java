package com.ycs.ws.authentication;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ycs.ezlink.dao.AuthenticationDAO;
import com.ycs.fe.exception.BackendException;

@WebService
public class Authorization {
	
private static Logger logger = Logger.getLogger(Authorization.class);
	
	/**
	 * @return TXNCODE or ScreenName to which the person has access
	 */
	@WebMethod
	public String authorize(String user){
		logger.info("Authorization");
		String ret = null;
			AuthenticationDAO authDAO = new AuthenticationDAO();
			try {
				ret = authDAO.getAuthorization(user);
			} catch (BackendException e) {
				logger.error("Authorization retrieval error");
			}
		
		return ret;
	}
}
