package com.ycs.ezlink.dto;

public class JobDTO {

	private String jobId;
	private String cronExp;
	private String jobDesc;

	public void setJobId(String res) {
		this.jobId = res;
	}

	public void setCronExp(String res) {
		this.cronExp = res;
	}

	public void setJobDesc(String res) {
		this.jobDesc = res;
	}

	public String getJobId() {
		return jobId;
	}

	public String getCronExp() {
		return cronExp;
	}

	public String getJobDesc() {
		return jobDesc;
	}
	
	
	
}
