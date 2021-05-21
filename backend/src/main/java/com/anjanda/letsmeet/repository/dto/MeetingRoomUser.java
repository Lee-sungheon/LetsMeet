package com.anjanda.letsmeet.repository.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 김동빈, 김지현, 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 데이터 VO 클래스
 * @Description
 *	- 롬복 사용해서 getter, setter 사용함 (그래서 아래 메소드들 필요없음)
 */

@Getter
@Setter
@ToString
public class MeetingRoomUser{
	
	private int mruMrNo;
	private int mruUNo;
	private String mruUserLat;
	private String mruUserLng;
	private String mruUserDates;
	public int getMruMrNo() {
		return mruMrNo;
	}
	public void setMruMrNo(int mruMrNo) {
		this.mruMrNo = mruMrNo;
	}
	public int getMruUNo() {
		return mruUNo;
	}
	public void setMruUNo(int mruUNo) {
		this.mruUNo = mruUNo;
	}
	public String getMruUserLat() {
		return mruUserLat;
	}
	public void setMruUserLat(String mruUserLat) {
		this.mruUserLat = mruUserLat;
	}
	public String getMruUserLng() {
		return mruUserLng;
	}
	public void setMruUserLng(String mruUserLng) {
		this.mruUserLng = mruUserLng;
	}
	public String getMruUserDates() {
		return mruUserDates;
	}
	public void setMruUserDates(String mruUserDates) {
		this.mruUserDates = mruUserDates;
	}
	
	
	
	
}
