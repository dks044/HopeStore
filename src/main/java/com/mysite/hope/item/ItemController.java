package com.mysite.hope.item;


import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.hope.cart.Cart;
import com.mysite.hope.cart.CartService;
import com.mysite.hope.cartItem.CartItem;
import com.mysite.hope.category.Category;
import com.mysite.hope.user.SiteUser;
import com.mysite.hope.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RequestMapping("/item")
@Controller
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;
	private final ItemRepository itemRepository;
	private final UserService userService;
	private final CartService cartService;
	
	//상품올리기 페이지
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/create")
	public String create() {
		
		return "item/itemForm";
	}
	//상품올리기 처리
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public String create(
			Item item,
			MultipartFile file
			
			) throws Exception {
		item.setCreateDate(LocalDateTime.now());
		item.setHit(0);
		item.setSell_count(0);
		
		itemService.saveItem(item,file);
		
		return "redirect:/";
	}
	//상품 삭제
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/detail/delete/{id}")
	public String delete(@PathVariable Integer id,
			Principal principal
			
			) {
		Item item = this.itemService.getItem(id);
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		//운영자가 아닐떄
		if(!user.get().getUsername().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.itemService.delete(item);
		
		return "redirect:/";
	}
	//더둘러보기에서 상품디테일에서 삭제할떄
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/item/detail/delete/{id}")
	public String delete2(@PathVariable Integer id,
			Principal principal
			
			) {
		Item item = this.itemService.getItem(id);
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		//운영자가 아닐떄
		if(!user.get().getUsername().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.itemService.delete(item);
		
		return "redirect:/";
	}
	//상품수정
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/detail/modify/{id}")
	public String modify(@PathVariable Integer id,Model model) {
		Item item = this.itemService.getItem(id);
		model.addAttribute("item", item);
		
		return "item/itemModifyForm";
	}
	//상품수정
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("item/detail/modify/{id}")
	public String modify2(@PathVariable Integer id,Model model) {
		Item item = this.itemService.getItem(id);
		model.addAttribute("item", item);
		
		return "item/itemModifyForm";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/modify/{id}")
	public String modify(
			@PathVariable("id") Integer id,
			@RequestParam String name,
			@RequestParam String text,
			@RequestParam int price,
			@RequestParam int stock,
			@RequestParam int isSoldOut,
			@RequestParam Category category,
			MultipartFile file
			
			)throws Exception {
		Item item = this.itemService.getItem(id);
		this.itemService.modify(item, name, text, price, stock, isSoldOut, category);
		this.itemService.saveItem(item,file);
		
		return String.format("redirect:/item/detail/%s", id);
	}
	
	//상품페이지
	@GetMapping(value="/detail/{id}")
	public String detail(
			@PathVariable("id") int id,
			Model model
			) {
//		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
//		Cart cart = this.cartService.getBySiteUser(user.get());
		
		Item item = this.itemService.getItem(id);
		this.itemService.hitAddItem(item);
		model.addAttribute("item", item);
		
		//카트아이템에 이미 해당아이템이 있다면
//		List<CartItem> cartItemList = cart.getCartItemList();
//		for(CartItem i : cartItemList) {
//			if(i.getItem().equals(item)) {
//				String equalsCartItem = "카트에 이미 담긴 상품입니다.";
//				model.addAttribute("equalsCartItem", equalsCartItem);
//				break;
//			}
//		}
		
		return "item/itemDetail";
	}
	//상품페이지
	@GetMapping(value="item/detail/{id}")
	public String detail2(
			@PathVariable("id") int id,
			Model model
			) {
		
		Item item = this.itemService.getItem(id);
		this.itemService.hitAddItem(item);
		model.addAttribute("item", item);
		return "item/itemDetail";
	}
	
	
	//상품리스트 페이지
	@GetMapping("/itemList")
	public String itemList(Model model) {
		List<Item> item_list = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
		model.addAttribute("item_list", item_list);
		
		return "item/itemList";
	}
	
	//[AJAX] 상품 정렬
	//TODO: 카테고리별, 가격순,판매순,최신순
	@PostMapping("/search")
	public String searchItem(Model model,
			@RequestParam(value = "category_id") Integer category_id,//카테고리 id
			@RequestParam(value = "field", defaultValue = "createDate") String field //정렬할 필드이름 (createDate, order_cnt, price)
			) {
		
		//카테고리는 선택안하고, 필드만 선택할 경우
//		if(category_id == null && !field.isEmpty()) {
//			List<Item> search_itemList = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,field));
//		}
		
		List<Item> search_itemList = this.itemService.getCategoryByItemList(category_id, field);
		model.addAttribute("search_itemList", search_itemList);
		
		return "item/search_item";
	}
	
	//[AJAX] 상품 정렬 (전체)
	@PostMapping("/search/all")
	public String searchItemAll(Model model,
			@RequestParam(value = "field", defaultValue = "createDate") String field //정렬할 필드이름 (createDate, order_cnt, price)
			) {
		//기본정렬값은 최신순으로
		List<Item> search_itemList = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,field));
		model.addAttribute("search_itemList", search_itemList);
		return "item/search_item";
	}
	
	
	
}
