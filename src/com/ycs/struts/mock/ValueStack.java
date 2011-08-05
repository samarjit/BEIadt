package com.ycs.struts.mock;

import java.util.Map;

import com.ycs.be.crud.QueryParseException;

import ognl.Ognl;
import ognl.OgnlException;

public class ValueStack {

	Map<String, Object> context = null;
	public Map<String, Object> getContext() {
		return context;
	}

	public void set(String key, String value) {
		context.put(key, value);
	}

	public String findString(String string) throws QueryParseException   {
		try {
			return (String) Ognl.getValue(string, context);
		} catch (OgnlException e) {
			throw new  QueryParseException();
		}

	}
}
