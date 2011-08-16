package com.ycs.ws;

import javax.xml.ws.Endpoint;

import com.ycs.fe.cache.AppCacheManager;

public class WSPublisher {

	public static void main(String[] args) {
		System.out.println("deploying ... endpoints..");
		Endpoint.publish("http://localhost:8183/WS/qservice", new QueryService());
		AppCacheManager.getInstance().initCache();
	}

}
