package com.example.saatCMSProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.dtos.ContentDto;

public interface ContentDao extends JpaRepository<Content, Integer> {

	Content findByname(String name);
	Content findByid(int id);

}
