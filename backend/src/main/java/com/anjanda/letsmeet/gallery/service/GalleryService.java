package com.anjanda.letsmeet.gallery.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.Gallery;

public interface GalleryService {

	/* C :: 추억 공유 사진 추가 */
	int insertGallery(Gallery gallery) throws Exception;

	/* R :: 추억 공유 사진 조회 */
	List<Gallery> selectGallery(int gMrNo) throws Exception;

	/* U :: 추억 공유 사진 수정 */
	int updateGallery(Gallery gallery) throws Exception;

	/* D :: 추억 공유 사진 삭제 */
	int deleteGallery(Gallery gallery) throws Exception;

}