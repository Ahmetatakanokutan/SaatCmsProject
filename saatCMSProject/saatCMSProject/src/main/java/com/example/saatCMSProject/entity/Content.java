package com.example.saatCMSProject.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;

import com.example.saatCMSProject.entity.dtos.ContentDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "content")
public class Content  {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id

	@Column(name = "content_id")
	
	private int id;


	@ManyToMany
	@JoinTable(
	  joinColumns = @JoinColumn(name = "content_id"), 
	  inverseJoinColumns = @JoinColumn(name = "license_id"))
	@Nullable
	private List<License> licenses;

	@Column(name = "name")
	@Nullable
	private String name;

	@Column(name = "status")
	@Nullable
	private String status;

	@Column(name = "poster_url")
	@Nullable
	private String posterUrl;

	@Column(name = "video_url")
	@Nullable
	private String videoUrl;
}
