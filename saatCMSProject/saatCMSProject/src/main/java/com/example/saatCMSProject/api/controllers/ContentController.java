package com.example.saatCMSProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

@RestController
@RequestMapping("api/Content")
@CrossOrigin(origins = "http://localhost:4200/")
public class ContentController {

	ContentService contentService;

	@Autowired
	public ContentController(ContentService contentService) {
		super();
		this.contentService = contentService;
	}

	@PostMapping("add_content")
	Result addContent(ContentDto contentDto) {
		return contentService.addContent(contentDto);

	}

	@GetMapping("/get_content_by_id")
	DataResult<Content> getContentByid(int id) {
		return contentService.getContentByid(id);

	}

	@PostMapping("/delete_content")
	Result deleteContent(String name) {
		return contentService.deleteContent(name);

	}

	@GetMapping("/get_all")
	DataResult<List<Content>> getAll() {
		return contentService.getAll();
	}
	
	@PostMapping("/addLicenseToContent")
	Result addLicenseToContent(String contentName , License license) {

		return contentService.addLicenseToContent(contentName, license);
	}

	@PostMapping("/update")
	Result updateContent(String contentName, ContentDto contentDto) {
		return contentService.updateContent(contentName, contentDto);

	}

}
