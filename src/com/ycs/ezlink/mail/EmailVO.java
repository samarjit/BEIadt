package com.ycs.ezlink.mail;

import java.io.File;
import java.util.Map;

public abstract class EmailVO {
	protected String senderName;
	protected String receiverName;
	protected String sendDate;
	protected String quotes;
	protected String senderEmailAddress;
	protected String[] toAddress;
	protected String vmTemplate;
	protected File attachment;
	protected String[] toCC;
	protected String[] toBcc;
	protected String subject;
	private String ftlTemplate;
	private String message;
	private String mobileNo;
	private Map<String, String> variables;
	private String bulkEmailMemberNo;
	
	public String getQuotes() {
		return this.quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmailAddress() {
		return this.senderEmailAddress;
	}

	public void setSenderEmailAddress(String senderEmailAddress) {
		this.senderEmailAddress = senderEmailAddress;
	}

	public String[] getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String getVmTemplate() {
		return this.vmTemplate;
	}

	public void setVmTemplate(String vmTemplate) {
		this.vmTemplate = vmTemplate;
	}

	public File getAttachment() {
		return this.attachment;
	}

	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public String[] getToBcc() {
		return this.toBcc;
	}

	public void setToBcc(String[] toBcc) {
		this.toBcc = toBcc;
	}

	public String[] getToCC() {
		return this.toCC;
	}

	public void setToCC(String[] toCC) {
		this.toCC = toCC;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFtlTemplate() {

		return ftlTemplate;
	}

	public void setFtlTemplate(String ftlTemplate) {
		this.ftlTemplate = ftlTemplate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setBulkEmailMemberNo(String bulkEmailMemberNo) {
		this.bulkEmailMemberNo = bulkEmailMemberNo;
	}

	public String getBulkEmailMemberNo() {
		return bulkEmailMemberNo;
	}

}