package com.mysite.hope;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	//운영자 계정 생성
	@Test
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
	
//	@Test
//	void test() {
//		SiteUser user = this.userRepository.findAllByusername("운영자1");
//		System.out.println("========TEST DATA=======");
//		System.out.println(user.getUsername());
//		System.out.println(user.getRole());
//		System.out.println("========TEST DATA=======");
//	}

}
