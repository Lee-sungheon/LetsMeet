package com.anjanda.letsmeet.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anjanda.letsmeet.repository.dto.User;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : User 관련 매퍼 인터페이스 클래스
 * @Description : UserService 및 usermapper.xml 참고
 * 
 */

@Mapper
public interface UserMapper {
	/* C :: 회원 가입 메소드 */
	public int insertUser(User user) throws Exception;
	
	/* C :: 카카오 회원 가입 메소드 */
	public int insertKakaoUser(User user) throws Exception;
	
	/* C :: 네이버 회원 가입 메소드 */
	public int insertNaverUser(User user) throws Exception;

	/* R :: 로그인 위한 메소드 */
	public User selectUserLogin(User user) throws Exception;

	/* R :: 회원상세정보조회 메소드 */
	public User selectUser(User user) throws Exception;

	/* U :: 회원 비밀번호 변경 메소드 */
	public int updateUserPassword(User user) throws Exception;
	
	/* U :: 회원 비밀번호 변경 메소드 */
	public int updateUserName(User user) throws Exception;
	
	/* D :: 회원 탈퇴 메소드 */
	public int deleteUser(String uEmail) throws Exception;
	
	// CRUD 외 추가 기능
	/* 이메일 중복 체크 메소드 */
	public int existEmail(String email) throws Exception;

	/* 로그인 시, 이메일로 맞는 salt 값 받아오기 */
	public String getuSaltByEmail(String uEmail) throws Exception;
	
	/* 아이디 찾기 */
	public List<User> selectEmailByName(String uName) throws Exception;
	
	/* 비밀번호 찾기 */
	public int selectPasswordByEmail(String uEmail, String tempPassword) throws Exception;



}
