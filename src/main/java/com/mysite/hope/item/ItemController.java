package com.mysite.hope.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
@RequestMapping("/item")
@Controller
@RequiredArgsConstructor
public class ItemController {
	//상품올리기
	@GetMapping("/itemForm")
	public String itemForm() {
		return "item/itemForm";
	}
	
	
}
