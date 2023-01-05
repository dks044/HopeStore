package com.mysite.hope.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	//회원가입
	@GetMapping("/signup")
	public String signup(UserSignupForm userSignupForm) {
		return "user/signup_form";
	}
	@PostMapping("/signup")
	public String signup(
			@Valid UserSignupForm userSignupForm,BindingResult bindingResult
			) {

		//오류가 발생한다면
		if(bindingResult.hasErrors()) {
		
			return "user/signup_form";
		}
		//비밀번호 확인이 일치하지 않다면
		if(!userSignupForm.getPassword1().equals(userSignupForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", 
                    "2개의 패스워드가 일치하지 않습니다.");
            //bindingResult.rejectValue의 각 파라미터는 bindingResult.rejectValue(필드명, 오류코드, 에러메시지)를 의미하며 
            //여기서 오류코드는 일단 "passwordInCorrect"로 정의
			return "user/signup_form";
		}
		
		//사용자 이름에 admin을 적었다면 -아무나 운영자로 가입 못하게
//		if(userSignupForm.getUsername().equals("admin")) {
//			bindingResult.rejectValue("username", "usernameNotInAdmin", 
//                    "admin 단어는 가입이름으로 허용되지 않습니다.");
//					return "user/signup_form";
//		}	
		
		try {

			
			userService.create(userSignupForm.getUsername(), 
							   userSignupForm.getPassword1(), 
							   userSignupForm.getEmail(),
							   userSignupForm.getPostcode(),
							   userSignupForm.getAddress(),
							   userSignupForm.getAddress_detail(),
							   userSignupForm.getPhone_number());
		}catch(DataIntegrityViolationException e) { //사용자ID 또는 이메일 주소가 동일할 경우에는 DataIntegrityViolationException이 발생
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자이거나 이미 등록된 이메일 입니다.");
            //DataIntegrityViolationException 예외가 발생할 경우 "이미 등록된 사용자입니다."라는 오류를 화면에 표시
            return "user/signup_form";
        }catch(Exception e) { //다른 오류의 경우에는 해당 오류의 메시지(e.getMessage())를 출력
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "user/signup_form";
        }
		
		return "redirect:/user/signup_success";
	}
	//회원가입 성공시
	@GetMapping("/signup_success")
	public String signup_success() {
		return "/user/signup_success";
	}
	
	//마이페이지
	@GetMapping("/mypage")
	public String mypage() {
		return "user/mypage";
	}
	
	//로그인
	//TODO:스프링시큐리티에도 로그인 관련 설정
	@GetMapping("/login")
	public String login() {
		return "user/login_form";
	}
	
}
