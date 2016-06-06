package com.csc.mail.jsh.db.hibernate.tools;

public class PageModel {
	
	private int currentPage = 1; //current page.
	private int pageRows = 100;    //row number shows in each page.
	private int allRows;     //number of all record.
	private int allPages;    //number of pages.
	
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageRows() {
		return pageRows;
	}
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}
	public int getAllRows() {
		return allRows;
	}
	public void setAllRows(int allRows) {
		this.allRows = allRows;
	}
	public int getAllPages() {
		return allPages;
	}
	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}	
}
