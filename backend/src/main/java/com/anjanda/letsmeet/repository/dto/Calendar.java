package com.anjanda.letsmeet.repository.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Calendar {
	private int cNo;
	private int cUNo;
	private String cDate;
	private String cContent;
	
	public int getcNo() {
		return cNo;
	}
	public void setcNo(int cNo) {
		this.cNo = cNo;
	}
	public int getcUNo() {
		return cUNo;
	}
	public void setcUNo(int cUNo) {
		this.cUNo = cUNo;
	}
	public String getcDate() {
		return cDate;
	}
	public void setcDate(String cDate) {
		this.cDate = cDate;
	}
	public String getcContent() {
		return cContent;
	}
	public void setcContent(String cContent) {
		this.cContent = cContent;
	}
	public Calendar() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Calendar(int cNo, int cUNo, String cDate, String cContent) {
		super();
		this.cNo = cNo;
		this.cUNo = cUNo;
		this.cDate = cDate;
		this.cContent = cContent;
	}
	
}
