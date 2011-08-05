package com.ycs.be.commandprocessor;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.ycs.be.dto.InputDTO;
import com.ycs.be.dto.ResultDTO;

public class BlCommandProcessor implements BaseCommandProcessor {
	private static Logger logger = Logger.getLogger(BlCommandProcessor.class);
	
	@Override
	public ResultDTO processCommand(String screenName, String querynodeXpath, JSONObject jsonRecord, InputDTO inputDTO, ResultDTO resultDTO) {
		logger.debug("Currently processing record:"+jsonRecord.toString());
		return null;
	}

}
