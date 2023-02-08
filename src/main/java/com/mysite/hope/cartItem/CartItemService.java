package com.mysite.hope.cartItem;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.hope.DataNotFoundException;
import com.mysite.hope.cart.Cart;
import com.mysite.hope.item.Item;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {
	private final CartItemRepository cartItemRepository;
	
	public CartItem create(Cart cart,Item item,int count) {
		CartItem ci = new CartItem();
		ci.setCart(cart);
		ci.setItem(item);
		ci.setCount(count);
		return this.cartItemRepository.save(ci);
		
	}
	//카트에 해당아이템이 있다면 수량 1증가
	public void addCount(CartItem cartItem,int count) {
		int cct = cartItem.getCount();
		cct += count;
		cartItem.setCount(cct);
		
		this.cartItemRepository.save(cartItem);
	}
	
	//카트아이템 삭제
	public void delete(CartItem cartItem) {
		this.cartItemRepository.delete(cartItem);
	}
	
	//id값으로 cartItem 얻기
	public CartItem getCartItemById(Integer id) {
		Optional<CartItem> cartItem = this.cartItemRepository.findById(id);
		if(cartItem.isPresent()) {
			return cartItem.get();
		}else {
			throw new DataNotFoundException("CartItem not found");
		}
		
	}
	
	
	
}
