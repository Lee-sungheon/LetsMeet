package com.anjanda.letsmeet.gallery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.Gallery;
import com.anjanda.letsmeet.repository.mapper.GalleryMapper;

@Service
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	private GalleryMapper galleryMapper;
	
	/* C :: 추억 공유 사진 추가 */
	@Override
	public int insertGallery(Gallery gallery) throws Exception {
		return galleryMapper.insertGallery(gallery);
	}
	
	/* R :: 추억 공유 사진 조회 */
	@Override
	public List<Gallery> selectGallery(int gMrNo) throws Exception {
		return galleryMapper.selectGallery(gMrNo);
	}
	
	/* U :: 추억 공유 사진 수정 */
	@Override
	public int updateGallery(Gallery gallery) throws Exception {
		return galleryMapper.updateGallery(gallery);
	}
	
	/* D :: 추억 공유 사진 삭제 */
	@Override
	public int deleteGallery(Gallery gallery) throws Exception {
		return galleryMapper.deleteGallery(gallery);
	}
}
