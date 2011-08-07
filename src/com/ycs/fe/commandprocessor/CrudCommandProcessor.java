package com.ycs.fe.commandprocessor;

import net.sf.json.JSONObject;

import com.ycs.fe.crud.JsrpcPojo;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;

public class CrudCommandProcessor implements BaseCommandProcessor {

	@Override
	public ResultDTO processCommand(String screenName, String querynodeXpath, JSONObject jsonRecord, InputDTO jsonInput, ResultDTO resultDTO) {
		JsrpcPojo rpc = new JsrpcPojo();
		return rpc.selectData(  screenName,   null, querynodeXpath ,   (JSONObject)jsonRecord,   jsonInput,   resultDTO);
	}

}
