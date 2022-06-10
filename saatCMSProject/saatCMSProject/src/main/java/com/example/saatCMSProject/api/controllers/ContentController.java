package com.example.saatCMSProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.Content;

@RestController
@RequestMapping("api/Content")
public class ContentController {
	
	ContentService contentService;
	
	@Autowired
	public ContentController(ContentService contentService) {
		super();
		this.contentService = contentService;
	}


	@PostMapping("add_content")
		Result addContent(Content Content){
			return contentService.addContent(Content);
			
		}
	

	@GetMapping("/get_content")
	DataResult<Content> getContent(int id) {
		return contentService.getContent(id);
		
	}
	
	@PostMapping("/delete_content")
	Result deleteContent(Content content) {
		return contentService.deleteContent(content);
		
	}
	
	@GetMapping("/get_all")
	DataResult<List<Content>> getAll(){
		return contentService.getAll();
	}

}
