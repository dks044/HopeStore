package com.mysite.hope.cartItem;

import com.mysite.hope.cart.Cart;
import com.mysite.hope.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@ManyToOne
	private Item item;
	
	@ManyToOne
	private Cart cart;
	
	//주문수량
	private int count;
	
}
