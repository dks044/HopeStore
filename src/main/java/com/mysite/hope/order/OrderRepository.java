package com.mysite.hope.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.hope.user.SiteUser;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	Order findAllByUser(SiteUser user);
	
	
}
