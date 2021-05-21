package com.anjanda.letsmeet.like.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.like.service.LikeStoreService;
import com.anjanda.letsmeet.repository.dto.LikeStore;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 사용자의 가게 찜 관련 컨트롤러 클래스
 * @Description
 *
 */

@RestController
@RequestMapping("/mypage/likestore")
public class LikeStoreController {

	/* 찜 서비스 객체 불러오기 */
	@Autowired
	private LikeStoreService likeStoreService;
	
	/* C :: 유저의 가게 찜 추가 */
	@PostMapping("/add")
	public ResponseEntity<String> createLikeStore(LikeStore likeStore) throws Exception {
		System.out.println("찜한 사용자 번호 " + likeStore.getLsUNo() + "찜한 가게 번호 : " + likeStore.getLsSNo());
		if(likeStoreService.insertLikeStore(likeStore) > 0) {
			return new ResponseEntity<String>("가게 찜 추가 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("가게 찜 추가 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 유저의 가게 찜 목록 조회 */
	@GetMapping("")
	public ResponseEntity<List<LikeStore>> reviewLikeStore(int uNo) throws Exception {
		return new ResponseEntity<List<LikeStore>> (likeStoreService.selectLikeStore(uNo), HttpStatus.OK);
	}

	
	/* D :: 유저의 가게 찜 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteLikeStore(LikeStore likeStore) throws Exception {
		System.out.println("삭제할 찜가게 정보 : " + likeStore.getLsSNo());
		if(likeStoreService.deleteLikeStore(likeStore)) {
			return new ResponseEntity<String>("찜 목록에서 가게 삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("찜 목록에서 가게 삭제 실패", HttpStatus.NO_CONTENT);
	}
}
