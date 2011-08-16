package org.apache.struts2;

import java.util.ResourceBundle;

import com.opensymphony.xwork2.ActionContext;

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
		String path = ResourceBundle.getBundle("path_config").getString("context.path");//"D:/workspace/BEIadt/src";
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
