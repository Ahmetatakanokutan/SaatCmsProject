package com.example.saatCMSProject.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.example.saatCMSProject.entity.dtos.LicenseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "license")
public class License extends LicenseDto{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	
	@Column(name = "license_id")
	private long id;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Nullable
	private List<Content> contents = new ArrayList<Content>();
	
	@Column(name = "name")
	@Nullable
	private String name;
	
	@Column(name = "start_time")
	@Nullable
	private String startTime;
	
	@Column(name = "ent_time")
	@Nullable
	private String endTime;
	

	
}
