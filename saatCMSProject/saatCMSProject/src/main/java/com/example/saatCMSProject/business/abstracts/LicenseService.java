package com.example.saatCMSProject.business.abstracts;

import java.util.List;

import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.entity.License;

public interface LicenseService {
	DataResult<List<License>> getAll();

	DataResult<License> getById(int id);

	Result addLicense(License license);

	Result deleteLicense(License license);
}
