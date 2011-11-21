package com.ycs.ezlink.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ycs.ezlink.mybatis.dto.AlertQueue;
import com.ycs.ezlink.mybatis.dto.AlertTemplateMaster;
import com.ycs.ezlink.mybatis.dto.OtpUserDtl;
import com.ycs.fe.dao.DBConnector;
import com.ycs.fe.dto.PrepstmtDTO;
import com.ycs.fe.dto.PrepstmtDTO.DataType;
import com.ycs.fe.dto.PrepstmtDTOArray;

public class AlertDAO {
	private static Log logger = LogFactory.getLog(AlertDAO.class); 
	
	public static AlertTemplateMaster getAlertTextDetails(String alert_txt_id) {
		
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		AlertTemplateMaster alert_txt_dtl = new AlertTemplateMaster();
		try {
			String qry = "SELECT ALERT_TEMPLATE_ID,ALERT_TYPE,ALERT_CONTENT  FROM ALERT_TEMPLATE_MASTER  where ALERT_TEMPLATE_ID= ?";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, alert_txt_id);
			crs = db.executePreparedQuery(qry, arPrepstmt);
			while(crs.next()){
				alert_txt_dtl.setAlertTemplateId(crs.getString("ALERT_TEMPLATE_ID"));
				alert_txt_dtl.setAlertType(crs.getString("ALERT_TYPE"));
				alert_txt_dtl.setAlertContent(crs.getString("ALERT_CONTENT"));
//				alert_txt_dtl.setSmsPassword(crs.getString("SMS_PASSWORD"));
//				alert_txt_dtl.setSmsUserid(crs.getString("SMS_USERID"));
//				alert_txt_dtl.setTechStudioCampaignId(crs.getString("TECH_STUDIO_CAMPAIGN_ID"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return alert_txt_dtl;
	}

	public static OtpUserDtl getOTPDetails(String nric) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		OtpUserDtl res = null;
		try {
			String qry = "select LOGIN_USER_ID,OTP_PASSWD,OTP_SENT_COUNT,OTP_VALID_TILL,OTP_FN_BLOCKED_TILL,INCORRECT_OTP_CNT,CREATED from OTP_USER_DTL  where LOGIN_USER_ID=?";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, nric);
			crs = db.executePreparedQuery(qry, arPrepstmt);
			
			if(crs.next()){
				res = new OtpUserDtl();
				res.setLoginUserId(crs.getString("LOGIN_USER_ID"));
				res.setOtpPasswd(crs.getString("OTP_PASSWD"));
				res.setOtpSentCount(crs.getByte("OTP_SENT_COUNT"));
				res.setOtpValidTill(crs.getTimestamp("OTP_VALID_TILL"));
				res.setOtpFnBlockedTill(crs.getTimestamp("OTP_FN_BLOCKED_TILL"));
				res.setIncorrectOtpCnt(crs.getByte("INCORRECT_OTP_CNT"));
				res.setCreated(crs.getTimestamp("CREATED"));
			}
		} catch (Exception e) {
			res = null;
			e.printStackTrace();
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}

