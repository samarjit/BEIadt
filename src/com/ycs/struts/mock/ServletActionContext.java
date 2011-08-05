package com.ycs.struts.mock;

public class ServletActionContext extends ActionContext{
	
	public static void create(){
		createContext();
	}
	public static ServletActionContext getServletContext() {
		return (ServletActionContext) context;
	}

	public String getRealPath(String string) {
		String path = "C:/Eclipse/workspace2/BEQueryProcssor/src";
		String parts[] = string.split("classes");
		return path+parts[1];
	}
	
	public static void destroy(){
		destroy();
	}
	
	
}
