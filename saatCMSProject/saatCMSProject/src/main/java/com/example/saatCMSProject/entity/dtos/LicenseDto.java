package com.example.saatCMSProject.entity.dtos;

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


	private int id;
	
	private String name;

	private Date startTime;

	private Date endTime;
	
}
