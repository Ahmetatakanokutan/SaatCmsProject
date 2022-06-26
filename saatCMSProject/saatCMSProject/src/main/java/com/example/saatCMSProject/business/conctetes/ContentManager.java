package com.example.saatCMSProject.business.conctetes;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.ErrorResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessDataResult;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.dataAccess.abstracts.ContentDao;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;

@Service
public class ContentManager implements ContentService {

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
		content.setStatus("in progress");

		contentCheck(content);
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

	@Override
	public Result addLicenseToContent(String name, License license) {
		if (contentDao.findByname(name).getLicenses().contains(license)) {
			return new ErrorResult("you have same license");
		} else
			contentCheck(contentDao.findByname(name));
		return new SuccessResult("new license added successfully");

	}

	@Override
	public void contentCheck(Content content) {
		if (content.getLicenses() != null && content.getPosterUrl() != null && content.getVideoUrl() != null) {
			content.setStatus("Published");
		}
	}

	@Override
	public Result updateContent(String name, Content content) {

		Date currentDate = new Date();

		for (int i = 0; i < content.getLicenses().size(); i++) {

			if (addLicenseToContent(name, content.getLicenses().get(i)).isSuccess()) {
			}

			else {
				return addLicenseToContent(name, content.getLicenses().get(i));
			}

			if (content.getLicenses().get(i).getEndTime().before(currentDate)) {
				content.setStatus("in progress");
			}
		}
		return null;

	}

}
