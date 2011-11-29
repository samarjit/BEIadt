package com.ycs.ezlink.mybatis.dto;

import java.util.Date;

public class AlertQueue {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.ALERT_Q_ID
     *
     * @mbggenerated
     */
    private Integer alertQId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.ALERT_TYPE
     *
     * @mbggenerated
     */
    private String alertType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.USER_ID
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.SMS_TEXT
     *
     * @mbggenerated
     */
    private String smsText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.EMAIL_TEXT
     *
     * @mbggenerated
     */
    private String emailText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.CREATE_TIME
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.SCHEDULED_TIME
     *
     * @mbggenerated
     */
    private Date scheduledTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.DELIVERED_STATUS
     *
     * @mbggenerated
     */
    private String deliveredStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.RETRY_COUNT
     *
     * @mbggenerated
     */
    private Short retryCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.MOBILE_NO
     *
     * @mbggenerated
     */
    private String mobileNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.EMAIL_TO
     *
     * @mbggenerated
     */
    private String emailTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.EMAIL_TEMPLATE_ID
     *
     * @mbggenerated
     */
    private String emailTemplateId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ALERT_QUEUE.BULK_EMAIL_MEMBER_NO
     *
     * @mbggenerated
     */
    private String bulkEmailMemberNo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.ALERT_Q_ID
     *
     * @return the value of ALERT_QUEUE.ALERT_Q_ID
     *
     * @mbggenerated
     */
    public Integer getAlertQId() {
        return alertQId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.ALERT_Q_ID
     *
     * @param alertQId the value for ALERT_QUEUE.ALERT_Q_ID
     *
     * @mbggenerated
     */
    public void setAlertQId(Integer alertQId) {
        this.alertQId = alertQId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.ALERT_TYPE
     *
     * @return the value of ALERT_QUEUE.ALERT_TYPE
     *
     * @mbggenerated
     */
    public String getAlertType() {
        return alertType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.ALERT_TYPE
     *
     * @param alertType the value for ALERT_QUEUE.ALERT_TYPE
     *
     * @mbggenerated
     */
    public void setAlertType(String alertType) {
        this.alertType = alertType == null ? null : alertType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.USER_ID
     *
     * @return the value of ALERT_QUEUE.USER_ID
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.USER_ID
     *
     * @param userId the value for ALERT_QUEUE.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.SMS_TEXT
     *
     * @return the value of ALERT_QUEUE.SMS_TEXT
     *
     * @mbggenerated
     */
    public String getSmsText() {
        return smsText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.SMS_TEXT
     *
     * @param smsText the value for ALERT_QUEUE.SMS_TEXT
     *
     * @mbggenerated
     */
    public void setSmsText(String smsText) {
        this.smsText = smsText == null ? null : smsText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.EMAIL_TEXT
     *
     * @return the value of ALERT_QUEUE.EMAIL_TEXT
     *
     * @mbggenerated
     */
    public String getEmailText() {
        return emailText;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.EMAIL_TEXT
     *
     * @param emailText the value for ALERT_QUEUE.EMAIL_TEXT
     *
     * @mbggenerated
     */
    public void setEmailText(String emailText) {
        this.emailText = emailText == null ? null : emailText.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.CREATE_TIME
     *
     * @return the value of ALERT_QUEUE.CREATE_TIME
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.CREATE_TIME
     *
     * @param createTime the value for ALERT_QUEUE.CREATE_TIME
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.SCHEDULED_TIME
     *
     * @return the value of ALERT_QUEUE.SCHEDULED_TIME
     *
     * @mbggenerated
     */
    public Date getScheduledTime() {
        return scheduledTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.SCHEDULED_TIME
     *
     * @param scheduledTime the value for ALERT_QUEUE.SCHEDULED_TIME
     *
     * @mbggenerated
     */
    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.DELIVERED_STATUS
     *
     * @return the value of ALERT_QUEUE.DELIVERED_STATUS
     *
     * @mbggenerated
     */
    public String getDeliveredStatus() {
        return deliveredStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.DELIVERED_STATUS
     *
     * @param deliveredStatus the value for ALERT_QUEUE.DELIVERED_STATUS
     *
     * @mbggenerated
     */
    public void setDeliveredStatus(String deliveredStatus) {
        this.deliveredStatus = deliveredStatus == null ? null : deliveredStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.RETRY_COUNT
     *
     * @return the value of ALERT_QUEUE.RETRY_COUNT
     *
     * @mbggenerated
     */
    public Short getRetryCount() {
        return retryCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.RETRY_COUNT
     *
     * @param retryCount the value for ALERT_QUEUE.RETRY_COUNT
     *
     * @mbggenerated
     */
    public void setRetryCount(Short retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.MOBILE_NO
     *
     * @return the value of ALERT_QUEUE.MOBILE_NO
     *
     * @mbggenerated
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.MOBILE_NO
     *
     * @param mobileNo the value for ALERT_QUEUE.MOBILE_NO
     *
     * @mbggenerated
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.EMAIL_TO
     *
     * @return the value of ALERT_QUEUE.EMAIL_TO
     *
     * @mbggenerated
     */
    public String getEmailTo() {
        return emailTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.EMAIL_TO
     *
     * @param emailTo the value for ALERT_QUEUE.EMAIL_TO
     *
     * @mbggenerated
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo == null ? null : emailTo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.EMAIL_TEMPLATE_ID
     *
     * @return the value of ALERT_QUEUE.EMAIL_TEMPLATE_ID
     *
     * @mbggenerated
     */
    public String getEmailTemplateId() {
        return emailTemplateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.EMAIL_TEMPLATE_ID
     *
     * @param emailTemplateId the value for ALERT_QUEUE.EMAIL_TEMPLATE_ID
     *
     * @mbggenerated
     */
    public void setEmailTemplateId(String emailTemplateId) {
        this.emailTemplateId = emailTemplateId == null ? null : emailTemplateId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ALERT_QUEUE.BULK_EMAIL_MEMBER_NO
     *
     * @return the value of ALERT_QUEUE.BULK_EMAIL_MEMBER_NO
     *
     * @mbggenerated
     */
    public String getBulkEmailMemberNo() {
        return bulkEmailMemberNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ALERT_QUEUE.BULK_EMAIL_MEMBER_NO
     *
     * @param bulkEmailMemberNo the value for ALERT_QUEUE.BULK_EMAIL_MEMBER_NO
     *
     * @mbggenerated
     */
    public void setBulkEmailMemberNo(String bulkEmailMemberNo) {
        this.bulkEmailMemberNo = bulkEmailMemberNo == null ? null : bulkEmailMemberNo.trim();
    }
}