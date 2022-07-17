package com.example.saatCMSProject.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int id;
	
	@Column(name = "name")
	@Nullable
	private String name;
	
	@Column(name = "start_time")
	@Nullable
	private Date startTime;
	
	@Column(name = "ent_time")
	@Nullable
	private Date endTime;
	
	@ManyToMany()
	@Nullable
	List<Content> contents;
	
}
