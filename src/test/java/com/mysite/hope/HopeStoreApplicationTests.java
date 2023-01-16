package com.mysite.hope;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mysite.hope.category.Category;
import com.mysite.hope.category.CategoryRepository;
import com.mysite.hope.item.Item;
import com.mysite.hope.item.ItemRepository;
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
	
	//운영자 계정 생성 -성공
//	@Test
//	void adminCreateTest() {
//		SiteUser admin = new SiteUser();
//		admin.setUsername("admin");
//		String password = "1234";
//		admin.setPassword(passwordEncoder.encode(password));
//		admin.setPostcode("00000");
//		admin.setAddress("운영자의 집");
//		admin.setAddress_detail("운영자의 집 밑");
//		admin.setEmail("buzz781game@gmail.com");
//		admin.setPhone_number("010-0000-0000");
//		admin.setRole(UserRole.ADMIN);
//		this.userRepository.save(admin);
//
//	}
	
	//카테고리생성 -성공
	/*
	@Test
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
	*/
	
	//카테고리별 상품정렬
	@Transactional
	@Test
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
	

}
