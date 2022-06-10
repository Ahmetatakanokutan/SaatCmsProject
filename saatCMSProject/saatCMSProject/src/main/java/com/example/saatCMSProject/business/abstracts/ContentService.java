package com.example.saatCMSProject.business.abstracts;

import java.util.List;

import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.Content;

public interface ContentService {

	DataResult<Content> getContent(int id);
	DataResult<List<Content>> getAll();
	Result addContent(Content content);
	Result deleteContent(Content content);
}
