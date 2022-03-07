package com.mega.controller.member;

import java.security.NoSuchAlgorithmException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mega.config.CtxBinder;
import com.mega.service.member.SignupService;
import com.mega.util.SHA256;

@Controller
@RequestMapping(value="/member/signup")
public class SignupController {
	private static ApplicationContext ctx = null;
	public static String SAVE_PATH = null;

	public SignupController() {
		ctx = new AnnotationConfigApplicationContext(CtxBinder.class);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "member/signup";
	}
	
	@PostMapping
	public String signup(@RequestParam String username,
			@RequestParam String id,
			@RequestParam String pw1,
			@RequestParam String pw2,
			@RequestParam MultipartFile profileImage,
			@RequestParam int profileImageType) {

		try {
			// 클라이언트가 보낸 값 검증
			
			// 비밀번호 암호화
			pw1 = SHA256.encrypt(pw1);
			
			// 회원 가입 처리
			SignupService service = ctx.getBean("signupService", SignupService.class);
			service.signup(username, id, pw1, profileImage, profileImageType);
			// 회원 가입 처리 결과 반환
			
			return "member/signupSuccess";
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "member/signup";
	}
}