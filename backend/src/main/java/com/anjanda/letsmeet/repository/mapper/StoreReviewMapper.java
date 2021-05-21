package com.anjanda.letsmeet.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anjanda.letsmeet.repository.dto.StoreReview;

@Mapper
public interface StoreReviewMapper {
	/* C :: 한 가게의 리뷰 추가 메소드 */
	public int insertStoreReview(StoreReview storeReview) throws Exception;

	/* R :: 한 가게의 모든 리뷰 조회 메소드 */
	public List<StoreReview> selectStoreReview(int srSNo) throws Exception;
	
	/* U :: 리뷰 작성자만 수정 가능한 수정 메소드 */
	public int updateStoreReview(StoreReview storeReview) throws Exception;
	
	/* D :: 리뷰 작성자 본인 것을 삭제 하는 메소드 */
	public int deleteStoreReview(StoreReview storeReview) throws Exception;
}
