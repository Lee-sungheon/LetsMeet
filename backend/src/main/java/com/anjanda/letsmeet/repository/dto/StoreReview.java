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
public class StoreReview {
	private int srNo;
	private int srSNo;
	private int srUNo;
	private String srContent;
	private int srScore;
	private String srTime;
	private String srImg;
	private String srUName;
	
	public StoreReview() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StoreReview(int srNo, int srSNo, int srUNo, String srContent, int srScore, String srTime, String srImg, String srUName) {
		super();
		this.srNo = srNo;
		this.srSNo = srSNo;
		this.srUNo = srUNo;
		this.srContent = srContent;
		this.srScore = srScore;
		this.srTime = srTime;
		this.srImg = srImg;
		this.srUName = srUName;
	}
	
	
}
