package com.anjanda.letsmeet.friend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.friend.service.FriendService;
import com.anjanda.letsmeet.repository.dto.User;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 친구 추가/삭제를 위한 Friend 컨트롤러 클래스
 * @Description
 *	- 마이페이지에 들어간 후, 친구관리 목록에서 활용됨
 *	- 이름or이메일 : 추가할 친구 입력값
 *	- 수정필요함. 
 */

@RestController
@RequestMapping("/mypage/friend")
public class FriendController {

	/* Friend 서비스 객체 불러오기 */
	@Autowired
	private FriendService friendService;
	
	/* C :: 사용자의 친구 추가 */
	@PostMapping("/add")
	public ResponseEntity<String> createFriend(@PathVariable(name="me") int me, @RequestBody User user) throws Exception {
		System.out.println("사용자 : " + me + ", 친구 요청 받은 사람의 uno : " + user.getuNo());
		
		if(friendService.createFriend(me, user) > 0) {
			return new ResponseEntity<String>("친구 추가 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("친구 추가 실패", HttpStatus.NO_CONTENT);
	}
	
	/* R :: 사용자의 친구 목록 조회*/
	@GetMapping("")
	public ResponseEntity<List<User>> reviewFriend(@RequestBody User user) throws Exception {
		return new ResponseEntity<List<User>>(friendService.reviewFriend(user), HttpStatus.OK);
	}

	/* R :: 사용자의 친구 검색 조회 */
	@GetMapping("/search")
	public ResponseEntity<List<User>> reviewSearchFriend(String searchEmail) throws Exception {
		System.out.println("검색할 친구 이메일 : " + searchEmail);
		return new ResponseEntity<List<User>>(friendService.reviewSearchFriend(searchEmail), HttpStatus.OK);
	}
	
	
	/* D :: 사용자의 친구 삭제 */
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteFriend(@PathVariable(name="me") int me, @RequestBody User user) throws Exception{
		System.out.println("사용자 : " + me + ", 삭제할 친구의 Uno : " + user.getuNo());
		if(friendService.deleteFriend(me, user)) {
			return new ResponseEntity<String>("친구 삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("친구 삭제 실패", HttpStatus.NO_CONTENT);
	}
}
