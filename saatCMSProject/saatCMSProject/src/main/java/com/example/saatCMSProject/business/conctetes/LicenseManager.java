package com.example.saatCMSProject.business.conctetes;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.saatCMSProject.business.abstracts.LicenseService;
import com.example.saatCMSProject.core.results.DataResult;
import com.example.saatCMSProject.core.results.Result;
import com.example.saatCMSProject.core.results.SuccessDataResult;
import com.example.saatCMSProject.core.results.SuccessResult;
import com.example.saatCMSProject.dataAccess.abstracts.LicenseDao;
import com.example.saatCMSProject.entity.License;
import com.example.saatCMSProject.entity.dtos.LicenseDto;

@Service
public class LicenseManager implements LicenseService {

	LicenseDao licenseDao;
	private final ModelMapper modelMapper;

	@Autowired
	public LicenseManager(LicenseDao licenseDao) {
		super();
		this.modelMapper = new ModelMapper();
		this.licenseDao = licenseDao;
	}

	public DataResult<List<License>> getAll() {
		return new SuccessDataResult<List<License>>(licenseDao.findAll());
	}

	public DataResult<License> getById(int id) {
		return new SuccessDataResult<License>(licenseDao.findById(id));
	}

	public Result addLicense(LicenseDto licenseDto) {
		
		License license = modelMapper.map(licenseDto, License.class);
		String endDate = license.getEndTime();
		licenseDao.save(license);
		return new SuccessResult("license added successfully");
	}

	public Result deleteLicense(String name) {
		licenseDao.delete(licenseDao.findByname(name));
		return new SuccessResult("license deleted successfully");
	}

	@Override
	public Result updateLicense(String licenseName, LicenseDto licenseDto) {
		
		License license = modelMapper.map(licenseDto, License.class);
		
		license.setId(licenseDao.findByname(licenseName).getId());
		licenseDao.save(license);
		return new SuccessResult("license updated successfully");
	}
}
