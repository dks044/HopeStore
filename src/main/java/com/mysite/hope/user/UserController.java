package com.mysite.hope.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	//회원가입
	@GetMapping("/signup")
	public String signup() {
		return "user/signup_form";
	}
	
}
