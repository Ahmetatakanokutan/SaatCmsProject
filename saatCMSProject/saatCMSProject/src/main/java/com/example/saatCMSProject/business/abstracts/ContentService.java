package com.example.saatCMSProject.business.abstracts;

import java.util.List;

import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

public interface ContentService {

	DataResult<Content> getContentByid(int id);

	DataResult<List<Content>> getAll();

	Result addContent(ContentDto contentDto);

	Result deleteContent(String name);

	Result addLicenseToContent(String name, License license);

	Result updateContent(String contentName, ContentDto contentDto);

	void contentCheck(Content content);
}
