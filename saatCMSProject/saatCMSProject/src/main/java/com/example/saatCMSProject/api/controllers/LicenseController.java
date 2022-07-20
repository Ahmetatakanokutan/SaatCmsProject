package com.example.saatCMSProject.api.controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.saatCMSProject.business.abstracts.LicenseService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

@RestController
@RequestMapping("api/licenses")
@CrossOrigin(origins = "http://localhost:4200/")
@AllArgsConstructor
public class LicenseController {
	private final LicenseService licenseService;


	@GetMapping
	DataResult<List<License>> getAll() {
		return licenseService.getAll();
	}

	@GetMapping("{id}")
	DataResult<License> getById(@RequestBody int id) {
		return licenseService.getById(id);
	}

	@PostMapping
	Result addLicense(@RequestBody LicenseDto licenseDto) {
		return licenseService.addLicense(licenseDto);
	}

	@DeleteMapping
	Result deleteLicense(@RequestBody long licenseId) {

		return licenseService.deleteLicense(licenseId);
	}
	
	@PutMapping
	Result UpdateLicense(@RequestBody LicenseDto licenseDto) {
		return licenseService.updateLicense(licenseDto);
	}

}
