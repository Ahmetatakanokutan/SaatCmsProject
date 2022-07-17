package com.example.saatCMSProject.business.conctetes;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.ErrorResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessDataResult;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.dataAccess.abstracts.ContentDao;
import com.example.saatCMSProject.dataAccess.abstracts.LicenseDao;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

@Service
public class ContentManager implements ContentService {

	ContentDao contentDao;
	LicenseDao licenseDao;
	

	private final ModelMapper modelMapper;

	@Autowired
	public ContentManager(ContentDao contentDao , LicenseDao licenseDao) {
		super();
		this.modelMapper = new ModelMapper();
		this.contentDao = contentDao;
		this.licenseDao = licenseDao;

	}

	
	@Override
	public DataResult<Content> getContentByid(int id) {
		contentDao.findById(id);
		return new SuccessDataResult<>();
		
	}

	@Override
	public Result addContent(ContentDto contentDto) {
		Content content = modelMapper.map(contentDto, Content.class);
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
	public Result deleteContent(String name) {
		contentDao.delete(contentDao.findByname(name));
		return new SuccessResult();
	}

	@Override
	public Result addLicenseToContent(String name, License license) {
		
		//License license = modelMapper.map(licenseDto, License.class);
		
		if (contentDao.findByname(name).getLicenses().contains(license)) {
			return new ErrorResult("you have same license");
		} else
			contentCheck(contentDao.findByname(name));
		
		Content content = contentDao.findByname(name);
		//license.getContents().add(content);

		content.getLicenses().add(license);

		contentDao.save(content);
		//licenseDao.save(license);
		return new SuccessResult("new license added successfully");

	}

	@Override
	public void contentCheck(Content content) {
		if (content.getLicenses() != null && content.getPosterUrl() != null && content.getVideoUrl() != null) {
			content.setStatus("Published");
		}
	}

	@Override
	public Result updateContent(String contentName, ContentDto contentDto) {

		Content content = modelMapper.map(contentDto, Content.class);
		Date currentDate = new Date();
		content.setId(contentDao.findByname(contentName).getId()); 

		for (int i = 0; i < content.getLicenses().size(); i++) {

			if (addLicenseToContent(contentName, content.getLicenses().get(i)).isSuccess()) {
			}

			else {
				return addLicenseToContent(contentName, content.getLicenses().get(i));
			}

			if (content.getLicenses().get(i).getEndTime().before(currentDate)) {
				content.getLicenses().remove(i);
				content.setStatus("in progress");
				contentDao.save(content);
			}
		}
		
		return null;
		
		
	}

}
