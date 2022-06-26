package com.example.saatCMSProject.business.conctetes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.saatCMSProject.business.abstracts.LicenseService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessDataResult;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.dataAccess.abstracts.LicenseDao;
import com.example.saatCMSProject.entity.License;

@Service
public class LicenseManager implements LicenseService {

	LicenseDao licenseDao;

	@Autowired
	public LicenseManager(LicenseDao licenseDao) {
		super();
		this.licenseDao = licenseDao;
	}

	public DataResult<List<License>> getAll() {
		return new SuccessDataResult<List<License>>(licenseDao.findAll());
	}

	public DataResult<License> getById(int id) {
		return new SuccessDataResult<License>(licenseDao.findById(id));
	}

	public Result addLicense(License license) {
		licenseDao.save(license);
		return new SuccessResult("license added successfully");
	}

	public Result deleteLicense(License license) {
		licenseDao.delete(license);
		return new SuccessResult("license deleted successfully");
	}
}
