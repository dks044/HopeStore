package com.mysite.hope.cart;

import java.util.List;

import com.mysite.hope.cartItem.CartItem;
import com.mysite.hope.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	//카트 유저의 이름
	@OneToOne
	private SiteUser user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
	private List<CartItem> cartItemList;
	
	
}
