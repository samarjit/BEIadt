package com.ycs.struts.mock;

import java.util.HashMap;
import java.util.Map;

public class ActionContext {
	protected static ActionContext context = null;
	private static ValueStack vs;
	private Map<String,String> session = new HashMap<String, String>();
	
	public static void createContext(ActionContext context){
		ActionContext.context =context;
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
