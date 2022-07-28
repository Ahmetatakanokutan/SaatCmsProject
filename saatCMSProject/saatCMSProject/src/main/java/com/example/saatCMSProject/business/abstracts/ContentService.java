package com.example.saatCMSProject.business.abstracts;

import java.util.List;

import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.dtos.LicenseDto;
import org.springframework.web.bind.annotation.RequestParam;

public interface ContentService {

	DataResult<Content> getContentById(long id);

	DataResult<List<Content>> getAll(String search);

	Result addContent(ContentDto contentDto);

	Result deleteContent(long id);

	Result addLicenseToContent(long contentId, long licenseId);

	Result updateContent(ContentDto contentDto);

	//void contentCheck(Content content);
}
