package com.example.saatCMSProject.business.conctetes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessDataResult;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.dataAccess.abstracts.ContentDao;
import com.example.saatCMSProject.entity.Content;

@Service
public class ContentManager implements ContentService{

	ContentDao contentDao;
	
	@Autowired
	public ContentManager(ContentDao contentDao) {
		super();
		this.contentDao = contentDao;
	}

	@Override
	public DataResult<Content> getContent(int id) {
		contentDao.findById(id);
		return new SuccessDataResult<>();
	}

	@Override
	public Result addContent(Content content) {
		contentDao.save(content);
		return new SuccessResult();
	}

	@Override
	public DataResult<List<Content>> getAll() {
		return new SuccessDataResult<List<Content>>(this.contentDao.findAll());
		
	}

	@Override
	public Result deleteContent(Content content) {
		contentDao.delete(content);
		return new SuccessResult();
	}

}
