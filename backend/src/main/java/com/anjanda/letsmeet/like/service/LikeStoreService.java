package com.anjanda.letsmeet.like.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.LikeStore;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 찜 서비스 인터페이스 클래스
 * @Description
 *
 */

public interface LikeStoreService {

	/* C :: 유저의 찜목록에서 가게 추가 메소드 */
	int insertLikeStore(LikeStore likeStore) throws Exception;

	/* R :: 유저의 찜목록 조회 메소드 */
	List<LikeStore> selectLikeStore(int uNo) throws Exception;

	/* D :: 유저의 찜목록에서 가게 삭제 메소드 */
	boolean deleteLikeStore(LikeStore likeStore) throws Exception;

}