package com.ycs.scheduler;

import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.ycs.scheduler.alertq.MyJobClass;
import com.ycs.scheduler.otp.OtpCleanJob;
import com.ycs.scheduler.refund.RefundJob;
import com.ycs.scheduler.txn.TxnHstConsolJob;

public class FixedTimeScheduler {
	Scheduler sched = null;
	
	public void startScheduler() throws ParseException, SchedulerException{
		  sched  = StdSchedulerFactory.getDefaultScheduler();
//		Map<String, JobDTO> allJobs = new SchedulerDAO().getAllJobs();
//		JobDTO alertQJob = allJobs.get("ALERT_Q");
//		alertQJob.getCronExp();
		// define the job and tie it to our HelloJob class
		JobDetail alertQjob = JobBuilder.newJob(MyJobClass.class).withIdentity("alertJob", "alertgroup1").build();
		CronTrigger alertQtrigger = TriggerBuilder.newTrigger().withIdentity("alertTrigger", "alertgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?")).build();
		sched.scheduleJob(alertQjob, alertQtrigger);

		JobDetail refundJob = JobBuilder.newJob(RefundJob.class).withIdentity("RefundJob", "RefundJobgroup1").build();
		CronTrigger refundTrigger = TriggerBuilder.newTrigger().withIdentity("RefundTrigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(refundJob, refundTrigger);
		
		JobDetail otpCleanJob = JobBuilder.newJob(OtpCleanJob.class).withIdentity("OtpCleanJob", "alertgroup1").build();
		CronTrigger otpCleanTrigger = TriggerBuilder.newTrigger().withIdentity("OtpCleantrigger1", "alertgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(otpCleanJob, otpCleanTrigger);
		
		JobDetail txnHstConsolJob = JobBuilder.newJob(TxnHstConsolJob.class).withIdentity("txnHstConsolJob", "txnHstConsolgroup1").build();
		CronTrigger txnHstConsolTrigger = TriggerBuilder.newTrigger().withIdentity("txnHstConsoltrigger1", "txnHstConsolgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(txnHstConsolJob, txnHstConsolTrigger);
		
		
		
		sched.start();
		
	}
	
	public void poller(){
		
		
	}

	public void stopScheduler() throws SchedulerException{
		sched.shutdown(true);
	}
	
	
	public static void main(String [] args) throws ParseException, SchedulerException, InterruptedException{
		FixedTimeScheduler fixedTimeScheduler = new FixedTimeScheduler();
		
		fixedTimeScheduler.startScheduler();
		Thread.sleep(10 * 1000);
		fixedTimeScheduler.stopScheduler();
	}
}
