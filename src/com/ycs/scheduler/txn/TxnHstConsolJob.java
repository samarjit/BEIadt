package com.ycs.scheduler.txn;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TxnHstConsolJob implements Job{

	/**
	 * Consolidate transaction two months history
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}

}
