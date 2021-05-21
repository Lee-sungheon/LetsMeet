package com.anjanda.letsmeet.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anjanda.letsmeet.repository.dto.User;
import com.anjanda.letsmeet.user.jwt.JwtService;
import com.anjanda.letsmeet.user.service.UserService;

/**
 * 
 * @Date : 2021. 2. 4.
 * @Team : AnJanDa
 * @author : 김동빈, 김지현
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : 로그인 관련 컨트롤러 클래스
 * @Description : 로그인 처리
 *
 */

@CrossOrigin(origins = { "*" }, maxAge = 6000)	// 이건 왜쓰지..
@RestController
public class LoginController {

	/* jwt 객체 불러오기 */
	@Autowired
	private JwtService jwtService;
	
	/* 유저 서비스 객체 불러오기 */
	@Autowired
	private UserService service;
	
	/* 로그인 */
	@PostMapping("/login")
//	public String login(@RequestBody User user, HttpServletResponse response, HttpSession session) throws Exception {
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response, HttpSession session) throws Exception {
		
		System.out.println(user+"유저정보"+user.getuEmail()+user.getuPassword());
		Map<String, Object> resultMap = new HashMap<>();
		User check = service.login(user);
		if(check != null) {
			String token = jwtService.create(check);
			
			// 파라미터 1번째 것은 FE 대로 따라가기..
			resultMap.put("auth-token", token);
			resultMap.put("uNo", check.getuNo());
			resultMap.put("uEmail", check.getuEmail()); 
			resultMap.put("uPassword", check.getuPassword());
			resultMap.put("uName", check.getuName());
			resultMap.put("uImage", check.getuImage());
			resultMap.put("uJoinDate", check.getuJoinDate());
			resultMap.put("uProvider", check.getuProvider());
			resultMap.put("uSalt", check.getuSalt());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		resultMap.put("message", "로그인에 실패하였습니다.");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/auth/kakao/callback")
	public ResponseEntity<?> kakaoCallback(@RequestBody User user, HttpServletResponse response, HttpSession session) throws Exception { // Data를 return 해주는 controller method
		User kakaoUser = new User();
		kakaoUser.setuEmail(user.getuEmail());
		kakaoUser.setuPassword(user.getuPassword());
		kakaoUser.setuName(user.getuName());
		kakaoUser.setuProvider("kakao");
		
		// 가입자 혹은 비가입자 체크해서 처리
		try {
			User originUser = service.selectUser(kakaoUser);
			if(originUser == null) {
				service.createKakaoUser(kakaoUser);
				System.out.println("카카오 아이디로 회원가입 성공");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		User toLogin = service.selectUser(user);
		
		Map<String, Object> resultMap = new HashMap<>();
		User check = service.kakaoLogin(toLogin);
		if(check != null) {
			String token = jwtService.create(check);
			
			resultMap.put("auth-token", token);
			resultMap.put("uNo", check.getuNo());
			resultMap.put("uEmail", check.getuEmail()); 
			resultMap.put("uPassword", check.getuPassword());
			resultMap.put("uName", check.getuName());
			resultMap.put("uImage", check.getuImage());
			resultMap.put("uJoinDate", check.getuJoinDate());
			resultMap.put("uProvider", check.getuProvider());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		resultMap.put("message", "로그인에 실패하였습니다.");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
	}

	@PostMapping("/auth/naver/callback")
	public ResponseEntity<?> naverCallback(@RequestBody User user, HttpServletResponse response, HttpSession session) throws Exception { // Data를 return 해주는 controller method
		User naverUser = new User();
		naverUser.setuEmail(user.getuEmail());
		naverUser.setuPassword(user.getuPassword());
		naverUser.setuName(user.getuName());
		naverUser.setuProvider("naver");
		
		// 가입자 혹은 비가입자 체크해서 처리
		try {
			User originUser = service.selectUser(naverUser);
			if(originUser == null) {
				service.createNaverUser(naverUser);
				System.out.println("네이버 아이디로 회원가입 성공");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		User toLogin = service.selectUser(user);
		
		Map<String, Object> resultMap = new HashMap<>();
		User check = service.naverLogin(toLogin);
		if(check != null) {
			String token = jwtService.create(check);
			
			resultMap.put("auth-token", token);
			resultMap.put("uNo", check.getuNo());
			resultMap.put("uEmail", check.getuEmail()); 
			resultMap.put("uPassword", check.getuPassword());
			resultMap.put("uName", check.getuName());
			resultMap.put("uImage", check.getuImage());
			resultMap.put("uJoinDate", check.getuJoinDate());
			resultMap.put("uProvider", check.getuProvider());
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		resultMap.put("message", "로그인에 실패하였습니다.");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
	}
}


// 우리 동빈이가 한 세션 로그인 컨트롤러!!!!!!
/*
package com.anjanda.letsmeet.login.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.anjanda.letsmeet.login.service.LoginService;
import com.anjanda.letsmeet.repository.dto.OAuthToken;
import com.anjanda.letsmeet.repository.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public void login(Model model) {
		model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 로그인 해주세요");
	}

	@PostMapping("/login")
	public String service(@RequestParam Map<String, String> map, HttpSession session, HttpServletResponse response,
			Model model) throws Exception {
		try {
			User user = loginService.login(map);
			if (user != null) { // 유저 정보가 왔을 경우
				session.setAttribute("userinfo", user);
				System.out.println(user.getuEmail());
				Cookie cookie = new Cookie("user_id", user.getuEmail());
				cookie.setPath("/");
				if ("saveok".equals(map.get("idsave"))) { // id저장 하면
					cookie.setMaxAge(60 * 60 * 24 * 365 * 50); // 단위가 초이기 때문에 초*분*시간*일*년을 하면 최대 ~년간 저장 된다.(윤년 고려 안함)
				} else {
					cookie.setMaxAge(0); // 저장 안하면 쿠키 저장 안함(0초간 저장)
				}
				response.addCookie(cookie);
			} else { // 유저 정보가 오지 않았으면 로그인 화면으로 리다이렉트
				return "redirect:login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "로그인 중 문제가 발생했습니다.");
			return "error/loginError"; // 로그인 중 문제가 발생했으니 로그인 에러로 이동 (로그인 에러 페이지 따로 만들어야함)
		}
		return "redirect:/main"; // 메인으로 리다이렉트 보낸다.
	}
//
//	// googleLogin
//	final static String GOOGLE_AUTH_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
//	final static String GOOGLE_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";
//	final static String GOOGLE_REVOKE_TOKEN_BASE_URL = "https://oauth2.googleapis.com/revoke";
//
//	@Value("${api.client_id}")
//	String clientId;
//	@Value("${api.client_secret}")
//	String clientSecret;
//
//
//	/**
//	 * Authentication Code를 전달 받는 엔드포인트
//	 **/
//	@GetMapping("google/auth")
//	public String googleAuth(Model model, @RequestParam(value = "code") String authCode)
//			throws JsonProcessingException {
//		
//		//HTTP Request를 위한 RestTemplate
//		RestTemplate restTemplate = new RestTemplate();
//
//		//Google OAuth Access Token 요청을 위한 파라미터 세팅
//		GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
//				.builder()
//				.clientId(clientId)
//				.clientSecret(clientSecret)
//				.code(authCode)
//				.redirectUri("http://localhost:8080/login/google/auth")
//				.grantType("authorization_code").build();
//
//		
//		//JSON 파싱을 위한 기본값 세팅
//		//요청시 파라미터는 스네이크 케이스로 세팅되므로 Object mapper에 미리 설정해준다.
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//		mapper.setSerializationInclusion(Include.NON_NULL);
//
//		//AccessToken 발급 요청
//		ResponseEntity<String> resultEntity = restTemplate.postForEntity(GOOGLE_TOKEN_BASE_URL, googleOAuthRequestParam, String.class);
//
//		//Token Request
//		GoogleOAuthResponse result = mapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {
//		});
//		
//		System.out.println(resultEntity.getBody());
//
//		//ID Token만 추출 (사용자의 정보는 jwt로 인코딩 되어있다)
//		String jwtToken = result.getIdToken();
//		String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
//		.queryParam("id_token", jwtToken).encode().toUriString();
//		
//		String resultJson = restTemplate.getForObject(requestUrl, String.class);
//		
//		Map<String,String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>(){});
//		model.addAllAttributes(userInfo);
//		model.addAttribute("token", result.getAccessToken());
//		System.out.println(userInfo);
//
//
//
//
//		return "/google.html";
//
//	}
//
//	/**
//	 * 토큰 무효화 
//	 **/
//	@GetMapping("google/revoke/token")
//	@ResponseBody
//	public Map<String, String> revokeToken(@RequestParam(value = "token") String token) throws JsonProcessingException {
//
//		Map<String, String> result = new HashMap<>();
//		RestTemplate restTemplate = new RestTemplate();
//		final String requestUrl = UriComponentsBuilder.fromHttpUrl(GOOGLE_REVOKE_TOKEN_BASE_URL)
//				.queryParam("token", token).encode().toUriString();
//		
//		System.out.println("TOKEN ? " + token);
//		
//		String resultJson = restTemplate.postForObject(requestUrl, null, String.class);
//		result.put("result", "success");
//		result.put("resultJson", resultJson);
//
//		return result;
//
//	}
//	