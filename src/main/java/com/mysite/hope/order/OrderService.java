package com.mysite.hope.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mysite.hope.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	
	
	public Order create(SiteUser user) {
		Order order = new Order();
		order.setUser(user);
		
		
		return this.orderRepository.save(order);
	}
	
	//user로 order 조회
	public Order getOrderByUser(SiteUser user) {	
		return this.orderRepository.findAllByUser(user);
	}
	

	
	
	
}
