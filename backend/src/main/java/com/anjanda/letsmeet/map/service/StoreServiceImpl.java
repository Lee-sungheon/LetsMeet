package com.anjanda.letsmeet.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.Store;
import com.anjanda.letsmeet.repository.mapper.StoreMapper;

/**
 * 
 * @Date : 2021. 2. 2.
 * @Team : AnJanDa
 * @author : 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 가게 서비스 클래스
 * @Description
 *
 */
@Service
public class StoreServiceImpl implements StoreService {
	
	/* 가게 매퍼 객체 불러오기 */
	@Autowired
	private StoreMapper mapper;

	/* R :: '동 이름'으로 가게 전체 조회 */
	@Override
	public List<Store> StoreByDong(String dong) throws Exception {
		return mapper.selectStoreByDong(dong);
	}

	/* R :: '좌표 값' 으로 해당 가게 조회 */
	@Override
	public Store StoreByPoint(String lat, String lng) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStoreByPoint(lat, lng);
	}
	
	/* R :: '스토어 넘버' 으로 해당 가게 조회 */
	@Override
	public Store StoreByNo(int sno) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectStoreByNo(sno);
	}

	/* R :: '중간 좌표 값'으로 일정 반경 내 가게들 조회 */
	@Override
	public List<Store> StoreByMidPoint(Store store) {
		// TODO Auto-generated method stub
		return mapper.selectStoreByMidPoint(store);
	}
	
	// 가게 등록
	@Override
	public int createStore(Store store) throws Exception {
		// TODO Auto-generated method stub
		return mapper.createStore(store);
	}

	@Override
	public List<Store> reviewStoreByDong(String dong) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store reviewStoreByPoint(String lat, String lng) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Store> reviewStoreByMidPoint(String lat, String lng) {
		// TODO Auto-generated method stub
		return null;
	}

	/* R :: '가게 명'으로 가게 전체 조회 */
	@Override
	public List<Store> StoreByName(String sName) {
		// TODO Auto-generated method stub
		return mapper.selectStoreByName(sName);
	}

	@Override
	public List<Store> StoreByDetail(String detail, String x, String y) {
		// TODO Auto-generated method stub
		return mapper.selectStoreByDetail(detail,x,y);
	}



}
