package com.ycs.programsetup.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Element;

import com.ycs.fe.businesslogic.EzlinkBL;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;
import com.ycs.fe.exception.FrontendException;
import com.ycs.fe.util.ScreenMapRepo;

public class LoginValidationBL extends EzlinkBL{

	@Override
	public ResultDTO executeCommand(String screenName, String querynodeXpath, JSONObject jsonRecord, InputDTO inputDTO, ResultDTO resultDTO) {
		ResultDTO resDTO = new ResultDTO();
		try {
			Element rootXml = ScreenMapRepo.findMapXMLRoot(screenName);
			Element processorElm = (Element) rootXml.selectSingleNode("/root/screen/bl/" + querynodeXpath + " ");
			String methodName = processorElm.attributeValue("method");

			if ("loginValidation".equals(methodName)) {
				 loginValidation(resDTO);
			} else {
				System.out.println("ProgramSetup BL methodname is not defined");
				List<String> errors = new ArrayList<String>();
				errors.add("business.logic.unimplemented");
				resDTO.setMessages(errors);
			}

		} catch (FrontendException e) {
			resDTO.addError("error.accessxml");
		}
		return resDTO;
	}

	public void loginValidation(ResultDTO resDTO){
		 String userName = "";
		 String password = "";
		 System.out.println("in Login BL"+resDTO);
		 HashMap<String, Object> map = new HashMap<String, Object>();
		 
		 resDTO.setData(map );
		 resDTO.setResult("loginPage");
		 Map <String,String> sessionData = new HashMap<String, String>();
		 sessionData.put("NAME","Merchant 1");
		 sessionData.put("ROLE","Merchant");
		 sessionData.put("MENU","Merchant Menu");
		 
		 resDTO.setSessionvars(sessionData);
	}
}
