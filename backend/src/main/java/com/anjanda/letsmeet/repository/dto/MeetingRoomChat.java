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
public class MeetingRoomChat {
	private int mrcNo;
	private int mrcMrNo;
	private int mrcUNo;
	private String mrcContent;
	private String mrcDate;
	public MeetingRoomChat() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getMrcNo() {
		return mrcNo;
	}
	public void setMrcNo(int mrcNo) {
		this.mrcNo = mrcNo;
	}
	public int getMrcMrNo() {
		return mrcMrNo;
	}
	public void setMrcMrNo(int mrcMrNo) {
		this.mrcMrNo = mrcMrNo;
	}
	public int getMrcUNo() {
		return mrcUNo;
	}
	public void setMrcUNo(int mrcUNo) {
		this.mrcUNo = mrcUNo;
	}
	public String getMrcContent() {
		return mrcContent;
	}
	public void setMrcContent(String mrcContent) {
		this.mrcContent = mrcContent;
	}
	public String getMrcDate() {
		return mrcDate;
	}
	public void setMrcDate(String mrcDate) {
		this.mrcDate = mrcDate;
	}
	@Override
	public String toString() {
		return "MeetingRoomChat [mrcNo=" + mrcNo + ", mrcMrNo=" + mrcMrNo + ", mrcUNo=" + mrcUNo + ", mrcContent="
				+ mrcContent + ", mrcDate=" + mrcDate + "]";
	}
	
	
}
