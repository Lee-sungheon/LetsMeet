package com.anjanda.letsmeet.like.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.LikeStore;
import com.anjanda.letsmeet.repository.mapper.LikeStoreMapper;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 찜 서비스 클래스
 * @Description
 *
 */

@Service
public class LikeStoreServiceImpl implements LikeStoreService {

	/* 찜가게 매퍼 객체 불러오기 */
	@Autowired
	private LikeStoreMapper likeStoreMapper;
	
	/* C :: 유저의 찜목록에서 가게 추가 메소드 */
	@Override
	public int insertLikeStore(LikeStore likeStore) throws Exception {
		return likeStoreMapper.insertLikeStore(likeStore);
	}
	
	/* R :: 유저의 찜목록 조회 메소드 */
	@Override
	public List<LikeStore> selectLikeStore(int uNo) throws Exception {
		return likeStoreMapper.selectLikeStore(uNo);
	}
	
	/* D :: 유저의 찜목록에서 가게 삭제 메소드 */
	@Override
	public boolean deleteLikeStore(LikeStore likeStore) throws Exception {
		return likeStoreMapper.deleteLikeStore(likeStore) == 1;
	}
}
