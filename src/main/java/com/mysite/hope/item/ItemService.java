package com.mysite.hope.item;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
	private final ItemRepository itemRepository;
	
	// 상품 등록
    public void saveItem(Item item, MultipartFile file) throws Exception {

        String orifileName = file.getOriginalFilename();
        String fileName = "";

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        // UUID 를 이용하여 파일명 새로 생성
        // UUID - 서로 다른 객체들을 구별하기 위한 클래스
        UUID uuid = UUID.randomUUID();

        String savedFileName = uuid + "_" + orifileName; // 파일명 -> imgName

        fileName = savedFileName;

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        item.setFilename(fileName);
        item.setFilepath("/files/" + fileName);

        itemRepository.save(item);
    }
	
    
    
    
//	public void create(String name,String text, MultipartFile file, int price, int stock)
//		throws Exception 
//	
//	{
//		Item i = new Item();
//		i.setName(name);
//		i.setText(text);
//		
//		//TODO: MultipartFile file
//		String projectPath= System.getProperty("user.dir") + "\\src\\main\\resourecs\\static\\files";
//		//프로젝트 경로담기
//		
//		UUID uuid = UUID.randomUUID(); //범용식별자 랜덤으로 넣음.
//		
//		String fileName = uuid + "_" + file.getOriginalFilename(); //식별자_원래파일이름
//		
//		File saveFile = new File(projectPath,fileName);
//		
//		file.transferTo(saveFile);
//		
//		i.setFilename(fileName);
//		i.setFilepath("/files/" + fileName);
//		
//		i.setPrice(price);
//		i.setStock(stock);
//		i.setSoldOut(false);
//		i.setCreateDate(LocalDateTime.now());
//		this.itemRepository.save(i);
//	}
	
//	//파일없을경우
//	public void create(String name,String text, int price, int stock)
//			
//			
//	{
//		Item i = new Item();
//		i.setName(name);
//		i.setText(text);
//		i.setPrice(price);
//		i.setStock(stock);
//		i.setSoldOut(false);
//		i.setCreateDate(LocalDateTime.now());
//		this.itemRepository.save(i);
//	}
	
}
