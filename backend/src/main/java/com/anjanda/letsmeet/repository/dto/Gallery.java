package com.anjanda.letsmeet.repository.dto;

import lombok.Data;
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
 *	- Data로 변경함
 */

@Data
@Getter
@Setter
@ToString

public class Gallery {
	private int gNo;
	private int gMrNo;
	private int gUNo;
	private String gName;
	
	
	public int getgNo() {
		return gNo;
	}
	public void setgNo(int gNo) {
		this.gNo = gNo;
	}
	public int getgMrNo() {
		return gMrNo;
	}
	public void setgMrNo(int gMrNo) {
		this.gMrNo = gMrNo;
	}
	public int getgUNo() {
		return gUNo;
	}
	public void setgUNo(int gUNo) {
		this.gUNo = gUNo;
	}
	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public Gallery(int gNo, int gMrNo, int gUNo, String gName) {
		super();
		this.gNo = gNo;
		this.gMrNo = gMrNo;
		this.gUNo = gUNo;
		this.gName = gName;
	}
	public Gallery() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}   
