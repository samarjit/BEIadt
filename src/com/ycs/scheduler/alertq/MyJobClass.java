package com.ycs.scheduler.alertq;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public class MyJobClass implements Job{

	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		System.out.println("MyJob .."+new Date());
	}

}
