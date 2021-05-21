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
public class Friend {
	private int fNo;
	private int fRecvUNo;
	private int fSendUNo;
	public Friend() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Friend(int fNo, int fRecvUNo, int fSendUNo) {
		super();
		this.fNo = fNo;
		this.fRecvUNo = fRecvUNo;
		this.fSendUNo = fSendUNo;
	}
	public int getfNo() {
		return fNo;
	}
	public void setfNo(int fNo) {
		this.fNo = fNo;
	}
	public int getfRecvUNo() {
		return fRecvUNo;
	}
	public void setfRecvUNo(int fRecvUNo) {
		this.fRecvUNo = fRecvUNo;
	}
	public int getfSendUNo() {
		return fSendUNo;
	}
	public void setfSendUNo(int fSendUNo) {
		this.fSendUNo = fSendUNo;
	}
	@Override
	public String toString() {
		return "Friend [fNo=" + fNo + ", fRecvUNo=" + fRecvUNo + ", fSendUNo=" + fSendUNo + "]";
	}
	
	
}
