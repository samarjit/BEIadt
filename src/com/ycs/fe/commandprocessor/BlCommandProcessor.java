package com.ycs.fe.commandprocessor;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ycs.fe.businesslogic.BaseBL;
import com.ycs.fe.cache.BusinessLogicFactory;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;

public class BlCommandProcessor implements BaseCommandProcessor {
	private static Logger logger = Logger.getLogger(BlCommandProcessor.class);
	
	@Override
	public ResultDTO processCommand(String screenName, String querynodeXpath, JSONObject jsonRecord, InputDTO inputDTO, ResultDTO resultDTO) {
		logger.debug("Currently processing record in remote BL");
		BaseBL bl = BusinessLogicFactory.getBusinessLogic(screenName);
		ResultDTO resDTO = bl.executeCommand(screenName, querynodeXpath, jsonRecord, inputDTO, resultDTO /*previous result*/);
		return resDTO;
	}

}
