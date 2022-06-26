package com.example.saatCMSProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saatCMSProject.entity.Content;

public interface ContentDao extends JpaRepository<Content, Integer> {
	Content findByname(String name);
}
