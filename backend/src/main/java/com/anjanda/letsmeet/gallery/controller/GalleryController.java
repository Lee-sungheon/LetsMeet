package com.anjanda.letsmeet.gallery.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anjanda.letsmeet.gallery.service.GalleryService;
import com.anjanda.letsmeet.repository.dto.Gallery;

@RestController
@RequestMapping("/memory")
public class GalleryController {
	
	@Value("${upload-images-path}")
	String path;
	
	@Autowired
	private GalleryService galleryService;
	
	/* C :: 추억 공유 사진 추가 */
	@PostMapping("/add")
	public ResponseEntity<String> addGallery(@RequestParam("iFile") MultipartFile file, @RequestParam("gMrNo") int gMrNo, @RequestParam("gUNo") int gUNo) throws Exception {
		String gImage = path + "/" + "gallery-" + gMrNo + "-" + gUNo + "-" + file.getOriginalFilename();
//		String gImage = "C:/" + "gallery-" + gMrNo + "-" + file.getOriginalFilename();
		
		File dest = new File(gImage);
		file.transferTo(dest);

		gImage = "https://i4d107.p.ssafy.io/images/" + "gallery-" + gMrNo + "-" + gUNo + "-" + file.getOriginalFilename();

		Gallery gallery = new Gallery();
		gallery.setgName(gImage);
		gallery.setgMrNo(gMrNo);
		gallery.setgUNo(gUNo);
		
		System.out.println("사진 경로명 : " + gallery.getgName());
		if(galleryService.insertGallery(gallery) > 0)
			return new ResponseEntity<String>("추억에 사진 추가 성공", HttpStatus.OK);
		return new ResponseEntity<String>("추억에 사진 추가 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 추억 공유 사진 조회 */
	@GetMapping("/detail")
	public ResponseEntity<?> reviewGallery(@RequestParam int gMrNo) throws Exception {
		if(galleryService.selectGallery(gMrNo) != null) {
			return new ResponseEntity<List<Gallery>>(galleryService.selectGallery(gMrNo), HttpStatus.OK);
		}
		return new ResponseEntity<String>("조회 실패", HttpStatus.NO_CONTENT);
	}
	
//	/* U :: 추억 공유 사진 수정 */
//	@PutMapping("/edit")
//	public ResponseEntity<String> updateGallery(Gallery gallery) throws Exception {
//		System.out.println("수정될 사진 번호 " + gallery.getgNo());
//		if(galleryService.updateGallery(gallery) > 0)
//			return new ResponseEntity<String>("사진 수정 성공", HttpStatus.OK);
//		return new ResponseEntity<String>("사진 수정 실패", HttpStatus.NO_CONTENT);
//	}
	
	/* D :: 추억 공유 사진 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteGallery(Gallery gallery) throws Exception {
		System.out.println("수정될 사진 번호 : " + gallery.getgNo());
		if(galleryService.deleteGallery(gallery) > 0)
			return new ResponseEntity<String>("추억사진 삭제 성공", HttpStatus.OK);
		return new ResponseEntity<String>("추억사진 삭제 실패", HttpStatus.NO_CONTENT);
	}
}
