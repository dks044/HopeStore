package com.mysite.hope;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mysite.hope.cart.Cart;
import com.mysite.hope.cart.CartRepository;
import com.mysite.hope.cartItem.CartItem;
import com.mysite.hope.category.Category;
import com.mysite.hope.category.CategoryRepository;
import com.mysite.hope.item.Item;
import com.mysite.hope.item.ItemRepository;
import com.mysite.hope.item.ItemService;
import com.mysite.hope.order.Order;
import com.mysite.hope.order.OrderRepository;
import com.mysite.hope.order.OrderService;
import com.mysite.hope.orderItem.OrderItem;
import com.mysite.hope.orderItem.OrderItemService;
import com.mysite.hope.user.SiteUser;
import com.mysite.hope.user.UserRepository;
import com.mysite.hope.user.UserRole;
import com.mysite.hope.user.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
class HopeStoreApplicationTests {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemService itemService;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;
	
	//운영자 계정 생성 -성공
	@Test
	@Disabled
	void adminCreateTest() {
		SiteUser admin = new SiteUser();
		admin.setUsername("admin");
		String password = "1234";
		admin.setPassword(passwordEncoder.encode(password));
		admin.setPostcode("00000");
		admin.setAddress("운영자의 집");
		admin.setAddress_detail("운영자의 집 밑");
		admin.setEmail("buzz781game@gmail.com");
		admin.setPhone_number("010-0000-0000");
		admin.setRole(UserRole.ADMIN);
		this.userRepository.save(admin);

	}
	
	//카테고리생성 -성공
	@Test
	@Disabled
	void create_Category() {
		Category top = new Category();
		top.setCategory_name("상의");
		Category pants = new Category();
		pants.setCategory_name("하의");
		Category author = new Category();
		author.setCategory_name("아우터");
		this.categoryRepository.save(top);
		this.categoryRepository.save(pants);
		this.categoryRepository.save(author);

	}
	
	
	//카테고리별 상품정렬
	@Transactional
	@Test
	@Disabled
	void sort_CategoryTest() {
		Optional<Category> category = this.categoryRepository.findById(3);
		List<Item> sort_itemList = this.itemRepository.findAllByCategory(category.get(),Sort.by(Sort.Direction.DESC,"price"));
		//리포지터리에 sort 매개변수 되는지 실험
		
		System.out.println("===============TEST DATA===============");
		for(Item i : sort_itemList) {
			//System.out.println(i.price);
			System.out.println(i.getPrice());
		}
		System.out.println("===============TEST DATA===============");
		
	}

	
	//유저로 카트리스트 조회
	@Test
	@Disabled
	void test() {
		SiteUser user = this.userRepository.findAllByusername("test3");
		//List<CartItem> cartItemList = this.cartRepository.findCartItemListByUser(user);
		Cart cart = this.cartRepository.findAllByUser(user);
		System.out.println("====TESTDATA====");
		for(CartItem i : cart.getCartItemList()) {
			System.out.println(i.getCount());
		}
		System.out.println("====TESTDATA====");
		
	}
	
	@Transactional
	@Test
	@Disabled
	void test11() {
		Optional<SiteUser> user = this.userService.getByUserName("test3");
		//Order order = this.orderRepository.findByUser(user.get());
		//List<Order> orders = this.orderRepository.findAllByUser(user.get());
		System.out.println("===============TEST DATA===============");
		//System.out.println(order.getUser().getUsername());
//		for(Order order : orders) {
//			System.out.println(order.getUser().getUsername());
//		}
		System.out.println("===============TEST DATA===============");
		
		
		
	}
	
	//페이징리스트확인
	@Transactional
	@Test
	@Disabled
	void test12() {
		Page<OrderItem> paging = this.orderItemService.getOrderItemList(0);
		System.out.println("===============TEST DATA===============");
		for(OrderItem i : paging) {
			System.out.println(i.getId());
		}
		System.out.println("===============TEST DATA===============");
		
		
	}
	
	
	@Test
	@Disabled
	void test13() {
    	List<Sort.Order> sorts = new ArrayList<>();
    	//Sort.Order 객체로 구성된 리스트에 Sort.Order 객체를 추가하고 Sort.by(소트리스트)로 소트 객체를 생성
    	
        sorts.add(Sort.Order.desc("sell_count"));
		
		List<Item> itemList = this.itemRepository.findAll(Sort.by(sorts));
//		List<Item> itemList =
//		this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"sellCount"));
		System.out.println("===============TEST DATA===============");
		for(Item i : itemList) {
			System.out.println(i.getName()+"   "+i.getSell_count());
		}
		System.out.println("===============TEST DATA===============");
		
		
		
	}
	
	
}
