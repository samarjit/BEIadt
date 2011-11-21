package com.ycs.ezlink.mail;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MethodNotSupportedException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ycs.ezlink.exception.EmptyAddressException;
import com.ycs.ezlink.util.EzLinkConstant;
import com.ycs.fe.util.CompoundResource;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class MailManager {
	private static Logger logger = Logger.getLogger(MailManager.class);
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	
	
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}



	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	 public void sendEmail(final EmailVO commonObject1) throws Exception
	  {
	    logger.debug("Inside sendConfirmationEmail : " + this.mailSender);
	    MimeMessagePreparator preparator = null;
	    try {
	      preparator = new MimeMessagePreparator( ) {
	        
	        private EmailVO commonObject = commonObject1;
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MailManager.logger.debug("Inside prepare : " + mimeMessage);
	          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

	          if (this.commonObject.getToAddress() != null)
	            message.setTo( commonObject.getToAddress());
	          else {
	            throw new EmptyAddressException("To Address is Empty");
	          }

	          if ( commonObject.getToCC() != null) {
	            message.setCc( commonObject.getToCC());
	          }

	          if ( commonObject.getToBcc() != null) {
	            message.setBcc( commonObject.getToBcc());
	          }

	          message.setFrom( commonObject.getSenderEmailAddress());

	          message.setSubject( commonObject.getSubject());
	          Map<String,String> model = new HashMap<String,String>(this.commonObject.getVariables());
	          
	          String text = "This is test emails";
	          if(commonObject.getFtlTemplate() != null && !"".equals(commonObject.getFtlTemplate()) ){
	            Template t = null;
	            if(commonObject.getFtlTemplate().endsWith(".ftl") || commonObject.getFtlTemplate().endsWith(".FTL")){
	            	//user file loader for templates
	            	Configuration cfg = new Configuration();
	            	ResourceBundle rb = ResourceBundle.getBundle(EzLinkConstant.PATH_CONFIG_PROPERTY_FILE_NAME);
	                cfg.setDirectoryForTemplateLoading(new File(CompoundResource.getString(rb, "email.template.dir")));
	                cfg.setObjectWrapper(new DefaultObjectWrapper());
	                t = cfg.getTemplate(commonObject.getFtlTemplate());
	                
	            }else{
	            	t = new Template("name", new StringReader(commonObject.getFtlTemplate()), new Configuration());
	            }
				StringWriter out = new StringWriter();
				t.process(model, out );
			  	text = out.toString();
	          }else{
	        	  if(commonObject.getFtlTemplate().endsWith(".vm") || commonObject.getFtlTemplate().endsWith(".VM")){
	        		  VelocityEngineUtils.mergeTemplateIntoString(  velocityEngine,	             commonObject.getVmTemplate(), model);
	        	  }else{
	        		  logger.error("Not supported Exception: Currently supported Velocity templates as files and not as inline String!");
	        		  throw new MethodNotSupportedException("Currently supported Velocity templates as files and not as inline String!");
	        	  }
	          }
			  message.setText(text, true);
				
	          //##Attachment##
			  if(commonObject.getAttachment() != null){
	          FileSystemResource res = new FileSystemResource(
	            commonObject.getAttachment());
	          message.addAttachment(res.getFilename(), res);
			  }
	        }
	      };
	      logger.debug("Preparator :" + preparator);
	      
	      this.mailSender.send(preparator);
	      logger.debug("sent email!");
	    } catch (Exception e) {
	      logger.error("[sendEmail] Error" + e);
	      throw e;
	    }
	    
	  }

	public static void main(String[] args) {
		 try
		    {
//			 ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
//			 EmailJob emailJob = new EmailJob();
//				AutowireCapableBeanFactory acbf = context.getAutowireCapableBeanFactory();
//				acbf.autowireBeanProperties(emailJob, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, true);
//				acbf.initializeBean(emailJob, "standaloneApp");
			 /*ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
			 EmailJob emailJob = new EmailJob();
			 AutowireCapableBeanFactory acbf = context.getAutowireCapableBeanFactory();
			    acbf.autowireBeanProperties(emailJob, AutowireCapableBeanFactory .AUTOWIRE_BY_NAME, true);
			    acbf.initializeBean(emailJob, "standaloneApp"); // any name will work
			// or use emailJob = (EmailJob) context.getBean("mailManager");
			  String[] to = { "samarjit.s@yalamanchili.com.sg" };
		      String[] cc = null;//{ "samarjit.s@yalamanchili.com.sg" };
		      String[] bcc = null;//{ "samarjit.s@yalamanchili.com.sg" };

		      EmailVO vo = new EmailVO(){};
		      vo.setSenderName("samarjit");
		      vo.setReceiverName("samarjit");

		      vo.setToAddress(to);
		      vo.setToCC(cc);
		      vo.setToBcc(bcc);
		      vo.setSubject("Hellooooooooooo");

		      vo.setSenderEmailAddress("samarjit.s@yalamanchili.com.sg");
		      vo.setVmTemplate("com/ycs/ezlink/mail/templates/SampleTemplateVO.vm");
		     // vo.setAttachment(new File("c:/abc.gif"));

		      emailJob.sendEmail(vo);*/
		     /* JavaMailSenderImpl sender = new JavaMailSenderImpl();
		      sender.setHost("smtp.gmail.com");
		      Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
		      sender.setJavaMailProperties(props);
		      MimeMessage message = sender.createMimeMessage();
		      MimeMessageHelper helper = new MimeMessageHelper(message);
		      helper.setTo("samarjit.s@yalamanchili.com.sg");
		      helper.setText("Thank you for ordering!");
		      sender.setUsername("samarjit.samanta");
		      sender.setPassword("sammy@1234");
		      sender.send(message);
		    */
		      /*
		      Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
		 
				Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("samarjit.samanta","sammy@1234");
						}
					});
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("samarjit.s@yalamanchili.com.sg"));
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("samarjit.s@yalamanchili.com.sg"));
					message.setSubject("Testing Subject");
					message.setText("Dear Mail Crawler," "\n\n No spam to my email, please!");
		 
					Transport.send(message);
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
				*/
		    } catch (Exception e) {
			      e.printStackTrace();
			      System.out.println("error :" + e);
			}	 
	}

}
