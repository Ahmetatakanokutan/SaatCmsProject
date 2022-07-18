package com.example.saatCMSProject.business.conctetes;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
@EnableScheduling
public class ContentManager implements ContentService {

	ContentDao contentDao;
	LicenseDao licenseDao;
	LocalDate currentDate = LocalDate.now() ;
	

	private final ModelMapper modelMapper;

	@Autowired
	public ContentManager(ContentDao contentDao , LicenseDao licenseDao) {
		super();
		this.modelMapper = new ModelMapper();
		this.contentDao = contentDao;
		this.licenseDao = licenseDao;

	}

	
	@Override
	public DataResult <Content> getContentByid(int id) {
		;
		return new SuccessDataResult <Content>(contentDao.findByid(id));
		
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
	public Result addLicenseToContent(String contentName, String licenseName) {
		
		License license = licenseDao.findByname(licenseName);
		
		if (contentDao.findByname(contentName).getLicenses().contains(license)) {
			return new ErrorResult("you have same license");
		} else
			contentCheck(contentDao.findByname(contentName));
		
		Content content = contentDao.findByname(contentName);
		content.getLicenses().add(license);

		contentDao.save(content);

		return new SuccessResult("new license added successfully");

	}

	@Override
	public void contentCheck(Content content) {
		
		if (content.getLicenses() != null && content.getPosterUrl() != null && content.getVideoUrl() != null) {
			content.setStatus("Published");
		}
		else
			content.setStatus("in progress");
	}

	@Override
	public Result updateContent(String contentName, ContentDto contentDto) {

		Content content = modelMapper.map(contentDto, Content.class);
		
		
		content.setId(contentDao.findByname(contentName).getId());
		content.setLicenses(contentDao.findByname(contentName).getLicenses());
		contentDao.save(content);

		return new SuccessResult("content upadted successfully");
		
		
	}
	
	@Scheduled(fixedRate = 10000)
	public void StartAndEndTimeController() {
		
		for (Content content : contentDao.findAll()) {
			for (License license : content.getLicenses()) {
				
				LocalDate startTime = LocalDate.parse(license.getStartTime());
				LocalDate endTime = LocalDate.parse(license.getEndTime());
				
				if(currentDate.isAfter(startTime) && currentDate.isBefore(endTime)) {
					contentCheck(content);	
				}
				else
					content.setStatus("in progress");
		}
			contentDao.save(content);
		}
	}

}
