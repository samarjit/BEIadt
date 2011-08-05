package com.ycs.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.ycs.be.crud.CommandProcessor;
import com.ycs.be.crud.SelectOnLoad;
import com.ycs.be.dto.InputDTO;
import com.ycs.be.dto.ResultDTO;
import com.ycs.struts.mock.ActionContext;
import com.ycs.struts.mock.ValueStack;

 
@WebService
public class QueryService {
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
		ActionContext.createContext();
		String tmpResDTO = "Query Service Error in selectOnLoad";
		
		SelectOnLoad sl = new SelectOnLoad();
		JSONObject jsonsubmitdata1 = JSONObject.fromObject(jsonsubmitdata);
		sl.selectOnLoad(screenName, jsonsubmitdata1 ); 
		
		tmpResDTO = (String) ActionContext.getContext().getValueStack().getContext().get("resDTO");
		
		ActionContext.destroy();
		
		return tmpResDTO ;
	}
	
	@WebMethod
	public String remoteCommandProcessor(String submitdataObj, String screenName){
		String tmpResDTO = "Query Service Error in remoteCommandProcessor";

		ActionContext.createContext();
		CommandProcessor processor = new CommandProcessor();
		JSON submitdata =  JSONObject.fromObject(submitdataObj);
		InputDTO inputDTO = new InputDTO();
		inputDTO.setData((JSONObject) submitdata);
		ResultDTO resultDTO = processor.commandProcessor(submitdata, screenName, inputDTO);
		
		tmpResDTO = JSONObject.fromObject(resultDTO).toString();
		
		ActionContext.destroy();
		
		return tmpResDTO ;
	}
}
