package com.anjanda.letsmeet.imageupload.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anjanda.letsmeet.imageupload.service.ImageUploadService;
import com.anjanda.letsmeet.repository.dto.Gallery;
import com.anjanda.letsmeet.repository.dto.MeetingRoom;
import com.anjanda.letsmeet.repository.dto.User;

/**
 * 
 * @Date : 2021. 2. 8.
 * @Team : AnJanDa
 * @author : 김동빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 이미지 업로드 관련 컨트롤러 클래스
 * @Description : 프로필 사진, 미팅룸 사진, 갤러리 사진 업로드
 *
 */

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/image")
public class ImageUploadController {

	@Value("${upload-images-path}")
	String path;
	
	@Autowired
	private ImageUploadService imageUploadService;

	@PutMapping("/profileUpload")
	public ResponseEntity<?> UpdateProfile(@RequestParam("iFile") MultipartFile file, @RequestParam("iUserEmail") String userEmail) throws Exception {
		String uImage = path + "/" + userEmail + "-" + file.getOriginalFilename();
//		String uImage = "C:/" + userEmail + "-" + file.getOriginalFilename();

		File dest = new File(uImage);
		file.transferTo(dest);
		
		uImage = "https://i4d107.p.ssafy.io/images/" + userEmail + "-" + file.getOriginalFilename();

		User user = new User();
		user.setuImage(uImage);
		user.setuEmail(userEmail);
		
		if(imageUploadService.UpdateProfile(user) > 0)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		
		return new ResponseEntity<User> (user, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/meetingroomImageUpload")
	public String UpdateMeetingroomImageUpload(@RequestParam("iFile") MultipartFile file, @RequestParam("mrNo") int mrNo) throws Exception {
		String mrImage = path + "/" + "mr-" + mrNo + "-" + file.getOriginalFilename();
//		String mrImage = "C:/" + "mr-" + mrNo + "-" + file.getOriginalFilename();

		File dest = new File(mrImage);
		file.transferTo(dest);
		
		mrImage = "https://i4d107.p.ssafy.io/images/" + "mr-" + mrNo + "-" + file.getOriginalFilename();
		
		MeetingRoom meetingroom = new MeetingRoom();
		meetingroom.setMrNo(mrNo);
		meetingroom.setMrImage(mrImage);
		
		return imageUploadService.UpdateMeetingroomImage(meetingroom) > 0 ? "OK" : "FAIL";
	}

	@PutMapping("/galleryImageUpload")
	public String InsertGalleryImage(@RequestParam("iFile") MultipartFile file, @RequestParam("gMrNo") int gMrNo, @RequestParam("gUNo") int gUNo) throws Exception {
		String gImage = path + "/" + "gallery-" + gMrNo + "-"  + file.getOriginalFilename();
//		String gImage = "C:/" + "gallery-" + gMrNo + "-" + file.getOriginalFilename();
		
		File dest = new File(gImage);
		file.transferTo(dest);

		gImage = "https://i4d107.p.ssafy.io/images/" + "gallery-" + gMrNo + "-"  + file.getOriginalFilename();

		Gallery gallery = new Gallery();
		gallery.setgName(gImage);
		gallery.setgMrNo(gMrNo);
		gallery.setgUNo(gUNo);
		return imageUploadService.InsertGalleryImage(gallery) > 0 ? "OK" : "FAIL";
	}

	@GetMapping("/getImageList")
	public List<Gallery> getImageList() {
		return imageUploadService.selectImageList();
	}
}
