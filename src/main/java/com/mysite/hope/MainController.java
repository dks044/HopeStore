package com.mysite.hope;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
	//루트URL(메인페이지)
	@GetMapping("/")
	public String main(Model model) {
		
		
		
		return "main";
	}
	
	//테스트공간
	@GetMapping("/test")
	public String test() {
		return "test";
	}

	
}
