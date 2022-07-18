package com.example.saatCMSProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.saatCMSProject.business.abstracts.LicenseService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

@RestController
@RequestMapping("api/licenses")
@CrossOrigin(origins = "http://localhost:4200/")
public class LicenseController {
	LicenseService licenseService;

	@Autowired
	public LicenseController(LicenseService licenseService) {
		super();
		this.licenseService = licenseService;
	}

	@GetMapping("/get_all")
	DataResult<List<License>> getAll() {
		return licenseService.getAll();
	}

	@GetMapping("/get_by_id")
	DataResult<License> getById(int id) {
		return licenseService.getById(id);
	}

	@PostMapping("/add_license")
	Result addLicense(LicenseDto licenseDto) {
		return licenseService.addLicense(licenseDto);
	}

	@PostMapping("/delete_license")
	Result deleteLicense(String name) {
		licenseService.deleteLicense(name);
		return new SuccessResult();
	}
	
	@PostMapping("/update")
	Result UpdateLicense(String licenseName , LicenseDto licenseDto) {
		return licenseService.updateLicense(licenseName, licenseDto);
	}

}
