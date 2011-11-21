package com.ycs.ezlink.exception;

public class EmptyAddressException extends Exception {
 
	private static final long serialVersionUID = 1L;
	private String message;
	
	public EmptyAddressException(String message) {
		super();
		this.message = message;
	}

	public String toString(){
		return "Exception: Recipient address is empty for email: "+ message;
	}
}
