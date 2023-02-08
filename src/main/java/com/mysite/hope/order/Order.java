package com.mysite.hope.order;

import java.util.List;


import com.mysite.hope.orderItem.OrderItem;
import com.mysite.hope.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	//주문자의 이름
	@OneToOne
	private SiteUser user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
	private List<OrderItem> orderItemList;
	
}
