package com.example.saatCMSProject.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "license")
public class License {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	
	@Column(name = "license_id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "ent_time")
	private Date endTime;
	
	@ManyToMany()
	List<Content> contents;
	
}
