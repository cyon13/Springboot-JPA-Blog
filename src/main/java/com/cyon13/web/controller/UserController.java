package com.cyon13.web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cyon13.web.model.KakaoProfile;
import com.cyon13.web.model.OAuthToken;
import com.cyon13.web.model.User;
import com.cyon13.web.service.Userservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용
@Controller
public class UserController {
	
	@Value("${cyon13.key}")
	private String cyon13Key;
	
	@Autowired
	private Userservice userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(@RequestParam String code) { 
		
		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate
		final String grantType = "authorization_code";
		final String clientId = "44d303b07ae72f0f38911251fa7d55bb";
		final String redirectUri = "http://localhost:8080/auth/kakao/callback";
		
		
		RestTemplate rt = new RestTemplate(); 
		
		// httpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// httpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", grantType);
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers); 
		// rt.exchange가 HttpEntity 객체를 받게 되어 있다.
		
		//Http요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token", // 토큰 발급 요청 주소
				HttpMethod.POST,  // 요청 메서드
				kakaoTokenRequest, // http 바디+ http 헤더 값
				String.class     // 응답을 받을 타입 설정
				);
	//=============================================================
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("카카오 엑세스 토큰:"+oauthToken.getAccess_token());
		
		//================================================================
		
		RestTemplate rt2 = new RestTemplate(); 
		
		// httpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = 
				new HttpEntity<>(headers2); 
		// rt.exchange가 HttpEntity 객체를 받게 되어 있다.
		
		//Http요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me", // 토큰 발급 요청 주소
				HttpMethod.POST,  // 요청 메서드
				kakaoProfileRequest, // http 바디+ http 헤더 값
				String.class     // 응답을 받을 타입 설정
				);
		//System.out.println(response2.getBody());

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("카카오 아이디(번호): "+kakaoProfile.getId());
		System.out.println("카카오 이메일: "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 유저네임: "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그서버 이메일: "+kakaoProfile.getKakao_account().getEmail());
		//UUID garbagePassword = UUID.randomUUID();
		System.out.println("블로그서버 패스워드: "+ cyon13Key);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cyon13Key)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 혹은 비가입자 체크해서 처리
		User originUser = userService.findUser(kakaoUser.getUsername());
		
		if(originUser.getUsername()==null) {
			System.out.println("기존 회원이 아닙니다!");
			userService.join(kakaoUser);
		}
		
		// 로그인 처리
		// 세션 등록
		Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cyon13Key)); // authenticationManager에 username, password를 담은 AuthenticationToken을 넘겨주면 인증해서 Authentication 객체를 만들어준다. 
		SecurityContextHolder.getContext().setAuthentication(authentication); // 만들어진 Authentication 객체를 SecurityContext에 저장시킨다.
		
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		
		return "user/updateForm";
	}

}
