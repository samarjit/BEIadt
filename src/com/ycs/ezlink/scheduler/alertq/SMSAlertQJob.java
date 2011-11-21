package com.ycs.ezlink.scheduler.alertq;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ycs.ezlink.dao.AlertDAO;
import com.ycs.ezlink.mybatis.confhelper.MybatisSessionHelper;
import com.ycs.ezlink.mybatis.dto.AlertQueue;
import com.ycs.ezlink.mybatis.dto.AlertQueueExample;
import com.ycs.ezlink.mybatis.mapper.AlertQueueMapper;
import com.ycs.ezlink.util.SmsRouter;



/**
 * 
 * @author Samarjit
 *
 */
public class SMSAlertQJob implements Job{

	private static Logger logger = Logger.getLogger(SMSAlertQJob.class);
	private static boolean alreadyRunning = false;
	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		if(alreadyRunning){logger.debug("-----SMSAlertQJob alreadyRunning-----"); return ;}
		alreadyRunning= true;
		logger.debug("------SMSAlertQJob start------"+new Date()/*+paramJobExecutionContext.getJobDetail().getKey()*/);
		Map<String, String> request = new HashMap<String, String>();
//		request.put("username", "extfevoagt");
//		request.put("password", "3xtf3v0agt");
		
		
		List<AlertQueue> alertQList = AlertDAO.selectAlertQueue("SMS");
		
		
		try {
			for (AlertQueue alertQueue : alertQList) {
				request.put("to",alertQueue.getMobileNo());
				request.put("text",URLEncoder.encode(alertQueue.getSmsText(), "UTF-8"));
				String res = new SmsRouter().sendSMS(request);
				logger.debug("result ["+res+"]");
				String result = null;
				if(res != null && res.startsWith("0")){
					result = "Y";
					logger.debug("sms send successful to "+alertQueue.getMobileNo());
					AlertDAO.deleteAlertQueueByPrimaryKey(alertQueue.getAlertQId());
					
				}else{
					if(res != null)
					result = res.replace('|', ' ');
					alertQueue.setDeliveredStatus(result);
					alertQueue.setRetryCount((short) (alertQueue.getRetryCount()+1));
					logger.debug("sms send failed to "+alertQueue.getMobileNo()+" error:"+result);
					AlertDAO.updateAlertQueue(alertQueue);
					 
				}
				
				
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.toString());
		}catch(NullPointerException e){	
			logger.error(e.toString());
		}
		
		
	

		logger.debug("------SMSAlertQJob end------"+new Date()/*+paramJobExecutionContext.getJobDetail().getKey()*/);
		alreadyRunning = false;
	}

	public static void main(String[] args) throws JobExecutionException {
		new SMSAlertQJob().execute(null);
		
// 
//		SqlSession sqlSession = MybatisSessionHelper.eINSTANCE.openSessionWithoutLogging();
//		AlertQueueMapper mapper = sqlSession.getMapper(AlertQueueMapper.class);
//		AlertQueueExample example = new AlertQueueExample();
//		example.or().andDeliveredStatusNotEqualTo("Y");
//		example.or().andDeliveredStatusIsNull();
//		 
//		System.out.println(mapper.countByExample(example));
//		List<AlertQueue> alertQueue = mapper.selectByExample(example);
//		System.out.println(alertQueue.get(0).getAlertType());
		
		
	}
}
