package com.mysite.hope.cart;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.hope.cartItem.CartItem;
import com.mysite.hope.cartItem.CartItemRepository;
import com.mysite.hope.cartItem.CartItemService;
import com.mysite.hope.item.Item;
import com.mysite.hope.item.ItemService;
import com.mysite.hope.user.SiteUser;
import com.mysite.hope.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@RequiredArgsConstructor
@Controller
public class CartController {
	private final CartRepository cartRepository;
	private final CartService cartService;
	private final UserRepository userRepository;
	private final ItemService itemService;
	private final CartItemService cartItemService;
	private final CartItemRepository cartItemRepository;
	
	  //[AJAX] 카트아이템 추가
	  
		/*
		 * @PreAuthorize("isAuthenticated()") //로그인한 유저만 가능
		 * 
		 * @PostMapping("/add") public String addCart(Model model,Principal principal,
		 * 
		 * @RequestParam(value = "id") Integer id ) {
		 * 
		 * 
		 * Item item = this.itemService.getItem(id); SiteUser user =
		 * this.userRepository.findAllByusername(principal.getName()); Cart cart =
		 * this.cartService.getCart(user);
		 * 
		 * List<Item> cartItemList = cart.getItemList(); cartItemList.add(item);
		 * 
		 * model.addAttribute("cartItemList", cartItemList);
		 * 
		 * return "cart/cart"; }
		 */
	 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add/{id}")
	public String addCart(
			@PathVariable(value = "id")int id,
			@RequestParam(value = "count",defaultValue = "1")int count,
			Principal principal,
			Model model
			) {
		//로그인한 사용자명으로 유저조회
		SiteUser user = this.userRepository.findAllByusername(principal.getName());
		Item item = this.itemService.getItem(id);		
		Cart cart = this.cartRepository.findAllByUser(user);
		
		
		//사용자에게 카트가 없다면 사용자에게 카트생성
		if(cart == null) {
			cart = this.cartService.create(user);
		}
		
		int cart_id = cart.getId();
		CartItem cartItem = this.cartItemRepository.findByCartIdAndItemId(cart_id, id);
		//해당상품이 카트에 없다면 카트에 상품 추가
		if(cartItem == null) {
			cartItem = this.cartItemService.create(cart, item, count);						
		}else if(cartItem.getItem().equals(item)) {
			this.cartItemService.addCount(cartItem,count);
			return String.format("redirect:/item/detail/%s", id);
		}
		
		
		return String.format("redirect:/item/detail/%s", id);
	}
	
	
	
	
}
