package com.mysite.hope.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	//CartItem findByCartIdAndItemId();
	CartItem findByCartIdAndItemId(int id, int id2);
	
}
