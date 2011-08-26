package com.ycs.fe.businesslogic;

import java.util.HashMap;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionInvocation;
import com.ycs.fe.businesslogic.BaseBL;
import com.ycs.fe.dto.InputDTO;
import com.ycs.fe.dto.ResultDTO;

public abstract class EzlinkBL implements BaseBL {

	@Override
	public HashMap preJsRPCListerner(ActionInvocation invocation) {
		return null;
	}

	@Override
	public HashMap postJsRPCListerner(ActionInvocation invocation) {
		return null;
	}

	@Override
	public HashMap preCrudListener(ActionInvocation invocation) {
		return null;
	}

	@Override
	public HashMap postCrudListener(ActionInvocation invocation) {
		return null;
	}

	@Override
	public HashMap preWorkflowListener(ActionInvocation invocation) {
		return null;
	}

	@Override
	public HashMap postWorkflowListener(ActionInvocation invocation) {
		return null;
	}

	@Override
	public abstract ResultDTO executeCommand(String screenName, String querynodeXpath, JSONObject jsonRecord, InputDTO inputDTO, ResultDTO resultDTO);
		


	
}