	public static int updateOTPDetails(OtpUserDtl otpUserDtl) {
		DBConnector db = new DBConnector();
		int result = -1;
		HashMap<String, Object> hm  = new HashMap<String, Object>();
		OtpUserDtl res = new OtpUserDtl();
		String qry = null;
		try {
			String updateSets = "";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			boolean first = true;
			if(otpUserDtl.getOtpPasswd()!= null){
				updateSets += (first)?" ":", ";
				first = false;
				updateSets += "OTP_PASSWD=?";
				arPrepstmt.add(DataType.STRING, otpUserDtl.getOtpPasswd());
			}
			if(otpUserDtl.getOtpSentCount()!= null){
				updateSets += (first)?" ":", ";
				first = false;
				updateSets += "OTP_SENT_COUNT=?";
				arPrepstmt.add(DataType.INT, String.valueOf(otpUserDtl.getOtpSentCount()));
			}
			if(otpUserDtl.getOtpValidTill() != null){
				updateSets += (first)?" ":", ";
				first = false;
				updateSets += "OTP_VALID_TILL=?";
				arPrepstmt.add(DataType.TIMESTAMP, PrepstmtDTO.getDateStringFormat(otpUserDtl.getOtpValidTill(),PrepstmtDTO.DATE_NS_FORMAT ));
			}
			if(otpUserDtl.getOtpFnBlockedTill() != null){
				updateSets += (first)?" ":", ";
				first = false;
				updateSets += "OTP_FN_BLOCKED_TILL=?";
				arPrepstmt.add(DataType.TIMESTAMP, PrepstmtDTO.getDateStringFormat(otpUserDtl.getOtpFnBlockedTill(),PrepstmtDTO.DATE_NS_FORMAT ));
			}
			if(otpUserDtl.getIncorrectOtpCnt() != null){
				updateSets += (first)?" ":", ";
				first = false;
				updateSets += "INCORRECT_OTP_CNT=?";
				arPrepstmt.add(DataType.INT, String.valueOf(otpUserDtl.getIncorrectOtpCnt()));
			}
			if(otpUserDtl.getCreated() != null){
				updateSets += (first)?" ":", ";
				first = false;
				updateSets += "CREATED=?";
				arPrepstmt.add(DataType.TIMESTAMP, PrepstmtDTO.getDateStringFormat(otpUserDtl.getCreated(),PrepstmtDTO.DATE_NS_FORMAT ));
			}
			
			qry = "update  OTP_USER_DTL set "+ updateSets  +" where LOGIN_USER_ID = ?";
			arPrepstmt.add(DataType.STRING, otpUserDtl.getLoginUserId());
			System.out.println(qry);
			logger.debug("Resultant output SQL updateOTPDetails:"+qry + " array :" +arPrepstmt);
			result = db.executePreparedUpdate(qry, arPrepstmt);
			
		} catch (Exception e) {
			result = -1;
			logger.error("Update failed in updateOTPDetails "+ qry, e);
		} finally {
			 
		}
		return result;
	}

	public static int insertOTPDetails(OtpUserDtl otpDtl) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		int result = -1;
		try {
			String fields = "";
			String values = "";
			boolean first  = true;
			
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			
			if(otpDtl.getLoginUserId()!= null){
				fields += first?"":",";
				fields += "LOGIN_USER_ID";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, otpDtl.getLoginUserId());
			}
			if(otpDtl.getOtpPasswd()!= null){
				fields += first?"":",";
				fields += "OTP_PASSWD";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, otpDtl.getOtpPasswd());
			}
			if(otpDtl.getOtpSentCount()!= null){
				fields += first?"":",";
				fields += "OTP_SENT_COUNT";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.INT, String.valueOf(otpDtl.getOtpSentCount()));
			}
			if(otpDtl.getOtpValidTill()!= null){
				fields += first?"":",";
				fields += "OTP_VALID_TILL";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.DATE_NS, PrepstmtDTO.getDateStringFormat(otpDtl.getOtpValidTill(), PrepstmtDTO.DATE_NS_FORMAT));
			}
			if(otpDtl.getOtpFnBlockedTill()!= null){
				fields += first?"":",";
				fields += "OTP_FN_BLOCKED_TILL";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.DATE_NS, PrepstmtDTO.getDateStringFormat(otpDtl.getOtpFnBlockedTill(), PrepstmtDTO.DATE_NS_FORMAT));
			}
			if(otpDtl.getIncorrectOtpCnt()!= null){
				fields += first?"":",";
				fields += "INCORRECT_OTP_CNT";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.INT,String.valueOf(otpDtl.getIncorrectOtpCnt()));
			}
			if(otpDtl.getCreated()!= null){
				fields += first?"":",";
				fields += "CREATED";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.DATE_NS, PrepstmtDTO.getDateStringFormat(otpDtl.getCreated(), PrepstmtDTO.DATE_NS_FORMAT));
			}
			String qry = "insert into OTP_USER_DTL ("+fields+") values ("+values+")";
			logger.debug("Insert created :"+qry+ " values :" +arPrepstmt.toString());
			System.out.println(qry);
			 result = db.executePreparedUpdate(qry, arPrepstmt);

		} catch (Exception e) {
			result = -1;
			logger.error(e);
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static int  insertAlertQueue(AlertQueue alertQ) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		int result = -1;
		try {
			String fields = "";
			String values = "";
			boolean first  = true;
			
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			
			//if(alertQ.getAlertQId()!= null){
				fields += "ALERT_Q_ID";
				values +=  "ALERT_Q_SEQ.NEXTVAL" ; 
				first = false; 
			//}
			if(alertQ.getAlertType()!= null){
				fields += first?"":",";
				fields += "ALERT_TYPE";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getAlertType() );
			}
			if(alertQ.getUserId()!= null){
				fields += first?"":",";
				fields += "USER_ID";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getUserId() );
			}
			if(alertQ.getSmsText()!= null){
				fields += first?"":",";
				fields += "SMS_TEXT";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getSmsText() );
			}
			if(alertQ.getEmailText()!= null){
				fields += first?"":",";
				fields += "EMAIL_TEXT";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getEmailText() );
			}
		//	if(alertQ.getCreateTime()!= null){
				fields += first?"":",";
				fields += "CREATE_TIME";
				values += first?" SYSDATE ":",SYSDATE ";
				first = false;
