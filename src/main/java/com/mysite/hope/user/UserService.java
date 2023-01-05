package com.mysite.hope.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.hope.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	

    
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
    
    
}
