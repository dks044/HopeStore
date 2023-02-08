package com.mysite.hope.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.hope.item.Item;
import com.mysite.hope.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {
	private final CartRepository cartRepository;
	
	

	
	public Cart create(SiteUser user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return this.cartRepository.save(cart);
	}
	
	public Cart getBySiteUser(SiteUser user) {
		return this.cartRepository.findAllByUser(user);
	}
}
