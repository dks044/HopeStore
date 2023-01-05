package com.mysite.hope.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //고유번호
	
	private String name; //상품이름
	
	@Column(columnDefinition = "TEXT")
	private String text; //상품내용
	
	
	private int price; //가격
	private int stock; //재고
	
	private boolean isSoldOut; //판매여부
	
	private String filename;
	private String filepath;
	
	
	
}
