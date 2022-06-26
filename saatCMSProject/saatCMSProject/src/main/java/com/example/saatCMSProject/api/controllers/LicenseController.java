package com.example.saatCMSProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saatCMSProject.business.abstracts.LicenseService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.entity.License;

@RestController
@RequestMapping("api/licenses")
public class LicenseController {
	LicenseService licenseService;

	@Autowired
	public LicenseController(LicenseService licenseService) {
		super();
		this.licenseService = licenseService;
	}

	@GetMapping("/getAll")
	DataResult<List<License>> getAll() {
		return licenseService.getAll();
	}

	@GetMapping("/getById")
	DataResult<License> getById(int id) {
		return licenseService.getById(id);
	}

	@PostMapping("/addLicense")
	Result addLicense(License license) {
		return licenseService.addLicense(license);
	}

	@PostMapping("/deleteLicense")
	Result deleteLicense(License license) {
		licenseService.deleteLicense(license);
		return new SuccessResult();
	}

}
