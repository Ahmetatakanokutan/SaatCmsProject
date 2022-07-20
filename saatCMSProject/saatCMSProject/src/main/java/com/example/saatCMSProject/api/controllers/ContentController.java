package com.example.saatCMSProject.api.controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

@RestController
@RequestMapping("api/contents")
@CrossOrigin(origins = "http://localhost:4200/")
@AllArgsConstructor
public class ContentController {

	private final ContentService contentService;

	@PostMapping
	Result addContent(@RequestBody ContentDto contentDto) {
		return contentService.addContent(contentDto);

	}

	@GetMapping("/{id}")
	DataResult<Content> getContentByid(@RequestBody int id) {
		return contentService.getContentByid(id);

	}

	@DeleteMapping
	Result deleteContent(@RequestBody String name) {
		return contentService.deleteContent(name);

	}

	@GetMapping
	DataResult<List<Content>> getAll() {
		return contentService.getAll();
	}
	
	@PostMapping("{contentId}/licenses/{licenseId}/add")
	Result addLicenseToContent(@PathVariable("contentId") Long contentId,
							   @PathVariable("licenseId") Long licenseId) {

		return contentService.addLicenseToContent(contentId, licenseId);
	}

	@PutMapping
	Result updateContent(@RequestBody ContentDto contentDto) {
		return contentService.updateContent(contentDto);

	}

}
