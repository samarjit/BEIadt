package com.ycs.ws;

import java.text.ParseException;
import java.util.ResourceBundle;

import javax.xml.ws.Endpoint;

import org.quartz.SchedulerException;

import com.ycs.ezlink.scheduler.FixedTimeScheduler;
import com.ycs.fe.cache.AppCacheManager;
import com.ycs.fe.util.FileSyncImpl;

public class WSPublisher {

	public static void main(String[] args) throws ParseException, SchedulerException {
		System.out.println("deploying ... endpoints..");
//		FixedTimeScheduler fixedTimeScheduler = new FixedTimeScheduler();
//		fixedTimeScheduler.startScheduler();
		
		ResourceBundle rb = ResourceBundle.getBundle("path_config");
		Endpoint.publish(rb.getString("be.webservice.basepath")+"/qservice", new QueryService());
		Endpoint.publish(rb.getString("be.webservice.basepath")+"/fservice", new FileSyncImpl());
		AppCacheManager.getInstance().initCache();
	}

}
