package com.example.saatCMSProject.business.conctetes;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

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

    private final ContentDao contentDao;
    private final LicenseDao licenseDao;
    private final LocalDate currentDate = LocalDate.now();


    private final ModelMapper modelMapper;

    @Autowired
    public ContentManager(ContentDao contentDao, LicenseDao licenseDao) {
        super();
        this.modelMapper = new ModelMapper();
        this.contentDao = contentDao;
        this.licenseDao = licenseDao;

    }


    @Override
    public DataResult<Content> getContentByid(long id) {
        return new SuccessDataResult<Content>(contentDao.getById(id));

    }

    @Override
    public Result addContent(ContentDto contentDto) {
        Content content = modelMapper.map(contentDto, Content.class);
        content.setStatus("in progress");
        content.getLicenses().clear();

        contentDao.save(content);
        return new SuccessResult();
    }

    @Override
    public DataResult<List<Content>> getAll() {
        return new SuccessDataResult<List<Content>>(this.contentDao.findAll());
    }

    @Override
    public Result deleteContent(long id) {
        contentDao.delete(contentDao.getById(id));
        return new SuccessResult();
    }

    @Override
    public Result addLicenseToContent(long contentId, long licenseId) {

        License license = licenseDao.findById(licenseId);

        if (contentDao.getById(contentId).getLicenses().contains(license)) {
            return new ErrorResult("you have same license");
        }

        Content content = contentDao.getById(contentId);
        content.getLicenses().add(license);

        contentDao.save(content);

        return new SuccessResult("new license added successfully");

    }



    @Override
    public Result updateContent(ContentDto contentDto) {

        Content content = modelMapper.map(contentDto, Content.class);
        content.getLicenses().clear();
        content.setLicenses(contentDao.getById(contentDto.getId()).getLicenses());
        contentDao.save(content);

        return new SuccessResult("content updated successfully");


    }

    @Scheduled(fixedRate = 1000)
    public void StartAndEndTimeController() {

        for (Content content : contentDao.getByStatus("in progress")) {
            if (content.getVideoUrl() == null) {
                continue;
            }
            if (content.getPosterUrl() == null) {
                continue;
            }

            for (License license : content.getLicenses()) {
                LocalDate startTime = LocalDate.parse(license.getStartTime());
                LocalDate endTime = LocalDate.parse(license.getEndTime());
                if (currentDate.isAfter(startTime) && currentDate.isBefore(endTime)) {
                    content.setStatus("Published");
                    contentDao.save(content);
                    return;
                }
            }
        }
    }

}
