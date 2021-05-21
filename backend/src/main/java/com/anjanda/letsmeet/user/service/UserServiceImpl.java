package com.anjanda.letsmeet.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.User;
import com.anjanda.letsmeet.repository.mapper.UserMapper;
import com.anjanda.letsmeet.user.controller.SaltSHA256;

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

@Service
public class UserServiceImpl implements UserService {

	/* 유저 매퍼 객체 불러오기 */
	@Autowired
	private UserMapper mapper;
	
//	/* 비밀번호를 해쉬로 저장하기 위한 인코더 */
//	@Autowired
//	private BCryptPasswordEncoder encoder;
	
	/* 로그인 메소드 */
	@Override
	public User login(User user) throws Exception {
		
		// 완전한 암호화 보안은 로그인 횟수까지 작업해야함 (나중에 시간 여유보고 작업할 것)
		System.out.println("로그인할 아이디 : " + user.getuEmail());
		String salt = mapper.getuSaltByEmail(user.getuEmail());
		System.out.println("출력 : " + salt);
		String password = user.getuPassword();
		
		password = SaltSHA256.getEncrypt(password, salt);
		user.setuPassword(password);
		
		User check = mapper.selectUser(user);
		if(user.getuPassword().equals(check.getuPassword()))
			return check;
		else
			return null;
	}
	
	/* C :: 회원 가입 메소드 */
	@Override
	public int createUser(User user) throws Exception {
		
		// 1. 가입할 회원의 고유 salt 생성 및 저장
		String salt = SaltSHA256.generateSalt(); 
		user.setuSalt(salt); 
		
		// 2. 입력된 password + 생성된 salt 활용해서 암호화된 password 생성
		String password = user.getuPassword();
		System.out.println("출력" + password);
		password = SaltSHA256.getEncrypt(password, salt);
		
		// 3. 입력된 비번 삽입
		user.setuPassword(password);
		
		// 4. 남은 유저 정보 삽입
		return mapper.insertUser(user);
	}

	/* C :: 카카오 회원 가입 메소드 */
	@Override
	public int createKakaoUser(User user) throws Exception {
//		String rawPassword = user.getuPassword(); // 비밀번호 원문
//		String encPassword = encoder.encode(rawPassword); // 해쉬
//		user.setuPassword(rawPassword);

		return mapper.insertKakaoUser(user);
	}
	
	/* R :: 로그인을 위한 조회 */
	@Override
	public User selectUserLogin(User user) throws Exception {
		return mapper.selectUserLogin(user);
	}
	
	/* R :: 회원 상세 조회 메소드 */
	@Override
	public User selectUser(User user) throws Exception {
		return mapper.selectUser(user);
	}
	
	/* U :: 비번 변경 전, 현재비밀번호 체크하기 */
	@Override
	public boolean updateCheckPassword(User user, String pastPassword) throws Exception {
		String salt = mapper.getuSaltByEmail(user.getuEmail()); // 사용자의 이메일로 salt 받아오기
		System.out.println("출력 : " + salt);
		
		String checkPassword = SaltSHA256.getEncrypt(pastPassword, salt);
		if(checkPassword.equals(user.getuPassword())) {
			System.out.println("일치일치일치!!");
			return true;
		}
		else
			return false;
	}
	
	/* U :: 회원 비밀번호 수정 메소드 */
	@Override
	public int updateUserPassword(User user) throws Exception {
		String salt = SaltSHA256.generateSalt(); 
		user.setuSalt(salt); 
		
		String password = user.getuPassword();
		password = SaltSHA256.getEncrypt(password, salt);
		
		user.setuPassword(password);
		
		return mapper.updateUserPassword(user);
	}
	
	/* U :: 회원 이름 수정 메소드 */
	@Override
	public int updateUserName(User user) throws Exception {
		return mapper.updateUserName(user);
	}
	
	/* D :: 회원 탈퇴 메소드 */
	@Override
	public int deleteUser(String uEmail) throws Exception {
		return mapper.deleteUser(uEmail);
	}
	
	// CRUD 외 추가 기능 
	/* 이메일 중복 체크 */
	@Override
	public int existEmail(String email) throws Exception {
		return mapper.existEmail(email);
	}
	
	/* 로그인 시, 이메일로 맞는 salt 값 받아오기 */
	public String getuSaltByEmail(String getuEmail) throws Exception {
		return mapper.getuSaltByEmail(getuEmail);
	}
	
	/* 아이디 찾기 */
	public List<User> findEmailByName(String uName) throws Exception {
		return mapper.selectEmailByName(uName);
	}
	
	/* 비밀번호 찾기 */
	public int findPasswordByEmail(String uEmail) throws Exception {
		
		String salt = mapper.getuSaltByEmail(uEmail);
		System.out.println("해당 고객 salt : " + salt);
		
		String tempPassword = "1234";
		tempPassword = SaltSHA256.getEncrypt(tempPassword, salt);
		
		return mapper.selectPasswordByEmail(uEmail, tempPassword);
	}

	/* 카카오 로그인 */
	@Override
	public User kakaoLogin(User user) throws Exception {
		User check = mapper.selectUser(user);
		if(user.getuPassword().equals(check.getuPassword()))
			return check;
		else
			return null;
	}

	/* C :: 네이버 회원 가입 메소드 */
	@Override
	public int createNaverUser(User user) throws Exception {
		return mapper.insertNaverUser(user);
	}

	/* 네이버 로그인 */
	@Override
	public User naverLogin(User user) throws Exception {
		User check = mapper.selectUser(user);
		if(user.getuPassword().equals(check.getuPassword()))
			return check;
		else
			return null;
	}
	
}





//우리 동빈이가 작업한 세션 로그인 서비스 클래스
/*
package com.anjanda.letsmeet.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anjanda.letsmeet.repository.dto.User;
import com.anjanda.letsmeet.repository.mapper.LoginMapper;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginMapper mapper;

	@Override
	public User login(Map<String, String> map) throws Exception {
		if (map.get("email") == null || map.get("password") == null) // if email or password is null then return null
			return null;
		return mapper.selectLogin(map); // if not either email or password are null then return selectLogin
	}

}
*/