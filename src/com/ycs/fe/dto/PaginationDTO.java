package com.ycs.fe.dto;

public class PaginationDTO {

	private int page; //pageno
	private int rows; //number of rows per page
	private String sidx; //order by sidx
	private String sord; //asc or desc
	
	public PaginationDTO(){
		
	}
	public PaginationDTO(int page,int rows,String sidx,String sord){
		this.page = page;
		this.rows = rows;
		this.sidx = sidx;
		this.sord = sord;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	
	
}
