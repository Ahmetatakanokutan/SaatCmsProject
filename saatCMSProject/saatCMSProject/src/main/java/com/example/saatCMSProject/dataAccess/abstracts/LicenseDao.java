package com.example.saatCMSProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.saatCMSProject.entity.License;

public interface LicenseDao extends JpaRepository<License, Long> {

	License findById(long id);
}
