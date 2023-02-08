package com.mysite.hope.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.hope.cart.Cart;
import com.mysite.hope.cart.CartRepository;
import com.mysite.hope.order.Order;
import com.mysite.hope.order.OrderRepository;
import com.mysite.hope.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	private final OrderRepository orderRepository;
	private final CartRepository cartRepository;
    
    
    //회원생성
    public SiteUser create(	   
    				       String username,
    					   String password,
    					   String email,
    					   String postcode,
    					   String address,
    					   String address_detail,
    					   String phone_number
    					   ) {
    	SiteUser user = new SiteUser();
    	user.setUsername(username);
    	//BCryptPasswordEncoder 객체를 직접 생성하여 사용하지 않고 빈으로 등록한 PasswordEncoder 객체를 주입받아 사용
    	user.setPassword(passwordEncoder.encode(password));
    	
    	user.setEmail(email);
    	user.setPostcode(postcode);
    	user.setAddress(address);
    	user.setAddress_detail(address_detail);
    	user.setPhone_number(phone_number);
    	user.setRole(UserRole.USER); //가입시 일반유저
    	this.userRepository.save(user);
    	return user;
    }
    public Optional<SiteUser> getByUserName(String username) {
    	Optional<SiteUser> user = this.userRepository.findByusername(username);
    	if(user.isPresent()) {
    		return user;
    	}else {
    		throw new com.mysite.hope.DataNotFoundException("SiteUser Not found");
    	}
    }
    
    //삭제할떄 order와 cart에서도 다 삭제되게
    //memo: 그냥 회원탈퇴 .delete 메서드를 사용하면 안되고, 관련된 엔티티에 있는 컬럼도 제거해야 하는 중요한걸 배웠다..
    public void deleteMember(SiteUser user) {
    	
    	Order order = this.orderRepository.findAllByUser(user);
    	this.orderRepository.delete(order);
    	Cart cart = this.cartRepository.findAllByUser(user);
    	this.cartRepository.delete(cart);
    	
    	if(order == null) {
    		this.userRepository.delete(user);
    	}
    	if(cart == null) {
    		this.userRepository.delete(user);
    	}
    	if(cart == null || order == null) {
    		this.userRepository.delete(user);
    	}
    	if(cart == null && order == null) {
    		this.userRepository.delete(user);
    	}
    	
    	this.userRepository.delete(user);
    }
    
}
