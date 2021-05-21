package com.anjanda.letsmeet.imageupload.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.Gallery;
import com.anjanda.letsmeet.repository.dto.MeetingRoom;
import com.anjanda.letsmeet.repository.dto.User;
import com.anjanda.letsmeet.repository.mapper.ImageMapper;

/**
 * 
 * @Date : 2021. 2. 8.
 * @Team : AnJanDa
 * @author : 김동빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 이미지 업로드 서비스 클래스
 * @Description
 *
 */

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
	@Autowired
	private ImageMapper mapper;

	@Override
	public int InsertImage(Gallery image) throws Exception {
		return mapper.insertImage(image);
	}

	@Override
	public List<Gallery> selectImageList() {
		return mapper.selectImageList();
	}

	@Override
	public int InsertGalleryImage(Gallery gallery) throws Exception {
		return mapper.insertGalleryImage(gallery);
	}

	@Override
	public int UpdateProfile(User user) throws Exception {
		return mapper.updateProfile(user);
	}

	@Override
	public int UpdateMeetingroomImage(MeetingRoom meetingroom) throws Exception {
		return mapper.updateMeetingroomImage(meetingroom);
	}

}
