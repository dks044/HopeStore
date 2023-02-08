package com.mysite.hope.orderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.hope.DataNotFoundException;
import com.mysite.hope.item.Item;
import com.mysite.hope.order.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {
	private final OrderItemRepository orderItemRepository;
	
	
	
	public OrderItem create(Order order,Item item,int count,int price,String address, String address_detail,String postcode,
			String shipping_name
			) {
		OrderItem oi = new OrderItem();
		oi.setDepositStatus(1);//기본값(1=입금전)
		
		oi.setOrder(order);
		oi.setItem(item);
		oi.setCount(count);
		oi.setPrice(price);
		oi.setAddress(address);
		oi.setAddress_detail(address_detail);
		oi.setPostcode(postcode);
		oi.setShipping_name(shipping_name);
		oi.setOrderDate(LocalDateTime.now());
		this.orderItemRepository.save(oi);
		return oi;
	}
	
	public void delete(OrderItem orderItem) {
		this.orderItemRepository.delete(orderItem);
	}
	
	public OrderItem getOrderItemById(Integer id) {
		Optional<OrderItem> orderItem = this.orderItemRepository.findById(id);
		if(orderItem.isPresent()) {
			return orderItem.get();
		}else {
			throw new DataNotFoundException("CartItem not found");
		}
	}
	
	public Page<OrderItem> getOrderItemList(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("orderDate"));
		
		Pageable pageable = PageRequest.of(page, 5,Sort.by(sorts));
		return this.orderItemRepository.findAll(pageable);
	}
	
	//입금 입금확인 개수확인.
	public int getOrderItemDepositStatusSize(int depositStatus) {
		List<OrderItem> orderItemList = this.orderItemRepository.findAllByDepositStatus(depositStatus);
		int size = orderItemList.size();
		
		return size;
		
	}
	
	//입금확인처리
	public OrderItem depositChecked(OrderItem orderItem) {
		orderItem.setDepositStatus(2); //입금확인은 '2'로 규정했음.
		this.orderItemRepository.save(orderItem);
		
		return orderItem;
	}
	
	
	
	
	
}
