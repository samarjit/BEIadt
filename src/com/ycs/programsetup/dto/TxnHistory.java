package com.ycs.programsetup.dto;
// default package
// Generated Sep 18, 2011 10:37:32 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TxnHistory generated by hbm2java
 */
public class TxnHistory implements java.io.Serializable {

	private BigDecimal txnId;
	private String userId;
	private String cardNo;
	private Date txnTime;
	private String txnType;
	private BigDecimal txnAmount;
	private BigDecimal balAmount;
	private BigDecimal agentId;
	private BigDecimal acqId;

	public TxnHistory() {
	}

	public TxnHistory(BigDecimal txnId) {
		this.txnId = txnId;
	}

	public TxnHistory(BigDecimal txnId, String userId, String cardNo, Date txnTime, String txnType, BigDecimal txnAmount, BigDecimal balAmount, BigDecimal agentId, BigDecimal acqId) {
		this.txnId = txnId;
		this.userId = userId;
		this.cardNo = cardNo;
		this.txnTime = txnTime;
		this.txnType = txnType;
		this.txnAmount = txnAmount;
		this.balAmount = balAmount;
		this.agentId = agentId;
		this.acqId = acqId;
	}

	public BigDecimal getTxnId() {
		return this.txnId;
	}

	public void setTxnId(BigDecimal txnId) {
		this.txnId = txnId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getTxnTime() {
		return this.txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	public String getTxnType() {
		return this.txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public BigDecimal getTxnAmount() {
		return this.txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public BigDecimal getBalAmount() {
		return this.balAmount;
	}

	public void setBalAmount(BigDecimal balAmount) {
		this.balAmount = balAmount;
	}

	public BigDecimal getAgentId() {
		return this.agentId;
	}

	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	public BigDecimal getAcqId() {
		return this.acqId;
	}

	public void setAcqId(BigDecimal acqId) {
		this.acqId = acqId;
	}

}
