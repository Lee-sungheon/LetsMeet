package com.anjanda.letsmeet.storereview.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.StoreReview;

public interface StoreReviewService {
	
	/* C :: 가게의 리뷰 생성 */
	int createStoreReview(StoreReview review) throws Exception;

	/* R :: 가게의 리뷰 조회 */
	List<StoreReview> reviewStoreReview(int srSNo) throws Exception;

	/* U :: 가게의 리뷰 수정(본인한정) */
	int updateStoreReview(StoreReview review) throws Exception;

	/* D :: 가게의 리뷰 삭제(본인한정) */
	int deleteStoreReview(StoreReview review) throws Exception;

}