package com.example.saatCMSProject.entity.dtos;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseDto {


	private long id;
	
	private String name;

	private String startTime;

	private String endTime;
	
}
