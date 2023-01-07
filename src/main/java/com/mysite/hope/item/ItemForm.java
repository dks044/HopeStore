package com.mysite.hope.item;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {
	@NotBlank(message = "상품제목은 필수 항목입니다.")
	private String name;
	
	private String text;
	
	
	private int price;
	private int stock;
	
	private MultipartFile file;
	
	
	
	
	
}
