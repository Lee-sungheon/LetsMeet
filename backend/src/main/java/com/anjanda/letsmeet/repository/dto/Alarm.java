package com.anjanda.letsmeet.repository.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Alarm {
	private int aNo;
	private String aTitle;
	private int aRecvUNo;
	private String aContent;
	private String aSenderImage;
	private String aTime;
	public Alarm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Alarm(int aNo, String aTitle, int aRecvUNo, String aContent, String aSenderImage, String aTime) {
		super();
		this.aNo = aNo;
		this.aTitle = aTitle;
		this.aRecvUNo = aRecvUNo;
		this.aContent = aContent;
		this.aSenderImage = aSenderImage;
		this.aTime = aTime;
	}
	public int getaNo() {
		return aNo;
	}
	public void setaNo(int aNo) {
		this.aNo = aNo;
	}
	public String getaTitle() {
		return aTitle;
	}
	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}
	public int getaRecvUNo() {
		return aRecvUNo;
	}
	public void setaRecvUNo(int aRecvUNo) {
		this.aRecvUNo = aRecvUNo;
	}
	public String getaContent() {
		return aContent;
	}
	public void setaContent(String aContent) {
		this.aContent = aContent;
	}
	public String getaSenderImage() {
		return aSenderImage;
	}
	public void setaSenderImage(String aSenderImage) {
		this.aSenderImage = aSenderImage;
	}
	public String getaTime() {
		return aTime;
	}
	public void setaTime(String aTime) {
		this.aTime = aTime;
	}
	
	
}


