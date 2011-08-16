package com.ycs.ws;

import java.util.Iterator;

import javax.jws.WebMethod;
import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.ycs.exception.FrontendException;
import com.ycs.fe.crud.CommandProcessor;
import com.ycs.fe.crud.SelectOnLoad;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;

 
@WebService
public class QueryService<str> {
	/**
	 * @param screenName
	 * @param querynodeXpath
	 * @param jsonRecord
	 * @param inputDTO
	 * @param resultDTO
	 * @return
	 */
	@WebMethod
	public String selectOnLoad(String screenName, String jsonsubmitdata ){
		ServletActionContext.create();
		String tmpResDTO = "Query Service Error in selectOnLoad";
		
		SelectOnLoad sl = new SelectOnLoad();
		JSONObject jsonsubmitdata1 = JSONObject.fromObject(jsonsubmitdata);

		ServletActionContext.create();
		InputDTO inpDTO = new InputDTO();
		inpDTO.setData(jsonsubmitdata1);
		ServletActionContext.getContext().getValueStack().set("inputDTO", inpDTO);
		JSONObject sessionvars = null;
		if(!inpDTO.getData().isNullObject())sessionvars = inpDTO.getData().getJSONObject("sessionvars");
		System.out.println(inpDTO.getData().toString());
		if(sessionvars!=null && !sessionvars.isNullObject())
		for (Iterator iterator = sessionvars.keys(); iterator.hasNext();) {
			String sessionkey = (String) iterator.next();
			String sessionval =  sessionvars.getString(sessionkey);
			System.out.println("sessionvars getValue :"+sessionval);
			ServletActionContext.getContext().getSession().put(sessionkey, sessionval);
		}
		
		try {
			sl.selectOnLoad(screenName, jsonsubmitdata1 );
			tmpResDTO = (String) ActionContext.getContext().getValueStack().getContext().get("resDTO");
		} catch (FrontendException e) {
			e.printStackTrace();
			tmpResDTO = "";
		} 
		
		
		ServletActionContext.destroy();
		
		return tmpResDTO ;
	}
	
	@WebMethod
	public String remoteCommandProcessor(String submitdataObj, String screenName) throws Exception{
		String tmpResDTO = "Query Service Error in remoteCommandProcessor";
		JSONObject submitdata =  JSONObject.fromObject(submitdataObj);

		ServletActionContext.create();
		InputDTO inpDTO = new InputDTO();
		inpDTO.setData(submitdata);
		ServletActionContext.getContext().getValueStack().set("inputDTO", inpDTO);
		JSONObject sessionvars =   inpDTO.getData().getJSONObject("sessionvars");
		System.out.println("InputDTO.getData() is "+inpDTO.getData().toString());
		if(sessionvars!=null && !sessionvars.isNullObject())
		for (Iterator iterator = sessionvars.keys(); iterator.hasNext();) {
			String sessionkey = (String) iterator.next();
			String sessionval =  sessionvars.getString(sessionkey);
			System.out.println("sessionvars getValue :"+sessionval);
			ServletActionContext.getContext().getSession().put(sessionkey, sessionval);
		}

		CommandProcessor processor = new CommandProcessor();
		ResultDTO resultDTO = processor.commandProcessor(submitdata, screenName);
		
		tmpResDTO = JSONObject.fromObject(resultDTO).toString();
		
		ServletActionContext.destroy();
		
		return tmpResDTO ;
	}
}
