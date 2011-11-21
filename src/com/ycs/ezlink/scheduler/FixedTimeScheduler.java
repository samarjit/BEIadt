package com.ycs.ezlink.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.ycs.ezlink.scheduler.alertq.SMSAlertQJob;

public class FixedTimeScheduler {
	
	private static Logger logger = Logger.getLogger(FixedTimeScheduler.class);
	
	Scheduler sched = null;
	
	public void startScheduler() throws ParseException, SchedulerException{
		  sched  = StdSchedulerFactory.getDefaultScheduler();
//		Map<String, JobDTO> allJobs = new SchedulerDAO().getAllJobs();
//		JobDTO alertQJob = allJobs.get("ALERT_Q");
//		alertQJob.getCronExp();
		  
		// define the job and tie it to our HelloJob class
		JobDetail smsAlertQjob = JobBuilder.newJob(SMSAlertQJob.class).withIdentity(SMSAlertQJob.class.getSimpleName() , "alertgroup1").build();
		CronTrigger smsAlertQtrigger = TriggerBuilder.newTrigger().withIdentity(SMSAlertQJob.class.getSimpleName()+"trigger", "alertgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
		sched.scheduleJob(smsAlertQjob, smsAlertQtrigger);

		  /*JobDetail refundJob = JobBuilder.newJob(RefundJob.class).withIdentity(RefundJob.class.getSimpleName(), "RefundJobgroup1").build();
		CronTrigger refundTrigger = TriggerBuilder.newTrigger().withIdentity(RefundJob.class.getSimpleName()+"trigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(refundJob, refundTrigger);
		
		JobDetail refundFileSend = JobBuilder.newJob(RefundFileSendJob.class).withIdentity(RefundFileSendJob.class.getSimpleName(), "RefundJobgroup1").build();
		CronTrigger refundFileSendTrigger = TriggerBuilder.newTrigger().withIdentity(RefundFileSendJob.class.getSimpleName()+"trigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(refundFileSend, refundFileSendTrigger);
		
		JobDetail refundFileRecvJob = JobBuilder.newJob(RefundFileRecvJob.class).withIdentity(RefundFileRecvJob.class.getSimpleName(), "RefundJobgroup1").build();
		CronTrigger refundFileRecvTrigger = TriggerBuilder.newTrigger().withIdentity(RefundFileRecvJob.class.getSimpleName()+"trigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(refundFileRecvJob, refundFileRecvTrigger);
		
		JobDetail syncFileRecvJob = JobBuilder.newJob(SyncFileRecvJob.class).withIdentity(SyncFileRecvJob.class.getSimpleName(), "RefundJobgroup1").build();
		CronTrigger syncFileRecvTrigger = TriggerBuilder.newTrigger().withIdentity(SyncFileRecvJob.class.getSimpleName()+"trigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(syncFileRecvJob, syncFileRecvTrigger);
		
		//Day 60 Nomination card reminder email. This is separate because client might come up with different intervals later
		JobDetail refundNomReminderJob = JobBuilder.newJob(RefundNomReminderJob.class).withIdentity(RefundNomReminderJob.class.getSimpleName(), "RefundJobgroup1").build();
		CronTrigger refundNomReminderTrigger = TriggerBuilder.newTrigger().withIdentity(RefundNomReminderJob.class.getSimpleName()+"trigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(refundNomReminderJob, refundNomReminderTrigger);
		
		//Day 60th reminder if cash not collected yet. This is separate because client might come up with different intervals later
		JobDetail refundCashReminderJob = JobBuilder.newJob(RefundCashReminderJob.class).withIdentity(RefundCashReminderJob.class.getSimpleName(), "RefundJobgroup1").build();
		CronTrigger refundCashReminderTrigger = TriggerBuilder.newTrigger().withIdentity(RefundCashReminderJob.class.getSimpleName()+"trigger1", "Refundgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(refundCashReminderJob, refundCashReminderTrigger);
		
		JobDetail txnHstConsolJob = JobBuilder.newJob(TxnHstConsolJob.class).withIdentity(TxnHstConsolJob.class.getSimpleName(), "txnHstConsolgroup1").build();
		CronTrigger txnHstConsolTrigger = TriggerBuilder.newTrigger().withIdentity(TxnHstConsolJob.class.getSimpleName()+"trigger1", "txnHstConsolgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(txnHstConsolJob, txnHstConsolTrigger);
		
		 
		
		JobDetail emailJob = JobBuilder.newJob(EmailJob.class).withIdentity(EmailJob.class.getSimpleName(), "emailgroup1").build();
		CronTrigger emailTrigger = TriggerBuilder.newTrigger().withIdentity(EmailJob.class.getSimpleName()+"trigger1", "emailgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(emailJob, emailTrigger);
		
		JobDetail bulkEmailJob = JobBuilder.newJob(BulkEmailJob.class).withIdentity(BulkEmailJob.class.getSimpleName(), "emailgroup1").build();
		CronTrigger bulkEmailTrigger = TriggerBuilder.newTrigger().withIdentity(BulkEmailJob.class.getSimpleName()+"trigger1", "emailgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(bulkEmailJob, bulkEmailTrigger);

		JobDetail ezReloadJob = JobBuilder.newJob(EzReloadJob.class).withIdentity(EzReloadJob.class.getSimpleName(), "emailgroup1").build();
		CronTrigger ezReloadTrigger = TriggerBuilder.newTrigger().withIdentity(EzReloadJob.class.getSimpleName()+"trigger1", "emailgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(ezReloadJob, ezReloadTrigger);
		
		
		JobDetail otpCleanJob = JobBuilder.newJob(OtpCleanJob.class).withIdentity(OtpCleanJob.class.getSimpleName(), "alertgroup1").build();
		CronTrigger otpCleanTrigger = TriggerBuilder.newTrigger().withIdentity(OtpCleanJob.class.getSimpleName()+"trigger1", "alertgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(otpCleanJob, otpCleanTrigger);
		
		JobDetail purgeFolderJob = JobBuilder.newJob(PurgeFolderJob.class).withIdentity(PurgeFolderJob.class.getSimpleName(), "emailgroup1").build();
		CronTrigger purgeFolderJobTrigger = TriggerBuilder.newTrigger().withIdentity(PurgeFolderJob.class.getSimpleName()+"trigger1", "emailgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(purgeFolderJob, purgeFolderJobTrigger);
		
		
		JobDetail updateInsurancePolicyYrJob = JobBuilder.newJob(UpdateInsurancePolicyYr.class).withIdentity(UpdateInsurancePolicyYr.class.getSimpleName(), "emailgroup1").build();
		CronTrigger updateInsurancePolicyYrTrigger = TriggerBuilder.newTrigger().withIdentity(UpdateInsurancePolicyYr.class.getSimpleName()+"trigger1", "emailgroup1").withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		sched.scheduleJob(updateInsurancePolicyYrJob, updateInsurancePolicyYrTrigger);
		*/
		
		sched.start();
		
		logger.debug(descJobStatuses());
		

	}
	
	public void poller(){
		 
		
	}

	public String descJobStatuses(){
		String ret = "[";
		// enumerate each job group
		try {
			boolean first = true;
			SimpleDateFormat sm = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			for(String group: sched.getJobGroupNames()) {
			    // enumerate each job in group
			    for(JobKey jobKey : sched.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
//			    	System.out.println("Found job identified by: " + jobKey);
			        List<? extends Trigger> jobTriggers = sched.getTriggersOfJob(jobKey);
			        ret += (first)?"":","; first = false;
			        ret += "{'Job':'"+jobKey+"', 'NextFireTime':'"+ sm.format(jobTriggers.get(0).getNextFireTime())+"'}";
//			        System.out.println(jobTriggers.get(0).getNextFireTime());
			    }
			}
			ret +="]";
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public void stopScheduler() throws SchedulerException{
		sched.shutdown(true);
	}
	
	
	public static void main(String [] args) throws ParseException, SchedulerException, InterruptedException{
		FixedTimeScheduler fixedTimeScheduler = new FixedTimeScheduler();
		
		fixedTimeScheduler.startScheduler();
		Thread.sleep(10 * 1000);
//		fixedTimeScheduler.stopScheduler();
	}
}
