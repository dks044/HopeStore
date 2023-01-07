package com.mysite.hope.item;


import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequestMapping("/item")
@Controller
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	
	//상품올리기 페이지
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/create")
	public String create() {
		
		return "item/itemForm";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public String create(
			Item item,
			MultipartFile file
			
			) throws Exception {
		item.setCreateDate(LocalDateTime.now());
		itemService.saveItem(item,file);
		
		return "redirect:/";
	}
	
	
	
	
}
