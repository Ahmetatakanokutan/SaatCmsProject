package com.example.saatCMSProject.dataAccess.abstracts;

import java.util.List;

import com.example.saatCMSProject.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.dtos.ContentDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentDao extends JpaRepository<Content, Long> , JpaSpecificationExecutor<Content> {

	List<Content> getByStatus(Status status);

}
