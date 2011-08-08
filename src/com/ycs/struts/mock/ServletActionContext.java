package com.ycs.struts.mock;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ServletActionContext extends ActionContext{
	private static ServletActionContext context;
	
	private ServletActionContext(){}
	public static void create(){
		context = new ServletActionContext();
		createContext((ActionContext)context);
	}
	public static ServletActionContext getServletContext() {
		return (ServletActionContext) context;
	}

	public String getRealPath(String string) {
		String path = "D:/workspace/BEIadt/src";
		if(string.contains("classes")){
		String parts[] = string.split("classes");
			if(parts.length ==1 )return path;
			else return path+parts[1];
		}
		else {
			throw new RuntimeException("WebContent relative path not implemented outside struts.mock");
		}
	}
	
	public static void destroy(){
		ActionContext.destroy();
	}
	
	
}
