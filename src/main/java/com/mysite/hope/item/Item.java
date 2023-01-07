package com.mysite.hope.item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String name; //상품이름
	
	@Column(columnDefinition = "TEXT")
	private String text; //상품내용
	
	
	private int price; //가격
	private int stock; //재고
	
	private int isSoldOut; //판매여부 (0 = 판매 / 1 = 판매중)
	
	@Column(length = 150)
	private String filename;
	
	@Column(length = 300)
	private String filepath;
	
	private LocalDateTime createDate; //상품등록일
	
	
	
}
