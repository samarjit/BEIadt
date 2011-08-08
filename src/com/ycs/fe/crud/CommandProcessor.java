package com.ycs.fe.crud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.ycs.fe.commandprocessor.BaseCommandProcessor;
import com.ycs.fe.commandprocessor.CommandProcessorResolver;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;
import com.ycs.fe.util.Constants;
import com.ycs.fe.util.ScreenMapRepo;
import com.ycs.struts.mock.ServletActionContext;
import com.ycs.ws.beclient.QueryService;
import com.ycs.ws.beclient.QueryServiceService;

public class CommandProcessor {

	private static Logger logger  = Logger.getLogger(CommandProcessor.class);

	/**
	 * command="jrpcCmd1" should be present in each record see in submitdata data structure
	 * 
	 * submitdata={"form1":[{"row":0,"programname":"LOYCARD","txtnewprogname":"LOYCARD","txtprogramdesc":"Loyalty Card Program",
	 * "issuername":"HSBC Bank","countryofissue":"SINGAPORE","txtstatus":"Modify",command:"jrpcCmd1"},
	 * {"row":1,"programname":"TRACARD","txtnewprogname":"TRACARD","txtprogramdesc":"Travel Card Program","issuername":"HSBC Bank","countryofissue":"SINGAPORE",
	 * "txtstatus":"Modify",
	 * command:"jrpcCmd1"}],
	 * “txnrec”:{single:””,multiple:[{aaa:’’},{aaa:’’}]}, bulkcmd:''}
	 * 
	 * default bulk process is true
	 * 
	 * if bulkcmd is present and command will be ignored and the whole request will be processed only once
	 * else the request will be treated as individual records with its own command
	 * 
	 * @param command
	 * @param submitdataObj
	 * @param screenName 
	 * @param inputDTO 
	 * @return
	 */
	public ResultDTO commandProcessor( JSONObject submitdataObj, String screenName){
//		JsrpcPojo rpc = new JsrpcPojo();
		
		ResultDTO resDTO = null;
		try{
		if(Constants.APP_LAYER == Constants.FRONTEND){	
			Element rootXml = ScreenMapRepo.findMapXMLRoot(screenName);
			Node sessionVar = rootXml.selectSingleNode("/root/screen/sessionvars");
			String strSessionVar = sessionVar.getText();
			Map<String, String> sessionMap = new HashMap<String,String>();
			if(strSessionVar != null || !"".equals(strSessionVar)){
				String[] arSessionVar = strSessionVar.split(",");
				if(arSessionVar.length >0){
					for (String sessVariable : arSessionVar) {
						String[] sessionField = sessVariable.split("|");
						String sessionData = "";
						if(sessionField.length >1){
							//datatype is defined and it is required
							sessionData = ServletActionContext.getContext().getSession().get(sessionField[0]);
							if(sessionField[1].equals("INT")){
								sessionData.matches("0-9");
								

							}
						}
						sessionMap.put(sessionField[0], sessionData);
					}
				}
			}
			((JSONObject) submitdataObj).put("sessionvars",sessionMap);
			
		}
		InputDTO inputDTO = new InputDTO();
		inputDTO.setData((JSONObject) submitdataObj);
		
		if(Constants.CMD_PROCESSOR == Constants.APP_LAYER){
			
			Element rootXml = ScreenMapRepo.findMapXMLRoot(screenName);
			
		    @SuppressWarnings("unchecked")
			Set<String>  itr =  ( (JSONObject) submitdataObj).keySet(); 
		    if(( (JSONObject) submitdataObj).get("bulkcmd") !=null){
		    	String bulkcmd = ((JSONObject) submitdataObj).getString("bulkcmd");
		    	Element elmBulkCmd = (Element) rootXml.selectSingleNode("/root/screen/commands/bulkcmd[@name='"+bulkcmd+"' ] ");
		    	logger.debug("/root/screen/commands/bulkcmd[@name='"+bulkcmd+"' ] ");
		    	String operation = elmBulkCmd.attributeValue("opt");
//	    		String strProcessor = elmBulkCmd.attributeValue("processor");
	    		logger.debug("Command Processor: operation:" + operation);
	    		String[] opts = operation.split("\\|"); //get chained commands
	    		for (String opt : opts) {
	    			String[] sqlcmd = opt.split("\\#"); //get Id of query 
	    			String querynodeXpath =  sqlcmd[0]+"[@id='"+sqlcmd[1]+"']"; //Query node xpath
	    			logger.debug("querynodeXpath:"+querynodeXpath);
	    			Element processorElm = (Element) rootXml.selectSingleNode("/root/screen/*/"+querynodeXpath+" ");
	    			String strProcessor = processorElm.getParent().getName();
	    		    BaseCommandProcessor cmdProcessor =  CommandProcessorResolver.getCommandProcessor(strProcessor);
	    		    resDTO = cmdProcessor.processCommand(screenName, querynodeXpath, null, inputDTO, resDTO);				
	    		    //resDTO = rpc.selectData(  screenName,   null, querynodeXpath ,   (JSONObject)jsonRecord);
	    		}
	    		
		    }else{
		    	
			    for (String dataSetkey : itr) { //form1, form2 ...
			    	JSONArray dataSetJobj = ((JSONObject) submitdataObj).getJSONArray(dataSetkey);
			    	for (Object jsonRecord : dataSetJobj) { //rows in dataset a Good place to insert DB Transaction
			    		String cmd = ((JSONObject) jsonRecord).getString("command");
			    		Element elmCmd = (Element) rootXml.selectSingleNode("/root/screen/commands/cmd[@name='"+cmd+"' ] ");
			    		System.out.println("/root/screen/commands/cmd[@name='"+cmd+"' ] ");
	//		    		String instack = elmCmd.attributeValue("instack");
			    		String operation = elmCmd.attributeValue("opt");
			    		String strProcessor = elmCmd.attributeValue("processor");
			    		logger  .debug("Command Processor:"+strProcessor+" operation:"+operation);
			    		String[] opts = operation.split("\\|"); //get chained commands
			    		for (String opt : opts) {
			    			String[] sqlcmd = opt.split("\\:"); //get Id of query 
			    			String querynodeXpath =  sqlcmd[0]+"[@id='"+sqlcmd[1]+"']"; //Query node xpath
			    			
			    		    BaseCommandProcessor cmdProcessor =  CommandProcessorResolver.getCommandProcessor(strProcessor);
			    		    resDTO = cmdProcessor.processCommand(screenName, querynodeXpath, (JSONObject) jsonRecord, inputDTO, resDTO);				
			    		    //resDTO = rpc.selectData(  screenName,   null, querynodeXpath ,   (JSONObject)jsonRecord);
			    		}
			    	}
				}
		    }
		}else{
			String resultJson = remoteCommandProcessor (submitdataObj.toString(), screenName);
			resDTO = (ResultDTO) JSONObject.toBean(JSONObject.fromObject(resultJson), ResultDTO.class);
		}
		}catch(Exception e){
			logger.debug("Unexpected error",e);
			if(resDTO == null)resDTO= new ResultDTO();
			List<String> errors = resDTO.getErrors();
			if(errors == null)errors = new ArrayList<String>();
			errors.add("command.processing.feerror");
		}
		return resDTO;
	}
	
	private String remoteCommandProcessor(String submitdataObj, String screenName) {

		QueryServiceService qss = new QueryServiceService();
		 QueryService queryServicePort = qss.getQueryServicePort();
		 String strResDTO = queryServicePort.remoteCommandProcessor(submitdataObj, screenName);
		return strResDTO;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
