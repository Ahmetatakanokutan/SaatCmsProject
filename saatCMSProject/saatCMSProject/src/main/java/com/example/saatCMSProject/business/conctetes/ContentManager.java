package com.example.saatCMSProject.business.conctetes;

import com.example.saatCMSProject.business.abstracts.ContentService;
import com.example.saatCMSProject.core.results.*;
import com.example.saatCMSProject.dataAccess.abstracts.ContentDao;
import com.example.saatCMSProject.dataAccess.abstracts.LicenseDao;
import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import com.example.saatCMSProject.entity.enums.Status;
import com.example.saatCMSProject.entity.spesifications.ContentBuilder;
import com.example.saatCMSProject.entity.spesifications.ContentSpesification;
import com.example.saatCMSProject.entity.spesifications.SearchCriteria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.service.ContentSpecification;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        return new SuccessDataResult(contentDao.getById(id));

    }

    @Override
    public Result addContent(ContentDto contentDto) {
        Content content = modelMapper.map(contentDto, Content.class);
        content.setStatus(Status.InProgress);

        contentDao.save(content);
        return new SuccessResult();
    }

    @Override
    public DataResult<List<Content>> getAll(String search) {

        search = "and " + search +" ,";
        ContentBuilder contentBuilder = new ContentBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(\\s)(\\w+?)(:|<|>)(\\w+?)(\\s),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            contentBuilder.with(matcher.group(1), matcher.group(3), matcher.group(4) , matcher.group(5));
        }

        Specification<Content> spec = contentBuilder.build();
        return new SuccessDataResult<List<Content>>(this.contentDao.findAll(spec));
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
                if(content.getStatus() == Status.Published){

                }
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

                if (currentDate.isAfter(startTime) && currentDate.isBefore(endTime)){
                    content.setStatus(Status.Published);
                    contentDao.save(content);
                    return;
                }
                content.setStatus(Status.InProgress);
                contentDao.save(content);
            }
        }
    }


}
