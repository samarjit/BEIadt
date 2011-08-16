package com.ycs.fe.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Element;

import com.opensymphony.xwork2.ActionInvocation;
import com.ycs.exception.FrontendException;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;
import com.ycs.fe.util.ScreenMapRepo;

public class ProgramSetupBL implements BaseBL {

	@Override
	public HashMap preJsRPCListerner(ActionInvocation invocation) {
		System.out.println("pre JsRPC BL");
		return null;
	}

	@Override
	public HashMap postJsRPCListerner(ActionInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap preCrudListener(ActionInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap postCrudListener(ActionInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap preWorkflowListener(ActionInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap postWorkflowListener(ActionInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultDTO executeCommand(String screenName, String querynodeXpath,
			JSONObject jsonRecord, InputDTO inputDTO, ResultDTO resultDTO) {
		ResultDTO resDTO = new ResultDTO();
		try{
		Element rootXml = ScreenMapRepo.findMapXMLRoot(screenName);
		Element processorElm = (Element) rootXml.selectSingleNode("/root/screen/bl/"+querynodeXpath+" ");
		String methodName = processorElm.attributeValue("method");
		
		if("testMethod1".equals(methodName)){
			testMethod1();
			System.out.println(ServletActionContext.getContext().getSession());
			System.out.println("Session var retreived:"+ServletActionContext.getContext().getSession().get("mysessionkey"));
		}else{
			System.out.println("ProgramSetup BL methodname is not defined");
		}
	
		List<String> errors = new ArrayList<String>();
		errors.add("business.logic.unimplemented");
		resDTO.setMessages(errors );
		}catch(FrontendException e){
			resDTO.addError("error.accessxml");	
		}
		return resDTO;
	}

	public void testMethod1(){
		System.out.println("test method 1 executing ... ");
	}
}
