package com.mysite.hope.orderItem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.hope.order.Order;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
	Page<OrderItem> findAll(Pageable pageable);
	List<OrderItem> findAllByDepositStatus(int depositStatus);
	
	
	
}
