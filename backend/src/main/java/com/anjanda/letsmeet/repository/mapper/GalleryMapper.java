package com.anjanda.letsmeet.repository.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.anjanda.letsmeet.repository.dto.Gallery;

@Mapper
public interface GalleryMapper {
	
	/* C :: 추억 공유 사진 추가 */
	public int insertGallery(Gallery gallery) throws Exception;
	
	/* R :: 추억 공유 사진 조회 */
	public List<Gallery> selectGallery(int gMrNo) throws Exception;
	
	/* U :: 추억 공유 사진 수정 */
	public int updateGallery(Gallery gallery) throws Exception;
	
	/* D :: 추억 공유 사진 삭제 */
	public int deleteGallery(Gallery gallery) throws Exception;
}
