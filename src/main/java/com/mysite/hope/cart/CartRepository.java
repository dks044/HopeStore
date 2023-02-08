package com.mysite.hope.cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.hope.cartItem.CartItem;
import com.mysite.hope.item.Item;
import com.mysite.hope.user.SiteUser;

public interface CartRepository extends JpaRepository<Cart, Integer>{
	Cart findAllByUser(SiteUser user);
	//List<Item> findItemListByUser(SiteUser user);
	//List<Item> findItemListById(int id);
	List<CartItem> findCartItemListByUser(SiteUser user);
}
