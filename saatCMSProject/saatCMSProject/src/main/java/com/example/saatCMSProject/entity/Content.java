package com.example.saatCMSProject.entity;

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
@Table(name = "content")
public class Content {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	
	@Column(name = "content_id")
	private int id;
	

	
	@ManyToMany()
	List<Content> licences;

	
	@Column(name = "name")
	private String name;
	
	@Column(name = "status")
	private boolean status;
	
	
	@Column(name = "poster_url")
	private String posterUrl;
	
	@Column(name = "video_url")
	private String videoUrl;
}
