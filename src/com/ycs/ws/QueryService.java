package com.ycs.ws;

import java.util.EnumSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.ycs.fe.crud.CommandProcessor;
import com.ycs.fe.crud.SelectOnLoad;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;
import com.ycs.struts.mock.ActionContext;
import com.ycs.struts.mock.ServletActionContext;
import com.ycs.struts.mock.ValueStack;

 
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
		Map<String, String> sessionvars =   inpDTO.getData().getJSONObject("sessionvars");
		System.out.println(inpDTO.getData().toString());
		for (Entry<String, String> itr : sessionvars.entrySet()) {
			ServletActionContext.getContext().getSession().put(itr.getKey(), itr.getValue());
		}
		
		sl.selectOnLoad(screenName, jsonsubmitdata1 ); 
		
		tmpResDTO = (String) ActionContext.getContext().getValueStack().getContext().get("resDTO");
		
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
		Map<String, String> sessionvars =   inpDTO.getData().getJSONObject("sessionvars");
		System.out.println(inpDTO.getData().toString());
		for (Entry<String, String> itr : sessionvars.entrySet()) {
			ServletActionContext.getContext().getSession().put(itr.getKey(), itr.getValue());
		}

		CommandProcessor processor = new CommandProcessor();
		ResultDTO resultDTO = processor.commandProcessor(submitdata, screenName);
		
		tmpResDTO = JSONObject.fromObject(resultDTO).toString();
		
		ServletActionContext.destroy();
		
		return tmpResDTO ;
	}
}
