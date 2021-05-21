package com.anjanda.letsmeet.storereview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.repository.dto.StoreReview;
import com.anjanda.letsmeet.storereview.service.StoreReviewService;

@RestController
@RequestMapping("/store/detail/review")
public class StoreReviewController {

	@Autowired
	private StoreReviewService storeReviewService;
	
	/* C :: 리뷰 생성 */
	@PostMapping("/add")
	public ResponseEntity<String> createStoreReview(StoreReview storeReview) throws Exception {
		System.out.println(storeReview.getSrContent());
		if(storeReviewService.createStoreReview(storeReview) > 0)
			return new ResponseEntity<String>("리뷰 작성 성공", HttpStatus.OK);
		return new ResponseEntity<String>("리뷰 작성 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 리뷰 조회 */
	@GetMapping("")
	public ResponseEntity<List<StoreReview>> reviewStoreReview(int srSNo) throws Exception {
		return new ResponseEntity<List<StoreReview>>(storeReviewService.reviewStoreReview(srSNo), HttpStatus.OK);
	}
	
	/* U :: 리뷰 수정 */
	@PutMapping("/edit")
	public ResponseEntity<String> updateStoreReview(StoreReview storeReview) throws Exception {
		System.out.println("수정하고자 하는 글번호:"+storeReview.getSrSNo() + "작성자:"+storeReview.getSrUNo());
		if(storeReviewService.updateStoreReview(storeReview) > 0)
			return new ResponseEntity<String>("리뷰 수정 성공", HttpStatus.OK);
		return new ResponseEntity<String>("리뷰 수정 실패", HttpStatus.NO_CONTENT);
	}
	
	/* D :: 리뷰 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteStoreReview(StoreReview storeReview) throws Exception {
		System.out.println("삭제 처리할 글" + storeReview.getSrContent());
		if(storeReviewService.deleteStoreReview(storeReview) > 0)
			return new ResponseEntity<String>("리뷰 삭제 성공", HttpStatus.OK);
		return new ResponseEntity<String>("리뷰 삭제 실패", HttpStatus.NO_CONTENT);
	}
}
