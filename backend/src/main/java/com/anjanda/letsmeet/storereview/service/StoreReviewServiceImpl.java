package com.anjanda.letsmeet.storereview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.StoreReview;
import com.anjanda.letsmeet.repository.mapper.StoreReviewMapper;

@Service
public class StoreReviewServiceImpl implements StoreReviewService {

	/* 유저 매퍼 객체 불러오기 */
	@Autowired
	private StoreReviewMapper mapper;

	/* C :: 가게의 리뷰 생성 */
	@Override
	public int createStoreReview(StoreReview review) throws Exception {
		return mapper.insertStoreReview(review);
	}
	/* R :: 가게의 리뷰 조회 */
	@Override
	public List<StoreReview> reviewStoreReview(int srSNo) throws Exception {
		return mapper.selectStoreReview(srSNo);
	}
	/* U :: 가게의 리뷰 수정(본인한정) */
	@Override
	public int updateStoreReview(StoreReview review) throws Exception {
		return mapper.updateStoreReview(review);
	}
	/* D :: 가게의 리뷰 삭제(본인한정) */
	@Override
	public int deleteStoreReview(StoreReview review) throws Exception {
		return mapper.deleteStoreReview(review);
	}
	
}
