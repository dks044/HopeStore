package com.mysite.hope;



import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.hope.item.Item;
import com.mysite.hope.item.ItemRepository;
import com.mysite.hope.item.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	private final ItemService itemService;
	private final ItemRepository itemRepository;
	
	//테스트공간
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	//루트URL(메인페이지)
	@GetMapping("/")
	public String main(Model model) {
		List<Item> itemList_letest = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
		model.addAttribute("itemList_letest", itemList_letest);
		
		List<Item> itemList_hit = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"hit"));
		model.addAttribute("itemList_hit", itemList_hit);
		
		
		return "main";
	}
	

	
}
