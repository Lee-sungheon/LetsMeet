package com.anjanda.letsmeet.map.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.Store;

/**
 * 
 * @Date : 2021. 2. 2.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 가게 서비스 인터페이스 클래스
 * @Description
 *
 */
public interface StoreService {

	/* '동 이름'으로 가게 전체 조회 */
	List<Store> StoreByDong(String dong) throws Exception;

	/* '좌표 값' 으로 해당 가게 조회 */
	Store StoreByPoint(String lat, String lng) throws Exception;

	/* '스토어 넘버' 으로 해당 가게 조회 */
	Store StoreByNo(int sno) throws Exception;
	
	/* '중간 좌표 값'으로 일정 반경 내 가게들 조회 */
	List<Store> StoreByMidPoint(Store store);
	
	/* C :: 가게 등록 메소드 */
	int createStore(Store store) throws Exception;
	
	/* R :: '동 이름'으로 가게 전체 조회 */
	List<Store> reviewStoreByDong(String dong) throws Exception;

	/* R :: '좌표 값' 으로 해당 가게 조회 */
	Store reviewStoreByPoint(String lat, String lng) throws Exception;
	

	/* R :: '중간 좌표 값'으로 일정 반경 내 가게들 조회 */
	List<Store> reviewStoreByMidPoint(String lat, String lng);

	/* R :: '가게명 '으로 가게 전체 조회 */
	List<Store> StoreByName(String sName);

	/* R :: '디테일'으로 가게 전체 조회 */
	List<Store> StoreByDetail(String detail, String x, String y);



}