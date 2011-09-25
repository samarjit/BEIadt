/**
 *  This is a manual run program to find that time of con job schedule that will fire next and in what interval? 
 *  Test cron expression firing times
 *  javac   -cp .:quartz-1.6.1-RC1.jar:    CronEval.java
 *  java    -cp .:quartz-1.6.1-RC1.jar:  CronEval
 *  ../classes/quartz_jobs.xml 
 *  @Author: samarjit
 *  @Date: June-01-2011
 */   
package com.ycs.scheduler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import org.quartz.Calendar;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerUtils;
import org.quartz.impl.calendar.DailyCalendar;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.OperableTrigger;

public class CronEval {    
     
     public static void main(String[] args) throws ParseException,IOException {   
          new CronEval().showTriggerFireTimes();
    }     
  
  public void showTriggerFireTimes() throws ParseException, IOException {   
    CronTriggerImpl trigger = new CronTriggerImpl();
    
	  CronTrigger trigger2 = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();//new CronTrigger();
   // Fire at 10:15am every Monday, Tuesday, Wednesday, and Thursday    
   System.out.println("Enter Cron Expression");
   BufferedReader exprRdr = new BufferedReader(new InputStreamReader(System.in));
   String expreRdr = "0 15 10 ? * MON-THU";
   expreRdr =  exprRdr.readLine();
   trigger.setCronExpression(expreRdr);
   String exprSummary = trigger.getExpressionSummary();
   System.out.println("Expression summary:"+ exprSummary); 
   java.util.Calendar startDate = java.util.Calendar.getInstance();
   startDate.set(2011,java.util.Calendar.JANUARY, 1);
   java.util.Calendar endDate = java.util.Calendar.getInstance();
   endDate.set(2011,java.util.Calendar.JUNE, 31);
   //Calendar newstartDate = new DailyCalendar();
//outputFireTimeList(trigger, startDate, endDate);
  // TriggerUtils.computeEndTimeToAllowParticularNumberOfFirings(trigger, newstartDate , 10);
//   org.quartz.Calendar triggerStartTime = java.util.Calendar.getInstance();
   List<java.util.Date> datesList = TriggerUtils.computeFireTimes(trigger,null,
             31); 
   for(java.util.Date date: datesList){
       System.out.println("Next fire time = " + date);
   } 
 }     
  
  @SuppressWarnings("rawtypes")  
  private void outputFireTimeList(OperableTrigger trigger, java.util.Calendar from, java.util.Calendar to) {
     
     List fireTimeList = TriggerUtils.computeFireTimesBetween(trigger, null, from.getTime(), to.getTime());
         for ( int i = 0; i < fireTimeList.size(); i++ ) {
             System.out.println(fireTimeList.get(i));
         }
     }
  }

