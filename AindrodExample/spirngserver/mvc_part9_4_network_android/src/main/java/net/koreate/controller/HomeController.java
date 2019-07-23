package net.koreate.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.koreate.vo.BoardVO;
import net.koreate.vo.TestVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	ServletContext context;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	// http://192.168.0.119/controller/testFile	
	@RequestMapping("testFile")
	@ResponseBody
	public ResponseEntity<byte[]> testFile(){
		ResponseEntity<byte[]> entity = null;
		InputStream is = null;
		try {
			String loadPath = context.getRealPath("/");
			is  = new FileInputStream(loadPath+"resources/test.json");
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.TEXT_PLAIN);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(is),header,HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(is != null) {is.close();}
			} catch (IOException e) {}
		}
		
		return entity;
	}
	// http://192.168.0.119/controller/testImageFile
	@RequestMapping("testImageFile")
	@ResponseBody
	public ResponseEntity<byte[]> testImageFile(){
		ResponseEntity<byte[]> entity = null;
		InputStream is = null;
		
		try {
			String loadPath = context.getRealPath("/");
			is = new FileInputStream(loadPath+"resources/1.jpg");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_PLAIN);
			entity = new ResponseEntity<byte[]>(
					IOUtils.toByteArray(is),
					headers,
					HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(is !=null)is.close();
			} catch (IOException e) {}
		}
		return entity;
	}
	
	
	
	@RequestMapping(value="/test",method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<TestVO> test(){
		ArrayList<TestVO> list = new ArrayList<>();
		for(int i=0; i<=10; i++) {
			list.add(new TestVO("id"+i,"pw"+i));	
		}
		return list;
	}
	
	
	@RequestMapping("/test1")
	@ResponseBody
	public Map<String,Object> test1(@RequestParam("title")String title){
		System.out.println("title : " + title);
		Map<String,Object> map = new HashMap<>();
		
		ArrayList<TestVO> list = new ArrayList<>();
		for(int i=0; i<=10; i++) {
			list.add(new TestVO("id"+i,"pw"+i));	
		}
		map.put("testList", list);
		
		ArrayList<BoardVO> boardList = new ArrayList<>();
		for(int i=1; i<=10; i++) {
			boardList.add(new BoardVO(
					i,
					"/resources/img/sample_"+i+".png",
					"content :"+i,
					"name"+i
					)
			);
		}
		map.put("boardList", boardList);
		return map;
	}
	
	@RequestMapping("imgUpload")
	@ResponseBody
	public ResponseEntity<String> 
	testImageUpload(String file,String fileName,String ext)
	throws Exception{
		ResponseEntity<String> entity = null;
		System.out.println(context.getRealPath("/"));
		File file1 = new File(context.getRealPath("/upload"));
		if(!file1.exists()) {
			file1.mkdirs();
		}
		
		File file2 = new File(file1.getPath(),fileName+"."+ext);
		
		byte[] bytes = Base64.decodeBase64(file);
		
		FileCopyUtils.copy(bytes, file2);
		
		String fullName = "/upload/"+fileName+"."+ext;
		entity = new ResponseEntity<String>(fullName,HttpStatus.CREATED);
		
		return entity;
	}
	
	
}









