package com.mysite.hope.category;

import java.util.List;

import com.mysite.hope.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//1 = 상의 / 2 = 하의 / 3 = 아우터
	
	
	private String category_name;
	
	@OneToMany(mappedBy = "category")
	private List<Item> item_list;
	
	
}
