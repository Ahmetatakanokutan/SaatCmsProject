package com.example.saatCMSProject.business.conctetes;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.*;
import com.example.saatCMSProject.dataAccess.abstracts.ContentDao;
import com.example.saatCMSProject.dataAccess.abstracts.LicenseDao;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.enums.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public DataResult<Content> getContentById(long id) {
        return new SuccessDataResult<Content>(contentDao.getById(id));

    }

    @Override
    public Result addContent(ContentDto contentDto) {
        Content content = modelMapper.map(contentDto, Content.class);
        content.setStatus(Status.InProgress);

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
        Content content = contentDao.getById(contentId);
        if (content.getLicenses().contains(license)) {
            return new ErrorResult("you have same license");
        }
        content.getLicenses().add(license);

        contentDao.save(content);

        return new SuccessResult("new license added successfully");
    }

    @Override
    public Result updateContent(ContentDto contentDto) {
        Content currentContent = contentDao.getById(contentDto.getId());
        Content content = modelMapper.map(contentDto, Content.class);
        content.setStatus(currentContent.getStatus());
        content.setLicenses(currentContent.getLicenses());
        contentDao.save(content);

        return new SuccessResult("content updated successfully");
    }


    @Scheduled(cron = "30 * * * * ?")
    public void StartAndEndTimeController() {

        for (Content content : contentDao.getByStatus(Status.InProgress)) {
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
                    content.setStatus(Status.Published);
                    contentDao.save(content);
                    return;
                }
            }
        }
    }
    @Scheduled(cron = "00 * * * * ?")
    public void CheckPublished(){
        for (Content content:contentDao.getByStatus(Status.Published)) {
            for (License license: content.getLicenses()) {
                LocalDate startTime = LocalDate.parse(license.getStartTime());
                LocalDate endTime = LocalDate.parse(license.getEndTime());

                if (!(currentDate.isAfter(startTime) && currentDate.isBefore(endTime))){
                    content.setStatus(Status.InProgress);
                    contentDao.save(content);
                }
            }
        }
    }

}
