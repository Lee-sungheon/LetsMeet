package com.anjanda.letsmeet.user.service;

import java.util.List;

import com.anjanda.letsmeet.repository.dto.User;

/**
 * 
 * @Date : 2021. 2. 5.
 * @Team : AnJanDa
 * @author : 김동빈, 김지현, 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 유저 서비스 클래스
 * @Description
 *
 */

public interface UserService {

	/* 로그인 메소드 */
	User login(User user) throws Exception;

	/* R :: 카카오 로그인 메소드 */
	User kakaoLogin(User user) throws Exception;

	/* C :: 회원 가입 메소드 */
	int createUser(User user) throws Exception;

	/* C :: 카카오 회원 가입 메소드 */
	int createKakaoUser(User user) throws Exception;

	/* C :: 네이버 회원 가입 메소드 */
	int createNaverUser(User user) throws Exception;

	/* R :: 네이버 로그인 메소드 */
	User naverLogin(User user) throws Exception;

	/* R :: 로그인을 위한 조회*/
	public User selectUserLogin(User user) throws Exception;
	
	/* R :: 회원 상세 조회 메소드 */
	public User selectUser(User user) throws Exception;
	
	/* U :: 비번 변경 전, 현재비밀번호 체크하기 */
	boolean updateCheckPassword(User user, String pastPassword) throws Exception;
	
	/* U :: 회원 비밀번호 변경 메소드 */
	int updateUserPassword(User user) throws Exception;
	
	/* U :: 회원 이름 변경 메소드 */
	int updateUserName(User user) throws Exception;

	/* D :: 회원 탈퇴 메소드 */
	public int deleteUser(String uEmail) throws Exception;

	// CRUD 외 추가 기능 
	/* 이메일 중복 체크 */
	int existEmail(String email) throws Exception;

	/* 아이디 찾기 */
	public List<User> findEmailByName(String uName) throws Exception;
	
	/* 비밀번호 찾기 */
	public int findPasswordByEmail(String uEmail) throws Exception;


}