package com.ycs.ezlink.scheduler.alertq;

import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ycs.ezlink.dao.AlertDAO;
import com.ycs.ezlink.mail.EmailVO;
import com.ycs.ezlink.mail.MailManager;
import com.ycs.ezlink.mybatis.confhelper.MybatisSessionHelper;
import com.ycs.ezlink.mybatis.dto.AlertQueue;
import com.ycs.ezlink.mybatis.dto.AlertTemplateMaster;
import com.ycs.ezlink.mybatis.mapper.AlertTemplateMasterMapper;
import com.ycs.ezlink.util.EzLinkConstant;

public class EmailJob implements Job{
	private static Logger logger = Logger.getLogger(EmailJob.class);
	private static boolean alreadyRunning = false;
	private static MailManager mailManager;

	static ApplicationContext context;
	 static{
		   context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		   mailManager =  (MailManager) context.getBean("mailManager");
	 }




	public EmailJob() {
		
	}

	@Override
	public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
		if(alreadyRunning){logger.debug("-----EmailJob alreadyRunning-----"); return ;}
		alreadyRunning = true;
		logger.debug("------EmailJob start------"/*+new Date()+paramJobExecutionContext.getJobDetail().getKey()*/);
		
	 
		
		List<AlertQueue> alertQList = AlertDAO.selectAlertQueue("EMAIL");
		String emailText = "";

		try {
			for (AlertQueue alertQueue : alertQList) {
				emailText = alertQueue.getEmailText();
				String emailId = alertQueue.getEmailTo();
				JSONObject jobjEmail = null;
				try {
					if(emailText != null && !"".equals(emailText)){
						jobjEmail = JSONObject.fromObject(emailText);
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				String templateId = alertQueue.getEmailTemplateId();//(jobjEmail.containsKey("template")) ? jobjEmail.getString("template") : "";
				SqlSession sqlSession = MybatisSessionHelper.eINSTANCE.openSessionWithoutLogging();
				AlertTemplateMasterMapper alertTemplateMapper = sqlSession.getMapper(AlertTemplateMasterMapper.class);
				AlertTemplateMaster alertTemplateMaster = alertTemplateMapper.selectByPrimaryKey(templateId);
				sqlSession.close();
				
				String templateText = alertTemplateMaster.getAlertContent();
				EmailVO vo = new EmailVO() {
				};
				String[] to = { emailId };
				String[] cc = null;// { "samarjit.s@yalamanchili.com.sg" };
				String[] bcc = null;// { "samarjit.s@yalamanchili.com.sg" };

				vo.setSenderName("ezlink");

				vo.setToAddress(to);
				vo.setToCC(cc);
				vo.setToBcc(bcc);
				vo.setSubject(alertTemplateMaster.getEmailSubject());
				ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
				
				vo.setSenderEmailAddress(rb.getString("email.sender"));
				vo.setVariables(jobjEmail);
				// vo.setAttachment(new File("c:/abc.gif"));
				vo.setFtlTemplate(templateText);
				
				try {
					 System.out.println("sending emails");
					mailManager.sendEmail(vo);
					logger.debug("email send successful to " +  alertQueue.getEmailTo());
					AlertDAO.deleteAlertQueueByPrimaryKey(alertQueue.getAlertQId());
				} catch (Exception e) {
					 
					String  result = e.getLocalizedMessage();
					alertQueue.setDeliveredStatus(result.substring(0,19));
					alertQueue.setRetryCount((short) (alertQueue.getRetryCount() + 1));
					logger.debug("email send failed to " + alertQueue.getMobileNo() + " error:" + result);
					AlertDAO.updateAlertQueue(alertQueue);
				}


			}
		} catch (NullPointerException e) {
			logger.error("Null pointer",e);
		}
		
		
	
		
		logger.debug("------EmailJob end------"/*+new Date()+paramJobExecutionContext.getJobDetail().getKey()*/);
		alreadyRunning = false;
	}

	
	 
	 
	 public static void main(String[] args) throws JobExecutionException {
				new EmailJob().execute(null);
		
	}

}
