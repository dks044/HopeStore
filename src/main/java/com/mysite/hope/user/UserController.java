package com.mysite.hope.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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
import org.springframework.web.server.ResponseStatusException;

import com.mysite.hope.cart.Cart;
import com.mysite.hope.cart.CartService;
import com.mysite.hope.cartItem.CartItem;
import com.mysite.hope.cartItem.CartItemService;
import com.mysite.hope.item.Item;
import com.mysite.hope.item.ItemService;
import com.mysite.hope.order.Order;
import com.mysite.hope.order.OrderService;
import com.mysite.hope.orderItem.OrderItem;
import com.mysite.hope.orderItem.OrderItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final OrderService orderService;
	private final OrderItemService orderItemService;
	private final UserRepository userRepository;
	private final CartService cartService;
	private final CartItemService cartItemService;
	private final ItemService itemService;
	
	
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
		}catch(DataIntegrityViolationException e) { //사용자ID 또는 이메일 주소가 동일할 경우, 핸드폰번호 동일한 경우에는 DataIntegrityViolationException이 발생
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자이거나 이미 등록된 이메일이거나 이미 등록된 전화번호 입니다.");
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mypage")
	public String mypage(Principal principal,
			Model model
			) {
		
		
		Optional<SiteUser> user = this.userRepository.findByusername(principal.getName());
		
		Order order = this.orderService.getOrderByUser(user.get());
		//order 객체가 없다면
		if(order == null) {
			order = this.orderService.create(user.get());
		}
			
		List<OrderItem> orderItemList = order.getOrderItemList();
		model.addAttribute("orderItemList", orderItemList);
		if(orderItemList.isEmpty()) {
			List<OrderItem> orderItemList1 = new ArrayList<>();
			model.addAttribute("orderItemList", orderItemList1);
		}
		
		//주문총금액
		int orderTotalPrice =0;
		for(OrderItem i : orderItemList) {
			orderTotalPrice += i.getPrice();
		}
		model.addAttribute("orderTotalPrice", orderTotalPrice);

		
		Cart cart = this.cartService.getBySiteUser(user.get());
		//사용자에게 카트가 없다면 사용자에게 카트생성
		if(cart == null) {
			cart = this.cartService.create(user.get());
		}
		

		List<CartItem> cartItemList = cart.getCartItemList();

		model.addAttribute("cartItemList", cartItemList);

		if(cartItemList.isEmpty()) {
			List<CartItem> cartItemList1 = new ArrayList<>();
			model.addAttribute("cartItemList", cartItemList1);
		}
		
		
		
		
		return "user/mypage";
	}
	
	
	//마이페이지에서 상품페이지 링크될시에
		@GetMapping(value="item/detail/{id}")
		public String detail3(
				@PathVariable("id") int id,
				Model model
				) {
			
			Item item = this.itemService.getItem(id);
			this.itemService.hitAddItem(item);
			model.addAttribute("item", item);
			return "item/itemDetail";
		}
		
	//카드에 담긴 상품 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="delete/cartItem/{id}")
	public String deleteCartItem(Principal principal,
			@PathVariable("id") int id
			) {
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		Cart cart = this.cartService.getBySiteUser(user.get());
		CartItem cartItem = this.cartItemService.getCartItemById(id);
		//cart소유자와 로그인한 사용자가 다를떄
		if(!cart.getUser().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.cartItemService.delete(cartItem);
		
		
		return "redirect:/user/mypage";
	}
	//주문취소
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="delete/orderItem/{id}")
	public String deleteOrderItem(Principal principal,
			@PathVariable("id") int id
			) {
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		Order order = this.orderService.getOrderByUser(user.get());
		OrderItem orderItem = this.orderItemService.getOrderItemById(id);
		//order소유자와 로그인한 사용자가 다를떄
		if(!order.getUser().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.orderItemService.delete(orderItem);
		
		
		return "redirect:/user/mypage";
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("mypage/delete")
	public String deleteMember(Principal principal) {
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		
		//로그인한 사용자랑 다를떄
		if(!user.get().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "탈퇴 권한이 없습니다.");
		}
		this.userService.deleteMember(user.get());
		
		return "redirect:/user/logout";
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/adminPage")
	public String adminPage(Model model,Principal principal,
			@RequestParam(value="page", defaultValue="0") int page
			) {
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		//운영자가 아닐떄(스프링시큐리티로 설정했더라도 권한검사 넣음)
		if(!user.get().getUsername().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "운영자페이지에 접근할 권한이 없습니다.");
		}
		Page<OrderItem> paging = this.orderItemService.getOrderItemList(page);
		model.addAttribute("paging", paging);
		
		//입금전 컬럼 개수
		int depositBefore = this.orderItemService.getOrderItemDepositStatusSize(1);
		model.addAttribute("depositBefore", depositBefore);

		//입금후 컬럼 개수
		int depositAfter = this.orderItemService.getOrderItemDepositStatusSize(2);
		model.addAttribute("depositAfter", depositAfter);

		return "user/adminPage";
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("depositSuccess/{id}")
	public String depositSuccess(Principal principal,
			@PathVariable("id") int id
			) {
		
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		if(!user.get().getUsername().equals("admin")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}
		OrderItem orderItem = this.orderItemService.getOrderItemById(id);
		this.orderItemService.depositChecked(orderItem);
		
		return "redirect:/user/adminPage";
	}
	
		
	//로그인
	//TODO:스프링시큐리티에도 로그인 관련 설정
	@GetMapping("/login")
	public String login() {
		return "user/login_form";
	}
	
}
