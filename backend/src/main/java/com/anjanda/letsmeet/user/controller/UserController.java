package com.anjanda.letsmeet.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.repository.dto.MeetingRoom;
import com.anjanda.letsmeet.repository.dto.User;
import com.anjanda.letsmeet.user.jwt.JwtService;
import com.anjanda.letsmeet.user.service.UserService;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 유저 컨트롤러 클래스
 * @Description - 유저 관련 컨트롤러 클래스 - REST API 적용
 */	

@RestController
@RequestMapping("/user")
public class UserController {

	/* JWT 객체 불러오기 */
	@Autowired
	private JwtService jwtService;

	/* 유저 서비스 객체 불러오기 */
	@Autowired
	private UserService userService;

	/* C :: 회원 가입 */
	@CrossOrigin(origins = "*")
	@PostMapping("/join")
	public ResponseEntity<String> createUser(User user) throws Exception {
		System.out.println(user + "유저정보" + user.getuName());
		if (userService.createUser(user) > 0) { // 해당 메소드가 int형이기에, 유저가 추가되는 순간 1이 뜨므로, > 0 으로 처리..
			return new ResponseEntity<String>("회원가입 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("회원가입 실패", HttpStatus.NO_CONTENT);
	}

	/* R :: 회원 개인 정보 조회 */
	@GetMapping("/mypage")
	public ResponseEntity<Map<String, Object>> reviewUser(HttpServletRequest request) {
		HttpStatus status = null;
		Map<String, Object> resultMap = new HashMap<>();

		try {
			resultMap.putAll(jwtService.get(request.getHeader("auth-token")));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* U :: 비번 변경 전, 현재비밀번호 체크하기 */
	@GetMapping("/mypage/updatecheckpassword")
	public ResponseEntity<String> updateCheckPassword(User user, String pastPassword) throws Exception {
		System.out.println("로그인 되어있는 아이디 " + user.toString());
		System.out.println(pastPassword.toString());
		if(userService.updateCheckPassword(user, pastPassword))
			return new ResponseEntity<String>("현재 비번 일치", HttpStatus.OK);
		return new ResponseEntity<String>("현재 비번 불일치", HttpStatus.NO_CONTENT);
	}
	
	/* U :: 유저 비밀번호 변경 */
	@PutMapping("/mypage/updatepassword") // uid로 해야하려나.. uEmail 이거 중복될걱같은데,,,
	public ResponseEntity<String> updateUser(@RequestBody User user) throws Exception {
		System.out.println(user + "유저정보" + user.getuPassword());
		if (userService.updateUserPassword(user) >= 0) {
			return new ResponseEntity<String>("업데이트 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("업데이트 실패", HttpStatus.NO_CONTENT);
	}

	/* U :: 유저 이름 변경 */
	@PutMapping("/mypage/updatename")
	public ResponseEntity<String> updateUserPassword(@RequestBody User user) throws Exception {
		System.out.println(user + "유저정보" + user.getuPassword());
		if (userService.updateUserName(user) >= 0) {
			return new ResponseEntity<String>("업데이트 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("업데이트 실패", HttpStatus.NO_CONTENT);
	}

	/* D :: 삭제 */
	@DeleteMapping("/delete/{uEmail}") // uid로..? uEmail로???
	public ResponseEntity<String> deleteUser(@PathVariable("uEmail") String uEmail) throws Exception {
		HttpStatus status = null;
		Map<String, Object> resultMap = new HashMap<>();

		if(userService.deleteUser(uEmail) > 0) {
			return new ResponseEntity<String>(uEmail+"님이 삭제되었습니다.", HttpStatus.OK);
		}

		return new ResponseEntity<String>("회원삭제처리 실패", HttpStatus.NO_CONTENT);
	}

	// 기본 CRUD 외 추가 기능
	/* 유저 이메일 중복체크 */
	@GetMapping("/checkemail")
	public ResponseEntity<?> checkEmail(String email) throws Exception {
		
		if (userService.existEmail(email) == 0) { // 이메일 중복한 게 없으면 0
			return new ResponseEntity<>("true", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("false", HttpStatus.OK);
		}
	}
	
	/* 유저 이메일 찾기 */
	@GetMapping("/find/email")
	public ResponseEntity<List<User>> findEmail(String uName) throws Exception {
		System.out.println("검색 이름 : " + uName);
		return new ResponseEntity<List<User>>(userService.findEmailByName(uName) , HttpStatus.OK);
	}
	
	/* 유저 비밀번호 찾기 */
	@GetMapping("/find/password")
	public ResponseEntity<String> findPassword(String uEmail) throws Exception {
		System.out.println("입력받은 정보 : " + uEmail);
		if(userService.findPasswordByEmail(uEmail) > 0)
			return new ResponseEntity<String>("임시비밀번호 발급 성공", HttpStatus.OK);
		return new ResponseEntity<String>("임시비밀번호 발급 실패", HttpStatus.NO_CONTENT);
	}
	
}
