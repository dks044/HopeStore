package com.mysite.hope.item;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.hope.category.Category;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	Item findAllById(int id);
	List<Item> findAllByCategory(Category category,Sort sort);
	List<Item> findAll(Sort sort);
}
