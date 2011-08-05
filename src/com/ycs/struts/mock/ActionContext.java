package com.ycs.struts.mock;

import java.util.Map;

public class ActionContext {
	protected static ActionContext context = null;
	private static ValueStack vs;
	private Map<String,String> session;
	
	public static void createContext(){
		context = new ActionContext();
		vs = new ValueStack();
	}
	
	public static ActionContext getContext() {
		return context;
	}

	public ValueStack getValueStack() {
		return vs;
	}

	public Map<String,String> getSession() {
		return session;
	}
	
	public static void destroy(){
		context = null;
	}
 
}
