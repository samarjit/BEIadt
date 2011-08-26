package com.ycs.ws;

import javax.xml.ws.Endpoint;

import com.ycs.fe.cache.AppCacheManager;
import com.ycs.fe.util.FileSync;
import com.ycs.fe.util.FileSyncImpl;

public class WSPublisher {

	public static void main(String[] args) {
		System.out.println("deploying ... endpoints..");
		Endpoint.publish("http://localhost:8183/WS/qservice", new QueryService());
		Endpoint.publish("http://localhost:8183/WS/fservice", new FileSyncImpl());
		AppCacheManager.getInstance().initCache();
	}

}