//				arPrepstmt.add(DataType.TIMESTAMP,PrepstmtDTO.getDateStringFormat(alertQ.getCreateTime() , PrepstmtDTO.DATE_NS_FORMAT) );
		//	}
			if(alertQ.getScheduledTime()!= null){
				fields += first?"":",";
				fields += "SCHEDULED_TIME";
				values += first?" SYSDATE ":",SYSDATE ";
				first = false;
//				arPrepstmt.add(DataType.TIMESTAMP,PrepstmtDTO.getDateStringFormat(alertQ.getScheduledTime() , PrepstmtDTO.DATE_NS_FORMAT) );
			}
			if(alertQ.getDeliveredStatus()!= null){
				fields += first?"":",";
				fields += "DELIVERED_STATUS";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getDeliveredStatus() );
			}
			if(alertQ.getMobileNo()!= null){
				fields += first?"":",";
				fields += "MOBILE_NO";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getMobileNo());
			}
			if(alertQ.getEmailTo()!= null){
				fields += first?"":",";
				fields += "EMAIL_TO";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getEmailTo());
			}
			if(alertQ.getEmailTemplateId()!= null){
				fields += first?"":",";
				fields += "EMAIL_TEMPLATE_ID";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, alertQ.getEmailTemplateId());
			}

			if(alertQ.getRetryCount()!= null){
				fields += first?"":",";
				fields += "RETRY_COUNT";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.INT, String.valueOf(alertQ.getRetryCount() ));
			}
			
			if(alertQ.getBulkEmailMemberNo()!= null){
				fields += first?"":",";
				fields += "BULK_EMAIL_MEMBER_NO";
				values += first?"?":",?";
				first = false;
				arPrepstmt.add(DataType.STRING, String.valueOf(alertQ.getBulkEmailMemberNo() ));
			}
			
			String qry = "insert into ALERT_QUEUE ("+fields+") values ("+values+")";
			logger.debug(arPrepstmt.toString(qry));
			 result = db.executePreparedUpdate(qry, arPrepstmt);
			
		
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static void deleteOTPDetails(String nric) {
		 DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String qry = null;
		try {
			qry = "delete from OTP_USER_DTL where LOGIN_USER_ID = ?";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, nric);
			db.executePreparedUpdate(qry, arPrepstmt);

		} catch (Exception e) {
			logger.error("DAO error" + e + " in " + qry);
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
	}

	public static List<AlertQueue> selectAlertQueue(String alertType) {
		 DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String qry = null;
		List<AlertQueue> alertQList = new ArrayList<AlertQueue>();
		try {
			qry = "select ALERT_Q_ID,ALERT_TYPE,USER_ID,SMS_TEXT,EMAIL_TEXT, CREATE_TIME, SCHEDULED_TIME,DELIVERED_STATUS,RETRY_COUNT,a.MOBILE_NO, EMAIL_TO, EMAIL_TEMPLATE_ID from ALERT_QUEUE a where SCHEDULED_TIME < SYSDATE and NVL(RETRY_COUNT,0) < 6 and NVL(DELIVERED_STATUS,'N') <> 'Y' and ALERT_TYPE=? ";
			if(alertType.equals("SMS")){
				qry+="and MOBILE_NO is not null and SMS_TEXT is not null";
			}else{
				qry += "and EMAIL_TO is not null and EMAIL_TEXT is not null";
			}
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, alertType);
			crs = db.executePreparedQuery(qry, arPrepstmt );
			while(crs.next()){
				AlertQueue alertQ = new AlertQueue();
				alertQ.setAlertQId(crs.getInt("ALERT_Q_ID"));
				alertQ.setAlertType(crs.getString("ALERT_TYPE"));
				alertQ.setUserId(crs.getString("USER_ID"));
				alertQ.setSmsText(crs.getString("SMS_TEXT"));
				alertQ.setEmailText(crs.getString("EMAIL_TEXT"));
				alertQ.setCreateTime(crs.getDate("CREATE_TIME"));
				alertQ.setScheduledTime(crs.getDate("SCHEDULED_TIME"));
				alertQ.setDeliveredStatus(crs.getString("DELIVERED_STATUS"));
				alertQ.setRetryCount(crs.getShort("RETRY_COUNT"));
				alertQ.setMobileNo(crs.getString("MOBILE_NO"));
				alertQ.setEmailTo(crs.getString("EMAIL_TO"));
				alertQ.setEmailTemplateId(crs.getString("EMAIL_TEMPLATE_ID"));
				alertQ.setBulkEmailMemberNo(crs.getString("USER_ID"));
				alertQList.add(alertQ);
			}

		} catch (Exception e) {
			logger.error("DAO error" + e + " in " + qry);
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		return alertQList;
	}

	public static void updateAlertQueue(AlertQueue alertQueue) {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String qry = null;
		try {
			qry = "update ALERT_QUEUE set DELIVERED_STATUS = ?, RETRY_COUNT=? where ALERT_Q_ID = ?";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.STRING, alertQueue.getDeliveredStatus());
			arPrepstmt.add(DataType.INT, alertQueue.getRetryCount());
			arPrepstmt.add(DataType.INT, alertQueue.getAlertQId());
			
			logger.debug(arPrepstmt.toString(qry));
			
			  db.executePreparedUpdate(qry, arPrepstmt);

		} catch (Exception e) {
			logger.error("DAO error" + e + " in " + qry);
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
	}

	public static void deleteAlertQueueByPrimaryKey(Integer alertQId) {
		 DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String qry = null;
		try {
			String qry_bak = "insert into ALERT_QUEUE_HST value (select * from ALERT_QUEUE where ALERT_Q_ID = ?)";
			qry = "delete from ALERT_QUEUE where ALERT_Q_ID = ?";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			arPrepstmt.add(DataType.INT, alertQId);
			db.executePreparedUpdate(qry_bak, arPrepstmt);
			db.executePreparedUpdate(qry, arPrepstmt);

		} catch (Exception e) {
			logger.error("DAO error" + e + " in " + qry);
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
	}

	public static int deleteOldAlertsQueue() {
		DBConnector db = new DBConnector();
		CachedRowSet crs = null;
		String qry = null;
		int res = -1;
		try {
			qry = "delete from ALERT_QUEUE where  SCHEDULED_TIME  < sysdate - 7";
			PrepstmtDTOArray arPrepstmt = new PrepstmtDTOArray();
			//arPrepstmt.add(DataType.STRING, "");
			res = db.executePreparedUpdate(qry, arPrepstmt);

		} catch (Exception e) {
			logger.error("DAO error" + e + " in " + qry);
		} finally {
			if (crs != null) {
				try {
					crs.close();
					crs = null;
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		return res;
	}
}
