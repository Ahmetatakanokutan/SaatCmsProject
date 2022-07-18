package com.example.saatCMSProject.business.abstracts;

import java.util.List;

import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

public interface LicenseService {
	DataResult<List<License>> getAll();

	DataResult<License> getById(int id);

	Result addLicense(LicenseDto licenseDto);

	Result deleteLicense(String name);
	
	Result updateLicense(String licenseName , LicenseDto licenseDto);
}
