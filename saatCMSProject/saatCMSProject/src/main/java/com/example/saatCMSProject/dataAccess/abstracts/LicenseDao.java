package com.example.saatCMSProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saatCMSProject.entity.License;

public interface LicenseDao extends JpaRepository<License, Integer> {

	License findById(int id);
	License findByname(String name);

}
